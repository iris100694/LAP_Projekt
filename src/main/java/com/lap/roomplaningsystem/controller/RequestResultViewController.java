package com.lap.roomplaningsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Result;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.utility.ResultUtility;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RequestResultViewController extends BaseController{


    @FXML
    private TableColumn<Result, String> descriptionColumn;

    @FXML
    private TableColumn<Result, String> locationColumn;

    @FXML
    private TableColumn<Result, String> numberColumn;

    @FXML
    private TableColumn<Result, Integer> sizeColumn;

    @FXML
    private TableColumn<Result, String> coachColumn;

    @FXML
    private TableView<Result> tableView;

    @FXML
    private Label label;





    @FXML
    void initialize() {

        ResultUtility resultUtility = new ResultUtility(model.getRequestResult(), model.getRequestResultCoaches());
        resultUtility.createResults();

        tableView.setItems(resultUtility.getResults());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" +dataFeatures.getValue().room().getRoomID()));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().room().getDescription()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().room().getMaxPersons()));
        locationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().room().getLocation().getDescription()));
        coachColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().coach() != null? dataFeatures.getValue().coach().getFirstname() + " " + dataFeatures.getValue().coach().getLastname() : ""));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) ->  {
            model.setSelectedResultProperty(nv);
            closeStage(label);
        });
    }


    @FXML
    void onReturnButtonClicked(ActionEvent event) throws IOException {
        closeStage(label);
        showNewView(Constants.PATH_TO_REQUEST_VIEW);
    }
}
