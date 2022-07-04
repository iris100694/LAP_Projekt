package com.lap.roomplaningsystem.controller.updateController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EquipmentOnUpdateController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label numberLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    private Equipment equipment;

    @FXML
    void initialize() {

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(equipment -> equipment == model.getSelectedEquipmentProperty()).findAny();

        if(optionalEquipment.isPresent()){
            equipment = optionalEquipment.get();

            numberLabel.setText("A" + String.valueOf(equipment.getEquipmentID()));
            descriptionInput.setText(equipment.getDescription());

        }
    }



    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){
            equipment.setDescription(descriptionInput.getText());

            boolean isUpdated = updateEquipmentByJDBC();

            if(isUpdated){
                showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                int index = model.getDataholder().getEquipments().indexOf(equipment);
                model.getDataholder().updateEquipment(index, equipment);

                model.getDataholder().updateRoomEquipments();
                closeStage(errorLabel);
            }
        }
    }



    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }


    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText());

        if(empty){
            errorLabel.setText("Bitte Feld ausfüllen!");
        }

        return empty;
    }

    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getEquipments().stream().noneMatch(e-> e.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText("Ausstattungsbezeichnung bereits vergeben!");
        }

        return explicit;
    }

    private boolean updateEquipmentByJDBC() throws Exception {
        return Dataholder.equipmentRepositoryJDBC.update(equipment);
    }
}
