package com.lap.roomplaningsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Room;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RequestResultViewController extends BaseController{


    @FXML
    private TableColumn<Room, String> descriptionColumn;

    @FXML
    private TableColumn<Room, String> locationColumn;

    @FXML
    private TableColumn<Room, String> numberColumn;

    @FXML
    private TableColumn<Room, Integer> sizeColumn;

    @FXML
    private TableView<Room> tableView;



    @FXML
    void initialize() {
        assert descriptionColumn != null : "fx:id=\"descriptionColumn\" was not injected: check your FXML file 'requestResult-view.fxml'.";
        assert locationColumn != null : "fx:id=\"locationColumn\" was not injected: check your FXML file 'requestResult-view.fxml'.";
        assert numberColumn != null : "fx:id=\"numberColumn\" was not injected: check your FXML file 'requestResult-view.fxml'.";
        assert sizeColumn != null : "fx:id=\"sizeColumn\" was not injected: check your FXML file 'requestResult-view.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'requestResult-view.fxml'.";

        initRoomTable(model.getRequestResult());
    }

    private void initRoomTable(ObservableList<Room> rooms) {

        tableView.setItems(rooms);

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("R" + String.valueOf(dataFeatures.getValue().getRoomID())));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMaxPersons()));
        locationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLocation().getDescription()));


        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) ->  {
            model.setSelectedRequestResult(nv.getRoomID());

            Stage resultStage = (Stage) tableView.getScene().getWindow();
            resultStage.close();
        });


    }






    @FXML
    void onReturnButtonClicked(MouseEvent event) throws IOException {

        showNewView(Constants.PATH_TO_REQUEST_VIEW);

        Stage detailStage = (Stage) tableView.getScene().getWindow();
        detailStage.close();

    }




}
