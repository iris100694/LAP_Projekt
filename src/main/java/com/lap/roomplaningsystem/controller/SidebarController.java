package com.lap.roomplaningsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SidebarController extends BaseController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label sidebarCreateEventLabel;
    @FXML
    private Label sidebarStammdataLabel;


    @FXML
    void initialize() {
        model.authorizationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldAuthorization, String newAuthorization) {
                if (newAuthorization != null) {
                    updateSidebarMenu(newAuthorization);
                }
            }
        });
    }

    private void updateSidebarMenu(String newAuthorization) {
        switch(newAuthorization){
            case "coach" -> sidebarCoachAuthorization();
            case "admin" -> sidebarAdminAuthorization();
            default -> sidebarStandardAuthorization();
        }
    }

    private void sidebarStandardAuthorization() {
        sidebarCreateEventLabel.setVisible(false);
        sidebarStammdataLabel.setVisible(false);

    }

    private void sidebarAdminAuthorization() {
        sidebarCreateEventLabel.setVisible(true);
        sidebarStammdataLabel.setVisible(true);
    }

    private void sidebarCoachAuthorization() {
        sidebarCreateEventLabel.setVisible(true);
    }


    @FXML
    void onMouseClickedCreateEvents(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_CREATE_EVENT_VIEW);
    }

    @FXML
    void onMouseClickedEvents(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_EVENT_VIEW);

    }

    @FXML
    void onMouseClickedHome(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }

    @FXML
    void onMouseClickedRooms(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_ROOM_VIEW);
    }

    @FXML
    void onMouseClickedStammdata(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_STAMMDATA_VIEW);

    }


    @FXML
    private void testLogin(MouseEvent mouseEvent) throws SQLException {
        model.validateLogin("admin", "test");
    }
}
