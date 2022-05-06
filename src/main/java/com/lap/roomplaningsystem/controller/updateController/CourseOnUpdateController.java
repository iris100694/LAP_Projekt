package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.matcher.ProgramMatcher;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Program;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import static com.lap.roomplaningsystem.controller.BaseController.model;

public class CourseOnUpdateController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label courseNumberLabel;

    @FXML
    private TextField courseEnd;

    @FXML
    private TextField courseMembers;

    @FXML
    private TextField courseStart;

    @FXML
    private TextField courseTitle;

    @FXML
    private Button saveCourse;

    @FXML
    private ComboBox<Program> programComboBox;


    @FXML
    void initialize() {
        assert courseNumberLabel != null : "fx:id=\"courseDetailViewNumberLabel\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert courseEnd != null : "fx:id=\"courseEnd\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert courseMembers != null : "fx:id=\"courseMembers\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert courseStart != null : "fx:id=\"courseStart\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert courseTitle != null : "fx:id=\"courseTitle\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert saveCourse != null : "fx:id=\"saveCourse\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert programComboBox != null : "fx:id=\"programComboBox\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";

        initView();
    }

    private void initView() {
        Course c = model.getShowCourse();

        courseNumberLabel.setText("K" + String.valueOf(c.getCourseID()));
        courseTitle.setText(c.getTitle());
        courseMembers.setText(String.valueOf(c.getMembers()));
        courseStart.setText(String.valueOf(c.getStart()));
        courseEnd.setText(String.valueOf(c.getEnd()));

        programComboBox.setItems(model.getDataholder().getPrograms());
        programComboBox.getSelectionModel().select(c.getProgram());

        programComboBox.setConverter(new StringConverter<Program>() {
            @Override
            public String toString(Program program) {
                return program.getDescription();
            }

            @Override
            public Program fromString(String s) {
                ProgramMatcher programMatcher = new ProgramMatcher();
                return programMatcher.matchByString(s, model.getDataholder().getPrograms());
            }
        });

    }

    @FXML
    void onCourseSaveButtonClicked(MouseEvent event) {

    }

}
