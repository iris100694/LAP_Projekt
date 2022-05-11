package com.lap.roomplaningsystem.filter;

import com.lap.roomplaningsystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;

public class RequestFilter {
    private  String location;
    private  String room;
    private  Boolean roomDisable = true;
    private  String size;
    private  String equipment;
    private  String course;
    private  String user;
    private LocalDate startDate;
    private  LocalDate endDate;
    private LocalTime startTime;
    private  LocalTime endTime;


    public void handleRequest(String location, String room, String user, String equipment,
                                     String size, LocalDate startDate, LocalDate endDate,
                                     LocalTime startTime, LocalTime endTime) {
        this.location = location;
        this.room = room;
        this.user = user;
        this.equipment = equipment;
        this.size = size;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;


    }

    public ObservableList<Room> filter(Model model) {
        ObservableList<Room> rooms = model.getDataholder().getRooms();

        if(!isBlank(location)) {
            rooms.removeIf(r -> !r.getLocation().getDescription().equals(location));
            System.out.println(rooms);
        }

        if(!isBlank(room) && !roomDisable){
            rooms.removeIf(r -> !room.equals(r.getDescription()));
            System.out.println(rooms);
        }

        if(!isBlank(size)){
            rooms.removeIf(r -> r.getMaxPersons() != Integer.parseInt(size));
            System.out.println(rooms);
        }

        if(startDate != null){
            if(endDate != null){
                for(Event e : model.getDataholder().getEvents()){
                    rooms.removeIf(r -> e.getRoom() == r && !e.getDate().isBefore(startDate)|| !e.getDate().isBefore(endDate));
                }

            } else {
                for(Event e : model.getDataholder().getEvents()) {
                    rooms.removeIf(r -> e.getRoom() == r && !e.getDate().isEqual(startDate));
                }
            }
            System.out.println(rooms);
        }

        if(!isBlank(equipment)){
            Room matchRoom = null;
            for(RoomEquipment roomEquipment : model.getDataholder().getRoomEquipments()){
                if(roomEquipment.getEquipment().getDescription().equals(equipment)){
                    matchRoom = roomEquipment.getRoom();
                    break;
                }
            }
            if(matchRoom != null){
                Room finalMatchRoom = matchRoom;
                rooms.removeIf(r -> r.getRoomID() != finalMatchRoom.getRoomID());
            } else {
                rooms.removeAll();
            }

            System.out.println(rooms);
        }

        if(!isBlank(user)){
            ObservableList<User> coaches = model.getDataholder().getCoaches();
            for(Event e : model.getDataholder().getEvents()){
                if((e.getCoach().getFirstname() + " " + e.getCoach().getLastname()).equals(user)){
                    if(!e.getDate().isBefore(startDate) || !e.getDate().isAfter(endDate)){
                        coaches.removeIf( coach -> e.getCoach() == coach);
                        System.out.println(coaches);
                    }
                }
            }
            System.out.println(rooms);
        }

        return rooms;
    }


    private boolean isBlank(String s){
        return s == null || s.equals("");
    }

    public Boolean getRoomDisable() {
        return roomDisable;
    }

    public void setRoomDisable(Boolean roomDisable) {
        this.roomDisable = roomDisable;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
