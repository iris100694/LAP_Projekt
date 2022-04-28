package com.lap.roomplaningsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class StammdataViewController extends BaseController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }

    @FXML
    void onLogoutLabelClicked(MouseEvent event) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }



}