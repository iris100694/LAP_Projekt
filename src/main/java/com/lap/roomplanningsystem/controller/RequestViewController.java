package com.lap.roomplanningsystem.controller;

import java.io.IOException;
import java.sql.SQLException;

import java.time.LocalTime;
import java.util.ArrayList;


import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.validation.DateValidator;
import com.lap.roomplanningsystem.validation.RequestValidator;
import com.lap.roomplanningsystem.filterBoxes.FilterComboBox;

import com.lap.roomplanningsystem.model.*;

import com.lap.roomplanningsystem.utility.ListUtility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RequestViewController extends BaseController{

    @FXML
    private ComboBox<String> coachComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private ComboBox<String> equipmentComboBox;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private ComboBox<String> locationComboBox;

    @FXML
    private ComboBox<String> roomComboBox;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private Label errorLabel;

    ObservableList<FilterComboBox> filterComboBoxes = FXCollections.observableArrayList();
    RequestValidator requestValidator = new RequestValidator();



    @FXML
    void initialize() throws SQLException {

        ArrayList<ObservableList<String>> list = Dataholder.roomRepositoryJDBC.listsForChoiceBox(Constants.CALL_LISTS_FOR_REQUEST_COMBOBOXES);

        filterComboBoxes.add(new FilterComboBox(locationComboBox, "Standort", list.get(0), false));
        filterComboBoxes.add(new FilterComboBox(equipmentComboBox, "Ausstattung", list.get(1), true));
        filterComboBoxes.add(new FilterComboBox(courseComboBox, "Kurs", list.get(2), true));
        filterComboBoxes.add(new FilterComboBox(coachComboBox, "Trainer", list.get(3),true));

        ObservableList<LocalTime> timeList = ListUtility.createTimeList();
        startTimeComboBox.setItems(timeList);
        endTimeComboBox.setItems(timeList);


        locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String location, String newLocation) {

                if(newLocation.equals("")){
                    locationComboBox.setPromptText("Standort");
                    roomComboBox.setDisable(true);
                    requestValidator.setRoomDisable(true);
                    sizeComboBox.setDisable(true);

                } else {
                    roomComboBox.setDisable(false);
                    requestValidator.setRoomDisable(false);
                    sizeComboBox.setDisable(false);

                    try {
                        ArrayList<ObservableList<String>> roomList = Dataholder.roomRepositoryJDBC.listsForRoomRequest(Constants.CALL_LISTS_FOR_REQUEST_ROOMS, newLocation);
                        roomComboBox.setItems(roomList.get(0));
                        sizeComboBox.setItems(roomList.get(1));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    roomComboBox.setPromptText("Raum");
                    sizeComboBox.setPromptText("Raumgröße");
                }
            }
        });



        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(datePicker.getValue() != null){
                    startTimeComboBox.setDisable(false);
                } else {
                    startTimeComboBox.setDisable(true);
                }
            }
        });

        startTimeComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(startTimeComboBox.getValue() != null){
                    endTimeComboBox.setDisable(false);
                } else {
                    endTimeComboBox.setDisable(true);
                }
            }
        });

    }

    @FXML
    void onRequestButtonClicked(MouseEvent event) throws IOException {
        if(validateFields()){
            requestValidator.setLocation(locationComboBox.getValue());
            requestValidator.setRoom(roomComboBox.getValue());
            requestValidator.setUser(coachComboBox.getValue());
            requestValidator.setEquipment(equipmentComboBox.getValue());
            requestValidator.setSize(sizeComboBox.getValue());
            requestValidator.setDate(datePicker.getValue());
            requestValidator.setStartTime(startTimeComboBox.getValue());
            requestValidator.setEndTime(endTimeComboBox.getValue());
            requestValidator.setCourse(courseComboBox.getValue());


            FilteredList<Room> filteredList = new FilteredList<Room>(model.getDataholder().getRooms());
            filteredList.setPredicate(requestValidator.filter());
            model.setRequestResult(filteredList);

            showNewView(Constants.PATH_TO_ROOM_REQUEST_RESULT_VIEW);

            Stage stage = (Stage) locationComboBox.getScene().getWindow();
            stage.close();

        }

    }

    private boolean validateFields() {
        return !emptyFields() && validateDate();
    }

    private boolean emptyFields() {
        boolean valid = startTimeComboBox.getValue() != null && (endTimeComboBox.getValue() == null);

        if(valid){
            errorLabel.setText("Bitte Endzeit angeben!");
        }
        return valid;

    }

    private boolean validateDate(){
        if(datePicker.getValue() != null){
            if(DateValidator.validDate(datePicker.getValue())){
                if(startTimeComboBox.getValue() != null){
                    if(DateValidator.validTime(startTimeComboBox.getValue(), endTimeComboBox.getValue())){
                        return true;
                    } else {
                        errorLabel.setText("Endzeit darf nicht vor und zur gleichen Startzeit gewählt werden!");
                        return false;
                    }
                } else{
                    return true;
                }

            } else{
                errorLabel.setText("Datum darf nicht in der Vergangenheit gewählt werden!");
                return false;
            }
        } else {
            return true;
        }
    }
}
