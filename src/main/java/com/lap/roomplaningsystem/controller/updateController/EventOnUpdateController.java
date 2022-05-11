package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<User> eventDetailViewCoachComboBox;

    @FXML
    private DatePicker eventDetailViewDatePicker;

    @FXML
    private TextField eventDetailViewEndInput;

    @FXML
    private ComboBox<Location> eventDetailViewLocationComboBox;

    @FXML
    private Label eventDetailViewNumberLabel;

    @FXML
    private ComboBox<Room> eventDetailViewRoomComboBox;

    @FXML
    private TextField eventDetailViewStartInput;

    @FXML
    private ComboBox<Course> eventDetailViewCourseComboBox;



    @FXML
    private Button saveEventButton;

    private ObservableList<FilterBox> filterboxes = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert eventDetailViewCoachComboBox != null : "fx:id=\"eventDetailViewCoachChoiceBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert eventDetailViewDatePicker != null : "fx:id=\"eventDetailViewDatePicker\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert eventDetailViewEndInput != null : "fx:id=\"eventDetailViewEndInput\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert eventDetailViewLocationComboBox != null : "fx:id=\"eventDetailViewLocationChoiceBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert eventDetailViewNumberLabel != null : "fx:id=\"eventDetailViewNumberLabel\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert eventDetailViewRoomComboBox != null : "fx:id=\"eventDetailViewRoomChoiceBox\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert eventDetailViewStartInput != null : "fx:id=\"eventDetailViewStartInput\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";
        assert saveEventButton != null : "fx:id=\"saveEventButton\" was not injected: check your FXML file 'eventDetailOnUpdate-view.fxml'.";


        initComboBoxes();
        setConverterOnChoiceBoxes();


    }


    private void initComboBoxes() {
        Event e = model.getShowEvent();

        eventDetailViewLocationComboBox.setItems(model.getDataholder().getLocations());
        eventDetailViewCoachComboBox.setItems(model.getDataholder().getCoaches());
        eventDetailViewCourseComboBox.setItems(model.getDataholder().getCourses());
        eventDetailViewRoomComboBox.setItems(availableRooms(e.getRoom().getLocation()));

        eventDetailViewLocationComboBox.getSelectionModel().select(e.getRoom().getLocation());
        eventDetailViewRoomComboBox.getSelectionModel().select(e.getRoom());
        eventDetailViewCoachComboBox.getSelectionModel().select(e.getCoach());
        eventDetailViewCourseComboBox.getSelectionModel().select(e.getCourse());

        eventDetailViewDatePicker.setValue(e.getDate());
        eventDetailViewStartInput.setText(e.getStartTime().toString());
        eventDetailViewEndInput.setText(e.getEndTime().toString());


        eventDetailViewLocationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location newLocation) {
                eventDetailViewRoomComboBox.setItems(availableRooms(newLocation));
                eventDetailViewRoomComboBox.getSelectionModel().select(null);
                eventDetailViewRoomComboBox.setPromptText("Raum");

            }
        });

    }


    private void setConverterOnChoiceBoxes() {
        eventDetailViewCourseComboBox.setConverter(new StringConverter<Course>() {
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

        eventDetailViewLocationComboBox.setConverter(new StringConverter<Location>() {
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

        eventDetailViewCoachComboBox.setConverter(new StringConverter<User>() {
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

        eventDetailViewRoomComboBox.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room == null ? "" : room.getDescription();
            }

            @Override
            public Room fromString(String s) {
                RoomMatcher roomMatcher = new RoomMatcher(eventDetailViewLocationComboBox.getValue());
                return roomMatcher.matchByString(s, model.getDataholder().getRooms());
            }
        });

    }


    @FXML
    void onSaveEventButtonClicked(MouseEvent event) {

    }

}
