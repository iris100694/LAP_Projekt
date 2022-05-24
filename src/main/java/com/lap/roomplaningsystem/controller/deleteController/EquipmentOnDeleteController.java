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

public class EquipmentOnDeleteController extends BaseController {



    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;



    @FXML
    void initialize() {
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'equipmentDetailOnDelete-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'equipmentDetailOnDelete-view.fxml'.";

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(e -> e.getEquipmentID() == model.getSelectedEquipmentProperty().getEquipmentID()).findAny();

        if(optionalEquipment.isPresent()){
            Equipment equipment = optionalEquipment.get();
            numberLabel.setText("A" + equipment.getEquipmentID());
            descriptionLabel.setText(equipment.getDescription());
        }

    }

    @FXML
    void onNoButtonClicked(MouseEvent event) {
        Stage stage = (Stage) descriptionLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onYesButtonClicked(MouseEvent event) throws Exception {

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(e -> e.getEquipmentID() == model.getSelectedEquipmentProperty().getEquipmentID()).findAny();
        model.setSelectedProgramProperty(null);

        optionalEquipment.ifPresent(e -> {
            try {
                boolean isDeleted = Dataholder.equipmentRepositoryJDBC.delete(e);
                if(isDeleted){
                    model.getDataholder().deleteEquipment(e);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Optional<ObservableList<RoomEquipment>> optionalRoomEquipments = Dataholder.roomEquipmentRepositoryJDBC.readAll();
        optionalRoomEquipments.ifPresent(roomEquipments -> model.getDataholder().setRoomEquipments(roomEquipments));

        Stage stage = (Stage) descriptionLabel.getScene().getWindow();
        stage.close();

    }

}
