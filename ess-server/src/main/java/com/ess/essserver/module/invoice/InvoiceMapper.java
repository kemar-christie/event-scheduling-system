package com.ess.essserver.module.invoice;

import com.ess.essserver.module.booking.BookingEntity;
import com.ess.essserver.module.event.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    public InvoiceEntity toEntity(InvoiceRequestDTO dto, EventEntity event, BookingEntity booking) {
        return InvoiceEntity.builder()
                .event(event)
                .booking(booking)
                .amount(dto.getAmount())
                .paymentStatus(dto.getPaymentStatus())
                .issuedOn(dto.getIssuedOn())
                .build();
    }

    public InvoiceResponseDTO toResponseDTO(InvoiceEntity entity) {
        return InvoiceResponseDTO.builder()
                .id(entity.getId())
                .eventName(entity.getEvent().getName())
                .bookingId(entity.getBooking().getId())
                .amount(entity.getAmount())
                .paymentStatus(entity.getPaymentStatus())
                .issuedOn(entity.getIssuedOn())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
