package com.lap.roomplaningsystem.model;

import com.lap.roomplaningsystem.repository.JDBC.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Dataholder {

    protected ObservableList<User> users;
    protected ObservableList<Location> locations;
    protected ObservableList<Course> courses;
    protected ObservableList<Program> programs;
    protected ObservableList<Equipment> equipments;
    protected ObservableList<Event> events;
    protected ObservableList<Room> rooms;
    protected ObservableList<RoomEquipment> roomEquipments;

    public static UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();
    public static RoomRepositoryJDBC roomRepositoryJDBC = new RoomRepositoryJDBC();
    public static EventRepositoryJDBC eventRepositoryJDBC = new EventRepositoryJDBC();
    public static CourseRepositoryJDBC courseRepositoryJDBC = new CourseRepositoryJDBC();
    public static ProgramRepositoryJDBC programRepositoryJDBC = new ProgramRepositoryJDBC();
    public static EquipmentRepositoryJDBC equipmentRepositoryJDBC = new EquipmentRepositoryJDBC();
    public static LocationRepositoryJDBC locationRepositoryJDBC = new LocationRepositoryJDBC();
    public static RoomEquipmentRepositoryJDBC roomEquipmentRepositoryJDBC = new RoomEquipmentRepositoryJDBC();


    public Dataholder() throws Exception {
        this.users = userRepositoryJDBC.readAll().orElse(null);
        this.locations = locationRepositoryJDBC.readAll().orElse(null);
        this.courses = courseRepositoryJDBC.readAll().orElse(null);
        this.programs = programRepositoryJDBC.readAll().orElse(null);
        this.equipments = equipmentRepositoryJDBC.readAll().orElse(null);
        this.events = eventRepositoryJDBC.readAll().orElse(null);
        this.rooms = roomRepositoryJDBC.readAll().orElse(null);
        this.roomEquipments = roomEquipmentRepositoryJDBC.readAll().orElse(null);

        initChangeListener();

    }

    private void initChangeListener() {
        events.addListener(new ListChangeListener<Event>() {
            @Override
            public void onChanged(Change<? extends Event> c) {

                while (c.next()) {
                    if (c.wasRemoved()) {
                        try {
                            eventRepositoryJDBC.delete(c.getRemoved().get(0));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } else if (c.wasUpdated()) {
                        //update item
                    }
                }
            }

        });
    }


    public ObservableList<User> getCoaches() {
        ObservableList<User> coaches = FXCollections.observableArrayList();
        for (User u : getUsers()){
            if(u.isTrainer() && u.isActive()){
                coaches.add(u);
            }
        }
        return coaches;
    }

    public void deleteEvent(Event e) {
        events.remove(e);
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

    public ObservableList<User> getUsers() {
        return users;
    }

    public void setUsers(ObservableList<User> users) {
        this.users = users;
    }

    public ObservableList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ObservableList<Location> locations) {
        this.locations = locations;
    }

    public ObservableList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ObservableList<Course> courses) {
        this.courses = courses;
    }

    public ObservableList<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(ObservableList<Program> programs) {
        this.programs = programs;
    }

    public ObservableList<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(ObservableList<Equipment> equipments) {
        this.equipments = equipments;
    }

    public ObservableList<RoomEquipment> getRoomEquipments() {
        return roomEquipments;
    }

    public void setRoomEquipments(ObservableList<RoomEquipment> roomEquipments) {
        this.roomEquipments = roomEquipments;
    }

    public void addProgram(Program program){
        this.programs.add(program);
    }


    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
    }

    public void addRoomEquipment(RoomEquipment roomEquipment) {
        this.roomEquipments.add(roomEquipment);
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void addEvents(ObservableList<Event> events) {
        this.events.addAll(events);
    }

}
