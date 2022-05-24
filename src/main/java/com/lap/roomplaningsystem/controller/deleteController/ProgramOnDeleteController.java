package com.lap.roomplaningsystem.controller.deleteController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ProgramOnDeleteController extends BaseController {


    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;



    @FXML
    void initialize() {
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'programDetailOnDelete-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'programDetailOnDelete-view.fxml'.";

        Optional<Program> optionalProgram = model.getDataholder().getPrograms().stream().filter(p -> p.getProgramID() == model.getSelectedProgramProperty().getProgramID()).findAny();

        if(optionalProgram.isPresent()){
            Program program = optionalProgram.get();
            numberLabel.setText("P" + program.getProgramID());
            descriptionLabel.setText(program.getDescription());
        }

    }

    @FXML
    void onNoButtonClicked(MouseEvent event) {
        Stage stage = (Stage) descriptionLabel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void onYesButtonClicked(MouseEvent event) throws Exception {

        Optional <Program> optionalProgram = model.getDataholder().getPrograms().stream().filter(p -> p.getProgramID() == model.getSelectedProgramProperty().getProgramID()).findAny();
        model.setSelectedProgramProperty(null);

        optionalProgram.ifPresent(p -> {
            try {
                boolean isDeleted = Dataholder.programRepositoryJDBC.delete(p);
                if(isDeleted){
                    model.getDataholder().deleteProgram(p);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Optional<ObservableList<Course>> optionalCourses = Dataholder.courseRepositoryJDBC.readAll();
        Optional<ObservableList<Event>> optionalEvents = Dataholder.eventRepositoryJDBC.readAll();
        optionalCourses.ifPresent(courses -> model.getDataholder().setCourses(courses));
        optionalEvents.ifPresent(events -> model.getDataholder().setEvents(events));

        Stage stage = (Stage) descriptionLabel.getScene().getWindow();
        stage.close();

    }
}
