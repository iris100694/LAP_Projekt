package com.lap.roomplaningsystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Model {
    private StringProperty pathToView = new SimpleStringProperty();
    private StringProperty authorization = new SimpleStringProperty("standard");

    private User user = null;
    private Datamodel datamodel = new Datamodel();
    private Event showEvent;
    private Room showRoom;

    public Model() {
    }

    public String getPathToView() {
        return pathToView.get();
    }

    public StringProperty pathToViewProperty() {
        return pathToView;
    }

    public void setPathToView(String pathToView) {
        this.pathToView.set(pathToView);
    }

    public String getAuthorization() {
        return authorization.get();
    }

    public StringProperty authorizationProperty() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization.set(authorization);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getShowEvent() {
        return showEvent;
    }

    public void setShowEvent(Event showEvent) {
        this.showEvent = showEvent;
    }

    public void setShowRoom(Room showRoom) {
        this.showRoom = showRoom;
    }

    public Room getShowRoom() {
        return showRoom;
    }

    public Datamodel getDatamodel() {
        return datamodel;
    }

    public void setDatamodel(Datamodel datamodel) {
        this.datamodel = datamodel;
    }

    public boolean validateLogin(String username, String password) {

        User validUser = DatabaseUtility.checkUsernamePw(username, password);

        if(validUser != null){
            setAuthorization(validUser.getAuthorization());
            setUser(validUser);
            return true;
        }
        return false;
    }
}
