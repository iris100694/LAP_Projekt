package com.lap.roomplaningsystem.controller.addController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.model.Room;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProgramOnAddController extends BaseController {

    @FXML
    private TextField descriptionInput;


    @FXML
    private Label errorLabel;

    boolean error;


    @FXML
    void initialize() {
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'programDetailOnAdd-view.fxml'.";
        assert descriptionInput != null : "fx:id=\"programDescriptionInput\" was not injected: check your FXML file 'programDetailOnAdd-view.fxml'.";

        descriptionInput.setOnKeyTyped(room -> {
            List<String> descriptions = model.getDataholder().getPrograms().stream().map(Program::getDescription).toList();
            boolean exist = descriptions.stream().anyMatch(description -> description.equals(descriptionInput.getText()));
            if (exist) {
                error = true;
                errorLabel.setText("Raumbezeichnung bereits vergeben!");
            } else {
                error = false;
                errorLabel.setText("");
            }
        });


    }




    @FXML
    void onProgramSaveButtonClicked(MouseEvent event) throws Exception {
        if(isBlank(descriptionInput.getText())){
            errorLabel.setText("Bitte wähle Programmnamen!");

        } else if (!error){
            Program program = Dataholder.programRepositoryJDBC.add(descriptionInput.getText());
            model.getDataholder().addProgram(program);

            Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
            detailStage.close();
        }


    }



}
