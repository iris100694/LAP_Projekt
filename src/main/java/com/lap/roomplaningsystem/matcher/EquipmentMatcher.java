package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EquipmentMatcher implements Matcher<Equipment> {
    @Override
    public Equipment matchByString(String s, ObservableList<Equipment> list) {
        for(Equipment e : list){
            if (e.getDescription().equals(s)){
                return e;
            }
        }
        return null;
    }

    public static ObservableList<String> stringList(ObservableList<Equipment> equipment){
        ObservableList<String> list = FXCollections.observableArrayList();

        for (Equipment e : equipment){
            list.add(e.getDescription());
        }

        return list;
    }
}
