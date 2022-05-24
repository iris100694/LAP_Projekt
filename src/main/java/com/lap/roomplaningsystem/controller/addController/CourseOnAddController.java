package com.lap.roomplaningsystem.controller.addController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.ProgramMatcher;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Program;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CourseOnAddController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField membersInput;

    @FXML
    private ComboBox<Program> programComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker startDatePicker;



    @FXML
    void initialize() {
        assert descriptionInput != null : "fx:id=\"descriptionInput\" was not injected: check your FXML file 'courseDetailOnAdd-view.fxml'.";
        assert endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'courseDetailOnAdd-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'courseDetailOnAdd-view.fxml'.";
        assert membersInput != null : "fx:id=\"membersInput\" was not injected: check your FXML file 'courseDetailOnAdd-view.fxml'.";
        assert programComboBox != null : "fx:id=\"programComboBox\" was not injected: check your FXML file 'courseDetailOnAdd-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'courseDetailOnAdd-view.fxml'.";
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'courseDetailOnAdd-view.fxml'.";


        programComboBox.setItems(model.getDataholder().getPrograms());
        programComboBox.setPromptText("Programm");
        programComboBox.setConverter(new StringConverter<Program>() {
            @Override
            public String toString(Program program) {
                return program == null ? "Programm" : program.getDescription();
            }

            @Override
            public Program fromString(String s) {
                ProgramMatcher programMatcher = new ProgramMatcher();
                return programMatcher.matchByString(s, model.getDataholder().getPrograms());
            }
        });
    }

    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if (isBlank(descriptionInput.getText()) || isBlank(membersInput.getText()) || startDatePicker.getValue() == null ||
                endDatePicker.getValue() == null || programComboBox.getValue() == null) {
            errorLabel.setText("Bitte alle Felder und Auswahlbox ausfüllen!");
        }else if(model.getDataholder().getCourses().stream().anyMatch(c-> c.getTitle().equals(descriptionInput.getText()))) {
            errorLabel.setText("Kursbezeichnung bereits vergeben!");

        } else if(getInt(membersInput.getText()) == null) {
            errorLabel.setText("Keine korrekte Teilnehmerzahl ausgefüllt!");
        } else if(endDatePicker.getValue().isBefore(startDatePicker.getValue())){
            errorLabel.setText("Endatum darf nicht vor dem Startdatum gewählt werden!");
        } else {
            Course course = Dataholder.courseRepositoryJDBC.add(descriptionInput.getText(), programComboBox.getValue(), getInt(membersInput.getText()),
                    Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()));

            model.getDataholder().addCourse(course);

            Stage detailStage = (Stage) descriptionInput.getScene().getWindow();
            detailStage.close();
        }

    }



}
