package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Location;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LocationTableController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Location, String> locationAdressColumn;

    @FXML
    private TableColumn<Location, String> locationCityColumn;

    @FXML
    private TableColumn<Location, String> locationDescriptionColumn;

    @FXML
    private TableColumn<Location, String> locationNumberColumn;

    @FXML
    private TableColumn<Location, String> locationPostCodeColumn;

    @FXML
    private TableView<Location> locationTableView;

    private ObjectProperty<Location> selectedEvent = new SimpleObjectProperty<>();

    @FXML
    void initialize() {
        assert locationAdressColumn != null : "fx:id=\"locationAdressColumn\" was not injected: check your FXML file 'locationTable.fxml'.";
        assert locationCityColumn != null : "fx:id=\"locationCityColumn\" was not injected: check your FXML file 'locationTable.fxml'.";
        assert locationDescriptionColumn != null : "fx:id=\"locationDescriptionColumn\" was not injected: check your FXML file 'locationTable.fxml'.";
        assert locationNumberColumn != null : "fx:id=\"locationNumberColumn\" was not injected: check your FXML file 'locationTable.fxml'.";
        assert locationPostCodeColumn != null : "fx:id=\"locationPostCodeColumn\" was not injected: check your FXML file 'locationTable.fxml'.";
        assert locationTableView != null : "fx:id=\"locationTableView\" was not injected: check your FXML file 'locationTable.fxml'.";

        initLocationTable(model.getDataholder().getLocations());
    }

    private void initLocationTable(ObservableList<Location> locations) {
        locationTableView.setItems(locations);

        locationNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("S" + String.valueOf(dataFeatures.getValue().getLocationID())));
        locationDescriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        locationAdressColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getAdress()));
        locationPostCodeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getPostCode()));
        locationCityColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCity()));

        locationTableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedEvent.set(nv));

        selectedEvent.addListener((o, ov, nv) -> {
            model.setShowLocation(nv);
            try {
                showNewView(Constants.PATH_TO_LOCATION_DETAIL_VIEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
