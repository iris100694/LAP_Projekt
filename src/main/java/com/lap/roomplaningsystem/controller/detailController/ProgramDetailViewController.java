package com.lap.roomplaningsystem.controller.detailController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Program;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProgramDetailViewController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteProgram;

    @FXML
    private Button editProgram;

    @FXML
    private Label programDetailViewDescriptionLabel;

    @FXML
    private Label programDetailViewNumberLabel;



    @FXML
    void initialize() {
        assert deleteProgram != null : "fx:id=\"deletePorgram\" was not injected: check your FXML file 'programDetail-view.fxml'.";
        assert editProgram != null : "fx:id=\"editProgram\" was not injected: check your FXML file 'programDetail-view.fxml'.";
        assert programDetailViewDescriptionLabel != null : "fx:id=\"programDetailViewDescriptionLabel\" was not injected: check your FXML file 'programDetail-view.fxml'.";
        assert programDetailViewNumberLabel != null : "fx:id=\"programDetailViewNumberLabel\" was not injected: check your FXML file 'programDetail-view.fxml'.";

        Optional<Program> optionalProgram = model.getDataholder().getPrograms().stream().filter(program -> program == model.getSelectedProgramProperty()).findAny();

        if(optionalProgram.isPresent()) {
            Program p = optionalProgram.get();
            programDetailViewNumberLabel.setText("P" + String.valueOf(p.getProgramID()));
            programDetailViewDescriptionLabel.setText(p.getDescription());
        }
    }

    private void initView() {

    }


    @FXML
    void onProgramDeleteButtonClicked(MouseEvent event) {

    }

    @FXML
    void onProgramEditButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_PROGRAM_UPDATE_VIEW);

        Stage detailStage = (Stage) editProgram.getScene().getWindow();
        detailStage.close();
    }

}
