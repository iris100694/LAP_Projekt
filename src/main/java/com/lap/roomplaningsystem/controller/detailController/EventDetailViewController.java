package com.lap.roomplaningsystem.controller.detailController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Location;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class EventDetailViewController extends BaseController {

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
    private Label eventDetailViewCourseLabel;
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
        assert eventDetailViewCourseLabel != null : "fx:id=\"eventDetailViewTitleLabel\" was not injected: check your FXML file 'eventDetail-view.fxml'.";

        Optional<Event> optionalEvent = model.getDataholder().getEvents().stream().filter(event -> event == model.getSelectedEventProperty()).findAny();



        if(optionalEvent.isPresent()){
            Event e = optionalEvent.get();
            eventDetailViewNumberLabel.setText("V" + e.getEventID());
            eventDetailViewCourseLabel.setText(e.getCourse().getTitle() + " " + e.getCourse().getProgram().getDescription());
            eventDetailViewLocationLabel.setText(e.getRoom().getLocation().getDescription());
            eventDetailViewRoomLabel.setText(e.getRoom().getDescription());
            eventDetailViewDateLabel.setText(e.getDate().toString());
            eventDetailViewStartLabel.setText(e.getStartTime().toString());
            eventDetailViewEndLabel.setText(e.getEndTime().toString());
            eventDetailViewCoachLabel.setText(e.getCoach().getLastname());
        }

        editAuthorization();

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
        Optional <Event> e = model.getDataholder().getEvents().stream().filter(event -> event == model.getSelectedEventProperty()).findAny();
        if(e.get().getCreator().getId() == model.getUser().getId()){
            editEvent.setVisible(true);
            deleteEvent.setVisible(true);
        }
    }

    @FXML
    void onEventDeleteButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EVENT_ON_DELETE_VIEW);

        Stage detailStage = (Stage) editEvent.getScene().getWindow();
        detailStage.close();

    }

    @FXML
    void onEventEditButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EVENT_ON_UPDATE_VIEW);

        Stage detailStage = (Stage) editEvent.getScene().getWindow();
        detailStage.close();
    }

}