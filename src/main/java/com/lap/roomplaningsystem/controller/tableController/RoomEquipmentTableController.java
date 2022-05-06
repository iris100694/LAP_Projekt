package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.RoomEquipment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RoomEquipmentTableController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<RoomEquipment, String> roomEquipmentEquipmentColumn;

    @FXML
    private TableColumn<RoomEquipment, String> roomEquipmentNumberColumn;

    @FXML
    private TableColumn<RoomEquipment, String> roomEquipmentRoomColumn;

    @FXML
    private TableView<RoomEquipment> roomEquipmentTableView;

    private ObjectProperty<RoomEquipment> selectedEvent = new SimpleObjectProperty<>();

    @FXML
    void initialize() {
        assert roomEquipmentEquipmentColumn != null : "fx:id=\"roomEquipmentEquipmentColumn\" was not injected: check your FXML file 'roomEquipmentTable.fxml'.";
        assert roomEquipmentNumberColumn != null : "fx:id=\"roomEquipmentNumberColumn\" was not injected: check your FXML file 'roomEquipmentTable.fxml'.";
        assert roomEquipmentRoomColumn != null : "fx:id=\"roomEquipmentRoomColumn\" was not injected: check your FXML file 'roomEquipmentTable.fxml'.";
        assert roomEquipmentTableView != null : "fx:id=\"roomEquipmentTableView\" was not injected: check your FXML file 'roomEquipmentTable.fxml'.";

        initRoomEquipmentTable(model.getDataholder().getRoomEquipments());
    }

    private void initRoomEquipmentTable(ObservableList<RoomEquipment> roomEquipments) {
        roomEquipmentTableView.setItems(roomEquipments);

        roomEquipmentNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("RA" + String.valueOf(dataFeatures.getValue().getRoomEquipmentID())));
        roomEquipmentRoomColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getRoom().getDescription()));
        roomEquipmentEquipmentColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEquipment().getDescription()));


        roomEquipmentTableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedEvent.set(nv));

        selectedEvent.addListener((o, ov, nv) -> {
            model.setShowRoomEquipment(nv);
            try {
                showNewView(Constants.PATH_TO_ROOMEQUIPMENT_DETAIL_VIEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
