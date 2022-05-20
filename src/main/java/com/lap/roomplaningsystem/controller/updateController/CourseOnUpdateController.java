package com.lap.roomplaningsystem.controller.updateController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.ProgramMatcher;
import com.lap.roomplaningsystem.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import static com.lap.roomplaningsystem.controller.BaseController.model;

public class CourseOnUpdateController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField membersInput;

    @FXML
    private Label numberLabel;

    @FXML
    private ComboBox<Program> programComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker startDatePicker;

    boolean descriptionIsChange;
    boolean programIsChange;
    boolean membersIsChange;
    boolean startIsChange;
    boolean endIsChange;



    @FXML
    void initialize() {
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert membersInput != null : "fx:id=\"membersInput\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert programComboBox != null : "fx:id=\"programComboBox\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'courseDetailOnUpdate-view.fxml'.";

        model.getDataholder().getPrograms().forEach(p -> System.out.println(p.getProgramID()));

        Optional<Course> optionalCourse = model.getDataholder().getCourses().stream().filter(course -> course == model.getSelectedCourseProperty()).findAny();

        if(optionalCourse.isPresent()){
            Course c = optionalCourse.get();

            numberLabel.setText("K" + String.valueOf(c.getCourseID()));
            descriptionInput.setText(c.getTitle());
            membersInput.setText(String.valueOf(c.getMembers()));
            startDatePicker.setValue(c.getStart().toLocalDate());
            endDatePicker.setValue(c.getEnd().toLocalDate());

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


            descriptionInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if(!newValue.equals(c.getTitle())){
                        descriptionIsChange = true;
                    }else {
                        descriptionIsChange= false;
                    }
                }
            });

            programComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Program>() {
                @Override
                public void changed(ObservableValue<? extends Program> observableValue, Program oldValue, Program newValue) {
                    if(newValue != c.getProgram()){
                        programIsChange = true;
                        System.out.println(newValue.getProgramID());
                    }else {
                        programIsChange = false;
                    }
                }
            });

            membersInput.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    Integer members = null;
                    try {
                        members = getInt(newValue);
                        membersIsChange = members == c.getMembers();
                    } catch (Exception e){
                        errorLabel.setText("Keine korrekte Teilnehmerzahl ausgefüllt!");
                    }
                }
            });

            startDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldValue, LocalDate newValue) {
                    if(newValue != c.getStart().toLocalDate()){
                        startIsChange = true;
                    }else {
                        startIsChange= false;
                    }
                }
            });

            endDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
                @Override
                public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldValue, LocalDate newValue) {
                    if(newValue != c.getEnd().toLocalDate()){
                        endIsChange = true;
                    }else {
                        endIsChange= false;
                    }
                }
            });
        }
    }



    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if(isBlank(descriptionInput.getText()) || programComboBox.getValue() == null || isBlank(membersInput.getText()) ||
                startDatePicker.getValue() == null || endDatePicker.getValue() == null){
            errorLabel.setText("Bitte Feld ausfüllen!");
        } else if(!descriptionIsChange && !programIsChange && !membersIsChange && !startIsChange && !endIsChange) {
            errorLabel.setText("Es wurden keine Änderungen vorgenommen!");
        }else{
            boolean exist = model.getDataholder().getCourses().stream().anyMatch(c-> c.getTitle().equals(descriptionInput.getText()) && c.getCourseID() != model.getSelectedCourseProperty().getCourseID());
            if(exist){
                errorLabel.setText("Bezeichung bereits vergeben!");
            } else {

                Optional<Course> optionalCourse = model.getDataholder().getCourses().stream().filter(e-> e.getCourseID() == model.getSelectedCourseProperty().getCourseID()).findAny();

                if(optionalCourse.isPresent()){
                    Course course = optionalCourse.get();

                    if(descriptionIsChange){
                        course.setTitle(descriptionInput.getText());
                    }

                    if(programIsChange){
                        course.setProgram(programComboBox.getValue());
                    }

                    if(membersIsChange){
                        course.setMembers(getInt(membersInput.getText()));
                    }

                    if(startIsChange){
                        course.setStart(Date.valueOf(startDatePicker.getValue()));
                    }

                    if(endIsChange){
                        course.setEnd(Date.valueOf(endDatePicker.getValue()));
                    }


                    boolean isUpdated = Dataholder.courseRepositoryJDBC.update(course);

                    if(isUpdated){
                        showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                        int index = model.getDataholder().getCourses().indexOf(course);
                        model.getDataholder().updateCourse(index, course);
                    }

                }


                //TODO: Change this two rows with a better method
                Optional<ObservableList<Event>> optionalEvents = Dataholder.eventRepositoryJDBC.readAll();
                optionalEvents.ifPresent(events -> model.getDataholder().setEvents(events));


                Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
                detailStage.close();
            }

        }
    }

}
