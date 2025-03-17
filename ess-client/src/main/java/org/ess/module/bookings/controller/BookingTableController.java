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
import org.ess.app.common.TabType;
import org.ess.app.controller.TabController;
import org.ess.module.bookings.model.BookingModel;
import org.ess.module.bookings.repository.BookingFakeRepository;
import org.ess.app.window.View;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookingTableController extends TabController implements Initializable {

    @FXML
    TableView<BookingModel> fxBookingTableLayout;

    private final ObservableList<BookingModel> userModelObservableList = FXCollections.observableArrayList(new ArrayList<>());

    protected static final Logger logger = LogManager.getLogger(BookingTableController.class);

    @Override
    public void initialize(Tab tab) {
        userModelObservableList.clear();
        userModelObservableList.addAll(BookingFakeRepository.getData(50));
        fxBookingTableLayout.setItems(userModelObservableList);
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
        endTimeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEndTime()));

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
    }

    private static @NotNull TableColumn<BookingModel, HBox> getBookingActionsTableColumn() {
        TableColumn<BookingModel, HBox> deleteStaff = new TableColumn<>("Actions");

        deleteStaff.setCellValueFactory(cellData -> {
            var hBox = new HBox();
            var editBtn = new Button("Edit");
            var deleteBtn = new Button("Delete");
            editBtn.setOnMouseClicked(event -> View.userFormWindow());
            deleteBtn.setOnMouseClicked(event -> View.userDeleteConfirmationWindow());
            hBox.getChildren().add(editBtn);
            hBox.getChildren().add(deleteBtn);
            hBox.setSpacing(10);
            hBox.styleProperty().set("-fx-alignment: center");

            return new SimpleObjectProperty<>(hBox);
        });
        return deleteStaff;
    }
}
