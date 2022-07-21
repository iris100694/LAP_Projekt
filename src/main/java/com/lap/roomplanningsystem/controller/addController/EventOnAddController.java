package com.lap.roomplanningsystem.controller.addController;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.CourseConverter;
import com.lap.roomplanningsystem.converter.LocationConverter;
import com.lap.roomplanningsystem.converter.RoomConverter;
import com.lap.roomplanningsystem.converter.UserConverter;
import com.lap.roomplanningsystem.formattor.DateFormattorJDBC;
import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.utility.ListUtility;
import com.lap.roomplanningsystem.validation.DateValidator;
import com.lap.roomplanningsystem.validation.EventValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventOnAddController extends BaseController {

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
    private ComboBox<Room> roomComboBox;

    @FXML
    private ComboBox<LocalTime> startComboBox;




    @FXML
    void initialize() {
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

    private void initFields() {
        ObservableList<LocalTime> timeList = ListUtility.createTimeList();
        locationComboBox.setItems(model.getDataholder().getLocations());
        coachComboBox.setItems(model.getDataholder().getCoaches());
        courseComboBox.setItems(model.getDataholder().getCourses());
        startComboBox.setItems(timeList);
        endComboBox.setItems(timeList);

//        locationComboBox.getSelectionModel().select(0);
//        roomComboBox.setItems(ListUtility.availableRoomAtLocation(locationComboBox.getValue()));
//        roomComboBox.getSelectionModel().select(0);
//        coachComboBox.getSelectionModel().select(0);
//        startComboBox.getSelectionModel().select(0);
//        endComboBox.getSelectionModel().select(0);



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
    void onSaveButtonClicked(ActionEvent actionEvent) throws Exception {
        errorLabel.setText("");
        EventValidator eventValidator = new EventValidator(model.getDataholder().getEvents());

        if(validateFields() && eventValidator.validSingle(roomComboBox.getValue(), courseComboBox.getValue(), coachComboBox.getValue(),datePicker.getValue(), startComboBox.getValue(),endComboBox.getValue())){

            Event event = addEventByJDBC();

            if(event != null){
                model.getDataholder().addEvent(event);
                model.setNewEvent(event);
                closeStage(errorLabel);

            }
        } else {
            if(errorLabel.getText().equals("")){
                errorLabel.setText(eventValidator.getErrString());
            }

        }
    }

    private boolean validateFields() {
        return !emptyFields() && validateDate() && validateSize();
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

    private boolean validateSize() {
        boolean valid = courseComboBox.getValue().getMembers() <= roomComboBox.getValue().getMaxPersons();

        if(!valid){
            errorLabel.setText("Teilnehmerzahl größer als maximale Plätze im Raum!");
            return false;
        }
        return valid;
    }




    private Event addEventByJDBC() throws Exception {
        LocalDateTime start = DateFormattorJDBC.format(datePicker.getValue(), startComboBox.getValue());
        LocalDateTime end = DateFormattorJDBC.format(datePicker.getValue(), endComboBox.getValue());
        return Dataholder.eventRepositoryJDBC.add(model.getUser(), roomComboBox.getValue(), courseComboBox.getValue(), coachComboBox.getValue(),
                datePicker.getValue(), start, end);
    }
}
