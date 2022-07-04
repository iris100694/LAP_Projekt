package com.lap.roomplaningsystem.controller.detailController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Equipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.lap.roomplaningsystem.controller.BaseController.model;

public class EquipmentDetailViewController extends BaseController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;

    private Equipment equipment;

    @FXML
    void initialize() {

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(equipment -> equipment == model.getSelectedEquipmentProperty()).findAny();

        if(optionalEquipment.isPresent()) {
            equipment = optionalEquipment.get();
            numberLabel.setText("A" + String.valueOf(equipment.getEquipmentID()));
            descriptionLabel.setText(equipment.getDescription());
        }

    }



    @FXML
    void onDeleteButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EQUIPMENT_ON_DELETE_VIEW);
        closeStage(numberLabel);
    }

    @FXML
    void onUpdateButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EQUIPMENT_UPDATE_VIEW);
        closeStage(numberLabel);
    }



}
