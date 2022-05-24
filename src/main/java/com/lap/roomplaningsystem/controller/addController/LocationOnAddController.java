package com.lap.roomplaningsystem.controller.addController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Program;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LocationOnAddController extends BaseController {


    @FXML
    private TextField adressInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField postCodeInput;

    @FXML
    private Button saveButton;

    boolean error;



    @FXML
    void initialize() {
        assert adressInput != null : "fx:id=\"adressInput\" was not injected: check your FXML file 'locationDetailOnAdd-view.fxml'.";
        assert cityInput != null : "fx:id=\"cityInput\" was not injected: check your FXML file 'locationDetailOnAdd-view.fxml'.";
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'locationDetailOnAdd-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'locationDetailOnAdd-view.fxml'.";
        assert postCodeInput != null : "fx:id=\"postCodeInput\" was not injected: check your FXML file 'locationDetailOnAdd-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'locationDetailOnAdd-view.fxml'.";

        descriptionInput.setOnKeyTyped(room -> {
            List<String> descriptions = model.getDataholder().getLocations().stream().map(Location::getDescription).toList();
            boolean exist = descriptions.stream().anyMatch(description -> description.equals(descriptionInput.getText()));
            if (exist) {
                error = true;
                errorLabel.setText("Raumbezeichnung bereits vergeben!");
            } else {
                error = false;
                errorLabel.setText("");
            }
        });

    }


    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if(isBlank(descriptionInput.getText()) || isBlank(adressInput.getText()) || isBlank(cityInput.getText()) || isBlank(postCodeInput.getText()) || error){
            errorLabel.setText("Bitte alle Felder ausfüllen!");
        } else if (!error){
            Location location = Dataholder.locationRepositoryJDBC.add(descriptionInput.getText(), adressInput.getText(),  postCodeInput.getText(), cityInput.getText());
            model.getDataholder().addLocation(location);

            Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
            detailStage.close();
        }

    }

}
