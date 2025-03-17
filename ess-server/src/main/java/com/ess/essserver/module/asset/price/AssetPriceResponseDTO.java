package com.ess.essserver.module.asset.price;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AssetPriceResponseDTO {

    private Long id;
    private Integer price;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}