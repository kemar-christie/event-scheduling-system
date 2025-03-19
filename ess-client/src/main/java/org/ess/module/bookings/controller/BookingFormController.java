package org.ess.module.bookings.controller;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ess.app.common.FormMode;
import org.ess.module.asset.model.AssetModel;
import org.ess.module.asset.service.AssetService;
import org.ess.module.bookings.event.BookingEvent;
import org.ess.module.bookings.model.BookingModel;
import org.ess.module.bookings.service.BookingService;
import org.ess.module.event.model.EventModel;
import org.ess.module.event.service.EventService;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BookingFormController implements Initializable {

    @FXML
    protected VBox fxRoot;

    @FXML
    protected ComboBox<Long> fxBookingEvent;

    @FXML
    protected ComboBox<Long> fxBookingAsset;

    @FXML
    protected DatePicker fxBookingStartDate;

    @FXML
    protected DatePicker fxBookingEndDate;

    private final EventService eventService = new EventService();
    private final AssetService assetService = new AssetService();
    private final BookingService bookingService = new BookingService();

    private final Logger logger = LogManager.getLogger(BookingFormController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Initialize");

        Platform.runLater(() -> {

            assetService.get(new Callback<>() {
                @Override
                public void onResponse(@NotNull Call<List<AssetModel>> call, @NotNull Response<List<AssetModel>> response) {
                    logger.info("Get Asset Success {} {}", response.code(), response.message());
                    if (response.isSuccessful() && response.body() != null) {
                        fxBookingAsset.getItems().clear();
                        fxBookingAsset.getItems().addAll(response.body().stream().map(AssetModel::getId).toList());
                        if (getFormMode().equals(FormMode.EDIT)) {
                            BookingModel bookingModel = getBookingModelFromData();
                            fxBookingAsset.setValue(bookingModel.getAssetId());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<AssetModel>> call, @NotNull Throwable throwable) {
                    logger.info("{} {}", throwable.getMessage(), throwable.getCause());
                }
            });

            eventService.get(new Callback<>() {
                @Override
                public void onResponse(@NotNull Call<List<EventModel>> call, @NotNull Response<List<EventModel>> response) {
                    logger.info("{} {}", response.code(), response.message());

                    if (response.isSuccessful() && response.body() != null) {
                        fxBookingEvent.getItems().clear();
                        fxBookingEvent.getItems().addAll(response.body().stream().map(EventModel::getId).toList());
                        if (getFormMode().equals(FormMode.EDIT)) {
                            BookingModel bookingModel = getBookingModelFromData();
                            fxBookingEvent.setValue(bookingModel.getEventId());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<EventModel>> call, @NotNull Throwable throwable) {
                    logger.info("{} {}", throwable.getMessage(), throwable.getCause());
                }
            });

            if (getFormMode().equals(FormMode.EDIT)) {
                BookingModel bookingModel = getBookingModelFromData();
                fxBookingEvent.setValue(bookingModel.getEventId());
                fxBookingAsset.setValue(bookingModel.getAssetId());
                fxBookingStartDate.setValue(LocalDate.parse(bookingModel.getStartDate()));
                fxBookingEndDate.setValue(LocalDate.parse(bookingModel.getEndDate()));
            }
        });
    }

    @FXML
    void onCancelBookingForm(MouseEvent event) {
        logger.info("Cancel Booking Form");
        Platform.runLater(() -> fxRoot.getScene().getWindow().hide());
    }

    @FXML
    void onSubmitBookingForm(MouseEvent event) {
        logger.info("onSubmitAssetForm");

        if (getFormMode().equals(FormMode.CREATE)) {
            createBooking();
        } else {
            updateBooking();
        }
    }

    private void createBooking() {
        bookingService.post(getBookingModelFromForm(), new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<BookingModel> call, @NotNull Response<BookingModel> response) {
                logger.info("Create Booking Success {} {}", response.code(), response.message());
                if (response.isSuccessful()) {
                    Platform.runLater(() -> fxRoot.getScene().getWindow().hide());
                    BookingEvent.subject.onNext(BookingEvent.Type.CREATE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<BookingModel> call, @NotNull Throwable throwable) {
                logger.info("{} {}", throwable.getMessage(), throwable.getCause());
            }
        });
    }

    private void updateBooking() {
        bookingService.put(getBookingModelFromData().setId(getBookingModelFromData().getId()), new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<BookingModel> call, @NotNull Response<BookingModel> response) {
                logger.info("Update Booking Success {} {}", response.code(), response.message());
                if (response.isSuccessful()) {
                    Platform.runLater(() -> fxRoot.getScene().getWindow().hide());
                    BookingEvent.subject.onNext(BookingEvent.Type.UPDATE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<BookingModel> call, @NotNull Throwable throwable) {
                logger.info("{} {}", throwable.getMessage(), throwable.getCause());
            }
        });
    }

    private BookingModel getBookingModelFromForm() {
        logger.info("{} {} {} {}", fxBookingEvent.getValue(), fxBookingAsset.getValue(), fxBookingStartDate.getValue(), fxBookingEndDate.getValue());
        BookingModel bookingModel = new BookingModel();
        bookingModel.setEventId(fxBookingEvent.getValue());
        bookingModel.setAssetId(fxBookingAsset.getValue());
        bookingModel.setStartDate(String.valueOf(fxBookingStartDate.getValue()));
        bookingModel.setEndDate(String.valueOf(fxBookingEndDate.getValue()));
        return bookingModel;
    }

    private FormMode getFormMode() {

        if (getData().get("mode").toString().equals(FormMode.EDIT.name())) {
            return FormMode.EDIT;
        }

        return FormMode.CREATE;
    }

    private BookingModel getBookingModelFromData() {
        var bookingModel = (BookingModel) getData().get("bookingModel");

        logger.info("Booking Model For Editing: {}", new Gson().toJson(bookingModel));

        return bookingModel;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getData() {
        return (Map<String, Object>) fxRoot.getScene().getWindow().getUserData();
    }
}
