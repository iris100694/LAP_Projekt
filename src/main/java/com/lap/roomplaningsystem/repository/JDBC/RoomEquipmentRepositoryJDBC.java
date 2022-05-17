package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.CourseRepository;
import com.lap.roomplaningsystem.repository.interfaces.RoomEquipmentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class RoomEquipmentRepositoryJDBC extends Repository implements RoomEquipmentRepository {
    @Override
    public Optional<ObservableList<RoomEquipment>> readAll() throws Exception {
        Connection connection = connect();

        String query = "{CALL roomEquipmentListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        return createRoomEquipment(resultSet);
    }

    @Override
    public RoomEquipment add(Room room, Equipment equipment) throws Exception {


        Connection connection = connect();

        String query = "INSERT INTO roomequipment (ROOMID, EQUIPMENTID) VALUES (?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, room.getRoomID());
        stmt.setInt(2, equipment.getEquipmentID());

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        RoomEquipment roomEquipment = null;

        while(resultSet.next()){

            int roomEquipmentID  = resultSet.getInt(1);
            roomEquipment = new RoomEquipment(roomEquipmentID, room, equipment);
        }

        return roomEquipment;
    }


    @Override
    public void update(RoomEquipment roomEquipment) throws SQLException {

    }

    @Override
    public void delete(RoomEquipment roomEquipment) throws SQLException {

    }





    private Optional<ObservableList<RoomEquipment>> createRoomEquipment(ResultSet resultSet) throws SQLException {
        ObservableList<RoomEquipment> observableListRoomEquipment = FXCollections.observableArrayList();

        while (resultSet.next()) {
            byte [] photo = createImageByteArray(resultSet.getBlob("PHOTO"));
            RoomEquipment roomEquipment = new RoomEquipment(resultSet.getInt("ROOMEQUIPMENTID"),
                    new Room(resultSet.getInt("ROOMID"),
                            resultSet.getString("DESCRIPTION"),
                            new Location(resultSet.getInt("LOCATIONID"),
                                    resultSet.getString("LOCATION"),
                                    resultSet.getString("ADRESS"),
                                    resultSet.getString("POSTCODE"),
                                    resultSet.getString("CITY")),
                            resultSet.getInt("MAXPERSONS"),
                            photo),
                    new Equipment(resultSet.getInt("EQUIPMENTID"),
                    resultSet.getString("EQUIPMENT")));


            observableListRoomEquipment.add(roomEquipment);
        }

        return Optional.of(observableListRoomEquipment);
    }


}
