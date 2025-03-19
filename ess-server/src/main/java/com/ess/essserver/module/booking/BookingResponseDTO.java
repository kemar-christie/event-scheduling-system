package com.ess.essserver.module.booking;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponseDTO {

    private Long id;
    private Long eventId;
    private Long assetId;
    private String eventName;
    private String assetName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}