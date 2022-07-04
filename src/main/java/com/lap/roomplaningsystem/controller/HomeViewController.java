package com.lap.roomplaningsystem.controller;

import com.calendarfx.model.*;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.ZonedDateTimeProvider;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Location;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;

public class HomeViewController extends BaseController {

    @FXML
    private CalendarView calendarView;
    @FXML
    private ImageView profilImage;
    @FXML
    private Button loginButton;

    Calendar calendar;

    @FXML
    void initialize() {
        if(model.getUser() != null){
            isLogged();
        }

        model.setCalendarView(calendarView);
        calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);

        initCalendar();

        model.newEventProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observableValue, Event event, Event newEvent) {
                System.out.println(newEvent.getEventID());
                Entry<Event> entry = createEntry(newEvent);
                calendar.addEntry(entry);
                calendarView.getCalendarSources().get(0).getCalendars().set(0, calendar);
                //TODO: find a Method to show new and deleted Events customized
            }
        });
    }

    private void isLogged() {
        setProfilImage(profilImage);
        loginButton.setText("Logout");
        loginButton.setOnAction(this::onLogoutButtonClicked);
    }

    private void initCalendar(){
//        model.setCalendarView(calendarView);
        ObservableList<Event> events = model.getDataholder().getEvents();
//        Calendar calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);

        //Handle Authorization
        if(model.getAuthorization().equals("standard")){
            calendar.setReadOnly(true);
        }

        //ADD Entries
        if(events.size() > 0){
            events.forEach(event->{
                Entry<Event> entry = createEntry(event);
                calendar.addEntry(entry);

            });
        }



        // Calendarevents ADD, DELETE
        calendar.addEventHandler(new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent calendarEvent) {
                if(calendarEvent.isEntryAdded()){

                    try {
                        model.setAddEventInCalendar(true);
                        calendar.removeEntry(calendarEvent.getEntry());
                        if(!model.isDetailView() && !model.isLogout()){
                            showNewView(Constants.PATH_TO_EVENT_ON_ADD_VIEW);
                        }
                        model.setAddEventInCalendar(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(calendarEvent.isEntryRemoved() && !model.isAddEventInCalendar()){
                    Entry<?> entry = calendarEvent.getEntry();
                    calendar.removeEntry(entry);
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
            return loadFXMLRootNode(model.getAuthorization().equals("standard")? Constants.PATH_TO_EVENT_DETAIL_VIEW: Constants.PATH_TO_EVENT_ON_UPDATE_VIEW);
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
        logout();
        loginButton.setText("Login");
        loginButton.setOnAction(this:: onLoginButtonClicked);
        removeProfilImage(profilImage);
        initCalendar();
        model.setLogout(false);
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
