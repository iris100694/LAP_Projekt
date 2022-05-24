package com.lap.roomplaningsystem.controller.deleteController;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RoomOnDeleteController extends BaseController {



    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label locationLabel;

    @FXML
    private Label sizeLabel;



    @FXML
    void initialize() {
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'roomDetailOnDelete-view.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'roomDetailOnDelete-view.fxml'.";
        assert locationLabel != null : "fx:id=\"locationLabel\" was not injected: check your FXML file 'roomDetailOnDelete-view.fxml'.";
        assert sizeLabel != null : "fx:id=\"sizeLabel\" was not injected: check your FXML file 'roomDetailOnDelete-view.fxml'.";


        Optional<Room> optionalRoom = model.getDataholder().getRooms().stream().filter(r -> r.getRoomID() == model.getSelectedRoomProperty().getRoomID()).findAny();

        if(optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            descriptionLabel.setText("R" + room.getRoomID() + " " + room.getDescription());
            locationLabel.setText(room.getLocation().getDescription());
            sizeLabel.setText(String.valueOf(room.getMaxPersons()));

            if(room.getPhoto() != null){
                imageView.setImage(new Image(new ByteArrayInputStream(room.getPhoto())));
            }
        }
    }

    @FXML
    void onNoButtonClicked(MouseEvent event) {
        Stage stage = (Stage) descriptionLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onYesButtonClicked(MouseEvent event) throws Exception {

        Optional <Room> optionalRoom = model.getDataholder().getRooms().stream().filter(r -> r.getRoomID() == model.getSelectedRoomProperty().getRoomID()).findAny();
        model.setSelectedProgramProperty(null);

        optionalRoom.ifPresent(r -> {
            try {
                boolean isDeleted = Dataholder.roomRepositoryJDBC.delete(r);
                if(isDeleted){
                    model.getDataholder().deleteRoom(r);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Optional<ObservableList<Event>> optionalEvents = Dataholder.eventRepositoryJDBC.readAll();
        Optional<ObservableList<RoomEquipment>> optionalRoomEquipments = Dataholder.roomEquipmentRepositoryJDBC.readAll();
        optionalEvents.ifPresent(events -> model.getDataholder().setEvents(events));
        optionalRoomEquipments.ifPresent(roomEquipments -> model.getDataholder().setRoomEquipments(roomEquipments));

        Stage stage = (Stage) descriptionLabel.getScene().getWindow();
        stage.close();


    }

}
