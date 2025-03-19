package com.ess.essserver.module.booking;

import com.ess.essserver.module.asset.AssetEntity;
import com.ess.essserver.module.event.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingEntity toEntity(BookingRequestDTO dto, EventEntity event, AssetEntity asset) {
        return BookingEntity.builder()
                .event(event)
                .asset(asset)
                .startTime(dto.getStartDate())
                .endTime(dto.getEndDate())
                .build();
    }

    public BookingResponseDTO toResponseDTO(BookingEntity entity) {
        return BookingResponseDTO.builder()
                .id(entity.getId())
                .assetId(entity.getAsset().getId())
                .eventId(entity.getEvent().getId())
                .eventName(entity.getEvent().getName())
                .assetName(entity.getAsset().getName())
                .startDate(entity.getStartTime())
                .endDate(entity.getEndTime())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}