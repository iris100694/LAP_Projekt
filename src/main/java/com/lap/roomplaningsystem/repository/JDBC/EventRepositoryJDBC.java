package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.EventRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;

public class EventRepositoryJDBC extends Repository implements EventRepository {



    @Override
    public Optional<ObservableList<Event>> readAll() throws Exception{
        //select events.EVENTID, events.CREATORID, rooms.DISCRIPTION AS "ROOM", course.TITLE AS "COURSE", program.DESCRIPTION AS "PROGRAM", users.LASTNAME AS "COACH", events.START, events.END FROM events LEFT JOIN rooms ON events.ROOMID = rooms.ROOMID LEFT JOIN course ON events.COURSEID = course.COURSEID INNER JOIN program ON course.PROGRAMID = program.PROGRAMID LEFT JOIN users ON events.COACHID = users.USERID;

        Connection connection = connect();

        String query = "{CALL eventListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        return createEvents(resultSet);

    }

    public String createFilterStatement(String id, String description, String date, String start, String end) {
        String stmt = "";

        if (!isBlank(id)) {
            stmt = stmt + "WHERE events.ROOMID = " + id;
        }

        if (!isBlank(description)) {
            stmt = isBlank(stmt) ? stmt + "WHERE program.DESCRIPTION = \"" + description + "\"" : stmt + " AND program.DESCRIPTION = \"" + description + "\"";
        }

        if (!isBlank(date)) {
            stmt = isBlank(stmt) ? stmt + "WHERE events.START LIKE \"" + date + "%\"" : stmt + " AND events.START LIKE \"" + date + "%\"";
        }

        if (!isBlank(start)) {
            stmt = isBlank(stmt) ? stmt + "WHERE events.START LIKE \"%" + start + "\"": stmt + " AND events.START LIKE \"%" + start + "\"";
        }

        if (!isBlank(end)) {
            stmt = isBlank(stmt) ? stmt + "WHERE events.END LIKE \"%" + end +"\"": stmt + " AND events.END LIKE \"%" + end + "\"";
        }

        return stmt;
    }

    private boolean isBlank(String s){
        return s.equals("");
    }


    @Override
    public Optional<ObservableList<Event>> filter(String filter) throws Exception{

        Connection connection = connect();

        String query = Constants.EVENTS_BASE_FILTER + filter;


        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();

        return createEvents(resultSet);
    }

    @Override
    public void add(Event event) throws SQLException {

    }

    @Override
    public void update(Event event) throws SQLException {

    }

    @Override
    public void delete(Event e) throws SQLException {
        Connection connection = connect();

        String query = "{CALL deleteEventStatement(?)}";

        CallableStatement stmt = connection.prepareCall(query);
        stmt.setInt(1, e.getEventID());

        ResultSet resultSet = stmt.executeQuery();

    }

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
                    coach.orElse(null), resultSet.getDate("START"),
                    resultSet.getTime("START"),
                    resultSet.getTime("END"));

            observableListEvents.add(event);
        }

        return Optional.of(observableListEvents);

    }

}
