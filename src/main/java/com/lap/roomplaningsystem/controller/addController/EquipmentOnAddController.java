package com.lap.roomplaningsystem.controller.addController;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Equipment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EquipmentOnAddController extends BaseController {


    @FXML
    private TextField descriptionInput;

    @FXML
    private Label errorLabel;

    @FXML
    private Button saveEquipment;


    @FXML
    void initialize() {
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'equipmentDetailOnAdd-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'equipmentDetailOnAdd-view.fxml'.";
        assert saveEquipment != null : "fx:id=\"saveEquipment\" was not injected: check your FXML file 'equipmentDetailOnAdd-view.fxml'.";


    }

    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if (isBlank(descriptionInput.getText())) {
            errorLabel.setText("Bitte Feld ausfüllen!");
        } else if(model.getDataholder().getEquipments().stream().anyMatch(e-> e.getDescription().equals(descriptionInput.getText()))){
            errorLabel.setText("Ausstattungsbezeichnung bereits vergeben!");
        }else{
            Equipment equipment = Dataholder.equipmentRepositoryJDBC.add(descriptionInput.getText());

            model.getDataholder().addEquipment(equipment);

            Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
            detailStage.close();
        }
    }

}
