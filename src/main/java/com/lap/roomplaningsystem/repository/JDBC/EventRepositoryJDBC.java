package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.EventRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        return event;
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

    @Override
    public ObservableList<Event> seriesDaily(User creator, Room room, Course course, User coach, LocalDate startDate, LocalDate endDate, String start, String end) {
//        Calendar calendar = Calendar.getInstance();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        ObservableList<LocalDate> days = null;
//        ObservableList<Event> events = null;
//
//        for(int i = 1; startDate.isBefore(endDate) || startDate.toString().equals(endDate.toString()); i++){
//            assert false;
//            LocalDate day = startDate.plusDays(i);
//            if(!isWeekend(day)){
//                days.add(day);
//            }
//        }
//
//        for(LocalDate d : days){
//            LocalDateTime finalStartDate = LocalDateTime.parse(d.toString() + " " + start, formatter);
//            LocalDateTime finalEndDare = LocalDateTime.parse(d.toString() + " " + end, formatter);
//
//            Event event = Dataholder.eventRepositoryJDBC.add(model.getUser(),roomComboBox.getValue(),courseComboBox.getValue(),
//                    coachComboBox.getValue(), singleDateDatePicker.getValue(), start, end);
//        };









        return null;
    }

    @Override
    public ObservableList<Event> seriesWeekly(User creator, Room room, Course course, User coach, LocalDate startDate, LocalDate endDate, String startTime, String endTime) {
        return null;
    }

    @Override
    public ObservableList<Event> seriesMonthly(User creator, Room room, Course course, User coach, LocalDate startDate, LocalDate endDate, String startTime, String endTime) {
        return null;
    }


    @Override
    public Optional<ObservableList<Event>> filter(String filter) throws Exception{

        Connection connection = connect();

        String query = Constants.EVENTS_BASE_FILTER + filter;


        PreparedStatement stmt = connection.prepareStatement(query);
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


    public static boolean isWeekend(final LocalDate ld)
    {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

}
