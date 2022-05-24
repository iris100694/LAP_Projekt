package com.lap.roomplaningsystem.controller.deleteController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Event;
import javafx.beans.property.SimpleIntegerProperty;
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


        Optional<Event> optionalEvent = model.getDataholder().getEvents().stream().filter(e -> e == model.getSelectedEventProperty()).findAny();

        if(optionalEvent.isPresent()){
            deleteLabel.setText("V" + String.valueOf(optionalEvent.get().getEventID()) + "  " + optionalEvent.get().getCourse().getTitle() + "  " + optionalEvent.get().getCourse().getProgram().getDescription());
            eventDateLabel.setText(String.valueOf(optionalEvent.get().getDate()));
            eventStartLabel.setText(String.valueOf(optionalEvent.get().getStartTime()));
            eventEndLabel.setText(String.valueOf(optionalEvent.get().getEndTime()));
            eventRoomLabel.setText(optionalEvent.get().getRoom().getDescription());
            eventLocationLabel.setText(optionalEvent.get().getRoom().getLocation().getDescription());
        }


    }


    @FXML
    void onNoButtonClicked(MouseEvent event) {
        Stage stage = (Stage) deleteLabel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void onYesButtonClicked(MouseEvent event) {
        Optional <Event> optionalEvent = model.getDataholder().getEvents().stream().filter(e -> e == model.getSelectedEventProperty()).findAny();
        model.setSelectedEventProperty(null);

        optionalEvent.ifPresent(e -> {
            try {
                boolean isDeleted = Dataholder.eventRepositoryJDBC.delete(e);

                if(isDeleted){
                    model.getDataholder().deleteEvent(e);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            Stage stage = (Stage) deleteLabel.getScene().getWindow();
            stage.close();

        });


    }
}
