package com.lap.roomplaningsystem.validation;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.function.Predicate;

public class EventValidator {
    private ObservableList<Event> events;
    private Event updateEvent = null;
    ObservableList<LocalDate> valid = FXCollections.observableArrayList();
    ObservableList<LocalDate> inValid = FXCollections.observableArrayList();
    private String errString;

    private Room selectedRoom;
    private Course selectedCourse;
    private LocalTime selectedStart;
    private LocalTime selectedEnd;



    public EventValidator(ObservableList<Event> eventList) {
        this.events = eventList;

        BaseController.model.getDataholder().getEvents().addListener(new ListChangeListener<Event>() {
            @Override
            public void onChanged(Change<? extends Event> change){
//                events = BaseController.model.getDataholder().getEvents();
                System.out.println(events.size());
            }
        });
    }


    public boolean validSingle(Room room, Course course, LocalDate day, LocalTime start, LocalTime end){
        setSelection(room,course, start,end);
        return collision(day) && withinCourse(day);
    }

    public boolean validSeries(Room room, Course course, ObservableList<LocalDate> days, LocalTime start, LocalTime end){
        setSelection(room,course, start,end);
        for(LocalDate day : days){
            if(collision(day) && withinCourse(day)){
                valid.add(day);
            } else{
                inValid.add(day);
            }
        }
        System.out.println(valid);
        return valid.size() > 0 || inValid.size() > 0;
    }

    public boolean collision(LocalDate day){
        FilteredList<Event> filteredList = new FilteredList<>(events);
        Predicate<Event> predicateForDay = event -> event.getDate().equals(day);
        filteredList.setPredicate(predicateForDay);

        for(Event event: filteredList){
            boolean verify = updateEvent == null || event.getEventID() != updateEvent.getEventID();
            System.out.println(filteredList.size());

            if(verify && isRoomNotAvailable(event)){
                setErrString("Veranstaltungen überschneidet sich!");
                return false;
            } else {
                if(verify && !isValidTime(event.getStartTime(), event.getEndTime()) && isCourseRegistered(event)){
                    setErrString("Veranstaltung für Kurs bereits gebucht!");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isRoomNotAvailable(Event event){
        System.out.println(event.getRoom().getRoomID() == selectedRoom.getRoomID() && !isValidTime(event.getStartTime(), event.getEndTime()));
        return event.getRoom().getRoomID() == selectedRoom.getRoomID() && !isValidTime(event.getStartTime(), event.getEndTime());
    }

    private boolean isCourseRegistered(Event event) {
        return event.getCourse().getCourseID() == selectedCourse.getCourseID();
    }

    private boolean isValidTime(Time start, Time end) {
        boolean valid = selectedStart.isBefore(start.toLocalTime()) ? selectedEnd.isBefore(start.toLocalTime()) || selectedEnd.equals(start.toLocalTime()) : selectedStart.isAfter(end.toLocalTime()) || selectedStart.equals(end.toLocalTime());

        if (!valid){
            setErrString("Veranstaltungszeiten überschneiden sich!");
        }
        return valid;
    }

    public static boolean isValidTime(LocalTime start, LocalTime end, Event event) {
        return start.isBefore(event.getStartTime().toLocalTime()) ? end.isBefore(event.getStartTime().toLocalTime()) || end.equals(event.getStartTime().toLocalTime()) : start.isAfter(event.getEndTime().toLocalTime()) || start.equals(event.getEndTime().toLocalTime());

    }

//    private boolean isEnd(LocalTime time, Time start, Time end ) {
//        boolean valid = !time.isAfter(start.toLocalTime()) && !time.isBefore(end.toLocalTime()) && !time.equals(end.toLocalTime());
//        if (!valid){
//            setErrString("Veranstaltungszeiten überschneiden sich!");
//        }
//
//        return valid;
//    }

    private boolean withinCourse(LocalDate day) {
        boolean valid = day.isAfter(selectedCourse.getStart().toLocalDate()) && day.isBefore(selectedCourse.getEnd().toLocalDate());
        if (!valid){
            setErrString("Datum außerhalb des Kursdatums");
        }
        return valid;
    }

    public String getErrString() {
        return errString;
    }

    public void setErrString(String errString) {
        this.errString = errString;
    }

    public ObservableList<LocalDate> getValid() {
        return valid;
    }

    public void setValid(ObservableList<LocalDate> valid) {
        this.valid = valid;
    }

    public ObservableList<LocalDate> getInValid() {
        return inValid;
    }

    public void setInValid(ObservableList<LocalDate> inValid) {
        this.inValid = inValid;
    }

    public Event getUpdateEvent() {
        return updateEvent;
    }

    public void setUpdateEvent(Event updateEvent) {
        this.updateEvent = updateEvent;
    }

    private void setSelection(Room room, Course course, LocalTime start, LocalTime end){
        this.selectedRoom = room;
        this.selectedCourse = course;
        this.selectedStart = start;
        this.selectedEnd = end;
    }
}
