package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;

public class UserMatcher implements Matcher<User>{
    @Override
    public User matchByString(String s, ObservableList<User> list) {
        for(User u : list){
            String name = u.getFirstname() + " " + u.getLastname();
            if(s.equals(name)){
                return u;
            }
        }

        return null;
    }
}
