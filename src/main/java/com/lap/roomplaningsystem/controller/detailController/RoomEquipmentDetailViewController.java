package com.lap.roomplaningsystem.controller.detailController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.RoomEquipment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RoomEquipmentDetailViewController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteRoomEquipment;

    @FXML
    private Button editRoomEquipment;

    @FXML
    private Label roomEquipmentDetailViewEquipmentLabel;

    @FXML
    private Label roomEquipmentDetailViewNumberLabel;

    @FXML
    private Label roomEquipmentDetailViewRoomLabel;

    @FXML
    private Label roomEquipmentDetailViewLocationLabel;



    @FXML
    void initialize() {
        assert deleteRoomEquipment != null : "fx:id=\"deleteRoomEquipment\" was not injected: check your FXML file 'roomEquipmentDetail-view.fxml'.";
        assert editRoomEquipment != null : "fx:id=\"editRoomEquipment\" was not injected: check your FXML file 'roomEquipmentDetail-view.fxml'.";
        assert roomEquipmentDetailViewEquipmentLabel != null : "fx:id=\"roomEquipmentDetailViewEquipmentLabel\" was not injected: check your FXML file 'roomEquipmentDetail-view.fxml'.";
        assert roomEquipmentDetailViewNumberLabel != null : "fx:id=\"roomEquipmentDetailViewNumberLabel\" was not injected: check your FXML file 'roomEquipmentDetail-view.fxml'.";
        assert roomEquipmentDetailViewRoomLabel != null : "fx:id=\"roomEquipmentDetailViewRoomLabel\" was not injected: check your FXML file 'roomEquipmentDetail-view.fxml'.";

        Optional<RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(roomEquipment -> roomEquipment == model.getSelectedRoomEquipmentProperty()).findAny();

        if(optionalRoomEquipment.isPresent()) {
            RoomEquipment roomEquipment = optionalRoomEquipment.get();


            roomEquipmentDetailViewNumberLabel.setText("RA" + String.valueOf(roomEquipment.getRoomEquipmentID()));
            roomEquipmentDetailViewRoomLabel.setText(roomEquipment.getRoom().getDescription());
            roomEquipmentDetailViewLocationLabel.setText(roomEquipment.getRoom().getLocation().getDescription());
            roomEquipmentDetailViewEquipmentLabel.setText(roomEquipment.getEquipment().getDescription());
        }
    }



    @FXML
    void onRoomEquipmentDeleteButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_ROOMEQUIPMENT_ON_DELETE_VIEW);

        Stage detailStage = (Stage) deleteRoomEquipment.getScene().getWindow();
        detailStage.close();
    }

    @FXML
    void onRoomEquipmentEditButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_ROOMEQUIPMENT_UPDATE_VIEW);

        Stage detailStage = (Stage) editRoomEquipment.getScene().getWindow();
        detailStage.close();
    }

}
