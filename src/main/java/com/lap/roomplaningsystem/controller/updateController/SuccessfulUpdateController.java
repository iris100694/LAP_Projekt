package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SuccessfulUpdateController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button acceptButton;



    @FXML
    void initialize() {
        assert acceptButton != null : "fx:id=\"acceptButton\" was not injected: check your FXML file 'successfulUpdate-view.fxml'.";


    }

    @FXML
    void onAcceptButtonClicked(MouseEvent event) {
        Stage detailStage = (Stage) acceptButton.getScene().getWindow();
        detailStage.close();
    }

}