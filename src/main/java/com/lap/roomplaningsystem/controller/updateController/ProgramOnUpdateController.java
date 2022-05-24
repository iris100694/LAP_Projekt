package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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

    @FXML
    private Button saveProgram;

    boolean descriptionIsChange;


    @FXML
    void initialize() {
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'programDetailOnUpdate-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'programDetailOnUpdate-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'programDetailOnUpdate-view.fxml'.";
        assert saveProgram != null : "fx:id=\"saveProgram\" was not injected: check your FXML file 'programDetailOnUpdate-view.fxml'.";

        Optional<Program> optionalProgram = model.getDataholder().getPrograms().stream().filter(program -> program == model.getSelectedProgramProperty()).findAny();

        if(optionalProgram.isPresent()){
            Program p = optionalProgram.get();

            numberLabel.setText("P" + String.valueOf(p.getProgramID()));
            descriptionInput.setText(p.getDescription());


            descriptionInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(p.getDescription())){
                        descriptionIsChange = true;
                    }else {
                        descriptionIsChange= false;
                    }
                }
            });
        }

    }



    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if(isBlank(descriptionInput.getText())){
            errorLabel.setText("Bitte Feld ausfüllen!");
        } else if(!descriptionIsChange) {
            errorLabel.setText("Es wurden keine Änderungen vorgenommen!");
        }else{
            boolean exist = model.getDataholder().getPrograms().stream().anyMatch(p-> p.getDescription().equals(descriptionInput.getText()));
            if(exist){
                errorLabel.setText("Bezeichung bereits vergeben!");
            } else {

                Optional<Program> optionalProgram = model.getDataholder().getPrograms().stream().filter(p -> p.getProgramID() == model.getSelectedProgramProperty().getProgramID()).findAny();

                if (optionalProgram.isPresent()) {
                    Program program = optionalProgram.get();


                    program.setDescription(descriptionInput.getText());


                    boolean isUpdated = Dataholder.programRepositoryJDBC.update(program);

                    if (isUpdated) {
                        showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                        int index = model.getDataholder().getPrograms().indexOf(program);
                        model.getDataholder().updateProgram(index, program);
                    }

                }


                //TODO: Change this two rows with a better method
                Optional<ObservableList<Course>> optionalCourses = Dataholder.courseRepositoryJDBC.readAll();
                Optional<ObservableList<Event>> optionalEvents = Dataholder.eventRepositoryJDBC.readAll();
                optionalCourses.ifPresent(courses -> model.getDataholder().setCourses(courses));
                optionalEvents.ifPresent(events -> model.getDataholder().setEvents(events));

                Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
                detailStage.close();
            }
        }
    }
}
