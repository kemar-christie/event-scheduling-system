package com.ess.essserver.module.booking;

import com.ess.essserver.module.asset.AssetEntity;
import com.ess.essserver.module.asset.AssetRepository;
import com.ess.essserver.module.event.EventEntity;
import com.ess.essserver.module.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final AssetRepository assetRepository;
    private final BookingMapper bookingMapper;

    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BookingResponseDTO getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
    }

    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        EventEntity event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("event not found with ID: " + dto.getEventId()));

        AssetEntity asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found with ID: " + dto.getAssetId()));

        BookingEntity booking = bookingMapper.toEntity(dto, event, asset);
        booking = bookingRepository.save(booking);

        return bookingMapper.toResponseDTO(booking);
    }

    public BookingResponseDTO updateBooking(Long id, BookingRequestDTO dto) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));

        EventEntity event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("event not found with ID: " + dto.getEventId()));

        AssetEntity asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found with ID: " + dto.getAssetId()));

        booking.setEvent(event);
        booking.setAsset(asset);
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());

        booking = bookingRepository.save(booking);

        return bookingMapper.toResponseDTO(booking);
    }

    public void deleteBooking(Long id) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + id));
        bookingRepository.delete(booking);
    }
}