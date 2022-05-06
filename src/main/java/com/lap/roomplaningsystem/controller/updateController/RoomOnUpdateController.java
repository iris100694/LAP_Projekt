package com.lap.roomplaningsystem.controller.updateController;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.matcher.RoomMatcher;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class RoomOnUpdateController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button roomChangeImage;

    @FXML
    private TextField roomDescriptionInput;

    @FXML
    private TextField roomMaxPersons;

    @FXML
    private ImageView roomImageView;

    @FXML
    private ComboBox<Location> roomLocationComboBox;

    @FXML
    private Label roomNumberLabel;

    @FXML
    private Button saveRoom;

    @FXML
    void initialize() {
        assert roomChangeImage != null : "fx:id=\"roomChangeImage\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert roomDescriptionInput != null : "fx:id=\"roomDescriptionInput\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert roomImageView != null : "fx:id=\"roomImageView\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert roomLocationComboBox != null : "fx:id=\"roomLocationComboBox\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert roomNumberLabel != null : "fx:id=\"roomNumberLabel\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert saveRoom != null : "fx:id=\"saveRoom\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";
        assert roomMaxPersons != null : "fx:id=\"roomMaxPersons\" was not injected: check your FXML file 'roomDetailOnUpdate-view.fxml'.";

        initView();
    }

    private void initView() {
        Room r = model.getShowRoom();

        roomNumberLabel.setText("R" + String.valueOf(r.getRoomID()));
        roomDescriptionInput.setText(r.getDescription());
        roomMaxPersons.setText(String.valueOf(r.getMaxPersons()));

        if(r.getPhoto() != null){
            roomImageView.setImage(new Image(new ByteArrayInputStream(r.getPhoto())));
        }

        roomLocationComboBox.setItems(model.getDataholder().getLocations());
        roomLocationComboBox.getSelectionModel().select(r.getLocation());

        roomLocationComboBox.setConverter(new StringConverter<Location>() {
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
    }

    @FXML
    void onRoomSaveButtonClicked(MouseEvent event) {

    }

    @FXML
    void onRoomChangeImageButtonClicked(MouseEvent event) {

    }

}
