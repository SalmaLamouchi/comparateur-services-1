package com.programming.microservices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationListener {

    @KafkaListener(topics = "notificationTopic", groupId = "notificationGroup")
    public void handleNotification(OrderPlacedEvent event) {
        // This method executes whenever a message arrives on the topic
        log.info("📩 Received Notification for Order - {}", event.getOrderNumber());
        // you could send an email, SMS, or push notification here
    }
}
