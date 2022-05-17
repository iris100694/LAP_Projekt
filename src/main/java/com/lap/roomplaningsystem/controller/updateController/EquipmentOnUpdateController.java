package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EquipmentOnUpdateController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField equipmentDescriptionInput;

    @FXML
    private Label equipmentNumberLabel;

    @FXML
    private Button saveEquipment;



    @FXML
    void initialize() {
        assert equipmentDescriptionInput != null : "fx:id=\"equipmentDescriptionInput\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";
        assert equipmentNumberLabel != null : "fx:id=\"equipmentNumberLabel\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";
        assert saveEquipment != null : "fx:id=\"saveEquipment\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(equipment -> equipment == model.getSelectedEquipmentProperty()).findAny();

        if(optionalEquipment.isPresent()){
            Equipment e = optionalEquipment.get();

            equipmentNumberLabel.setText("A" + String.valueOf(e.getEquipmentID()));
            equipmentDescriptionInput.setText(e.getDescription());
        }
    }



    @FXML
    void onEquipmentSaveButtonClicked(MouseEvent event) {

    }

}
