package com.lap.roomplaningsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.matcher.RoomMatcher;
import com.lap.roomplaningsystem.matcher.UserMatcher;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class CreateEventViewController extends BaseController{


    @FXML
    private ComboBox<User> coachComboBox;

    @FXML
    private ComboBox<Course> courseComboBox;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private ComboBox<Room> roomComboBox;

    @FXML
    private DatePicker seriesDateEndDatePicker;

    @FXML
    private ComboBox<LocalTime> seriesDateEndTimeComboBox;

    @FXML
    private DatePicker seriesDateStartDatePicker;

    @FXML
    private ComboBox<LocalTime> seriesDateStartTimeComboBox;

    @FXML
    private ComboBox<LocalTime> singleDateStartTimeComboBox;

    @FXML
    private DatePicker singleDateDatePicker;

    @FXML
    private ComboBox<LocalTime> singleDateEndTimeComboBox;

    @FXML
    private ComboBox<String> typComboBox;



    @FXML
    void initialize() {
        assert coachComboBox != null : "fx:id=\"coachComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert courseComboBox != null : "fx:id=\"courseComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert roomComboBox != null : "fx:id=\"roomComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert seriesDateEndDatePicker != null : "fx:id=\"seriesDateEndDatePicker\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert seriesDateEndTimeComboBox != null : "fx:id=\"seriesDateEndTimeComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert seriesDateStartDatePicker != null : "fx:id=\"seriesDateStartDatePicker\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert seriesDateStartTimeComboBox != null : "fx:id=\"seriesDateStartTimeComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert singleDateEndTimeComboBox != null : "fx:id=\"singeDateEndTimeComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert singleDateStartTimeComboBox != null : "fx:id=\"singeDateStartTimeComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert singleDateDatePicker != null : "fx:id=\"singleDateDatePicker\" was not injected: check your FXML file 'eventCreate-view.fxml'.";
        assert typComboBox != null : "fx:id=\"typComboBox\" was not injected: check your FXML file 'eventCreate-view.fxml'.";

        initView();
        setConverterOnChoiceBoxes();
    }

    private void initView() {

        locationComboBox.setItems(model.getDataholder().getLocations());
        coachComboBox.setItems(model.getDataholder().getCoaches());
        typComboBox.setItems(createTypList());
        courseComboBox.setItems(model.getDataholder().getCourses());

        ObservableList<LocalTime> timeList = createTimeList();
        singleDateStartTimeComboBox.setItems(timeList);
        singleDateEndTimeComboBox.setItems(timeList);
        seriesDateStartTimeComboBox.setItems(timeList);
        seriesDateEndTimeComboBox.setItems(timeList);

        locationComboBox.setPromptText("Standort");
        coachComboBox.setPromptText("Trainer");
        typComboBox.setPromptText("Termintyp");
        courseComboBox.setPromptText("Kurse");
        singleDateStartTimeComboBox.setPromptText("Beginn");
        singleDateEndTimeComboBox.setPromptText("Ende");
        seriesDateStartTimeComboBox.setPromptText("Beginn");
        seriesDateEndTimeComboBox.setPromptText("Ende");

        locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location newLocation) {
                roomComboBox.setDisable(false);
                roomComboBox.setItems(availableRooms(locationComboBox.getValue()));
                roomComboBox.setPromptText("Raum");

            }
        });

        typComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                switch (newValue){
                    case "einmalig" -> setOnTimeDateAreaVisable();
                    default -> setSeriesDateAreaVisable();
                }
            }

        });

        model.selectedRequestResultProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldRoom, Number newRoom) {
                Optional<Room> room = model.getDataholder().getRooms().stream()
                        .filter(r -> r.getRoomID() == newRoom.intValue())
                        .findAny();

                if(room.isPresent()){
                    locationComboBox.getSelectionModel().select(room.get().getLocation());
                    roomComboBox.getSelectionModel().select(room.get());
                }
            }
        });



    }



    private void setSeriesDateAreaVisable() {
        singleDateDatePicker.setDisable(true);
        singleDateStartTimeComboBox.setDisable(true);
        singleDateEndTimeComboBox.setDisable(true);

        seriesDateStartDatePicker.setDisable(false);
        seriesDateEndDatePicker.setDisable(false);
        seriesDateStartTimeComboBox.setDisable(false);
        seriesDateEndTimeComboBox.setDisable(false);
    }

    private void setOnTimeDateAreaVisable() {
        singleDateDatePicker.setDisable(false);
        singleDateStartTimeComboBox.setDisable(false);
        singleDateEndTimeComboBox.setDisable(false);

        seriesDateStartDatePicker.setDisable(true);
        seriesDateEndDatePicker.setDisable(true);
        seriesDateStartTimeComboBox.setDisable(true);
        seriesDateEndTimeComboBox.setDisable(true);
    }

    private ObservableList<String> createTypList() {
        ObservableList<String> typs = FXCollections.observableArrayList();

        typs.add("einmalig");
        typs.add("täglich");
        typs.add("wöchentlich");
        typs.add("monatlich");

        return typs;
    }

    private void setConverterOnChoiceBoxes() {
        locationComboBox.setConverter(new StringConverter<Location>() {
            @Override
            public String toString(Location location) {
                return location.getDescription();
            }

            @Override
            public Location fromString(String s) {
                LocationMatcher locationMatcher = new LocationMatcher();
                return locationMatcher.matchByString(s, model.getDataholder().getLocations());
            }
        });



        coachComboBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getFirstname() + " " + user.getLastname();
            }

            @Override
            public User fromString(String s) {
                UserMatcher userMatcher = new UserMatcher();
                return userMatcher.matchByString(s, model.getDataholder().getUsers());
            }
        });

        roomComboBox.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room == null ? "" : room.getDescription();
            }

            @Override
            public Room fromString(String s) {
                RoomMatcher roomMatcher = new RoomMatcher(locationComboBox.getValue());
                return roomMatcher.matchByString(s, model.getDataholder().getRooms());
            }
        });
    }

    @FXML
    void onLogoutLabelClicked(MouseEvent event) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }

    @FXML
    private void onRequestButtonClicked(MouseEvent mouseEvent) throws IOException {
        showNewView(Constants.PATH_TO_REQUEST_VIEW);
    }
}
