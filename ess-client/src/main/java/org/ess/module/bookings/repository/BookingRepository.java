package org.ess.module.bookings.repository;

import org.ess.module.bookings.model.BookingModel;
import org.ess.module.event.model.EventModel;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BookingRepository {

    @GET("/api/bookings")
    Call<List<BookingModel>> get();

    @POST("/api/bookings")
    Call<BookingModel> post(@Body BookingModel bookingModel);

    @GET("/api/bookings/{id}")
    Call<BookingModel> get(@Path("id") long id);

    @PUT("/api/bookings/{id}")
    Call<BookingModel> put(@Path("id") long id, @Body BookingModel bookingModel);

    @DELETE("/api/bookings/{id}")
    Call<Boolean> delete(@Path("id") long id);
}
