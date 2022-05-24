package com.lap.roomplaningsystem.controller.deleteController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LocationOnDeleteController extends BaseController {

    @FXML
    private Label adressLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label postCodeLabel;



    @FXML
    void initialize() {
        assert adressLabel != null : "fx:id=\"adressLabel\" was not injected: check your FXML file 'locationDetailOnDelete-view.fxml'.";
        assert cityLabel != null : "fx:id=\"cityLabel\" was not injected: check your FXML file 'locationDetailOnDelete-view.fxml'.";
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'locationDetailOnDelete-view.fxml'.";
        assert postCodeLabel != null : "fx:id=\"postCodeLabel\" was not injected: check your FXML file 'locationDetailOnDelete-view.fxml'.";

        Optional<Location> optionalLocation = model.getDataholder().getLocations().stream().filter(l -> l.getLocationID() == model.getSelectedLocationProperty().getLocationID()).findAny();

        if(optionalLocation.isPresent()){
            Location location = optionalLocation.get();
            descriptionLabel.setText("S" + location.getLocationID() + " " + location.getDescription());
            adressLabel.setText(location.getAdress());
            postCodeLabel.setText(location.getPostCode());
            cityLabel.setText(location.getCity());

        }

    }

    @FXML
    void onNoButtonClicked(MouseEvent event) {
        Stage stage = (Stage) descriptionLabel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void onYesButtonClicked(MouseEvent event) throws Exception {

        Optional <Location> optionalLocation = model.getDataholder().getLocations().stream().filter(l -> l.getLocationID() == model.getSelectedLocationProperty().getLocationID()).findAny();
        model.setSelectedLocationProperty(null);

        optionalLocation.ifPresent(l -> {
            try {
                boolean isDeleted = Dataholder.locationRepositoryJDBC.delete(l);

                if(isDeleted){
                    model.getDataholder().deleteLocation(l);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Optional<ObservableList<Room>> optionalRooms = Dataholder.roomRepositoryJDBC.readAll();
        Optional<ObservableList<RoomEquipment>> optionalRoomEquipments = Dataholder.roomEquipmentRepositoryJDBC.readAll();
        Optional<ObservableList<Event>> optionalEvents = Dataholder.eventRepositoryJDBC.readAll();
        optionalRooms.ifPresent(rooms -> model.getDataholder().setRooms(rooms));
        optionalRoomEquipments.ifPresent(roomEquipments -> model.getDataholder().setRoomEquipments(roomEquipments));
        optionalEvents.ifPresent(events -> model.getDataholder().setEvents(events));

        Stage stage = (Stage) descriptionLabel.getScene().getWindow();
        stage.close();

    }
}
