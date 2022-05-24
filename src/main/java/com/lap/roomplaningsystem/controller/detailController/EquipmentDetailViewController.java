package com.lap.roomplaningsystem.controller.detailController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Equipment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.lap.roomplaningsystem.controller.BaseController.model;

public class EquipmentDetailViewController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteEquipment;

    @FXML
    private Button editEquipment;

    @FXML
    private Label equipmentDescriptionLabel;

    @FXML
    private Label equipmentDetailViewNumberLabel;



    @FXML
    void initialize() {
        assert deleteEquipment != null : "fx:id=\"deleteEquipment\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";
        assert editEquipment != null : "fx:id=\"editEquipment\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";
        assert equipmentDescriptionLabel != null : "fx:id=\"equipmentDescriptionLabel\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";
        assert equipmentDetailViewNumberLabel != null : "fx:id=\"equipmentDetailViewNumberLabel\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(equipment -> equipment == model.getSelectedEquipmentProperty()).findAny();

        if(optionalEquipment.isPresent()) {
            Equipment e = optionalEquipment.get();
            equipmentDetailViewNumberLabel.setText("A" + String.valueOf(e.getEquipmentID()));
            equipmentDescriptionLabel.setText(e.getDescription());
        }

    }



    @FXML
    void onEquipmentDeleteButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EQUIPMENT_ON_DELETE_VIEW);

        Stage detailStage = (Stage) equipmentDescriptionLabel.getScene().getWindow();
        detailStage.close();
    }

    @FXML
    void onEquipmentEditButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EQUIPMENT_UPDATE_VIEW);

        Stage detailStage = (Stage) editEquipment.getScene().getWindow();
        detailStage.close();
    }



}
