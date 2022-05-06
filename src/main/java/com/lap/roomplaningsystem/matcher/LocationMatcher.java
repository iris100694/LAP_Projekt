package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.repository.JDBC.LocationRepositoryJDBC;
import javafx.collections.ObservableList;

import java.util.Optional;

public class LocationMatcher implements Matcher<Location> {


    @Override
    public Location matchByString(String s, ObservableList<Location> list) {
        for(Location l : list){
            if(l.getDescription().equals(s)){
                return l;
            }
        }
        return null;
    }
}
