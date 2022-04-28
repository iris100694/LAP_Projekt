package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.filter.FilterBox;
import com.lap.roomplaningsystem.filter.FilterCheckBox;
import com.lap.roomplaningsystem.filter.Roomfilter;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class RoomsViewController extends BaseController{

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private ChoiceBox<String> roomDescriptionChoiceBox;

    @FXML
    private ChoiceBox<String> roomEquipmentChoiceBox;

    @FXML
    private CheckBox roomImageCheckBox;

    @FXML
    private ChoiceBox<String> roomLocationChoiceBox;

    @FXML
    private ChoiceBox<String> roomMembersChoiceBox;

    @FXML
    private ChoiceBox<String> roomNumberChoiceBox;

    @FXML
    private TableColumn<Room, String> roomLocationColumn;

    @FXML
    private TableColumn<Room, String> roomNumberColumn;

    @FXML
    private TableColumn<Room, Integer> roomSizeColumn;

    @FXML
    private TableColumn<Room, String> roomTitleColumn;

    @FXML
    private Label loginLabel;

    Roomfilter filter = new Roomfilter();
    private final ObjectProperty<Room> selectedRoom = new SimpleObjectProperty<>();



    @FXML
    void initialize() {
        if(model.getUser() != null){
            loginLabel.setText("Logout");
            loginLabel.setOnMouseClicked(this::onLogoutLabelClicked);
        }

        initFilterItems();
        initRoomTable(model.getDatamodel().getRooms());

    }

    private void initFilterItems() {
        ArrayList<ObservableList<String>> list = DatabaseUtility.listsForChoiceBox(Constants.CALL_LISTS_FOR_CHOICEBOX_ROOM_FILTER);

        filter.addFilterBox(new FilterBox(roomNumberChoiceBox, "Raum-Nr." , list.get(0)));
        filter.addFilterBox(new FilterBox(roomDescriptionChoiceBox, "Raumbeschreibung" , list.get(1)));
        filter.addFilterBox(new FilterBox(roomMembersChoiceBox, "max. Personen" , list.get(2)));
        filter.addFilterBox(new FilterBox(roomLocationChoiceBox, "Standort" , list.get(3)));
        filter.addFilterBox(new FilterBox(roomEquipmentChoiceBox, "Ausstattung" , list.get(4)));
        filter.setImageCheckBox(new FilterCheckBox(roomImageCheckBox));

        setFilterListenerChoiceBox();
        setFilterListenerCheckbox();

    }

   private void setFilterListenerChoiceBox() {
        filter.getFilterBoxes().addListener(new ListChangeListener<FilterBox>() {
            @Override
            public void onChanged(Change<? extends FilterBox> c) {

                while(c.next()) {
                    FilterBox box = filter.getFilterBoxes().get(c.getFrom());

                    if (c.wasUpdated()) {

                        if(!isBlank(box.getValue())){
                            if(!box.getValue().equals(box.getDefaultValue())){
                                initRoomTable(filter.filterValue(box.getChoiceBox().getId(), box.getValue()));
                            }
                        } else{
                            initRoomTable(filter.filterValue(box.getChoiceBox().getId(), box.getValue()));
                            box.getChoiceBox().setValue(box.getDefaultValue());
                        }


                    }
                }

            }
        });

    }

    private void setFilterListenerCheckbox() {
        filter.getImageCheckBox().checkProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                initRoomTable(filter.filterValueWithImage(newValue));
            }
        });
    }


    private boolean isBlank(String value) {
        return value.equals("");
    }


    private void initRoomTable(ObservableList<Room> rooms) {

        roomTable.setItems(rooms);

        roomNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" + String.valueOf(dataFeatures.getValue().getRoomID())));
        roomTitleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        roomSizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMaxPersons()));
        roomLocationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLocation().getDescription()));


        roomTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedRoom.set(nv));

        selectedRoom.addListener((o, ov, nv) -> {
            try {
                showRoomDetailView(nv);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void showRoomDetailView(Room room) throws IOException {
        model.setShowRoom(room);

        FXMLLoader fxmlLoader = new FXMLLoader(RoomplaningsystemApplication.class.getResource(Constants.PATH_TO_ROOM_DETAIL_VIEW));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("R" + room.getRoomID());
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private void onLoginLabelClicked(MouseEvent mouseEvent) {
        model.setPathToView(Constants.PATH_TO_LOGIN_VIEW);
    }

    private void onLogoutLabelClicked(MouseEvent mouseEvent){
        model.setAuthorization("standard");
        model.setUser(null);
        loginLabel.setText("Login");
        loginLabel.setOnMouseClicked(this::onLoginLabelClicked);
    }
}
