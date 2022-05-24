package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.EquipmentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;

public class EquipmentRepositoryJDBC extends Repository implements EquipmentRepository {
    @Override
    public Optional<ObservableList<Equipment>> readAll() throws Exception {
        Connection connection = connect();

        String query = "{CALL equipmentListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);;
        ResultSet resultSet = stmt.executeQuery();;

        return createEquipment(resultSet);
    }

    @Override
    public Equipment add(String description) throws Exception {
        Connection connection = connect();

        String query = "INSERT INTO equipment (DESCRIPTION) VALUES (\"" + description + "\")";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, description);

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Equipment equipment = null;

        while(resultSet.next()){
            int equipmentID  = resultSet.getInt(1);
            equipment = new Equipment(equipmentID, description);
        }

        return equipment;
    }


    @Override
    public boolean update(Equipment equipment) throws SQLException {

        Connection connection = connect();

        String query = "UPDATE equipment SET DESCRIPTION = ? WHERE EQUIPMENTID = ?";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query);

        stmt.setString(1, equipment.getDescription());
        stmt.setInt(2, equipment.getEquipmentID());


        int isUpdated = stmt.executeUpdate();

        return isUpdated != 0;


    }

    @Override
    public boolean delete(Equipment equipment) throws SQLException {

        Connection connection = connect();

        String query = "DELETE FROM equipment WHERE EQUIPMENTID = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, equipment.getEquipmentID());

        int isDeleted = stmt.executeUpdate();

        return isDeleted != 0;
    }




    private Optional<ObservableList<Equipment>> createEquipment(ResultSet resultSet) throws SQLException {
        ObservableList<Equipment> observableListEquipment = FXCollections.observableArrayList();

        while (resultSet.next()) {

            Equipment equipment = new Equipment(resultSet.getInt("EQUIPMENTID"),
                    resultSet.getString("DESCRIPTION"));


            observableListEquipment.add(equipment);
        }

        return Optional.of(observableListEquipment);
    }


}
