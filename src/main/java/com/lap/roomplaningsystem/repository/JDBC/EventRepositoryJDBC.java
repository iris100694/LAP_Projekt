package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.formattor.DateFormattorJDBC;
import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.EventRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Optional;

public class EventRepositoryJDBC extends Repository implements EventRepository {



    @Override
    public Optional<ObservableList<Event>> readAll() throws Exception{
        //select events.EVENTID, events.CREATORID, rooms.DISCRIPTION AS "ROOM", course.TITLE AS "COURSE", program.DESCRIPTION AS "PROGRAM", users.LASTNAME AS "COACH", events.START, events.END FROM events LEFT JOIN rooms ON events.ROOMID = rooms.ROOMID LEFT JOIN course ON events.COURSEID = course.COURSEID INNER JOIN program ON course.PROGRAMID = program.PROGRAMID LEFT JOIN users ON events.COACHID = users.USERID;

        Connection connection = connect();

        String query = "{CALL eventListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        connection.close();
        System.out.println(connection.isClosed() ? "Connection closed": "Connection not closed");

        return createEvents(resultSet);

    }


    @Override
    public Event add(User creator, Room room, Course course, User coach, LocalDate date, LocalDateTime start, LocalDateTime end) throws Exception {
        Connection connection = connect();

        String query = "INSERT INTO events (CREATORID, ROOMID, COURSEID, COACHID, START, END) VALUES (?,?,?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, creator.getId());
        stmt.setInt(2, room.getRoomID());
        stmt.setInt(3, course.getCourseID());
        stmt.setInt(4, coach.getId());
        stmt.setString(5, start.toString());
        stmt.setString(6, end.toString());

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Event event = null;

        while(resultSet.next()){
            int eventID  = resultSet.getInt(1);
            event = new Event(eventID, creator, room, course, coach, date, Time.valueOf(start.toLocalTime()), Time.valueOf(end.toLocalTime()));
            System.out.println(event.toString());
        }

        connection.close();
        System.out.println(connection.isClosed() ? "Connection closed": "Connection not closed");

        return event;
    }


    @Override
    public boolean update(Event event) throws SQLException {

        String start = event.getDate().toString() + " " + event.getStartTime().toString();
        String end = event.getDate().toString() + " " + event.getEndTime().toString();

        Connection connection = connect();

        String query = "UPDATE events SET COURSEID = ?, ROOMID = ?, COACHID = ?, START = ?, END = ? WHERE EVENTID = ?";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query);

        stmt.setInt(1, event.getCourse().getCourseID());
        stmt.setInt(2, event.getRoom().getRoomID());
        stmt.setInt(3, event.getCoach().getId());
        stmt.setString(4, start);
        stmt.setString(5, end);
        stmt.setInt(6, event.getEventID());


        int isUpdated = stmt.executeUpdate();

        connection.close();
        System.out.println(connection.isClosed() ? "Connection closed": "Connection not closed");

        return isUpdated != 0;

    }

    @Override
    public boolean delete(Event e) throws SQLException {
        Connection connection = connect();

        String query = "{CALL deleteEventStatement(?)}";

        CallableStatement stmt = connection.prepareCall(query);
        stmt.setInt(1, e.getEventID());

        int isDeleted = stmt.executeUpdate();

        connection.close();
        System.out.println(connection.isClosed() ? "Connection closed": "Connection not closed");

        return isDeleted != 0;
    }

    //series Events



    @Override
    public ObservableList<Event> compileEvents(User creator, Room room, Course course, User coach, ObservableList<LocalDate> days, LocalTime start, LocalTime end) throws Exception{
        ObservableList<Event> events = FXCollections.observableArrayList();

        for(LocalDate d : days){
            LocalDateTime startDate = DateFormattorJDBC.format(d, start);
            LocalDateTime endDate = DateFormattorJDBC.format(d, end);

            Event event = add(creator,room,course, coach, d, startDate, endDate);

            if(event != null){
                events.add(event);
            }
        }


        return events;
    }


    //Filter Events
    @Override
    public Optional<ObservableList<Event>> filter(String filter) throws Exception{

        Connection connection = connect();

        String query = Constants.EVENTS_BASE_FILTER + filter;


        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();

        connection.close();
        System.out.println(connection.isClosed() ? "Connection closed": "Connection not closed");

        return createEvents(resultSet);
    }


//    public String createFilterStatement(String id, String description, String date, String start, String end) {
//        String stmt = "";
//
//        if (!isBlank(id)) {
//            stmt = stmt + "WHERE events.ROOMID = " + id;
//        }
//
//        if (!isBlank(description)) {
//            stmt = isBlank(stmt) ? stmt + "WHERE program.DESCRIPTION = \"" + description + "\"" : stmt + " AND program.DESCRIPTION = \"" + description + "\"";
//        }
//
//        if (!isBlank(date)) {
//            stmt = isBlank(stmt) ? stmt + "WHERE events.START LIKE \"" + date + "%\"" : stmt + " AND events.START LIKE \"" + date + "%\"";
//        }
//
//        if (!isBlank(start)) {
//            stmt = isBlank(stmt) ? stmt + "WHERE events.START LIKE \"%" + start + "\"": stmt + " AND events.START LIKE \"%" + start + "\"";
//        }
//
//        if (!isBlank(end)) {
//            stmt = isBlank(stmt) ? stmt + "WHERE events.END LIKE \"%" + end +"\"": stmt + " AND events.END LIKE \"%" + end + "\"";
//        }
//
//        return stmt;
//    }

    //Create Events
    private Optional<ObservableList<Event>> createEvents(ResultSet resultSet) throws Exception{
        ObservableList<Event> observableListEvents = FXCollections.observableArrayList();

        UserRepositoryJDBC userRepositoryJDBC = new UserRepositoryJDBC();

        while(resultSet.next()){
            byte[] roomImage = resultSet.getBytes("PHOTO");

            Optional<User> creator = userRepositoryJDBC.readUserByID(resultSet.getInt("CREATORID"));
            Optional<User> coach = userRepositoryJDBC.readUserByID(resultSet.getInt("COACHID"));

            Event event = new Event(resultSet.getInt("EVENTID"), creator.orElse(null),
                    new Room(resultSet.getInt("ROOMID"),
                            resultSet.getString("ROOMDESCRIPTION"),
                            new Location(resultSet.getInt("LOCATIONID"),
                                    resultSet.getString("LOCATIONDESCRIPTION"),
                                    resultSet.getString("ADRESS"),
                                    resultSet.getString("POSTCODE"),
                                    resultSet.getString("CITY")),
                            resultSet.getInt("MAXPERSONS"),
                            roomImage),
                    new Course(resultSet.getInt("COURSEID"),
                            new Program(resultSet.getInt("PROGRAMID"),
                                    resultSet.getString("PROGRAMDESCRIPTION")),
                            resultSet.getString("TITLE"),
                            resultSet.getInt("MEMBERS"),
                            resultSet.getDate("COURSESTART"),
                            resultSet.getDate("COURSEEND")),
                    coach.orElse(null), resultSet.getDate("START").toLocalDate(),
                    resultSet.getTime("START"),
                    resultSet.getTime("END"));

            observableListEvents.add(event);
        }

        return Optional.of(observableListEvents);

    }




}
