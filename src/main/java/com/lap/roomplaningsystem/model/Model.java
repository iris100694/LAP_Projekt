package com.lap.roomplaningsystem.model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;


public class Model {
    private StringProperty pathToView = new SimpleStringProperty();
    private StringProperty authorization = new SimpleStringProperty("standard");

    private User user = null;
    private Dataholder dataholder;


    private ObjectProperty<Event> selectedEventProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Room> selectedRoomProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Course> selectedCourseProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Program> selectedProgramProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Location> selectedLocationProperty = new SimpleObjectProperty<>();
    private ObjectProperty<Equipment> selectedEquipmentProperty = new SimpleObjectProperty<>();
    private ObjectProperty<RoomEquipment> selectedRoomEquipmentProperty = new SimpleObjectProperty<>();
    private ObjectProperty<User> selectedUserProperty = new SimpleObjectProperty<>();

    private ObservableList<Room> requestResult;
    private ObjectProperty<Room> selectedResultProperty = new SimpleObjectProperty<>();

    private StringProperty hashedPassword=  new SimpleStringProperty();

    public Model()  {

        try {
            this.dataholder = new Dataholder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean validateLogin(String username, String password) throws SQLException {

        Optional<User> validUser = Dataholder.userRepositoryJDBC.checkUsernamePw(username, password);

        if(validUser.isPresent()){
            setAuthorization(validUser.get().getAuthorization());
            setUser(validUser.get());
            return true;
        }
        return false;
    }


    public String getHashedPassword() {
        return hashedPassword.get();
    }

    public StringProperty hashedPasswordProperty() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword.set(hashedPassword);
    }

    public Event getSelectedEventProperty() {
        return selectedEventProperty.get();
    }

    public void setSelectedEventProperty(Event selectedEventProperty) {
        this.selectedEventProperty.set(selectedEventProperty);
    }

    public Room getSelectedResultProperty() {
        return selectedResultProperty.get();
    }

    public ObjectProperty<Room> selectedResultProperty() {
        return selectedResultProperty;
    }

    public void setSelectedResultProperty(Room selectedResultProperty) {
        this.selectedResultProperty.set(selectedResultProperty);
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

    public Dataholder getDataholder() {
        return dataholder;
    }

    public void setDataholder(Dataholder dataholder) {
        this.dataholder = dataholder;
    }

    public ObjectProperty<Event> selectedEventProperty() {
        return selectedEventProperty;
    }

    public Room getSelectedRoomProperty() {
        return selectedRoomProperty.get();
    }

    public ObjectProperty<Room> selectedRoomProperty() {
        return selectedRoomProperty;
    }

    public void setSelectedRoomProperty(Room selectedRoomProperty) {
        this.selectedRoomProperty.set(selectedRoomProperty);
    }

    public Course getSelectedCourseProperty() {
        return selectedCourseProperty.get();
    }

    public ObjectProperty<Course> selectedCourseProperty() {
        return selectedCourseProperty;
    }

    public void setSelectedCourseProperty(Course selectedCourseProperty) {
        this.selectedCourseProperty.set(selectedCourseProperty);
    }

    public Program getSelectedProgramProperty() {
        return selectedProgramProperty.get();
    }

    public ObjectProperty<Program> selectedProgramProperty() {
        return selectedProgramProperty;
    }

    public void setSelectedProgramProperty(Program selectedProgramProperty) {
        this.selectedProgramProperty.set(selectedProgramProperty);
    }

    public Location getSelectedLocationProperty() {
        return selectedLocationProperty.get();
    }

    public ObjectProperty<Location> selectedLocationProperty() {
        return selectedLocationProperty;
    }

    public void setSelectedLocationProperty(Location selectedLocationProperty) {
        this.selectedLocationProperty.set(selectedLocationProperty);
    }

    public Equipment getSelectedEquipmentProperty() {
        return selectedEquipmentProperty.get();
    }

    public ObjectProperty<Equipment> selectedEquipmentProperty() {
        return selectedEquipmentProperty;
    }

    public void setSelectedEquipmentProperty(Equipment selectedEquipmentProperty) {
        this.selectedEquipmentProperty.set(selectedEquipmentProperty);
    }

    public RoomEquipment getSelectedRoomEquipmentProperty() {
        return selectedRoomEquipmentProperty.get();
    }

    public ObjectProperty<RoomEquipment> selectedRoomEquipmentProperty() {
        return selectedRoomEquipmentProperty;
    }

    public void setSelectedRoomEquipmentProperty(RoomEquipment selectedRoomEquipmentProperty) {
        this.selectedRoomEquipmentProperty.set(selectedRoomEquipmentProperty);
    }

    public User getSelectedUserProperty() {
        return selectedUserProperty.get();
    }

    public ObjectProperty<User> selectedUserProperty() {
        return selectedUserProperty;
    }

    public void setSelectedUserProperty(User selectedUserProperty) {
        this.selectedUserProperty.set(selectedUserProperty);
    }

    public ObservableList<Room> getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(ObservableList<Room> requestResult) {
        this.requestResult = requestResult;
    }


}
