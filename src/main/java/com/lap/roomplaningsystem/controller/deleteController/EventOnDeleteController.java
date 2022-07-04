package com.lap.roomplaningsystem.controller.deleteController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Event;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EventOnDeleteController extends BaseController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label endLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label roomLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Label numberLabel;

    private Event event;


    @FXML
    void initialize() {


        Optional<Event> optionalEvent = model.getDataholder().getEvents().stream().filter(e -> e == model.getSelectedEventProperty()).findAny();

        if(optionalEvent.isPresent()){
            event = optionalEvent.get();

            numberLabel.setText("V" + String.valueOf(event.getEventID()) + "  " + event.getCourse().getTitle() + "  " + event.getCourse().getProgram().getDescription());
            dateLabel.setText(String.valueOf(event.getDate()));
            startLabel.setText(String.valueOf(event.getStartTime()));
            endLabel.setText(String.valueOf(event.getEndTime()));
            roomLabel.setText(event.getRoom().getDescription());
            locationLabel.setText(event.getRoom().getLocation().getDescription());
        }

    }


    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(numberLabel);
    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws SQLException {
        model.setSelectedEventProperty(null);

        if(deleteEventByJDBC()){
            model.getDataholder().deleteEvent(this.event);
        }

        closeStage(numberLabel);

    }

    private boolean deleteEventByJDBC() throws SQLException {
        return Dataholder.eventRepositoryJDBC.delete(event);
    }
}
