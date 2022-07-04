package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProgramOnUpdateController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label errorLabel;

    @FXML
    private Label numberLabel;

    private Program program;


    @FXML
    void initialize() {

        Optional<Program> optionalProgram = model.getDataholder().getPrograms().stream().filter(program -> program == model.getSelectedProgramProperty()).findAny();

        if(optionalProgram.isPresent()){
            program = optionalProgram.get();

            numberLabel.setText("P" + String.valueOf(program.getProgramID()));
            descriptionInput.setText(program.getDescription());

        }

    }


    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        if(validateFields()) {
            program.setDescription(descriptionInput.getText());

            boolean isUpdated = updateProgramByJDBC();

            if (isUpdated) {
                showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                int index = model.getDataholder().getPrograms().indexOf(program);
                model.getDataholder().updateProgram(index, program);
                model.getDataholder().updateCourses();
                model.getDataholder().updateEvents();
                closeStage(errorLabel);
            }
        }
    }


    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }

    private boolean explicitDescription() {
        List<Program> programs = model.getDataholder().getPrograms().stream().filter(p-> p != program).toList();
        boolean explicit = programs.stream().noneMatch(p-> p.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText("Programmbezeichung bereits vergeben!");
        }

        return explicit;
    }

    private boolean emptyFields() {
        if(isBlank(descriptionInput.getText())){
            errorLabel.setText("Bitte Feld ausfüllen!");
        }

        return isBlank(descriptionInput.getText());
    }

    private boolean updateProgramByJDBC() throws Exception {
        return Dataholder.programRepositoryJDBC.update(program);
    }
}
