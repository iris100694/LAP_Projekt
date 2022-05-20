package com.lap.roomplaningsystem.controller.updateController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.EquipmentMatcher;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.matcher.RoomMatcher;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class RoomEquipmentOnUpdateController extends BaseController {

    @FXML
    private ComboBox<Equipment> equipmentComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private Label numberLabel;

    @FXML
    private ComboBox<Room> roomComboBox;

    @FXML
    private Button saveButton;



    @FXML
    void initialize() {
        assert equipmentComboBox != null : "fx:id=\"equipmentComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert locationComboBox != null : "fx:id=\"locationComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert roomComboBox != null : "fx:id=\"roomComboBox\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'roomEquipmentDetailOnUpdate-view.fxml'.";

        initView();
        setConverterOnChoiceBoxes();
    }


    private void initView() {
        Optional<RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(roomEquipment -> roomEquipment == model.getSelectedRoomEquipmentProperty()).findAny();
        if(optionalRoomEquipment.isPresent()){
            RoomEquipment roomEquipment = optionalRoomEquipment.get();
            numberLabel.setText("RA" + String.valueOf(roomEquipment.getRoomEquipmentID()));

            locationComboBox.setItems(model.getDataholder().getLocations());
            roomComboBox.setItems(availableRooms(roomEquipment.getRoom().getLocation()));

            List<Equipment> notAvailableEquipments = model.getDataholder().getRoomEquipments().stream().map(RoomEquipment::getEquipment).toList();
            //TODO: Undo all pointer in this Project !!!
            List<Equipment> equipments = new ArrayList<>(model.getDataholder().getEquipments());
            equipments.removeIf(e -> e.getEquipmentID() != roomEquipment.getEquipment().getEquipmentID() && notAvailableEquipments.stream().anyMatch(equipment -> equipment.getEquipmentID() == e.getEquipmentID()));
            equipmentComboBox.setItems(FXCollections.observableList(equipments));

            locationComboBox.getSelectionModel().select(roomEquipment.getRoom().getLocation());
            roomComboBox.getSelectionModel().select(roomEquipment.getRoom());
            equipmentComboBox.getSelectionModel().select(roomEquipment.getEquipment());
        }


        locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location newLocation) {
                roomComboBox.setItems(availableRooms(newLocation));
                roomComboBox.getSelectionModel().select(null);
                roomComboBox.setPromptText("Raum");
            }
        });

    }

    private void setConverterOnChoiceBoxes() {
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
                return room == null ? "" : room.getDescription();
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
        Optional<RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(roomEquipment-> roomEquipment.getRoomEquipmentID() == model.getSelectedRoomEquipmentProperty().getRoomEquipmentID()).findAny();

        if(optionalRoomEquipment.isPresent()) {
            RoomEquipment roomEquipment = optionalRoomEquipment.get();

            roomEquipment.setRoom(roomComboBox.getValue());
            roomEquipment.setEquipment(equipmentComboBox.getValue());

            boolean isUpdated = Dataholder.roomEquipmentRepositoryJDBC.update(roomEquipment);

            if(isUpdated){
                showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                int index = model.getDataholder().getRoomEquipments().indexOf(roomEquipment);
                model.getDataholder().updateRoomEquipment(index, roomEquipment);
            }
        }


        Stage detailStage = (Stage) numberLabel.getScene().getWindow();
        detailStage.close();

    }






}
