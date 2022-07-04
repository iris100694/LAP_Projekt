package com.lap.roomplaningsystem.controller.deleteController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EquipmentOnDeleteController extends BaseController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;

    private Equipment equipment;



    @FXML
    void initialize() {
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'equipmentOnDelete-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'equipmentOnDelete-view.fxml'.";

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(e -> e.getEquipmentID() == model.getSelectedEquipmentProperty().getEquipmentID()).findAny();

        if(optionalEquipment.isPresent()){
            equipment = optionalEquipment.get();
            numberLabel.setText("A" + equipment.getEquipmentID());
            descriptionLabel.setText(equipment.getDescription());
        }

    }

    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(numberLabel);
    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws Exception {
        model.setSelectedEquipmentProperty(null);

        if(deleteEventByJDBC()){
            model.getDataholder().deleteEquipment(equipment);
            model.getDataholder().updateUsers();
        }

        closeStage(numberLabel);

    }

    private boolean deleteEventByJDBC() throws Exception{
        return Dataholder.equipmentRepositoryJDBC.delete(equipment);
    }

}
