package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.*;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.CourseRepository;
import com.lap.roomplaningsystem.repository.interfaces.RoomEquipmentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void add(RoomEquipment roomEquipment) throws SQLException {

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
