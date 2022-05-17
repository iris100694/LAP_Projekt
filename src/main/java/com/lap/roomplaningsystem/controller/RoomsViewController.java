package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.filterBoxes.FilterBox;
import com.lap.roomplaningsystem.filterBoxes.FilterCheckBox;
import com.lap.roomplaningsystem.filter.Roomfilter;
import com.lap.roomplaningsystem.model.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

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

    private final Roomfilter filter = new Roomfilter();


    @FXML
    private TextField searchField;


    @FXML
    void initialize() throws SQLException {
        if(model.getUser() != null){
            loginLabel.setText("Logout");
            loginLabel.setOnMouseClicked(this::onLogoutLabelClicked);
        }

        initFilterItems();

        roomTable.setItems(model.getDataholder().getRooms());

        roomNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("R" + String.valueOf(dataFeatures.getValue().getRoomID())));
        roomTitleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        roomSizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMaxPersons()));
        roomLocationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLocation().getDescription()));


        roomTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            try {
                if(nv != null){
                    model.setSelectedRoomProperty(nv);
                    showNewView(Constants.PATH_TO_ROOM_DETAIL_VIEW);
//                    Platform.runLater( ()-> {  roomTable.getSelectionModel().clearSelection();  });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void initFilterItems() throws SQLException {
        ArrayList<ObservableList<String>> list = Dataholder.roomRepositoryJDBC.listsForChoiceBox(Constants.CALL_LISTS_FOR_CHOICEBOX_ROOM_FILTER);

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
                                try {
                                    Optional<ObservableList<Room>> rooms = filter.filterValue(Dataholder.roomRepositoryJDBC, box.getChoiceBox().getId(), box.getValue());
                                    initRoomTable(rooms.orElse(null));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } else{
                            try {
                                Optional<ObservableList<Room>> rooms = filter.filterValue(Dataholder.roomRepositoryJDBC, box.getChoiceBox().getId(), box.getValue());
                                initRoomTable(rooms.orElse(null));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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

                try {
                    Optional<ObservableList<Room>> rooms = filter.filterValueWithImage(Dataholder.roomRepositoryJDBC, newValue);
                    initRoomTable(rooms.orElse(null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }





    private void initRoomTable(ObservableList<Room> rooms) {
        roomTable.setItems(rooms);
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

    @FXML
    private void onSearch(KeyEvent keyEvent) throws Exception {
        String searchFor = searchField.getText();
        ObservableList<Room> searchResults = FXCollections.observableArrayList();
        Optional<ObservableList<Room>> rooms = filter.getTableByFilterState(Dataholder.roomRepositoryJDBC);

        if(rooms.isPresent()) {
            if (!searchFor.equals("")) {
                for (Room r : rooms.get()) {
                    if (("R" + String.valueOf(r.getRoomID())).toLowerCase().contains(searchFor.toLowerCase()) || r.getDescription().toLowerCase().contains(searchFor.toLowerCase())
                            || String.valueOf(r.getMaxPersons()).toLowerCase().contains(searchFor.toLowerCase()) || r.getLocation().getDescription().toLowerCase().contains(searchFor.toLowerCase())) {
                        searchResults.add(r);
                    }
                }

                initRoomTable(searchResults);
            } else {

                initRoomTable(rooms.get());
            }
        }
    }
}
