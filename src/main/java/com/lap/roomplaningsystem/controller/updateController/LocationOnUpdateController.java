package com.lap.roomplaningsystem.controller.updateController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.model.Room;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LocationOnUpdateController extends BaseController {

    @FXML
    private TextField adressInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField postCodeInput;
    @FXML
    private Label errorLabel;

    boolean descriptionIsChange;
    boolean adressIsChange;
    boolean postCodeIsChange;
    boolean cityIsChange;
    boolean validChange;


    @FXML
    void initialize() {
        assert adressInput != null : "fx:id=\"adressInput\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert cityInput != null : "fx:id=\"cityInput\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert postCodeInput != null : "fx:id=\"postCodeInput\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";

        Optional<Location> optionalLocation = model.getDataholder().getLocations().stream().filter(location -> location == model.getSelectedLocationProperty()).findAny();

        if(optionalLocation.isPresent()){
            Location l = optionalLocation.get();
            numberLabel.setText("S" + String.valueOf(l.getLocationID()));
            descriptionInput.setText(l.getDescription());
            adressInput.setText(l.getAdress());
            postCodeInput.setText(l.getPostCode());
            cityInput.setText(l.getCity());

            descriptionInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(l.getDescription())){
                        descriptionIsChange = true;
                    } else {
                        descriptionIsChange = false;
                    }
                }
            });

            adressInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(l.getAdress())){
                        adressIsChange = true;
                    }else {
                        adressIsChange = false;
                    }
                }
            });

            postCodeInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(l.getPostCode())){
                        postCodeIsChange = true;
                    } else{

                        postCodeIsChange = false;

                    }
                }
            });

            cityInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(l.getCity())){
                        cityIsChange = true;
                    }else {
                        cityIsChange= false;
                    }
                }
            });
        }


    }



    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if(isBlank(descriptionInput.getText()) || isBlank(adressInput.getText()) || isBlank(cityInput.getText()) || isBlank(postCodeInput.getText())){
            errorLabel.setText("Bitte alle Felder ausfüllen!");
        } else if(!descriptionIsChange && !adressIsChange && !postCodeIsChange && !cityIsChange) {
            errorLabel.setText("Es wurden keine Änderungen vorgenommen!");
        } else if(descriptionIsChange){
            boolean exist = model.getDataholder().getLocations().stream().anyMatch(l-> l.getDescription().equals(descriptionInput.getText()));
            if(exist){
                errorLabel.setText("Bezeichung bereits vergeben!");
            } else{
                validChange = true;
            }
        } else {
            validChange = true;
        }

        if(validChange){
            Optional<Location> optionalLocation = model.getDataholder().getLocations().stream().filter(l-> l.getLocationID() == model.getSelectedLocationProperty().getLocationID()).findAny();

            if(optionalLocation.isPresent()){
                Location location = optionalLocation.get();

                if(descriptionIsChange){
                    location.setDescription(descriptionInput.getText());
                }

                if(adressIsChange){
                    location.setAdress(adressInput.getText());
                }

                if(postCodeIsChange){
                    location.setPostCode(postCodeInput.getText());
                }

                if(cityIsChange){
                    location.setCity(cityInput.getText());
                }

                boolean isUpdated = Dataholder.locationRepositoryJDBC.update(location);

                if(isUpdated){
                    showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                    int index = model.getDataholder().getLocations().indexOf(location);
                    model.getDataholder().updateLocation(index, location);
                }

            }


            //TODO: Change this two rows with a better method
            Optional<ObservableList<Room>> optionalRooms = Dataholder.roomRepositoryJDBC.readAll();
            optionalRooms.ifPresent(rooms -> model.getDataholder().setRooms(rooms));


            Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
            detailStage.close();
        }

    }

}
