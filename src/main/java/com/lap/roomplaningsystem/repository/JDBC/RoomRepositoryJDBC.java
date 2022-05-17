package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.RoomRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


public class RoomRepositoryJDBC extends Repository implements RoomRepository {

    @Override
    public Optional<ObservableList<Room>> readAll() throws Exception {

        Connection connection = connect();

        String query = "{CALL roomListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        return createRooms(resultSet);

    }

    public String createFilterStatement(String id, String description, String size, String location, String equipment, boolean image) {
        String stmt = "";

        if (!isBlank(id)) {
            stmt = stmt + " WHERE rooms.ROOMID = " + id;
        }

        if (!isBlank(description)) {
            stmt = isBlank(stmt) ? stmt + " WHERE rooms.DESCRIPTION = \"" + description + "\"" : stmt + " AND rooms.DESCRIPTION = \"" + description + "\"";
        }

        if (!isBlank(size)) {
            stmt = isBlank(stmt) ? stmt + " WHERE rooms.MAXPERSONS = " + size : stmt + " AND rooms.MAXPERSONS = " + size;
        }

        if (!isBlank(location)) {
            stmt = isBlank(stmt) ? stmt + " WHERE locations.DESCRIPTION = \"" + location + "\"" : stmt + " AND locations.DESCRIPTION = \"" + location + "\"";
        }

        if (!isBlank(equipment)) {
            stmt = isBlank(stmt) ? stmt + " WHERE equipment.DESCRIPTION = \"" + equipment + "\"" : stmt + " AND equipment.DESCRIPTION = \"" + equipment + "\"";
        }

        if(image){
            stmt = isBlank(stmt) ? stmt + " WHERE rooms.PHOTO IS NOT NULL" : stmt + " AND rooms.PHOTO IS NOT NULL";
        }

        return stmt;
    }

    private boolean isBlank(String stmt) {
        return stmt.equals("");
    }


    @Override
    public Optional<ObservableList<Room>> filter(String filter, boolean equipment) throws Exception {

        Connection connection = connect();

        String query = equipment ?  Constants.ROOM_BASE_FILTER + filter : Constants.ROOM_EQUIPMENT_FILTER + filter;

        System.out.println(query);

        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();

        return createRooms(resultSet);
    }

    @Override
    public Room add(String description, Location location, String maxPersons, InputStream inputStream) throws SQLException {

        byte[] photo= new byte[0];
        if (inputStream != null) {
            try {
                photo = inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Connection connection = connect();

        String query = "INSERT INTO rooms (DESCRIPTION, LOCATIONID, MAXPERSONS, PHOTO) VALUES (?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, description);
        stmt.setInt(2, location.getLocationID());
        stmt.setInt(3, Integer.parseInt(maxPersons));
        stmt.setBlob(4, inputStream);

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Room room = null;

        while(resultSet.next()){

            int roomID  = resultSet.getInt(1);
            room = new Room(roomID, description, location, Integer.parseInt(maxPersons), photo);
        }

        return room;


    }

    @Override
    public void update(Room room) throws SQLException {

    }

    @Override
    public void delete(Room room) throws SQLException {

    }






    private Optional<ObservableList<Room>> createRooms(ResultSet resultSet) throws Exception{
        ObservableList<Room> observableListRooms = FXCollections.observableArrayList();

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

            observableListRooms.add(room);
        }

        return Optional.of(observableListRooms);
    }

}
