package com.lap.roomplaningsystem.filter;

import com.lap.roomplaningsystem.controller.RoomsViewController;
import com.lap.roomplaningsystem.model.DatabaseUtility;
import com.lap.roomplaningsystem.model.Datamodel;
import com.lap.roomplaningsystem.model.Room;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.util.Callback;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class Roomfilter {

    private String id = "";
    private String description = "";
    private String size = "";
    private String location = "";
    private String equipment = "";
    private boolean image;

    private FilterCheckBox imageCheckBox;
    ObservableList<FilterBox> filterBoxes= FXCollections.observableArrayList(new Callback<FilterBox, Observable[]>() {
        @Override
        public Observable[] call(FilterBox filterBox) {
            return new Observable[]{filterBox.valueProperty()};
        }
    });


    public Roomfilter() {
    }



    public ObservableList<Room> filterValue(String id, String newValue) {
        switch (id) {
            case "roomID": {setId(newValue); break;}
            case "roomDescription": {setDescription(newValue); break;}
            case "roomSize": {setSize(newValue); break;}
            case "roomLocation": {setLocation(newValue); break;}
            case "roomEquipment": {setEquipment(newValue); break;}
        }

        return requestToDatabaseUtility(createStatement());
    }

    public ObservableList<Room> filterValueWithImage(boolean checkImage) {

        image = checkImage;

        return requestToDatabaseUtility(createStatement());
    }

    private ObservableList<Room> requestToDatabaseUtility(String stmt) {
        return isBlank(equipment) ? DatabaseUtility.filterRooms(stmt, false) : DatabaseUtility.filterRooms(stmt, true);
    }


    private String createStatement() {
        String stmt = "";

        if (!isBlank(id)) {
            stmt = stmt + " WHERE rooms.ROOMID = " + id;
        }

        if (!isBlank(description)) {
            stmt = isBlank(stmt) ? stmt + " WHERE rooms.DESCRIPTION = \"" + description + "\"" : stmt + " AND rooms.DESCRIPTION = \"" + description + "\"";
        }

        if (!isBlank(size)) {
            stmt = isBlank(stmt) ? stmt + " WHERE rooms.MAXPERSONS = " + size : stmt + " AND rooms.MAXPERSONS = " + size;
        }

        if (!isBlank(location)) {
            stmt = isBlank(stmt) ? stmt + " WHERE locations.DESCRIPTION = \"" + location + "\"" : stmt + " AND locations.DESCRIPTION = \"" + location + "\"";
        }

        if (!isBlank(equipment)) {
            stmt = isBlank(stmt) ? stmt + " WHERE equipment.DESCRIPTION = \"" + equipment + "\"" : stmt + " AND equipment.DESCRIPTION = \"" + equipment + "\"";
        }

        if(image){
            stmt = isBlank(stmt) ? stmt + " WHERE rooms.PHOTO IS NOT NULL" : stmt + " AND rooms.PHOTO IS NOT NULL";
        }

        return stmt;
    }


    private boolean isBlank(String s) {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public ObservableList<FilterBox> getFilterBoxes() {
        return filterBoxes;
    }

    public void setFilterBoxes(ObservableList<FilterBox> filterBoxes) {
        this.filterBoxes = filterBoxes;
    }


    public FilterCheckBox getImageCheckBox() {
        return imageCheckBox;
    }

    public void setImageCheckBox(FilterCheckBox imageCheckBox) {
        this.imageCheckBox = imageCheckBox;
    }
}
