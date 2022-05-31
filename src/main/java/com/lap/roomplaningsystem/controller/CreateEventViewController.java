package com.lap.roomplaningsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.matcher.CourseMatcher;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.matcher.RoomMatcher;
import com.lap.roomplaningsystem.matcher.UserMatcher;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
    private Button saveButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label resultLabel;

    boolean isSeries;
    boolean isOneTime = true;
    @FXML
    private ImageView profilImage;


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

        if(model.getUser()!= null){
            setProfilImage(profilImage);
        }

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

        model.selectedResultProperty().addListener(new ChangeListener<Room>() {
            @Override
            public void changed(ObservableValue<? extends Room> observableValue, Room oldRoom, Room newRoom) {
                Optional<Room> room = model.getDataholder().getRooms().stream()
                        .filter(r -> r == newRoom)
                        .findAny();

                if(room.isPresent()){
                    locationComboBox.getSelectionModel().select(room.get().getLocation());
                    roomComboBox.getSelectionModel().select(room.get());
                }
            }
        });

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

        courseComboBox.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                return course == null ? "Kurs" :  course.getTitle();
            }

            @Override
            public Course fromString(String s) {
                CourseMatcher courseMatcher = new CourseMatcher();
                return courseMatcher.matchByString(s, model.getDataholder().getCourses());
            }
        });
    }



    private void setSeriesDateAreaVisable() {
        isSeries = true;
        isOneTime = false;
        singleDateDatePicker.setDisable(true);
        singleDateStartTimeComboBox.setDisable(true);
        singleDateEndTimeComboBox.setDisable(true);

        seriesDateStartDatePicker.setDisable(false);
        seriesDateEndDatePicker.setDisable(false);
        seriesDateStartTimeComboBox.setDisable(false);
        seriesDateEndTimeComboBox.setDisable(false);
    }

    private void setOnTimeDateAreaVisable() {
        isSeries = false;
        isOneTime = true;
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


    @FXML
    void onLogoutButtonClicked(ActionEvent actionEvent) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }

    @FXML
    private void onRequestButtonClicked(MouseEvent mouseEvent) throws IOException {
        showNewView(Constants.PATH_TO_REQUEST_VIEW);
    }

    @FXML
    private void onSaveButtonClicked(MouseEvent mouseEvent) throws Exception {
        boolean valid = false;
        if (locationComboBox.getValue() == null || roomComboBox.getValue() == null ||
            courseComboBox.getValue() == null || coachComboBox.getValue() == null){
            errorLabel.setText("Bitte alle Felder ausfüllen!");
        } else if(isOneTime){
            if(singleDateDatePicker.getValue() == null || singleDateStartTimeComboBox.getValue() == null ||
            singleDateEndTimeComboBox.getValue() == null){
                errorLabel.setText("Bitte Datum und Uhrzeit ausfüllen!");
            } else {
                if(singleDateDatePicker.getValue().isBefore(LocalDate.now())){
                    errorLabel.setText("Datum darf nicht in der Vergangenheit gewählt werden!");
                }else if(singleDateEndTimeComboBox.getValue().isBefore(singleDateStartTimeComboBox.getValue())){
                    errorLabel.setText("Endzeit darf nicht vor dem Stardatum gewählt werden!");
                } else {
                    valid = true;
                }
            }
        } else {
            if(seriesDateStartDatePicker.getValue() == null || seriesDateEndDatePicker.getValue() == null ||
                    seriesDateStartTimeComboBox.getValue() == null || seriesDateEndDatePicker.getValue() == null){
                errorLabel.setText("Bitte Datum und Uhrzeit ausfüllen!");
            } else {
                if(seriesDateStartDatePicker.getValue().isBefore(LocalDate.now())){
                    errorLabel.setText("Startdatum darf nicht in der Vergangenheit gewählt werden!");
                }else if(seriesDateEndDatePicker.getValue().isBefore(seriesDateStartDatePicker.getValue())){
                    errorLabel.setText("Enddatum darf nicht vor dem Stardatum gewählt werden!");
                } else if (seriesDateEndTimeComboBox.getValue().isBefore(seriesDateStartTimeComboBox.getValue()) ||
                        seriesDateEndTimeComboBox.getValue().toString().equals(seriesDateStartTimeComboBox.getValue().toString())){
                    errorLabel.setText("Endzeit darf nicht vor und zur gleichen Startzeit gewählt werden!");
                } else {
                    valid = true;
                }
            }
        }

        if(valid){

            if(isOneTime){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String startDate = singleDateDatePicker.getValue().toString() + " "+ singleDateStartTimeComboBox.getValue().toString();
                String endDate = singleDateDatePicker.getValue().toString() + " "+ singleDateEndTimeComboBox.getValue().toString();
                LocalDateTime start = LocalDateTime.parse(startDate, formatter);
                LocalDateTime end = LocalDateTime.parse(endDate, formatter);

                Event event = Dataholder.eventRepositoryJDBC.add(model.getUser(),roomComboBox.getValue(),courseComboBox.getValue(),
                        coachComboBox.getValue(), singleDateDatePicker.getValue(), start, end);

                model.getDataholder().addEvent(event);
                errorLabel.setText("");
                resultLabel.setText("Veranstaltung erfolgreich erfasst!");
            } else if(isSeries){
                ObservableList<Event> events = handleSeries(typComboBox.getValue());

                model.getDataholder().addEvents(events);
                errorLabel.setText("");
                resultLabel.setText("Veranstaltung erfolgreich erfasst!");
            }

        }
    }


    private ObservableList<Event> handleSeries(String serie){
        ObservableList<Event> isBooked = null;


        return isBooked = switch (serie){
            case "täglich" -> Dataholder.eventRepositoryJDBC.seriesDaily(model.getUser(),roomComboBox.getValue(),courseComboBox.getValue(),
                    coachComboBox.getValue(), seriesDateStartDatePicker.getValue(), seriesDateEndDatePicker.getValue(), seriesDateStartTimeComboBox.getValue().toString(), seriesDateEndTimeComboBox.getValue().toString());
            case "wöchentlich" -> Dataholder.eventRepositoryJDBC.seriesWeekly(model.getUser(),roomComboBox.getValue(),courseComboBox.getValue(),
                    coachComboBox.getValue(), seriesDateStartDatePicker.getValue(), seriesDateEndDatePicker.getValue(), seriesDateStartTimeComboBox.getValue().toString(), seriesDateEndTimeComboBox.getValue().toString());
            case "monatlich" -> Dataholder.eventRepositoryJDBC.seriesMonthly(model.getUser(),roomComboBox.getValue(),courseComboBox.getValue(),
                    coachComboBox.getValue(), seriesDateStartDatePicker.getValue(), seriesDateEndDatePicker.getValue(), seriesDateStartTimeComboBox.getValue().toString(), seriesDateEndTimeComboBox.getValue().toString());
            default-> null;
        };
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }
}
