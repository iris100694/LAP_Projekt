package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Room;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CourseTableController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Course, String> courseDescriptionColumn;

    @FXML
    private TableColumn<Course, Date> courseEndColumn;

    @FXML
    private TableColumn<Course, String> courseNumberColumn;

    @FXML
    private TableColumn<Course, String> courseProgramColumn;

    @FXML
    private TableColumn<Course, Integer> courseSizeColumn;

    @FXML
    private TableColumn<Course, Date> courseStartColumn;

    @FXML
    private TableView<Course> courseTableView;

    private ObjectProperty<Course> selectedEvent = new SimpleObjectProperty<>();

    @FXML
    void initialize() {
        assert courseDescriptionColumn != null : "fx:id=\"courseDescriptionColumn\" was not injected: check your FXML file 'courseTable.fxml'.";
        assert courseEndColumn != null : "fx:id=\"courseEndColumn\" was not injected: check your FXML file 'courseTable.fxml'.";
        assert courseNumberColumn != null : "fx:id=\"courseNumberColumn\" was not injected: check your FXML file 'courseTable.fxml'.";
        assert courseProgramColumn != null : "fx:id=\"courseProgramColumn\" was not injected: check your FXML file 'courseTable.fxml'.";
        assert courseSizeColumn != null : "fx:id=\"courseSizeColumn\" was not injected: check your FXML file 'courseTable.fxml'.";
        assert courseStartColumn != null : "fx:id=\"courseStartColumn\" was not injected: check your FXML file 'courseTable.fxml'.";
        assert courseTableView != null : "fx:id=\"courseTable\" was not injected: check your FXML file 'courseTable.fxml'.";

        initCourseTable(model.getDataholder().getCourses());
    }

    private void initCourseTable(ObservableList<Course> course) {
        courseTableView.setItems(course);

        courseNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("K" + String.valueOf(dataFeatures.getValue().getCourseID())));
        courseProgramColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getProgram().getDescription()));
        courseDescriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getTitle()));
        courseSizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMembers()));
        courseStartColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Date>(dataFeatures.getValue().getStart()));
        courseEndColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Date>(dataFeatures.getValue().getEnd()));

        courseTableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedEvent.set(nv));

        selectedEvent.addListener((o, ov, nv) -> {
            model.setShowCourse(nv);
            try {
                showNewView(Constants.PATH_TO_COURSE_DETAIL_VIEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}


