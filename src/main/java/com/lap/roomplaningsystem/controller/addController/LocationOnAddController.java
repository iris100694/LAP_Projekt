package com.lap.roomplaningsystem.controller.addController;



import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


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
    void initialize() {

    }


    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){
            Location location = addLocationByJDBC();

            if(location != null){
                model.getDataholder().addLocation(location);
                closeStage(errorLabel);
            }


        }
    }

    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }


    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText()) || isBlank(adressInput.getText()) || isBlank(cityInput.getText()) || isBlank(postCodeInput.getText());

        if(empty){
            errorLabel.setText("Bitte Felder ausfüllen!");
        }

        return empty;
    }

    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getLocations().stream().noneMatch(l-> l.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText("Standortbezeichnung bereits vergeben!");
        }

        return explicit;
    }

    private Location addLocationByJDBC() throws Exception {
        return Dataholder.locationRepositoryJDBC.add(descriptionInput.getText(), adressInput.getText(),  postCodeInput.getText(), cityInput.getText());
    }
}
