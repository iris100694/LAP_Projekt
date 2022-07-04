package com.lap.roomplaningsystem.converter;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Dataholder;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class CourseConverter implements ConverterInterface<Course>{

    private ObservableList<Course> courseList;


    public CourseConverter() {
        this.courseList = BaseController.model.getDataholder().getCourses();

//        courseList.addListener(new ListChangeListener<Course>() {
//            @Override
//            public void onChanged(Change<? extends Course> change) {
//                updateList(courseList);
//            }
//        });
    }

    @Override
    public void updateList(ObservableList<Course> list) {
        this.courseList = list;
    }

    @Override
    public void setConverter(ComboBox<Course> box) {

        box.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                return course == null ? "Kurs" : course.getTitle() + " " + course.getProgram().getDescription();
            }

            @Override
            public Course fromString(String s) {
                return matchByString(s);
            }
        });
    }

    @Override
    public Course matchByString(String s) {
        String[] find = s.split(" ");
        for(Course c : courseList){
            if(c.getTitle().equals(find[0])){
                return c;
            }
        }
        return null;
    }
}
