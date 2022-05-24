package com.lap.roomplaningsystem.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Location;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class HomeViewController extends BaseController {


    @FXML
    private Label loginLabel;
    @FXML
    private CalendarView calendarView;

    @FXML
    void initialize() {
        if(model.getUser() != null){
            loginLabel.setText("Logout");
            loginLabel.setOnMouseClicked(this::onLogoutLabelClicked);
        }

        ObservableList<Event> events = model.getDataholder().getEvents();
        Calendar calendar = new Calendar("Veranstaltungen");

        events.forEach(event->{
            Entry entry = createEntry(event);
            calendar.addEntry(entry);

        });

        CalendarSource calendarSource = new CalendarSource();
        calendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(calendarSource);

    }

    private Entry createEntry(Event event) {
        LocalDate d = event.getDate();
        LocalTime start = event.getStartTime().toLocalTime();
        LocalTime end = event.getEndTime().toLocalTime();
        Location location = event.getRoom().getLocation();

        Interval interval = new Interval(LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), start.getHour(), start.getMinute()),
                LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), end.getHour(), end.getMinute()));

        Entry entry = new Entry(event.getCourse().getTitle() + " " + event.getCourse().getProgram().getDescription(), interval);
        System.out.println(entry);
        entry.setLocation(event.getRoom().getDescription() + ": " +location.getDescription() + ", " + location.getAdress() + ", " + location.getPostCode() + " " + location.getCity());
        entry.setUserObject(event);

        return entry;
    }

    private void onLogoutLabelClicked(MouseEvent mouseEvent){
        model.setAuthorization("standard");
        model.setUser(null);
        loginLabel.setText("Login");
        loginLabel.setOnMouseClicked(this::onLoginLabelClicked);
    }

    @FXML
    private void onLoginLabelClicked(MouseEvent mouseEvent) {
        model.setPathToView(Constants.PATH_TO_LOGIN_VIEW);
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }
}
