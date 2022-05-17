package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.Course;
import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.CourseRepository;
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
    public void update(Equipment equipment) throws SQLException {

    }

    @Override
    public void delete(Equipment equipment) throws SQLException {

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
