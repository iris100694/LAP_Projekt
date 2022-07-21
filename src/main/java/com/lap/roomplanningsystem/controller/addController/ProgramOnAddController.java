package com.lap.roomplanningsystem.controller.addController;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProgramOnAddController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label errorLabel;

    @FXML
    void initialize() {
    }

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        if(validateFields()) {
            Program program = addProgramByJDBC();

            if (program != null) {
                model.getDataholder().addProgram(program);
                closeStage(errorLabel);
            }


        }
    }


    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }

    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getPrograms().stream().noneMatch(p-> p.getDescription().equals(descriptionInput.getText()));

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

    private Program addProgramByJDBC() throws Exception {
       return Dataholder.programRepositoryJDBC.add(descriptionInput.getText());
    }


}
