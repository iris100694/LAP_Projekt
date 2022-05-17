package com.lap.roomplaningsystem.controller.addController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.EquipmentMatcher;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.matcher.RoomMatcher;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RoomEquipmentOnAddController extends BaseController {


    @FXML
    private ComboBox<Equipment> equipmentComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private ComboBox<Room> roomComboBox;

    @FXML
    private Button saveButton;


    @FXML
    void initialize() {
        assert equipmentComboBox != null : "fx:id=\"equipmentComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnAdd-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'roomEquipmentDetailOnAdd-view.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnAdd-view.fxml'.";
        assert roomComboBox != null : "fx:id=\"roomComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnAdd-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'roomEquipmentDetailOnAdd-view.fxml'.";


        locationComboBox.setItems(model.getDataholder().getLocations());
        locationComboBox.getSelectionModel().select(0);

        List<Room> rooms = model.getDataholder().getRooms().stream().filter(r -> r.getLocation().getDescription().equals(locationComboBox.getValue().getDescription())).toList();
        roomComboBox.setItems(FXCollections.observableList(rooms));
        roomComboBox.getSelectionModel().select(0);

        List<Equipment> notAvailableEquipments = model.getDataholder().getRoomEquipments().stream().map(RoomEquipment::getEquipment).toList();
        System.out.println(notAvailableEquipments);
        List<Equipment> equipments = model.getDataholder().getEquipments();
        equipments.removeIf(e -> notAvailableEquipments.stream().anyMatch(equipment -> equipment.getEquipmentID() == e.getEquipmentID()));

        System.out.println(equipments);
        equipmentComboBox.setItems(FXCollections.observableList(equipments));
        equipmentComboBox.getSelectionModel().select(0);

        locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location t1) {
                List<Room> rooms = model.getDataholder().getRooms().stream().filter(r -> r.getLocation().getDescription().equals(locationComboBox.getValue().getDescription())).toList();
                roomComboBox.setItems(FXCollections.observableList(rooms));
                roomComboBox.getSelectionModel().select(0);
            }
        });

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

        roomComboBox.setConverter(new StringConverter<Room>() {
            @Override
            public String toString(Room room) {
                return room == null ? "":room.getDescription();
            }

            @Override
            public Room fromString(String s) {
                RoomMatcher roomMatcher = new RoomMatcher(locationComboBox.getValue());
                return roomMatcher.matchByString(s, model.getDataholder().getRooms());
            }
        });

        equipmentComboBox.setConverter(new StringConverter<Equipment>() {
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


    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        RoomEquipment roomEquipment = Dataholder.roomEquipmentRepositoryJDBC.add(roomComboBox.getValue(), equipmentComboBox.getValue());
        model.getDataholder().addRoomEquipment(roomEquipment);

        Stage detailStage = (Stage) locationComboBox.getScene().getWindow();
        detailStage.close();
    }

}
