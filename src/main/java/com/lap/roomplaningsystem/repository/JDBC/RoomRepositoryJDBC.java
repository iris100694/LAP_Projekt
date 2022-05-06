package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.RoomRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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
    public void add(Room room) throws SQLException {

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
