package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.InventoryResponse;
import com.microservices.orderservice.dto.OrderLineItemsDto;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderLineItems;
import com.microservices.orderservice.repository.OrderRepository;
import com.programming.microservices.OrderPlacedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    // Micrometer Tracing beans (already auto-configured with the deps we discussed)
    private final Tracer tracer;
    private final ObservationRegistry observationRegistry;

    public String placeOrder(OrderRequest orderRequest) {
        // High-level observation for this business action
        Observation obs = Observation.createNotStarted("order.place", observationRegistry)
                .lowCardinalityKeyValue("items.count", String.valueOf(
                        orderRequest.getOrderLineItemsDtoList().size()))
                .start();

        try {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());

            List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                    .stream()
                    .map(this::mapToDto)
                    .toList();
            order.setOrderLineItemsList(orderLineItems);

            List<String> skuCodes = order.getOrderLineItemsList()
                    .stream().map(OrderLineItems::getSkuCode).toList();

            log.info("Calling inventory service for SKUs {}", skuCodes);

            // Child span focused on the remote call
            Span invLookup = tracer.nextSpan().name("inventory.lookup");
            try (Tracer.SpanInScope ws = tracer.withSpan(invLookup.start())) {
                InventoryResponse[] inventoryResponseArray = webClientBuilder.build()
                        .get()
                        .uri("http://inventory-service/api/inventory",
                                uri -> uri.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .timeout(Duration.ofSeconds(5)) // optional: avoid very long waits
                        .block();

                boolean allProductsInStock = Arrays.stream(
                        Objects.requireNonNull(inventoryResponseArray, "inventory response is null"))
                        .allMatch(InventoryResponse::isInStock);

                if (allProductsInStock) {
                    orderRepository.save(order);
                    kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                    log.info("Published Kafka event for order {}", order.getOrderNumber());
                    return "Order Placed Successfully";
                } else {
                    throw new IllegalArgumentException("Product is not in stock, please try again later");
                }

            } finally {
                invLookup.end();
            }
        } catch (RuntimeException ex) {
            // The current span/observation will be tagged with error by Micrometer
            // automatically,
            // but an explicit log helps during debugging.
            log.error("Failed to place order", ex);
            throw ex;
        } finally {
            obs.stop();
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
