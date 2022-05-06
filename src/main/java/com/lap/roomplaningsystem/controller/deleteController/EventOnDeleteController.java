package com.lap.roomplaningsystem.controller.deleteController;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EventOnDeleteController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label deleteLabel;

   @FXML
    private Label eventDateLabel;

    @FXML
    private Label eventEndLabel;

    @FXML
    private Label eventStartLabel;

    @FXML
    private Label eventLocationLabel;

    @FXML
    private Label eventRoomLabel;




    @FXML
    void initialize() {
        assert deleteLabel != null : "fx:id=\"deleteLabel\" was not injected: check your FXML file 'eventDetailOnDelete-view.fxml'.";
        assert eventDateLabel != null : "fx:id=\"eventDateLabel\" was not injected: check your FXML file 'eventDetailOnDelete-view.fxml'.";
        assert eventEndLabel != null : "fx:id=\"eventEndLabel\" was not injected: check your FXML file 'eventDetailOnDelete-view.fxml'.";
        assert eventStartLabel != null : "fx:id=\"eventStartLabel\" was not injected: check your FXML file 'eventDetailOnDelete-view.fxml'.";


        initEvent();
    }

    private void initEvent() {
        Event e = model.getShowEvent();
        System.out.println(e.getDate());
        deleteLabel.setText("V" + String.valueOf(e.getEventID()) + "  " + e.getCourse().getTitle() + "  " + e.getCourse().getProgram().getDescription());
        eventDateLabel.setText(String.valueOf(e.getDate()));
        eventStartLabel.setText(String.valueOf(e.getStartTime()));
        eventEndLabel.setText(String.valueOf(e.getEndTime()));
        eventRoomLabel.setText(e.getRoom().getDescription());
        eventLocationLabel.setText(e.getRoom().getLocation().getDescription());
    }

    @FXML
    void onNoButtonClicked(MouseEvent event) {
        Stage stage = (Stage) deleteLabel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void onYesButtonClicked(MouseEvent event) {
        Stage stage = (Stage) deleteLabel.getScene().getWindow();
        model.getDataholder().deleteEvent(model.getShowEvent());
        stage.close();

    }


}
