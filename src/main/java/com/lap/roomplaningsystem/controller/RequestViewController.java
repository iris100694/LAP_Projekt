package com.lap.roomplaningsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.filter.RequestFilter;
import com.lap.roomplaningsystem.filterBoxes.FilterComboBox;
import com.lap.roomplaningsystem.matcher.*;
import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.repository.Repository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RequestViewController extends BaseController{

    @FXML
    private ComboBox<String> coachComboBox;

    @FXML
    private DatePicker endDatePicker;

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
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    ObservableList<FilterComboBox> filterComboBoxes = FXCollections.observableArrayList();
    RequestFilter requestFilter = new RequestFilter();


    @FXML
    void initialize() throws SQLException {
        assert coachComboBox != null : "fx:id=\"coachComboBox\" was not injected: check your FXML file 'request-view.fxml'.";
        assert endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'request-view.fxml'.";
        assert endTimeComboBox != null : "fx:id=\"endTimeComboBox\" was not injected: check your FXML file 'request-view.fxml'.";
        assert equipmentComboBox != null : "fx:id=\"equipmentComboBox\" was not injected: check your FXML file 'request-view.fxml'.";
        assert courseComboBox != null : "fx:id=\"eventComboBox\" was not injected: check your FXML file 'request-view.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'request-view.fxml'.";
        assert roomComboBox != null : "fx:id=\"roomComboBox\" was not injected: check your FXML file 'request-view.fxml'.";
        assert sizeComboBox != null : "fx:id=\"sizeComboBox\" was not injected: check your FXML file 'request-view.fxml'.";
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'request-view.fxml'.";
        assert startTimeComboBox != null : "fx:id=\"startTimeComboBox\" was not injected: check your FXML file 'request-view.fxml'.";

        initView();

    }



    private void initView() throws SQLException {
        ArrayList<ObservableList<String>> list = Dataholder.roomRepositoryJDBC.listsForChoiceBox(Constants.CALL_LISTS_FOR_REQUEST_COMBOBOXES);

        filterComboBoxes.add(new FilterComboBox(locationComboBox, "Standort", list.get(0), false));
        filterComboBoxes.add(new FilterComboBox(equipmentComboBox, "Ausstattung", list.get(1), true));
        filterComboBoxes.add(new FilterComboBox(courseComboBox, "Kurs", list.get(2), true));
        filterComboBoxes.add(new FilterComboBox(coachComboBox, "Trainer", list.get(3),true));

        startTimeComboBox.setItems(createTimeList());
        endTimeComboBox.setItems(createTimeList());


        locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String location, String newLocation) {

                if(newLocation.equals("")){
                    locationComboBox.setPromptText("Standort");
                    roomComboBox.setDisable(true);
                    requestFilter.setRoomDisable(true);
                    sizeComboBox.setDisable(true);


                } else {
                    roomComboBox.setDisable(false);
                    requestFilter.setRoomDisable(false);
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

        startDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(startDatePicker.getValue() != null){
                    endDatePicker.setDisable(false);
                } else{
                    endDatePicker.setDisable(true);
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
        requestFilter.handleRequest(locationComboBox.getValue(), roomComboBox.getValue(),coachComboBox.getValue(),
                equipmentComboBox.getValue(),sizeComboBox.getValue(), startDatePicker.getValue(), endDatePicker.getValue(),
                startTimeComboBox.getValue(), endTimeComboBox.getValue());

        model.setRequestResult(requestFilter.filter(model));

        showNewView(Constants.PATH_TO_ROOM_REQUEST_RESULT_VIEW);

        Stage stage = (Stage) locationComboBox.getScene().getWindow();
        stage.close();
    }

}
