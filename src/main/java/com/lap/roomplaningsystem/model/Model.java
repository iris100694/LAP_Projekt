package com.lap.roomplaningsystem.model;

import com.lap.roomplaningsystem.repository.JDBC.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.SQLException;
import java.util.Optional;


public class Model {
    private StringProperty pathToView = new SimpleStringProperty();
    private StringProperty authorization = new SimpleStringProperty("standard");

    private User user = null;
    private Dataholder dataholder;

    private Event showEvent;
    private Room showRoom;
    private Course showCourse;
    private Program showProgram;
    private Location showLocation;
    private Equipment showEquipment;
    private RoomEquipment showRoomEquipment;
    private User  showUser;

    public Model()  {

        try {
            this.dataholder = new Dataholder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validateLogin(String username, String password) throws SQLException {

        Optional<User> validUser = dataholder.userRepositoryJDBC.checkUsernamePw(username, password);

        if(validUser.isPresent()){
            setAuthorization(validUser.get().getAuthorization());
            setUser(validUser.get());
            return true;
        }
        return false;
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

    public Dataholder getDataholder() {
        return dataholder;
    }

    public void setDataholder(Dataholder dataholder) {
        this.dataholder = dataholder;
    }

    public Course getShowCourse() {
        return showCourse;
    }

    public void setShowCourse(Course showCourse) {
        this.showCourse = showCourse;
    }

    public Program getShowProgram() {
        return showProgram;
    }

    public void setShowProgram(Program showProgram) {
        this.showProgram = showProgram;
    }

    public Location getShowLocation() {
        return showLocation;
    }

    public void setShowLocation(Location showLocation) {
        this.showLocation = showLocation;
    }

    public Equipment getShowEquipment() {
        return showEquipment;
    }

    public void setShowEquipment(Equipment showEquipment) {
        this.showEquipment = showEquipment;
    }

    public RoomEquipment getShowRoomEquipment() {
        return showRoomEquipment;
    }

    public void setShowRoomEquipment(RoomEquipment showRoomEquipment) {
        this.showRoomEquipment = showRoomEquipment;
    }

    public User getShowUser() {
        return showUser;
    }

    public void setShowUser(User showUser) {
        this.showUser = showUser;
    }
}
