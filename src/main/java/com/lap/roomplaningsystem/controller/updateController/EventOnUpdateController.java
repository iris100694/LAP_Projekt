package com.lap.roomplaningsystem.controller.updateController;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Optional;

import com.calendarfx.model.Entry;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.converter.CourseConverter;
import com.lap.roomplaningsystem.converter.LocationConverter;
import com.lap.roomplaningsystem.converter.RoomConverter;
import com.lap.roomplaningsystem.converter.UserConverter;
import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.utility.ListUtility;
import com.lap.roomplaningsystem.validation.DateValidator;
import com.lap.roomplaningsystem.validation.EventValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Button saveButton;

    @FXML
    private ComboBox<Room> roomComboBox;

    @FXML
    private ComboBox<LocalTime> startComboBox;

    private Event event;


    @FXML
    void initialize() {

        Optional<Event> optionalEvent = model.getDataholder().getEvents().stream().filter(e -> e == model.getSelectedEventProperty()).findAny();

        if (optionalEvent.isPresent()) {
            event = optionalEvent.get();

            initFields();
            initConverter();

            locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
                @Override
                public void changed(ObservableValue<? extends Location> observableValue, Location location, Location newLocation) {
                    roomComboBox.setItems(ListUtility.availableRoomAtLocation(newLocation));
                    roomComboBox.getSelectionModel().select(null);
                    roomComboBox.setPromptText("Raum");

                }
            });

        }
    }

    private void initFields() {
        ObservableList<LocalTime> timeList = ListUtility.createTimeList();
        numberLabel.setText("V" + String.valueOf(event.getEventID()));
        locationComboBox.setItems(model.getDataholder().getLocations());
        coachComboBox.setItems(model.getDataholder().getCoaches());
        courseComboBox.setItems(model.getDataholder().getCourses());
        roomComboBox.setItems(ListUtility.availableRoomAtLocation(event.getRoom().getLocation()));
        startComboBox.setItems(timeList);
        endComboBox.setItems(timeList);
        locationComboBox.getSelectionModel().select(event.getRoom().getLocation());
        roomComboBox.getSelectionModel().select(event.getRoom());
        coachComboBox.getSelectionModel().select(event.getCoach());
        courseComboBox.getSelectionModel().select(event.getCourse());
        datePicker.setValue(event.getDate());
        startComboBox.getSelectionModel().select(event.getStartTime().toLocalTime());
        endComboBox.getSelectionModel().select(event.getEndTime().toLocalTime());
    }


    private void initConverter(){
        CourseConverter courseConverter = new CourseConverter();
        courseConverter.setConverter(courseComboBox);
        LocationConverter locationConverter = new LocationConverter();
        locationConverter.setConverter(locationComboBox);
        RoomConverter roomConverter = new RoomConverter(locationComboBox);
        roomConverter.setConverter(roomComboBox);
        UserConverter userConverter = new UserConverter();
        userConverter.setConverter(coachComboBox);
    }

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        EventValidator eventValidator = new EventValidator(model.getDataholder().getEvents());
        eventValidator.setUpdateEvent(this.event);

        if(validateFields() && eventValidator.validSingle(roomComboBox.getValue(), courseComboBox.getValue(), datePicker.getValue(), startComboBox.getValue(),endComboBox.getValue())){

            this.event.setCourse(courseComboBox.getValue());
            this.event.setRoom(roomComboBox.getValue());
            this.event.setCoach(coachComboBox.getValue());
            this.event.setDate(datePicker.getValue());
            this.event.setStartTime(Time.valueOf(startComboBox.getValue()));
            this.event.setEndTime(Time.valueOf(endComboBox.getValue()));


            boolean isUpdated = updateEventByJDBC();

            if(isUpdated){
                int index = model.getDataholder().getEvents().indexOf(this.event);
                model.getDataholder().updateEvent(index, this.event);

                if(!model.isShowInCalendar()){
                    showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                    closeStage(errorLabel);

                } else {
                    model.setShowInCalendar(false);
                    saveButton.setDisable(true);
//                    model.getCalendarView().
                }
            }
        }
    }

    private boolean validateFields() {
        return !emptyFields() && validateDate();
    }


    private boolean emptyFields() {
        boolean empty = courseComboBox.getValue() == null || locationComboBox.getValue() == null || roomComboBox.getValue() == null ||
                datePicker.getValue() == null || startComboBox.getValue() == null || endComboBox.getValue() == null || coachComboBox.getValue() == null;

        if(empty){
            errorLabel.setText("Bitte Felder ausfüllen!");
        }

        return empty;
    }

    private boolean validateDate(){
        if(DateValidator.validDate(datePicker.getValue())){
            if(DateValidator.validTime(startComboBox.getValue(), endComboBox.getValue())){
                return true;
            } else {
                errorLabel.setText("Endzeit darf nicht vor und zur gleichen Startzeit gewählt werden!");
                return false;
            }
        } else{
            errorLabel.setText("Datum darf nicht in der Vergangenheit gewählt werden!");
            return false;
        }

    }



    private boolean updateEventByJDBC() throws Exception {
        return Dataholder.eventRepositoryJDBC.update(event);
    }
}
