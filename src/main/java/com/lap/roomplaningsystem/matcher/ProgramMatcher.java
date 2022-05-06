package com.lap.roomplaningsystem.matcher;

import com.lap.roomplaningsystem.model.Program;
import javafx.collections.ObservableList;

public class ProgramMatcher implements Matcher<Program>{
    @Override
    public Program matchByString(String s, ObservableList<Program> list) {
        for(Program p : list){
            if (p.getDescription().equals(s)){
                return p;
            }
        }
        return null;
    }
}
