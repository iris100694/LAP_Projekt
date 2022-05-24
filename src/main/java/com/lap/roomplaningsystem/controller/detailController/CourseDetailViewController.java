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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CourseDetailViewController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label courseDetailViewDescriptionLabel;

    @FXML
    private Label courseDetailViewEndLabel;

    @FXML
    private Label courseDetailViewMembersLabel;

    @FXML
    private Label courseDetailViewNumberLabel;

    @FXML
    private Label courseDetailViewProgramLabel;

    @FXML
    private Label courseDetailViewStartLabel;

    @FXML
    private Button deleteCourse;

    @FXML
    private Button editCourse;



    @FXML
    void initialize() {
        assert courseDetailViewDescriptionLabel != null : "fx:id=\"courseDetailViewDescriptionLabel\" was not injected: check your FXML file 'courseDetail-view.fxml'.";
        assert courseDetailViewEndLabel != null : "fx:id=\"courseDetailViewEndLabel\" was not injected: check your FXML file 'courseDetail-view.fxml'.";
        assert courseDetailViewMembersLabel != null : "fx:id=\"courseDetailViewMembersLabel\" was not injected: check your FXML file 'courseDetail-view.fxml'.";
        assert courseDetailViewNumberLabel != null : "fx:id=\"courseDetailViewNumberLabel\" was not injected: check your FXML file 'courseDetail-view.fxml'.";
        assert courseDetailViewProgramLabel != null : "fx:id=\"courseDetailViewProgramLabel\" was not injected: check your FXML file 'courseDetail-view.fxml'.";
        assert courseDetailViewStartLabel != null : "fx:id=\"courseDetailViewStartLabel\" was not injected: check your FXML file 'courseDetail-view.fxml'.";
        assert deleteCourse != null : "fx:id=\"deleteCourse\" was not injected: check your FXML file 'courseDetail-view.fxml'.";
        assert editCourse != null : "fx:id=\"editCourse\" was not injected: check your FXML file 'courseDetail-view.fxml'.";

        Optional<Course> optionalCourse = model.getDataholder().getCourses().stream().filter(course -> course == model.getSelectedCourseProperty()).findAny();

        if(optionalCourse.isPresent()) {
            Course c = optionalCourse.get();

            courseDetailViewNumberLabel.setText("K" + c.getCourseID());
            courseDetailViewDescriptionLabel.setText(c.getTitle());
            courseDetailViewProgramLabel.setText(c.getProgram().getDescription());
            courseDetailViewMembersLabel.setText(String.valueOf(c.getMembers()));
            courseDetailViewStartLabel.setText(c.getStart().toString());
            courseDetailViewEndLabel.setText(c.getEnd().toString());
        }

    }




    @FXML
    void onCourseDeleteButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_COURSE_ON_DELETE_VIEW);

        Stage detailStage = (Stage) courseDetailViewDescriptionLabel.getScene().getWindow();
        detailStage.close();

    }

    @FXML
    void onCourseEditButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_COURSE_UPDATE_VIEW);

        Stage detailStage = (Stage) editCourse.getScene().getWindow();
        detailStage.close();

    }

}
