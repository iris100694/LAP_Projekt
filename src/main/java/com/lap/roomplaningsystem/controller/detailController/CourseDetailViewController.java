package com.lap.roomplaningsystem.controller.detailController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CourseDetailViewController extends BaseController {

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

        Optional<Course> optionalCourse = model.getDataholder().getCourses().stream().filter(course -> course == model.getSelectedCourseProperty()).findAny();

        if(optionalCourse.isPresent()) {
            course = optionalCourse.get();

            numberLabel.setText("K" + course.getCourseID());
            descriptionLabel.setText(course.getTitle());
            programLabel.setText(course.getProgram().getDescription());
            membersLabel.setText(String.valueOf(course.getMembers()));
            startLabel.setText(course.getStart().toString());
            endLabel.setText(course.getEnd().toString());
        }

    }


    @FXML
    void onDeleteButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_COURSE_ON_DELETE_VIEW);
        closeStage(numberLabel);
    }

    @FXML
    void onUpdateButtonClicked(ActionEvent event)  throws IOException {
        showNewView(Constants.PATH_TO_COURSE_UPDATE_VIEW);
        closeStage(numberLabel);
    }

}
