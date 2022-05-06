package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Event;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EventTableController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Event, String> eventDateColumn;

    @FXML
    private TableColumn<Event, String> eventTitleColumn;

    @FXML
    private TableColumn<Event, String> eventEndColumn;

    @FXML
    private TableColumn<Event, String> eventNumberColumn;

    @FXML
    private TableColumn<Event, String> eventStartColumn;

    @FXML
    private TableView<Event> eventTableView;

    private ObjectProperty<Event> selectedEvent = new SimpleObjectProperty<>();

    @FXML
    void initialize() {
        assert eventDateColumn != null : "fx:id=\"eventDateColumn\" was not injected: check your FXML file 'eventTable.fxml'.";
        assert eventTitleColumn != null : "fx:id=\"eventDescriptionColumn\" was not injected: check your FXML file 'eventTable.fxml'.";
        assert eventEndColumn != null : "fx:id=\"eventEndColumn\" was not injected: check your FXML file 'eventTable.fxml'.";
        assert eventNumberColumn != null : "fx:id=\"eventNumberColumn\" was not injected: check your FXML file 'eventTable.fxml'.";
        assert eventStartColumn != null : "fx:id=\"eventStartColumn\" was not injected: check your FXML file 'eventTable.fxml'.";
        assert eventTableView != null : "fx:id=\"eventTableView\" was not injected: check your FXML file 'eventTable.fxml'.";

        initEventTable(model.getDataholder().getEvents());
    }

    private void initEventTable(ObservableList<Event> events) {
        eventTableView.setItems(events);

        eventNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" + String.valueOf(dataFeatures.getValue().getEventID())));
        eventTitleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCourse().getTitle() + "   " + dataFeatures.getValue().getCourse().getProgram().getDescription()));
        eventDateColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDate().toString()));
        eventStartColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getStartTime().toString()));
        eventEndColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEndTime().toString()));

        eventTableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedEvent.set(nv));

        selectedEvent.addListener((o, ov, nv) -> {
            model.setShowEvent(nv);

            try {
                showNewView(Constants.PATH_TO_EVENT_DETAIL_VIEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
