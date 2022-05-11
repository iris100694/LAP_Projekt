package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomMatcher implements Matcher<Room>{
    private Location location;

    public RoomMatcher(Location location) {
        this.location = location;
    }

    @Override
    public Room matchByString(String s, ObservableList<Room> list) {
        ObservableList<Room> rooms = listOfLocation(list);
        for(Room r : rooms){
            if(r.getDescription().equals(s)){
                return r;
            }
        }
        return null;
    }

    private ObservableList<Room> listOfLocation(ObservableList<Room> list) {
        list.removeIf(r -> r.getLocation() != location);
        System.out.println(list);
        return list;
    }

    public static ObservableList<String> stringList(String location, ObservableList<Room> rooms){
        ObservableList<String> list = FXCollections.observableArrayList();

        for (Room r : rooms){
            if(r.getLocation().getDescription().equals(location)){
                list.add(r.getDescription());
            }
        }

        return list;
    }
}
