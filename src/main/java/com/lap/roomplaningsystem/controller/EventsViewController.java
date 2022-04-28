package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.filter.EventFilter;
import com.lap.roomplaningsystem.filter.FilterBox;
import com.lap.roomplaningsystem.filter.FilterCheckBox;
import com.lap.roomplaningsystem.model.DatabaseUtility;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Room;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;

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

    private EventFilter filter = new EventFilter();
    private final ObjectProperty<Event> selectedEvent = new SimpleObjectProperty<>();

    @FXML
    void initialize() {
        if(model.getUser() != null){
            loginLabel.setText("Logout");
            loginLabel.setOnMouseClicked(this::onLogoutLabelClicked);
        }

        initFilter();
        initEventTable(model.getDatamodel().getEvents());
    }

    private void initFilter() {
        ArrayList<ObservableList<String>> list = DatabaseUtility.listsForChoiceBox(Constants.CALL_LISTS_FOR_CHOICEBOX_EVENT_FILTER);
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
                                initEventTable(filter.filterValue(box.getChoiceBox().getId(), box.getValue()));
                            }
                        } else{
                            initEventTable(filter.filterValue(box.getChoiceBox().getId(), box.getValue()));
                            box.getChoiceBox().setValue(box.getDefaultValue());
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
        eventTitleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCourse().title() + "   " + dataFeatures.getValue().getCourse().program().description()));
        eventDateColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDate().toString()));
        eventStartColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getStartTime().toString()));
        eventEndColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEndTime().toString()));

        eventTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedEvent.set(nv));

        selectedEvent.addListener((o, ov, nv) -> {
            try {
                showEventDetailView(nv);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }


    private void showEventDetailView(Event event) throws IOException {
        model.setShowEvent(event);

        FXMLLoader fxmlLoader = new FXMLLoader(RoomplaningsystemApplication.class.getResource(Constants.PATH_TO_EVENT_DETAIL_VIEW));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("V" + event.getEventID());
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
