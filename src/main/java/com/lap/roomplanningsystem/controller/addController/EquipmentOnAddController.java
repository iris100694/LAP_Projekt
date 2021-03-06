package com.lap.roomplanningsystem.controller.addController;



import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Equipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.constant.Constable;


public class EquipmentOnAddController extends BaseController {


    @FXML
    private TextField descriptionInput;

    @FXML
    private Label errorLabel;


    @FXML
    void initialize() {
    }

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){
            Equipment equipment = addEquipmentByJDBC();

            if(equipment != null){
                model.getDataholder().addEquipment(equipment);
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
            errorLabel.setText(Constants.EMPTY_FIELDS);
        }

        return empty;
    }

    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getEquipments().stream().noneMatch(e-> e.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.EQUIPMENT_DESCRIPTION_NOT_ALLOWED);
        }

        return explicit;
    }

    private Equipment addEquipmentByJDBC() throws Exception {
        return Dataholder.equipmentRepositoryJDBC.add(descriptionInput.getText());
    }
}
