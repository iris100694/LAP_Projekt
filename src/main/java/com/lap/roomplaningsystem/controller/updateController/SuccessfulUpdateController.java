package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SuccessfulUpdateController extends BaseController {


    @FXML
    private Label label;



    @FXML
    void initialize() {
    }

    @FXML
    void onAcceptButtonClicked(ActionEvent event) {
        closeStage(label);
    }

}