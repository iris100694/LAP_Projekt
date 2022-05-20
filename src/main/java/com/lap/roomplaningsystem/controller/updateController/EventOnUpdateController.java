package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.filterBoxes.FilterBox;
import com.lap.roomplaningsystem.matcher.CourseMatcher;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.matcher.RoomMatcher;
import com.lap.roomplaningsystem.matcher.UserMatcher;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

public class EventOnUpdateController extends BaseController {

    @FXML
    private ComboBox<User> coachComboBox;

    @FXML
    private ComboBox<Course> courseComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<LocalTime> endComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private Label numberLabel;

    @FXML
    private ComboBox<Room> roomComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<LocalTime> startComboBox;


    @FXML
    void initialize() {
        assert coachComboBox != null : "fx:id=\"coachComboBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert courseComboBox != null : "fx:id=\"courseComboBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert endComboBox != null : "fx:id=\"endComboBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert roomComboBox != null : "fx:id=\"roomComboBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert startComboBox != null : "fx:id=\"startComboBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";


        initComboBoxes();
        setConverterOnChoiceBoxes();


    }


    private void initComboBoxes() {
        Optional<Event> optionalEvent = model.getDataholder().getEvents().stream().filter(e -> e == model.getSelectedEventProperty()).findAny();

        if(optionalEvent.isPresent()){
            locationComboBox.setItems(model.getDataholder().getLocations());
            coachComboBox.setItems(model.getDataholder().getCoaches());
            courseComboBox.setItems(model.getDataholder().getCourses());
            roomComboBox.setItems(availableRooms(optionalEvent.get().getRoom().getLocation()));

            locationComboBox.getSelectionModel().select(optionalEvent.get().getRoom().getLocation());
            roomComboBox.getSelectionModel().select(optionalEvent.get().getRoom());
            coachComboBox.getSelectionModel().select(optionalEvent.get().getCoach());
            courseComboBox.getSelectionModel().select(optionalEvent.get().getCourse());

            datePicker.setValue(optionalEvent.get().getDate());
            startComboBox.setItems(createTimeList());

        }


        locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location newLocation) {
                roomComboBox.setItems(availableRooms(newLocation));
                roomComboBox.getSelectionModel().select(null);
                roomComboBox.setPromptText("Raum");

            }
        });

    }


    private void setConverterOnChoiceBoxes() {
        courseComboBox.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                return course == null ? "": course.getTitle() + " " + course.getProgram().getDescription();
            }

            @Override
            public Course fromString(String s) {
                CourseMatcher courseMatcher = new CourseMatcher();

                return courseMatcher.matchByString(s, model.getDataholder().getCourses());
            }
        });

        locationComboBox.setConverter(new StringConverter<Location>() {
            @Override
            public String toString(Location location) {
                return location == null ? "" : location.getDescription();
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
                return user == null ? "" : user.getFirstname() + " " + user.getLastname();
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
    void onSaveButtonClicked(MouseEvent event) {

    }

}
