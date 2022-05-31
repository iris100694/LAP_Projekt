package com.lap.roomplaningsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
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
    private Button newButton;
    @FXML
    private ImageView profilImage;


    @FXML
    void initialize() throws IOException {
        if(model.getUser()!= null){
            setProfilImage(profilImage);
        }

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
        items.add("Veranstaltungen");
        items.add("Kurse");
        items.add("Programme");
        items.add("Standorte");
        items.add("Ausstattung");
        items.add("Ausstattung - in Verwendung");

        return items;
    }

    private void vaildateTable(String newValue) throws IOException {
        switch(newValue){
            case "Benutzer" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_USER_TABLE_VIEW);
            case "Räume" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_ROOM_TABLE_VIEW);
            case "Veranstaltungen" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_EVENT_TABLE_VIEW);
            case "Kurse" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_COURSE_TABLE_VIEW);
            case "Programme" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_PROGRAM_TABLE_VIEW);
            case "Standorte" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_LOCATION_TABLE_VIEW);
            case "Ausstattung" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_EQUIPMENT_TABLE_VIEW);
            case "Ausstattung - in Verwendung" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_ROOMEQUIPMENT_TABLE_VIEW);
        }
    }

    @FXML
    void onLogoutButtonClicked(ActionEvent actionEvent) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }


    @FXML
    private void onNewButtonClicked(MouseEvent mouseEvent) throws IOException {
        switch(choiceBoxForTables.getValue()){
            case "Benutzer" -> showNewView(Constants.PATH_TO_USER_ADD_VIEW);
            case "Räume" -> showNewView(Constants.PATH_TO_ROOM_ADD_VIEW);
            case "Veranstaltungen" -> model.setPathToView(Constants.PATH_TO_CREATE_EVENT_VIEW);
            case "Kurse" -> showNewView(Constants.PATH_TO_COURSE_ADD_VIEW);
            case "Programme" -> showNewView(Constants.PATH_TO_PROGRAM_ADD_VIEW);
            case "Standorte" -> showNewView(Constants.PATH_TO_LOCATION_ADD_VIEW);
            case "Ausstattung" -> showNewView(Constants.PATH_TO_EQUIPMENT_ADD_VIEW);
            case "Ausstattung - in Verwendung" -> showNewView(Constants.PATH_TO_ROOMEQUIPMENT_ADD_VIEW);

        }
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }
}