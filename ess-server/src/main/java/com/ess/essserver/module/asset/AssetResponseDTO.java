package com.ess.essserver.module.asset;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class AssetResponseDTO {
    private Long id;
    private String name;
    private String category;
    private Integer quantity;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
