package com.lap.roomplaningsystem.filter;

import com.lap.roomplaningsystem.model.DatabaseUtility;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Room;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.sql.Date;

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

    public ObservableList<Event> filterValue(String id, String newValue) {

        switch(id){
            case "eventID": {setId(newValue); break;}
            case "eventDescription": {setDescription(newValue); break;}
            case "eventDate" : {setDate(newValue); break;}
            case "eventStart" : {setStart(newValue); break;}
            case "eventEnd": {setEnd(newValue); break;}
        }


        return requestToDatabaseUtility(createStatement());
    }

    private ObservableList<Event> requestToDatabaseUtility(String stmt) {
        return DatabaseUtility.filterEvents(stmt);
    }

    private String createStatement() {
        String stmt = "";

        if (!isBlank(id)) {
            stmt = stmt + "WHERE events.ROOMID = " + id;
        }

        if (!isBlank(description)) {
            stmt = isBlank(stmt) ? stmt + "WHERE program.DESCRIPTION = \"" + description + "\"" : stmt + " AND program.DESCRIPTION = \"" + description + "\"";
        }

        if (!isBlank(date)) {
            stmt = isBlank(stmt) ? stmt + "WHERE events.START LIKE \"" + date + "%\"" : stmt + " AND events.START LIKE \"" + date + "%\"";
        }

        if (!isBlank(start)) {
            stmt = isBlank(stmt) ? stmt + "WHERE events.START LIKE \"%" + start + "\"": stmt + " AND events.START LIKE \"%" + start + "\"";
        }

        if (!isBlank(end)) {
            stmt = isBlank(stmt) ? stmt + "WHERE events.END LIKE \"%" + end +"\"": stmt + " AND events.END LIKE \"%" + end + "\"";
        }

        return stmt;
    }

    private boolean isBlank(String s){
        return s.equals("");
    }

    public void addFilterBox(FilterBox filterBox){
        filterBoxes.add(filterBox);
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

    public ObservableList<FilterBox> getFilterBoxes() {
        return filterBoxes;
    }

    public void setFilterBoxes(ObservableList<FilterBox> filterBoxes) {
        this.filterBoxes = filterBoxes;
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
