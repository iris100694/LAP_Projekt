package com.lap.roomplaningsystem.controller.updateController;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;
import java.util.Optional;

import com.lap.roomplaningsystem.app.Constants;
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


public class RoomOnUpdateController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label errorLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private TextField maxPersonsInput;

    @FXML
    private Label numberLabel;

    private Room room;

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;
    byte[] photo = null;


    @FXML
    void initialize() {
        Optional<Room> optionalRoom = model.getDataholder().getRooms().stream().filter(room -> room == model.getSelectedRoomProperty()).findAny();

        if (optionalRoom.isPresent()){
            room = optionalRoom.get();

            numberLabel.setText("R" + String.valueOf(room.getRoomID()));
            descriptionInput.setText(room.getDescription());
            maxPersonsInput.setText(String.valueOf(room.getMaxPersons()));
            locationComboBox.setValue(room.getLocation());

            if(room.getPhoto() != null){
                imageView.setImage(new Image(new ByteArrayInputStream(room.getPhoto())));
            }

            LocationConverter converter = new LocationConverter();
            converter.setConverter(locationComboBox);

        }

    }


    @FXML
    void onUpdateImageButtonClicked(ActionEvent event) throws IOException {
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());
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
        room.setDescription(descriptionInput.getText());
        room.setMaxPersons(Integer.parseInt(maxPersonsInput.getText()));
        room.setPhoto(photo != null ? photo: null);

        if (validateFields()) {

            boolean isUpdated = updateRoomByJDBC();

            if (isUpdated) {
                showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                int index = model.getDataholder().getRooms().indexOf(room);
                model.getDataholder().updateRoom(index, room);
                model.getDataholder().updateEvents();
                model.getDataholder().updateRoomEquipments();
                closeStage(errorLabel);
            }


        }

    }

    private boolean validateFields() {
        return !emptyFields() && explicitDescription() && validInteger();
    }

    private boolean validInteger() {
        try{
            Integer.parseInt(maxPersonsInput.getText());
            return true;
        } catch (Exception e){
            errorLabel.setText("Bitte für maximale Personenzahl eine ganze Zahl eingeben!");
            return false;
        }
    }

    private boolean explicitDescription() {
        List<Room> rooms = model.getDataholder().getRooms().stream().filter(r-> r !=room).toList();
        boolean explicit = rooms.stream().noneMatch(r-> r.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText("Raumbezeichung bereits vergeben!");
        }

        return explicit;
    }

    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText())  || isBlank(maxPersonsInput.getText());

        if(empty){
            errorLabel.setText("Bitte Felder ausfüllen!");
        }

        return empty;
    }

    private boolean updateRoomByJDBC() throws Exception {
        return Dataholder.roomRepositoryJDBC.update(room);
    }



}
