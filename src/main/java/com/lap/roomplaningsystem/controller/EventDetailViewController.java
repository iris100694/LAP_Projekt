package com.lap.roomplaningsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class EventDetailViewController extends BaseController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label eventDetailViewCoachLabel;

    @FXML
    private Label eventDetailViewDateLabel;

    @FXML
    private Label eventDetailViewEndLabel;

    @FXML
    private Label eventDetailViewLocationLabel;

    @FXML
    private Label eventDetailViewNumberLabel;

    @FXML
    private Label eventDetailViewRoomLabel;

    @FXML
    private Label eventDetailViewStartLabel;

    @FXML
    private Label eventDetailViewTitleLabel;
    @FXML
    private Button deleteEvent;
    @FXML
    private Button editEvent;



    @FXML
    void initialize() {
        assert eventDetailViewCoachLabel != null : "fx:id=\"eventDetailViewCoachLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";
        assert eventDetailViewDateLabel != null : "fx:id=\"eventDetailViewDateLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";
        assert eventDetailViewEndLabel != null : "fx:id=\"eventDetailViewEndLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";
        assert eventDetailViewLocationLabel != null : "fx:id=\"eventDetailViewLocationLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";
        assert eventDetailViewNumberLabel != null : "fx:id=\"eventDetailViewNumberLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";
        assert eventDetailViewRoomLabel != null : "fx:id=\"eventDetailViewRoomLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";
        assert eventDetailViewStartLabel != null : "fx:id=\"eventDetailViewStartLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";
        assert eventDetailViewTitleLabel != null : "fx:id=\"eventDetailViewTitleLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";

        initData();
        editAuthorization();

    }

    private void initData() {
        Event e = model.getShowEvent();

        eventDetailViewNumberLabel.setText("V" + e.getEventID());
        eventDetailViewTitleLabel.setText(e.getCourse().title() + " " + e.getCourse().program().description());
        eventDetailViewLocationLabel.setText(e.getRoom().getLocation().getDescription());
        eventDetailViewRoomLabel.setText(e.getRoom().getDescription());
        eventDetailViewDateLabel.setText(e.getDate().toString());
        eventDetailViewStartLabel.setText(e.getStartTime().toString());
        eventDetailViewEndLabel.setText(e.getEndTime().toString());
        eventDetailViewCoachLabel.setText(e.getCoach().getLastname());
    }

    private void editAuthorization() {
        switch(model.getAuthorization()){
            case "coach" -> editEventCoachAuthorization();
            case "admin" -> editEventAdminAuthorization();
        }
    }

    private void editEventAdminAuthorization() {
        editEvent.setVisible(true);
        deleteEvent.setVisible(true);
    }

    private void editEventCoachAuthorization() {
        System.out.println(model.getShowEvent());
        if(model.getShowEvent().getCreator().getId() == model.getUser().getId()){
            editEvent.setVisible(true);
            deleteEvent.setVisible(true);
        }
    }

    @FXML
    void onEventDeleteButtonClicked(MouseEvent event) {

    }

    @FXML
    void onEventEditButtonClicked(MouseEvent event) {

    }

}