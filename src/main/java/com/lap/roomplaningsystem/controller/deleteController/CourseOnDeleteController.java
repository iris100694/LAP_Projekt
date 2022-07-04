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

public class CourseOnDeleteController extends BaseController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label endLabel;

    @FXML
    private Label membersLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label programLabel;

    @FXML
    private Label startLabel;

    private Course course;



    @FXML
    void initialize() {

        Optional<Course> optionalCourse = model.getDataholder().getCourses().stream().filter(c -> c.getCourseID() == model.getSelectedCourseProperty().getCourseID()).findAny();

        if(optionalCourse.isPresent()){
            course = optionalCourse.get();
            numberLabel.setText("K" + course.getCourseID());
            descriptionLabel.setText(course.getTitle());
            programLabel.setText(course.getProgram().getDescription());
            membersLabel.setText(String.valueOf(course.getMembers()));
            startLabel.setText(String.valueOf(course.getStart()));
            endLabel.setText(String.valueOf(course.getEnd()));
        }

    }


    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(numberLabel);
    }


    @FXML
    void onYesButtonClicked(ActionEvent event) throws Exception {
        model.setSelectedCourseProperty(null);

        if(deleteCourseByJDBC()){
            model.getDataholder().deleteCourse(course);
            model.getDataholder().updateEvents();
        }

        closeStage(numberLabel);
    }

    private boolean deleteCourseByJDBC() throws Exception{
        return Dataholder.courseRepositoryJDBC.delete(course);
    }

}
