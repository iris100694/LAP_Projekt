package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.EquipmentMatcher;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.matcher.RoomMatcher;
import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.RoomEquipment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class RoomEquipmentOnUpdateController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Equipment> roomEquipmentEquipmentComboBox;

    @FXML
    private ComboBox<Location> roomEquipmentLocationComboBox;

    @FXML
    private Label roomEquipmentNumberLabel;

    @FXML
    private ComboBox<Room> roomEquipmentRoomComboBox;

    @FXML
    private Button saveRoomEquipment;

    @FXML
    void onRoomEquipmentSaveButtonClicked(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert roomEquipmentEquipmentComboBox != null : "fx:id=\"roomEquipmentEquipmentComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert roomEquipmentLocationComboBox != null : "fx:id=\"roomEquipmentLocationComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert roomEquipmentNumberLabel != null : "fx:id=\"roomEquipmentNumberLabel\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert roomEquipmentRoomComboBox != null : "fx:id=\"roomEquipmentRoomComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert saveRoomEquipment != null : "fx:id=\"saveRoomEquipment\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";

        initView();
        setConverterOnChoiceBoxes();
    }


    private void initView() {
        Optional<RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(roomEquipment -> roomEquipment == model.getSelectedRoomEquipmentProperty()).findAny();

        if(optionalRoomEquipment.isPresent()){
            RoomEquipment roomEquipment = optionalRoomEquipment.get();

            roomEquipmentNumberLabel.setText("RA" + String.valueOf(roomEquipment.getRoomEquipmentID()));

            roomEquipmentLocationComboBox.setItems(model.getDataholder().getLocations());
            roomEquipmentRoomComboBox.setItems(availableRooms(roomEquipment.getRoom().getLocation()));
            roomEquipmentEquipmentComboBox.setItems(model.getDataholder().getEquipments());

            roomEquipmentLocationComboBox.getSelectionModel().select(roomEquipment.getRoom().getLocation());
            roomEquipmentRoomComboBox.getSelectionModel().select(roomEquipment.getRoom());
            roomEquipmentEquipmentComboBox.getSelectionModel().select(roomEquipment.getEquipment());
        }


        roomEquipmentLocationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location newLocation) {
                roomEquipmentRoomComboBox.setItems(availableRooms(newLocation));
                roomEquipmentRoomComboBox.getSelectionModel().select(null);
                roomEquipmentRoomComboBox.setPromptText("Raum");
            }
        });

    }

    private void setConverterOnChoiceBoxes() {
        roomEquipmentLocationComboBox.setConverter(new StringConverter<Location>() {
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

        roomEquipmentRoomComboBox.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room == null ? "" : room.getDescription();
            }

            @Override
            public Room fromString(String s) {
                RoomMatcher roomMatcher = new RoomMatcher(roomEquipmentLocationComboBox.getValue());
                return roomMatcher.matchByString(s, model.getDataholder().getRooms());
            }
        });

        roomEquipmentEquipmentComboBox.setConverter(new StringConverter<Equipment>() {
            @Override
            public String toString(Equipment equipment) {
                return equipment.getDescription();
            }

            @Override
            public Equipment fromString(String s) {
                EquipmentMatcher equipmentMatcher = new EquipmentMatcher();
                return equipmentMatcher.matchByString(s, model.getDataholder().getEquipments());
            }
        });
    }








}
