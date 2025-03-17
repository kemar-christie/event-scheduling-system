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
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    public BookingResponseDTO toResponseDTO(BookingEntity entity) {
        return BookingResponseDTO.builder()
                .id(entity.getId())
                .eventName(entity.getEvent().getName())
                .assetName(entity.getAsset().getName())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}