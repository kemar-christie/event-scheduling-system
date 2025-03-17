package com.ess.essserver.module.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingRequestDTO {

    @NotNull(message = "event ID is required")
    private Long eventId;

    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
}