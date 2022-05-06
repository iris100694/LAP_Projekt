package com.lap.roomplaningsystem.filter;

import com.lap.roomplaningsystem.filterBoxes.FilterBox;
import com.lap.roomplaningsystem.filterBoxes.FilterCheckBox;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.repository.JDBC.RoomRepositoryJDBC;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.util.Optional;

public class Roomfilter {

    private FilterCheckBox imageCheckBox;

    private String id = "";
    private String description = "";
    private String size = "";
    private String location = "";
    private String equipment = "";
    private boolean image;

    ObservableList<FilterBox> filterBoxes= FXCollections.observableArrayList(new Callback<FilterBox, Observable[]>() {
        @Override
        public Observable[] call(FilterBox filterBox) {
            return new Observable[]{filterBox.valueProperty()};
        }
    });


    public Roomfilter() {
    }

    public Optional<ObservableList<Room>> filterValue(RoomRepositoryJDBC roomRepositoryJDBC, String id, String newValue) throws Exception {
        switch (id) {
            case "roomID": {setId(newValue); break;}
            case "roomDescription": {setDescription(newValue); break;}
            case "roomSize": {setSize(newValue); break;}
            case "roomLocation": {setLocation(newValue); break;}
            case "roomEquipment": {setEquipment(newValue); break;}
        }

        return roomRepositoryJDBC.filter(roomRepositoryJDBC.createFilterStatement(this.getId(), this.getDescription(), this.getSize(), this.getLocation(), this.getEquipment(), this.isImage()), isBlank(this.getEquipment()));
    }

    public Optional<ObservableList<Room>> getTableByFilterState(RoomRepositoryJDBC roomRepositoryJDBC) throws Exception {
        return roomRepositoryJDBC.filter(roomRepositoryJDBC.createFilterStatement(this.getId(), this.getDescription(), this.getSize(), this.getLocation(), this.getEquipment(), this.isImage()), isBlank(this.getEquipment()));
    }

    public Optional<ObservableList<Room>> filterValueWithImage(RoomRepositoryJDBC roomRepositoryJDBC, boolean checkImage) throws Exception {

        setImage(checkImage);

        return roomRepositoryJDBC.filter(roomRepositoryJDBC.createFilterStatement(this.getId(), this.getDescription(), this.getSize(), this.getLocation(), this.getEquipment(), this.isImage()), isBlank(this.getEquipment()));
    }

    private boolean isBlank(String s){
        return s.equals("");
    }

    public void addFilterBox(FilterBox filterBox){
        filterBoxes.add(filterBox);
    }

    public FilterCheckBox getImageCheckBox() {
        return imageCheckBox;
    }

    public void setImageCheckBox(FilterCheckBox imageCheckBox) {
        this.imageCheckBox = imageCheckBox;
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

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
}
