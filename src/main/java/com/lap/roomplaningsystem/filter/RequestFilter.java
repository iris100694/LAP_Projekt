package com.lap.roomplaningsystem.filter;

import com.lap.roomplaningsystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RequestFilter {
    private  String location = "";
    private  String room = "";
    private  Boolean roomDisable = true;
    private  String size = "";
    private  String equipment = "";
    private  String course = "";
    private  String user = "";
    private LocalDate startDate;
    private  LocalDate endDate;
    private LocalTime startTime;
    private  LocalTime endTime;


    public Predicate<Room> filter(Model model) {
        List<Predicate<Room>> predicateList = new ArrayList<>();

        if(!isBlank(location)) {
            predicateList.add(r -> r.getLocation().getDescription().equals(location));
        }

        if(!isBlank(room) && !roomDisable){
            predicateList.add(r -> room.equals(r.getDescription()));

        }

        if(!isBlank(size)){
            predicateList.add(r -> r.getMaxPersons() > Integer.parseInt(size));

        }

        //TODO: Kursdaten müssen mit Veranstaltungsdatum Single und Serienbuchung übereinstimmen !!!!

        if(startDate != null){
            if(endDate != null){
                for(Event e : model.getDataholder().getEvents()){
                    predicateList.add(r -> e.getRoom() != r && e.getDate().isBefore(startDate)|| e.getDate().isBefore(endDate));
//
                }

            } else {
                for(Event e : model.getDataholder().getEvents()) {
                    predicateList.add(r -> e.getRoom() != r && e.getDate().isBefore(startDate)|| e.getDate().isBefore(endDate));
                }
            }
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
                predicateList.add(r -> r.getRoomID() == finalMatchRoom.getRoomID());
            } else {
                predicateList.add(r -> false);
            }

        }

        if(!isBlank(user)){
            FilteredList<User> coaches = new FilteredList<>(model.getDataholder().getCoaches());
            for(Event e : model.getDataholder().getEvents()){
                if((e.getCoach().getFirstname() + " " + e.getCoach().getLastname()).equals(user)){
                    if(!e.getDate().isBefore(startDate) || !e.getDate().isAfter(endDate)){
                        coaches.setPredicate( coach -> e.getCoach() != coach);

                    }
                }
            }

        }

        Predicate<Room> combinedPredicate = predicateList.stream().reduce(r -> true, Predicate::and);

        return combinedPredicate;
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
