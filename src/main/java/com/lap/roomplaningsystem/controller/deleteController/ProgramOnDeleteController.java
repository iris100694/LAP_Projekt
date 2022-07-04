package com.lap.roomplaningsystem.controller.deleteController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProgramOnDeleteController extends BaseController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;

    private Program program;


    @FXML
    void initialize() {

        Optional<Program> optionalProgram = model.getDataholder().getPrograms().stream().filter(p -> p.getProgramID() == model.getSelectedProgramProperty().getProgramID()).findAny();

        if(optionalProgram.isPresent()){
            program = optionalProgram.get();
            numberLabel.setText("P" + program.getProgramID());
            descriptionLabel.setText(program.getDescription());
        }

    }

    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(numberLabel);
    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws Exception {
        model.setSelectedProgramProperty(null);

        if(deleteProgramByJDBC()){
            model.getDataholder().deleteProgram(program);
            model.getDataholder().updateCourses();
            model.getDataholder().updateEvents();
        }

        closeStage(numberLabel);
    }

    private boolean deleteProgramByJDBC() throws SQLException {
        return Dataholder.programRepositoryJDBC.delete(program);
    }
}
