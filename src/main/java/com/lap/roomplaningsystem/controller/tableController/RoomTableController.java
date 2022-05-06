package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Room;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RoomTableController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Room, String> roomLocationColumn;

    @FXML
    private TableColumn<Room, String> roomNumberColumn;

    @FXML
    private TableColumn<Room, Integer> roomSizeColumn;

    @FXML
    private TableView<Room> roomTableView;

    @FXML
    private TableColumn<Room, String> roomTitleColumn;

    private ObjectProperty<Room> selectedRoom = new SimpleObjectProperty<>();

    @FXML
    void initialize() {
        assert roomLocationColumn != null : "fx:id=\"roomLocationColumn\" was not injected: check your FXML file 'roomTable.fxml'.";
        assert roomNumberColumn != null : "fx:id=\"roomNumberColumn\" was not injected: check your FXML file 'roomTable.fxml'.";
        assert roomSizeColumn != null : "fx:id=\"roomSizeColumn\" was not injected: check your FXML file 'roomTable.fxml'.";
        assert roomTableView != null : "fx:id=\"roomTableView\" was not injected: check your FXML file 'roomTable.fxml'.";
        assert roomTitleColumn != null : "fx:id=\"roomTitleColumn\" was not injected: check your FXML file 'roomTable.fxml'.";

        initRoomTable(model.getDataholder().getRooms());
    }

    private void initRoomTable(ObservableList<Room> rooms) {

        roomTableView.setItems(rooms);

        roomNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("R" + String.valueOf(dataFeatures.getValue().getRoomID())));
        roomTitleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        roomSizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMaxPersons()));
        roomLocationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLocation().getDescription()));


        roomTableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedRoom.set(nv));

        selectedRoom.addListener((o, ov, nv) -> {
            model.setShowRoom(nv);
            try {
                showNewView(Constants.PATH_TO_ROOM_DETAIL_VIEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

}

