package com.ess.essserver.module.invoice;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class InvoiceResponseDTO {

    private Long id;
    private String eventName;
    private Long bookingId;
    private BigDecimal amount;
    private String paymentStatus;
    private LocalDateTime issuedOn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}