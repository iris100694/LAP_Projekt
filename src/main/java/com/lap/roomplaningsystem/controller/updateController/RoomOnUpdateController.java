package com.lap.roomplaningsystem.controller.updateController;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.matcher.RoomMatcher;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RoomOnUpdateController extends BaseController {

    @FXML
    private Button changeImage;

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

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;

    boolean descriptionIsChange;
    boolean maxPersonsIsChange;
    boolean imageIsChange;
    boolean locationIsChange;
    boolean validChange;


    @FXML
    void initialize() {
        assert changeImage != null : "fx:id=\"changeImage\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";

        Optional<Room> optionalRoom = model.getDataholder().getRooms().stream().filter(room -> room == model.getSelectedRoomProperty()).findAny();

        if (optionalRoom.isPresent()){
            Room r = optionalRoom.get();

            numberLabel.setText("R" + String.valueOf(r.getRoomID()));
            descriptionInput.setText(r.getDescription());
            maxPersonsInput.setText(String.valueOf(r.getMaxPersons()));

            if(r.getPhoto() != null){
                imageView.setImage(new Image(new ByteArrayInputStream(r.getPhoto())));
            }

            locationComboBox.setItems(model.getDataholder().getLocations());
            locationComboBox.getSelectionModel().select(r.getLocation());

            locationComboBox.setConverter(new StringConverter<Location>() {
                @Override
                public String toString(Location location) {
                    return location.getDescription();
                }

                @Override
                public Location fromString(String s) {
                    LocationMatcher locationMatcher = new LocationMatcher();
                    return locationMatcher.matchByString(s, model.getDataholder().getLocations());
                }
            });

            descriptionInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(r.getDescription())){
                        descriptionIsChange = true;
                    }else {
                        descriptionIsChange= false;
                    }
                }
            });

            maxPersonsInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(r.getMaxPersons())){
                        maxPersonsIsChange = true;
                    }else {
                        maxPersonsIsChange= false;
                    }
                }
            });


        }

    }


    @FXML
    void onChangeImageButtonClicked(MouseEvent event)  {
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        try{
            inputStream = new FileInputStream(file);
            imageView.setImage(new Image(inputStream));
            imageIsChange = true;
        } catch (Exception e){
            System.out.println("Kein Bild ausgewählt!");
        }
        System.out.println(2);
    }

    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if(isBlank(descriptionInput.getText())  || isBlank(maxPersonsInput.getText())){
            errorLabel.setText("Bitte alle Felder ausfüllen!");

        } else if (!descriptionIsChange && !maxPersonsIsChange && !imageIsChange){
            errorLabel.setText("Es wurden keine Änderungen vorgenommen)");
        }else if(descriptionIsChange){
            boolean exist = model.getDataholder().getLocations().stream().anyMatch(l-> l.getDescription().equals(descriptionInput.getText()));
            if(exist){
                errorLabel.setText("Bezeichung bereits vergeben!");
            } else{
                if(maxPersonsIsChange){
                    validChange = checkMaxPersons();
                }
            }
        } else if (maxPersonsIsChange){
            validChange = checkMaxPersons();

        } else {
            validChange = true;
        }

        if(validChange){
            Optional<Room> optionalRoom = model.getDataholder().getRooms().stream().filter(l-> l.getRoomID() == model.getSelectedRoomProperty().getRoomID()).findAny();

            if(optionalRoom.isPresent()){
                Room room = optionalRoom.get();

                if(descriptionIsChange){
                    room.setDescription(descriptionInput.getText());
                }


                if(maxPersonsIsChange){
                    room.setMaxPersons(Integer.parseInt(maxPersonsInput.getText()));
                }

                if(imageIsChange){
                    try{
                        room.setPhoto(inputStream.readAllBytes());
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
                System.out.println(inputStream);
                boolean isUpdated = Dataholder.roomRepositoryJDBC.update(room, inputStream);

                if(isUpdated){
                    showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                    int index = model.getDataholder().getRooms().indexOf(room);
                    model.getDataholder().updateRoom(index, room);
                }

            }

            //TODO: Change this four rows with a better method
            Optional<ObservableList<Event>> optionalEvents = Dataholder.eventRepositoryJDBC.readAll();
            Optional<ObservableList<RoomEquipment>> optionalRoomEquipments = Dataholder.roomEquipmentRepositoryJDBC.readAll();
            optionalEvents.ifPresent(events -> model.getDataholder().setEvents(events));
            optionalRoomEquipments.ifPresent(roomEquipments -> model.getDataholder().setRoomEquipments(roomEquipments));

            Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
            detailStage.close();
        }
    }

    private boolean checkMaxPersons() {
        try{
            Integer.parseInt(maxPersonsInput.getText());
            return true;
        } catch (Exception e){
            errorLabel.setText("Bitte für maximale Personenzahl eine ganze Zahl eingeben!");
            return false;
        }
    }

}
