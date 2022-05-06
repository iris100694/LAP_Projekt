package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.User;
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
}
