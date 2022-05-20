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

    boolean descriptionIsChange;

    @FXML
    void initialize() {
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'equipmentDetailOnUpdate-view.fxml'.";

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(equipment -> equipment == model.getSelectedEquipmentProperty()).findAny();

        if(optionalEquipment.isPresent()){
            Equipment e = optionalEquipment.get();

            numberLabel.setText("A" + String.valueOf(e.getEquipmentID()));
            descriptionInput.setText(e.getDescription());

            descriptionInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(e.getDescription())){
                        descriptionIsChange = true;
                    }else {
                        descriptionIsChange= false;
                    }
                }
            });
        }


    }



    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if(isBlank(descriptionInput.getText())){
            errorLabel.setText("Bitte Feld ausfüllen!");
        } else if(!descriptionIsChange) {
            errorLabel.setText("Es wurden keine Änderungen vorgenommen!");
        }else{
            boolean exist = model.getDataholder().getEquipments().stream().anyMatch(e-> e.getDescription().equals(descriptionInput.getText()));
            if(exist){
                errorLabel.setText("Bezeichung bereits vergeben!");
            } else {

                System.out.println("Hello");
                Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(e-> e.getEquipmentID() == model.getSelectedEquipmentProperty().getEquipmentID()).findAny();

                if(optionalEquipment.isPresent()){
                    Equipment equipment = optionalEquipment.get();


                    equipment.setDescription(descriptionInput.getText());



                    boolean isUpdated = Dataholder.equipmentRepositoryJDBC.update(equipment);

                    if(isUpdated){
                        showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                        int index = model.getDataholder().getEquipments().indexOf(equipment);
                        model.getDataholder().updateEquipment(index, equipment);
                    }

                }


                //TODO: Change this two rows with a better method
                Optional<ObservableList<RoomEquipment>> optionalRoomEquipments = Dataholder.roomEquipmentRepositoryJDBC.readAll();
                optionalRoomEquipments.ifPresent(roomEquipments -> model.getDataholder().setRoomEquipments(roomEquipments));


                Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
                detailStage.close();
            }

        }
    }
}
