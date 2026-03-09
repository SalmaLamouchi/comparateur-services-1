package com.micorservices.providerservice.service;
import com.micorservices.providerservice.dto.AvailabilityDto;
import com.micorservices.providerservice.model.Availability;
import com.micorservices.providerservice.repository.AvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    @Override
    public AvailabilityDto create(AvailabilityDto dto) {
        Availability saved = availabilityRepository.save(
            Availability.builder()
                .startDateTime(dto.startDateTime())
                .endDateTime(dto.endDateTime())
                .maxSimultaneousServices(dto.maxSimultaneousServices())
                .userId(dto.userId())
                .build()
        );
        return toDto(saved);
    }

    @Override
    public AvailabilityDto update(Long id, AvailabilityDto dto) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        availability.setStartDateTime(dto.startDateTime());
        availability.setEndDateTime(dto.endDateTime());
        availability.setMaxSimultaneousServices(dto.maxSimultaneousServices());
        availability.setUserId(dto.userId());

        return toDto(availabilityRepository.save(availability));
    }

    @Override
    public void delete(Long id) {
        if (!availabilityRepository.existsById(id))
            throw new RuntimeException("Availability not found");
        availabilityRepository.deleteById(id);
    }

    @Override
    public AvailabilityDto getById(Long id) {
        return availabilityRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Availability not found"));
    }

    @Override
    public List<AvailabilityDto> getByUserId(Long userId) {
        return availabilityRepository.findByUserId(userId)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AvailabilityDto> getAvailableSlotsByUserId(Long userId) {
        return availabilityRepository.findByUserIdAndIsBookedFalse(userId)
                .stream().map(this::toDto)
                .collect(Collectors.toList());
    }

    private AvailabilityDto toDto(Availability a) {
        return new AvailabilityDto(
            a.getId(),
            a.getStartDateTime(),
            a.getEndDateTime(),
            a.getMaxSimultaneousServices(),
            a.getCurrentBookingsCount(),
            a.isBooked(),
            a.getUserId()
        );
    }
}