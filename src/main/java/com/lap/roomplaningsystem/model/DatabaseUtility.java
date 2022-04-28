package com.lap.roomplaningsystem.model;

import com.lap.roomplaningsystem.app.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.*;


public class DatabaseUtility {

    static final String dbPrefix = "jdbc:mariadb:";
    //location = IP-Adresse + Port + / Datenbank-Name
    static final String location = "//localhost:3306/lap_roomplaningsystem";
    static final String dbUser = "root";
    static final String dbPw = "";

    private static Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location, dbUser, dbPw);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    public static User checkUsernamePw(String username, String password) {
        Connection connection = DatabaseUtility.connect();


        String query = "{CALL loginStatement(?,?)}";

        CallableStatement stmt = null;
        ResultSet resultSet = null;

        try {
            stmt = connection.prepareCall(query);

            stmt.setString(1, username);
            stmt.setString(2, password);

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getBoolean("ACTIVE")) {
                    User user = new User(resultSet.getInt("USERID"),
                            resultSet.getString("TITLE"),
                            resultSet.getString("FIRSTNAME"),
                            resultSet.getString("LASTNAME"),
                            resultSet.getString("USERNAME"),
                            resultSet.getString("AUTHORIZATION"),
                            resultSet.getBoolean("COACH"),
                            resultSet.getString("TEXT"),
                            resultSet.getBoolean("TEXTVISABLE"),
                            resultSet.getString("PHONE"),
                            resultSet.getBoolean("PHONEVISABLE"),
                            resultSet.getString("EMAIL"),
                            resultSet.getBoolean("EMAILVISABLE"),
                            resultSet.getBytes("PHOTO"),
                            resultSet.getBoolean("PHOTOVISABLE"));

                    return user;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public static ObservableList<Room> createRooms() {
        ObservableList<Room> rooms = FXCollections.observableArrayList();

        Connection connection = DatabaseUtility.connect();

        String query = "{CALL roomListStatement()}";

        CallableStatement stmt = null;
        ResultSet resultSet = null;

        try {
            stmt = connection.prepareCall(query);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                byte [] photo = createImageByteArray(resultSet.getBlob("PHOTO"));

                Room room = new Room(resultSet.getInt("ROOMID"),
                        resultSet.getString("DESCRIPTION"),
                        new Location(resultSet.getInt("LOCATIONID"),
                                resultSet.getString("LDESCRIPTION"),
                                resultSet.getString("ADRESS"),
                                resultSet.getString("POSTCODE"),
                                resultSet.getString("CITY")),
                        resultSet.getInt("MAXPERSONS"),
                        photo);

                rooms.add(room);
                System.out.println(room.toString());
            }

            return rooms;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] createImageByteArray(Blob photo) {
        byte[] image = null;

        if(photo != null){
            try{
                int bloblength = (int) photo.length();
                image = photo.getBytes(1, bloblength);

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return image;
    }


    public static ObservableList<Event> createEvents() {
        //select events.EVENTID, events.CREATORID, rooms.DISCRIPTION AS "ROOM", course.TITLE AS "COURSE", program.DESCRIPTION AS "PROGRAM", users.LASTNAME AS "COACH", events.START, events.END FROM events LEFT JOIN rooms ON events.ROOMID = rooms.ROOMID LEFT JOIN course ON events.COURSEID = course.COURSEID INNER JOIN program ON course.PROGRAMID = program.PROGRAMID LEFT JOIN users ON events.COACHID = users.USERID;
        ObservableList<Event> events = FXCollections.observableArrayList();

        Connection connection = DatabaseUtility.connect();


        String query = "{CALL eventListStatement()}";

        CallableStatement stmt = null;
        ResultSet resultSet = null;

        try {
            stmt = connection.prepareCall(query);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                byte[] roomImage = resultSet.getBytes("PHOTO");
                User creator = getUser(resultSet.getInt("CREATORID"));
                User coach = getUser(resultSet.getInt("COACHID"));

                Event event = new Event(resultSet.getInt("EVENTID"), creator,
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
                        coach, resultSet.getDate("START"),
                        resultSet.getTime("START"),
                        resultSet.getTime("END"));

                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static User getUser(int userID) {
        Connection connection = DatabaseUtility.connect();


        String query = "{CALL getUserStatement(?)}";

        CallableStatement stmt = null;
        ResultSet resultSet = null;

        try {
            stmt = connection.prepareCall(query);

            stmt.setInt(1, userID);

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                    User user = new User(resultSet.getInt("USERID"),
                            resultSet.getString("TITLE"),
                            resultSet.getString("FIRSTNAME"),
                            resultSet.getString("LASTNAME"),
                            resultSet.getString("USERNAME"),
                            resultSet.getString("AUTHORIZATION"),
                            resultSet.getBoolean("COACH"),
                            resultSet.getString("TEXT"),
                            resultSet.getBoolean("TEXTVISABLE"),
                            resultSet.getString("PHONE"),
                            resultSet.getBoolean("PHONEVISABLE"),
                            resultSet.getString("EMAIL"),
                            resultSet.getBoolean("EMAILVISABLE"),
                            resultSet.getBytes("PHOTO"),
                            resultSet.getBoolean("PHOTOVISABLE"));

                    return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public static ArrayList<ObservableList<String>> listsForChoiceBox(String query) {
        ArrayList<ObservableList<String>> list = new ArrayList<>();


        Connection connection = DatabaseUtility.connect();

        CallableStatement stmt;
        ResultSet resultSet;
        System.out.println(query);

        try {
            stmt = connection.prepareCall(query);

            stmt.executeQuery();
            boolean getMoreResults = true;

            while(getMoreResults){
                ObservableList<String> results = FXCollections.observableArrayList();
                results.add("");
                resultSet = stmt.getResultSet();

                while(resultSet.next()){
                    results.add(resultSet.getString("RESULTS"));
                }

                list.add(results);
                getMoreResults = stmt.getMoreResults();


            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }


    public static ObservableList<Room> filterRooms(String sqlFilter, boolean equipment) {
        ObservableList<Room> rooms = FXCollections.observableArrayList();

        Connection connection = DatabaseUtility.connect();

        String query = getRoomQuery(equipment) + sqlFilter;

        System.out.println(query);

        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            stmt = connection.prepareStatement(query);

            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                byte [] photo = createImageByteArray(resultSet.getBlob("PHOTO"));
                Room room = new Room(resultSet.getInt("ROOMID"),
                        resultSet.getString("DESCRIPTION"),
                        new Location(resultSet.getInt("LOCATIONID"),
                                resultSet.getString("LDESCRIPTION"),
                                resultSet.getString("ADRESS"),
                                resultSet.getString("POSTCODE"),
                                resultSet.getString("CITY")),
                        resultSet.getInt("MAXPERSONS"),
                        photo);

                rooms.add(room);
            }

            return rooms;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private static String getRoomQuery(boolean equipment) {
        return equipment ? Constants.ROOM_EQUIPMENT_FILTER : Constants.ROOM_BASE_FILTER;
    }

    public static ObservableList<Event> filterEvents(String sqlFilter) {
        ObservableList<Event> events = FXCollections.observableArrayList();

        Connection connection = DatabaseUtility.connect();

        String query = Constants.EVENTS_BASE_FILTER + sqlFilter;

        System.out.println(query);

        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            stmt = connection.prepareStatement(query);

            resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                byte[] roomImage = resultSet.getBytes("PHOTO");
                User creator = getUser(resultSet.getInt("CREATORID"));
                User coach = getUser(resultSet.getInt("COACHID"));

                Event event = new Event(resultSet.getInt("EVENTID"), creator,
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
                        coach, resultSet.getDate("START"),
                        resultSet.getTime("START"),
                        resultSet.getTime("END"));

                events.add(event);
            }

            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
