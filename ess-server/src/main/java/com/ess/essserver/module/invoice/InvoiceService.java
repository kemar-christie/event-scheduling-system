package com.ess.essserver.module.invoice;

import com.ess.essserver.module.booking.BookingEntity;
import com.ess.essserver.module.booking.BookingRepository;
import com.ess.essserver.module.event.EventEntity;
import com.ess.essserver.module.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final InvoiceMapper invoiceMapper;

    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public InvoiceResponseDTO getInvoiceById(Long id) {
        InvoiceEntity invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));
        return invoiceMapper.toResponseDTO(invoice);
    }

    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto) {
        EventEntity event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("event not found with ID: " + dto.getEventId()));

        BookingEntity booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + dto.getBookingId()));

        InvoiceEntity invoice = invoiceMapper.toEntity(dto, event, booking);
        invoice = invoiceRepository.save(invoice);

        return invoiceMapper.toResponseDTO(invoice);
    }

    public InvoiceResponseDTO updateInvoice(Long id, InvoiceRequestDTO dto) {
        InvoiceEntity invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));

        EventEntity event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("event not found with ID: " + dto.getEventId()));

        BookingEntity booking = bookingRepository.findById(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + dto.getBookingId()));

        invoice.setEvent(event);
        invoice.setBooking(booking);
        invoice.setAmount(dto.getAmount());
        invoice.setPaymentStatus(dto.getPaymentStatus());
        invoice.setIssuedOn(dto.getIssuedOn());

        invoice = invoiceRepository.save(invoice);

        return invoiceMapper.toResponseDTO(invoice);
    }

    public void deleteInvoice(Long id) {
        InvoiceEntity invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with ID: " + id));
        invoiceRepository.delete(invoice);
    }
}