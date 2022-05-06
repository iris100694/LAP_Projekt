package com.lap.roomplaningsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class StammdataViewController extends BaseController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ChoiceBox<String> choiceBoxForTables;
    @FXML
    private BorderPane tableBorderPane;

    @FXML
    void initialize() throws IOException {
        loadFXMLInBorderPaneCenter(Constants.PATH_TO_USER_TABLE_VIEW);


        choiceBoxForTables.setItems(initItems());
        choiceBoxForTables.setValue("Benutzer");

        choiceBoxForTables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    vaildateTable(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void loadFXMLInBorderPaneCenter(String fxmlPath) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
        System.out.println(fxmlPath);
        tableBorderPane.setCenter(view);
    }

    private ObservableList<String> initItems() {
        ObservableList<String> items = FXCollections.observableArrayList();

        items.add("Benutzer");
        items.add("Räume");
        items.add("Events");
        items.add("Kurse");
        items.add("Programme");
        items.add("Standorte");
        items.add("Ausstattung");
        items.add("Ausstattung - in Verwendung");

        return items;
    }

    private void vaildateTable(String newValue) throws IOException {
        switch(newValue){
            case "Räume" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_ROOM_TABLE_VIEW);
            case "Events" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_EVENT_TABLE_VIEW);
            case "Kurse" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_COURSE_TABLE_VIEW);
            case "Programme" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_PROGRAM_TABLE_VIEW);
            case "Standorte" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_LOCATION_TABLE_VIEW);
            case "Ausstattung" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_EQUIPMENT_TABLE_VIEW);
            case "Ausstattung - in Verwendung" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_ROOMEQUIPMENT_TABLE_VIEW);
        }
    }

    @FXML
    void onLogoutLabelClicked(MouseEvent event) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }



}