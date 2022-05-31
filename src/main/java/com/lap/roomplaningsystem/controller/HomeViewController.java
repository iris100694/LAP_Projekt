package com.lap.roomplaningsystem.controller;

import com.calendarfx.model.*;
import com.calendarfx.view.CalendarView;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Location;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class HomeViewController extends BaseController {



    @FXML
    private CalendarView calendarView;
    @FXML
    private ImageView profilImage;
    @FXML
    private Button loginButton;

    @FXML
    void initialize() {
        if(model.getUser() != null){
            loginButton.setText("Logout");
            loginButton.setOnAction(this::onLogoutButtonClicked);
        }

       initView();
    }

    private void initView(){
        ObservableList<Event> events = model.getDataholder().getEvents();

        Calendar calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);


        events.forEach(event->{
            Entry<Event> entry = createEntry(event);
            calendar.addEntry(entry);

        });

        if(model.getAuthorization().equals("standard")){
            calendar.setReadOnly(true);
        } else{
            setProfilImage(profilImage);
        }


        calendar.addEventHandler(new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent calendarEvent) {
                if(calendarEvent.isEntryRemoved()){
                    Entry<?> entry = calendarEvent.getEntry();
                    Event event = (Event) entry.getUserObject();
                    try {
                        model.setSelectedEventProperty(null);
                        boolean isDeleted = Dataholder.eventRepositoryJDBC.delete(event);
                        if(isDeleted){
                            model.getDataholder().deleteEvent(event);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        calendarView.setEntryDetailsPopOverContentCallback(param -> {
            Entry<?> entry = param.getEntry();
            Event event = (Event) entry.getUserObject();
            model.setSelectedEventProperty(event);
            model.setShowInCalendar(true);
            return loadFXMLRootNode(Constants.PATH_TO_EVENT_ON_UPDATE_VIEW);
        });

    }



    private Entry<Event> createEntry(Event event) {
        LocalDate d = event.getDate();
        LocalTime start = event.getStartTime().toLocalTime();
        LocalTime end = event.getEndTime().toLocalTime();
        Location location = event.getRoom().getLocation();

        Interval interval = new Interval(LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), start.getHour(), start.getMinute()),
                LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), end.getHour(), end.getMinute()));
        Entry<Event> entry = new Entry<>(event.getCourse().getTitle() + " " + event.getCourse().getProgram().getDescription(), interval);
        entry.setLocation(event.getRoom().getDescription() + ": " +location.getDescription() + ", " + location.getAdress() + ", " + location.getPostCode() + " " + location.getCity());
        entry.setUserObject(event);


        return entry;
    }



    private void onLogoutButtonClicked(ActionEvent actionEvent){
        model.setAuthorization("standard");
        model.setUser(null);
        loginButton.setText("Login");
        loginButton.setOnAction(this:: onLoginButtonClicked);
        initView();
    }

    @FXML
    private void onLoginButtonClicked(ActionEvent actionEvent) {
        model.setPathToView(Constants.PATH_TO_LOGIN_VIEW);
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }


}
