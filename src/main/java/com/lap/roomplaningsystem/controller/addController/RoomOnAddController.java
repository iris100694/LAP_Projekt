package com.lap.roomplaningsystem.controller.addController;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;


import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.converter.LocationConverter;

import com.lap.roomplaningsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;


public class RoomOnAddController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private TextField maxPersons;

    @FXML
    private ImageView imageView;

    @FXML
    private Label errorLabel;

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;
    byte[] photo = null;




    @FXML
    void initialize() {

        locationComboBox.setItems(model.getDataholder().getLocations());
        locationComboBox.getSelectionModel().select(0);

        LocationConverter locationConverter = new LocationConverter();
        locationConverter.setConverter(locationComboBox);

        initFileChooser();
    }

    private void initFileChooser() {
        fileChooser.setTitle("Raumbild hinzufügen");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
    }


    @FXML
    void onSetImageButtonClicked(ActionEvent event) throws IOException {
        File file = fileChooser.showOpenDialog(descriptionInput.getScene().getWindow());

        try{
            inputStream = new FileInputStream(file);
            imageView.setImage(new Image(inputStream));
            photo = Files.newInputStream(Path.of(file.getAbsolutePath())).readAllBytes();
        } catch (Exception e){
            System.out.println("Kein Bild ausgewählt!");
        }
    }

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){

            Room room = addRoomByJDBC();

            if(room != null){
                model.getDataholder().addRoom(room);
                closeStage(errorLabel);
            }
        }
    }


    private boolean validateFields() {
        return !emptyFields() && explicitDescription() && validInteger();
    }

    private boolean validInteger() {
        try{
            Integer.parseInt(maxPersons.getText());
            return true;
        } catch (Exception e){
            errorLabel.setText("Bitte für maximale Personenzahl eine ganze Zahl eingeben!");
            return false;
        }
    }

    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getRooms().stream().noneMatch(r-> r.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText("Raumbezeichung bereits vergeben!");
        }

        return explicit;
    }

    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText())  || isBlank(maxPersons.getText());

        if(empty){
            errorLabel.setText("Bitte Felder ausfüllen!");
        }

        return empty;
    }

    private Room addRoomByJDBC() throws Exception {
        return Dataholder.roomRepositoryJDBC.add(descriptionInput.getText(), locationComboBox.getValue(), Integer.parseInt(maxPersons.getText()), photo);
    }


}
