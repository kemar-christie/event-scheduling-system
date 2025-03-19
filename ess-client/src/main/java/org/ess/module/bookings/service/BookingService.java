package org.ess.module.bookings.service;

import org.ess.app.common.HttpClient;
import org.ess.module.bookings.model.BookingModel;
import org.ess.module.bookings.repository.BookingRepository;
import retrofit2.Callback;

import java.util.List;

public class BookingService {

    public void get(Callback<List<BookingModel>> callback) {
        HttpClient.use(BookingRepository.class).get().enqueue(callback);
    }

    public void post(BookingModel bookingModel, Callback<BookingModel> callback) {
        HttpClient.use(BookingRepository.class).post(bookingModel).enqueue(callback);
    }

    public void get(long id, Callback<BookingModel> callback) {
        HttpClient.use(BookingRepository.class).get(id).enqueue(callback);
    }

    public void put(BookingModel bookingModel, Callback<BookingModel> callback) {
        HttpClient.use(BookingRepository.class).put(bookingModel.getId(), bookingModel).enqueue(callback);
    }

    public void delete(long id, Callback<Boolean> callback) {
        HttpClient.use(BookingRepository.class).delete(id).enqueue(callback);
    }
}
