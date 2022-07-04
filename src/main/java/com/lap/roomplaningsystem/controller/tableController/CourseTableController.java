package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Room;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private TableColumn<Course, String> descriptionColumn;

    @FXML
    private TableColumn<Course, Date> endColumn;

    @FXML
    private TableColumn<Course, String> numberColumn;

    @FXML
    private TableColumn<Course, String> programColumn;

    @FXML
    private TableColumn<Course, Integer> sizeColumn;

    @FXML
    private TableColumn<Course, Date> startColumn;

    @FXML
    private TableView<Course> tableView;




    @FXML
    void initialize() {
        tableView.setItems(model.getDataholder().getCourses());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("K" + String.valueOf(dataFeatures.getValue().getCourseID())));
        programColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getProgram().getDescription()));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getTitle()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMembers()));
        startColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Date>(dataFeatures.getValue().getStart()));
        endColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Date>(dataFeatures.getValue().getEnd()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedCourseProperty(nv);
                    showNewView(Constants.PATH_TO_COURSE_DETAIL_VIEW);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedCourseProperty().addListener(new ChangeListener<Course>() {
            @Override
            public void changed(ObservableValue<? extends Course> observableValue, Course oldCourse, Course newCourse) {
                if(newCourse == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });
    }


}


