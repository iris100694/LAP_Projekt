package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

public class RoomMatcher implements Matcher<Room>{

    @Override
    public Room matchByString(String s, ObservableList<Room> list) {
        for(Room r : list){
            if(r.getDescription().equals(s)){
                return r;
            }
        }
        return null;
    }
}
