package com.lap.roomplanningsystem.controller.addController;


import java.sql.Date;


import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.ProgramConverter;

import com.lap.roomplanningsystem.model.Course;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Program;
import com.lap.roomplanningsystem.utility.IntegerUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;




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
        ProgramConverter programConverter = new ProgramConverter();

        programComboBox.setItems(model.getDataholder().getPrograms());
        programComboBox.setPromptText("Programm");
        programConverter.setConverter(programComboBox);
    }

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        if(validateFields()){
            Course course = addCourseByJDBC();

            if(course != null){
                model.getDataholder().addCourse(course);
                closeStage(errorLabel);
            }
        }
    }

    private boolean validateFields() {
        return !emptyFields() && explicitDescription() && validMembers() && validDate();
    }


    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText()) || isBlank(membersInput.getText()) || startDatePicker.getValue() == null || programComboBox.getValue() == null;

        if(empty){
            errorLabel.setText("Bitte Felder und Auswahlbox ausfüllen!");
        }

        return empty;
    }

    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getCourses().stream().noneMatch(c-> c.getTitle().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText("Kursbezeichnung bereits vergeben!");
        }

        return explicit;
    }

    private boolean validMembers() {
        boolean valid = IntegerUtility.getInt(membersInput.getText()) != null;

        if(!valid){
            errorLabel.setText("Keine korrekte Teilnehmerzahl ausgefüllt!");
        }

        return valid;
    }

    private boolean validDate() {
        boolean valid = !endDatePicker.getValue().isBefore(startDatePicker.getValue());

        if(!valid){
            errorLabel.setText("Endatum darf nicht vor dem Startdatum gewählt werden!");
        }

        return valid;
    }


    private Course addCourseByJDBC() throws Exception {
        return Dataholder.courseRepositoryJDBC.add(descriptionInput.getText(), programComboBox.getValue(), IntegerUtility.getInt(membersInput.getText()),
                Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()));
    }

}
