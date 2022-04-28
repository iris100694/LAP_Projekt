package com.lap.roomplaningsystem.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Datamodel {

    protected ObservableList<Event> events;
    protected ObservableList<Room> rooms;

    public Datamodel() {
        this.rooms = DatabaseUtility.createRooms();
        this.events = DatabaseUtility.createEvents();
    }

    public ObservableList<Event> getEvents() {
        return events;
    }

    public void setEvents(ObservableList<Event> events) {
        this.events = events;
    }

    public ObservableList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ObservableList<Room> rooms) {
        this.rooms = rooms;
    }
}
