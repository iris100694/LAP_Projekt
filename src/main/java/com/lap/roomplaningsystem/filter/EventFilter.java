package com.lap.roomplaningsystem.filter;

import com.lap.roomplaningsystem.filterBoxes.FilterBox;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.repository.JDBC.EventRepositoryJDBC;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.util.Optional;

public class EventFilter {

    private String id = "";
    private String description = "";
    private String date = "";
    private String start = "";
    private String end = "";

    ObservableList<FilterBox> filterBoxes= FXCollections.observableArrayList(new Callback<FilterBox, Observable[]>() {
        @Override
        public Observable[] call(FilterBox filterBox) {
            return new Observable[]{filterBox.valueProperty()};
        }
    });

    public Optional<ObservableList<Event>> filterValue(EventRepositoryJDBC eventRepositoryJDBC, String id, String newValue) throws Exception {

        switch(id){
            case "eventID": {setId(newValue); break;}
            case "eventDescription": {setDescription(newValue); break;}
            case "eventDate" : {setDate(newValue); break;}
            case "eventStart" : {setStart(newValue); break;}
            case "eventEnd": {setEnd(newValue); break;}
        }


        return eventRepositoryJDBC.filter(eventRepositoryJDBC.createFilterStatement(this.getId(), this.getDescription(), this.getDate(), this.getStart(), this.getEnd()));
    }

    public Optional<ObservableList<Event>> getTableByFilterState(EventRepositoryJDBC eventRepositoryJDBC) throws Exception {
        return eventRepositoryJDBC.filter(eventRepositoryJDBC.createFilterStatement(this.getId(), this.getDescription(), this.getDate(), this.getStart(), this.getEnd()));
    }


    public void addFilterBox(FilterBox filterBox){
        filterBoxes.add(filterBox);
    }


    public ObservableList<FilterBox> getFilterBoxes() {
        return filterBoxes;
    }

    public void setFilterBoxes(ObservableList<FilterBox> filterBoxes) {
        this.filterBoxes = filterBoxes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


}
