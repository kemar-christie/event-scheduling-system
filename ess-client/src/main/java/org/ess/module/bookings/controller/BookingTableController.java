package org.ess.module.bookings.controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ess.app.common.FormMode;
import org.ess.app.common.TabType;
import org.ess.app.controller.TabController;
import org.ess.module.bookings.event.BookingEvent;
import org.ess.module.bookings.model.BookingModel;
import org.ess.app.window.View;
import org.ess.module.bookings.service.BookingService;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BookingTableController extends TabController implements Initializable {

    @FXML
    TableView<BookingModel> fxBookingTableLayout;

    private final BookingService bookingService = new BookingService();

    private final ObservableList<BookingModel> userModelObservableList = FXCollections.observableArrayList(new ArrayList<>());

    protected static final Logger logger = LogManager.getLogger(BookingTableController.class);

    @Override
    public void initialize(Tab tab) {
        requestBookingData();

        fxBookingTableLayout.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        fxBookingTableLayout.setPrefWidth(1); // Enables auto-sizing
        fxBookingTableLayout.setPrefWidth(1); // Enables auto-sizing
        fxBookingTableLayout.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            fxBookingTableLayout.refresh(); // Forces the table to re-render itself
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("Initialize");

        TableColumn<BookingModel, String> startDateColumn = new TableColumn<>("Start Date");
        startDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDate()));

        TableColumn<BookingModel, String> startTimeColumn = new TableColumn<>("Start Time");
        startTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartTime()));

        TableColumn<BookingModel, String> endDateColumn = new TableColumn<>("Start Date");
        endDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndDate()));

        TableColumn<BookingModel, String> endTimeColumn = new TableColumn<>("End Time");
        endTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDate()));

        TableColumn<BookingModel, String> updateAtColumn = new TableColumn<>("Update At");
        updateAtColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUpdatedAt()));

        TableColumn<BookingModel, String> createAtColumn = new TableColumn<>("Create At");
        createAtColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCreatedAt()));

        TableColumn<BookingModel, HBox> deleteStaff = getBookingActionsTableColumn();

        fxBookingTableLayout
                .getColumns()
                .addAll(startDateColumn, startTimeColumn, endDateColumn, endTimeColumn, updateAtColumn, createAtColumn, deleteStaff);

        setTabType(TabType.BOOKING);
        Platform.runLater(this);
        BookingEvent.subscribe(BookingEvent.Type.CREATE, event -> requestBookingData());
        BookingEvent.subscribe(BookingEvent.Type.UPDATE, event -> requestBookingData());
        BookingEvent.subscribe(BookingEvent.Type.DELETE, event -> requestBookingData());
    }

    private void requestBookingData() {
        bookingService.get(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<List<BookingModel>> call, @NotNull Response<List<BookingModel>> response) {
                logger.info("Booking Success {} {}", response.code(), response.message());

                if (response.isSuccessful() && response.body() != null) {
                    userModelObservableList.clear();
                    userModelObservableList.addAll(response.body());
                    fxBookingTableLayout.setItems(userModelObservableList);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<BookingModel>> call, @NotNull Throwable throwable) {
                logger.info("Booking Failure {}", throwable.getMessage());
            }
        });
    }

    private static @NotNull TableColumn<BookingModel, HBox> getBookingActionsTableColumn() {
        TableColumn<BookingModel, HBox> deleteStaff = new TableColumn<>("Actions");

        deleteStaff.setCellValueFactory(cellData -> {
            var hBox = new HBox();
            var editBtn = new Button("Edit");
            var deleteBtn = new Button("Delete");
            editBtn.setOnMouseClicked(event -> View.bookingFormWindow(Map.of("mode", FormMode.EDIT, "bookingModel", cellData.getValue())));
            deleteBtn.setOnMouseClicked(event -> View.bookingDeleteConfirmationWindow(Map.of("id", cellData.getValue().getId())));
            hBox.getChildren().add(editBtn);
            hBox.getChildren().add(deleteBtn);
            hBox.setSpacing(10);
            hBox.styleProperty().set("-fx-alignment: center");

            return new SimpleObjectProperty<>(hBox);
        });
        return deleteStaff;
    }
}
