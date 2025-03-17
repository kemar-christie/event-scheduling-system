package com.ess.essserver.module.asset.price;

import com.ess.essserver.module.asset.AssetEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AssetPriceMapper {

    public AssetPriceEntity toEntity(AssetEntity assetEntity, AssetPriceRequestDTO dto) {
        return AssetPriceEntity.builder()
                .asset(assetEntity)
                .price(dto.getPrice())
                .status(dto.getStatus())
                .build();
    }

    public AssetPriceResponseDTO toResponseDTO(AssetPriceEntity entity) {
        return AssetPriceResponseDTO.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}