package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.filter.EventFilter;
import com.lap.roomplaningsystem.filterBoxes.FilterBox;

import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Event;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


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

public class EventsViewController extends BaseController{
    @FXML
    private Label loginLabel;
    @FXML
    private ChoiceBox<String> eventDescriptionChoiceBox;
    @FXML
    private ChoiceBox<String> eventEndChoiceBox;
    @FXML
    private ChoiceBox<String> eventDateChoiceBox;
    @FXML
    private ChoiceBox<String> eventNumberChoiceBox;
    @FXML
    private ChoiceBox<String> eventStartChoiceBox;
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> eventNumberColumn;
    @FXML
    private TableColumn<Event, String> eventTitleColumn;
    @FXML
    private TableColumn<Event, String> eventDateColumn;
    @FXML
    private TableColumn<Event, String> eventStartColumn;
    @FXML
    private TableColumn<Event, String> eventEndColumn;

    private final EventFilter filter = new EventFilter();
    private ObjectProperty<Event> selectedEvent = new SimpleObjectProperty<>();
    @FXML
    private TextField searchField;

    @FXML
    void initialize() throws SQLException {
        if(model.getUser() != null){
            loginLabel.setText("Logout");
            loginLabel.setOnMouseClicked(this::onLogoutLabelClicked);
        }

        initFilter();
        initEventTable(model.getDataholder().getEvents());
    }

    private void initFilter() throws SQLException {
        ArrayList<ObservableList<String>> list = Dataholder.eventRepositoryJDBC.listsForChoiceBox(Constants.CALL_LISTS_FOR_CHOICEBOX_EVENT_FILTER);
        ObservableList<String> times = createTimeValues();

        filter.addFilterBox(new FilterBox(eventNumberChoiceBox, "Nr." , list.get(0)));
        filter.addFilterBox(new FilterBox(eventDescriptionChoiceBox, "Veranstaltung" , list.get(1)));
        filter.addFilterBox(new FilterBox(eventDateChoiceBox, "Datum" , createDateValues(list.get(2))));
        filter.addFilterBox(new FilterBox(eventStartChoiceBox, "Beginn" , times));
        filter.addFilterBox(new FilterBox(eventEndChoiceBox, "Ende" , times));

        setFilterListenerChoiceBox();

    }

    private ObservableList<String> createTimeValues() {
        ObservableList<String> time = FXCollections.observableArrayList();
        time.add("");
        for(int i = 0; i<24; i++){
            if(i<10){
                time.add("0" + i + ":00:00");
            } else {
                time.add(i + ":00:00");
            }
        }

        return time;
    }

    private ObservableList<String> createDateValues(ObservableList<String> datetime) {
        ObservableList<String> dates = FXCollections.observableArrayList();

        for(String d : datetime){
            if(!isBlank(d)) {
                dates.add(d.substring(0, 10));
            }else {
                dates.add(d);
            }

        }

        return dates;

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
                                    Optional<ObservableList<Event>> events = filter.filterValue(Dataholder.eventRepositoryJDBC, box.getChoiceBox().getId(), box.getValue());
                                    initEventTable(events.orElse(null));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else{
                            try {
                                Optional<ObservableList<Event>> events = filter.filterValue(Dataholder.eventRepositoryJDBC, box.getChoiceBox().getId(), box.getValue());
                                initEventTable(events.orElse(null));
                                box.getChoiceBox().setValue(box.getDefaultValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean isBlank(String value) {
        return value.equals("");
    }



    private void initEventTable(ObservableList<Event> events) {
        eventTable.setItems(events);

        eventNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" + String.valueOf(dataFeatures.getValue().getEventID())));
        eventTitleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCourse().getTitle() + "   " + dataFeatures.getValue().getCourse().getProgram().getDescription()));
        eventDateColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDate().toString()));
        eventStartColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getStartTime().toString()));
        eventEndColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEndTime().toString()));

        eventTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedEvent.set(nv));

        selectedEvent.addListener((o, ov, nv) -> {
            try {
                model.setShowEvent(nv);
                showNewView(Constants.PATH_TO_EVENT_DETAIL_VIEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


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
        ObservableList<Event> searchResults = FXCollections.observableArrayList();
        Optional<ObservableList<Event>> events = filter.getTableByFilterState(Dataholder.eventRepositoryJDBC);

        if(events.isPresent()) {
            if (!searchFor.equals("")) {
                for (Event e : events.get()) {
                    if (("V" + String.valueOf(e.getEventID())).toLowerCase().contains(searchFor.toLowerCase()) || e.getCourse().getTitle().toLowerCase().contains(searchFor.toLowerCase()) ||
                            e.getCourse().getProgram().getDescription().toLowerCase().contains(searchFor.toLowerCase()) || String.valueOf(e.getDate()).toLowerCase().contains(searchFor.toLowerCase()) ||
                            String.valueOf(e.getStartTime()).toLowerCase().contains(searchFor.toLowerCase()) || String.valueOf(e.getEndTime()).toLowerCase().contains(searchFor.toLowerCase())) {
                        searchResults.add(e);
                    }
                }

                initEventTable(searchResults);
            } else {

                initEventTable(events.get());
            }
        }
    }

}
