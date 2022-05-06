package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Program;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProgramOnUpdateController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField programDescriptionInput;

    @FXML
    private Label programNumberLabel;

    @FXML
    private Button saveProgram;



    @FXML
    void initialize() {
        assert programDescriptionInput != null : "fx:id=\"programDescriptionInput\" was not injected: check your FXML file 'programDetailOnUpdate-view.fxml'.";
        assert programNumberLabel != null : "fx:id=\"programNumberLabel\" was not injected: check your FXML file 'programDetailOnUpdate-view.fxml'.";
        assert saveProgram != null : "fx:id=\"saveProgram\" was not injected: check your FXML file 'programDetailOnUpdate-view.fxml'.";

        initView();
    }

    private void initView() {
        Program p = model.getShowProgram();

        programNumberLabel.setText("P" + String.valueOf(p.getProgramID()));
        programDescriptionInput.setText(p.getDescription());
    }

    @FXML
    void onProgramSaveButtonClicked(MouseEvent event) {

    }

}
