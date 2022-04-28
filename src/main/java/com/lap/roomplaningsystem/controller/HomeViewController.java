package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.app.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class HomeViewController extends BaseController {


    @FXML
    private Label loginLabel;

    @FXML
    void initialize() {
        if(model.getUser() != null){
            loginLabel.setText("Logout");
            loginLabel.setOnMouseClicked(this::onLogoutLabelClicked);
        }



    }

    private void onLogoutLabelClicked(MouseEvent mouseEvent){
        model.setAuthorization("standard");
        model.setUser(null);
        loginLabel.setText("Login");
        loginLabel.setOnMouseClicked(this::onLoginLabelClicked);
    }

    @FXML
    private void onLoginLabelClicked(MouseEvent mouseEvent) {
        model.setPathToView(Constants.PATH_TO_LOGIN_VIEW);
    }
}
