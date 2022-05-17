package com.lap.roomplaningsystem.controller.addController;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
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

public class RoomOnAddController extends BaseController {


    @FXML
    private Button addImage;

    @FXML
    private TextField descriptionInput;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private TextField maxPersons;

    @FXML
    private ImageView roomImageView;

    @FXML
    private Button saveRoom;

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;
    @FXML
    private Label errorLabel;
    boolean error;


    @FXML
    void initialize() {
        assert addImage != null : "fx:id=\"addImage\" was not injected: check your FXML file 'roomDetailOnAdd-view.fxml'.";
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'roomDetailOnAdd-view.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'roomDetailOnAdd-view.fxml'.";
        assert maxPersons != null : "fx:id=\"maxPersons\" was not injected: check your FXML file 'roomDetailOnAdd-view.fxml'.";
        assert roomImageView != null : "fx:id=\"roomImageView\" was not injected: check your FXML file 'roomDetailOnAdd-view.fxml'.";
        assert saveRoom != null : "fx:id=\"saveRoom\" was not injected: check your FXML file 'roomDetailOnAdd-view.fxml'.";

        locationComboBox.setItems(model.getDataholder().getLocations());
        locationComboBox.getSelectionModel().select(0);

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

        fileChooser.setTitle("Raumbild hinzufügen");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        descriptionInput.setOnKeyTyped(room -> {
            List<String> descriptions = model.getDataholder().getRooms().stream().map(Room::getDescription).toList();
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
    void onAddImageButtonClicked(MouseEvent event) throws FileNotFoundException {
      File file = fileChooser.showOpenDialog(addImage.getScene().getWindow());
      inputStream = new FileInputStream(file);
      roomImageView.setImage(new Image(inputStream));


    }

    @FXML
    void onSaveButtonClicked(MouseEvent event) throws SQLException {

        if(isBlank(descriptionInput.getText())  || isBlank(maxPersons.getText())){
            errorLabel.setText("Bitte alle Felder ausfüllen!");

        } else if (!error){
            Room room = Dataholder.roomRepositoryJDBC.add(descriptionInput.getText(), locationComboBox.getValue(), maxPersons.getText(),
                    inputStream);
            model.getDataholder().addRoom(room);

            Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
            detailStage.close();
        }
    }



}
