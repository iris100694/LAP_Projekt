package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseMatcher implements Matcher<Course>{
    @Override
    public Course matchByString(String s, ObservableList<Course> list) {
        String[] find = s.split(" ");
        for(Course c : list){
            if(c.getTitle().equals(find[0])){
                return c;
            }
        }
        return null;
    }

    public static ObservableList<String> stringList(ObservableList<Course> courses){
        ObservableList<String> list = FXCollections.observableArrayList();

        for (Course c : courses){
            list.add(c.getTitle() + " " + c.getProgram().getDescription());
        }

        return list;
    }
}
