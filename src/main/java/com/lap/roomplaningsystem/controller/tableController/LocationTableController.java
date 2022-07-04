package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LocationTableController extends BaseController {


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

    @FXML
    private TableColumn<Location, String> adressColumn;

    @FXML
    private TableColumn<Location, String> cityColumn;

    @FXML
    private TableColumn<Location, String> descriptionColumn;

    @FXML
    private TableColumn<Location, String> numberColumn;

    @FXML
    private TableColumn<Location, String> postCodeColumn;

    @FXML
    private TableView<Location> tableView;



    @FXML
    void initialize() {
        tableView.setItems(model.getDataholder().getLocations());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("S" + String.valueOf(dataFeatures.getValue().getLocationID())));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        adressColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getAdress()));
        postCodeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getPostCode()));
        cityColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCity()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedLocationProperty(nv);
                    showNewView(Constants.PATH_TO_LOCATION_DETAIL_VIEW);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedLocationProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location oldLocation, Location newLocation) {
                if(newLocation == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });
    }


}
