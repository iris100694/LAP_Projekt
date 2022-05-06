package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.CourseRepository;
import com.lap.roomplaningsystem.repository.interfaces.ProgramRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProgramRepositoryJDBC extends Repository implements ProgramRepository {
    @Override
    public Optional<ObservableList<Program>> readAll() throws Exception {
        Connection connection = connect();

        String query = "{CALL programListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        return createPrograms(resultSet);
    }

    @Override
    public void add(Program program) throws SQLException {

    }

    @Override
    public void update(Program program) throws SQLException {

    }

    @Override
    public void delete(Program program) throws SQLException {

    }




    private Optional<ObservableList<Program>> createPrograms(ResultSet resultSet) throws SQLException {
        ObservableList<Program> observableListProgam = FXCollections.observableArrayList();

        while (resultSet.next()) {

            Program program = new Program(resultSet.getInt("PROGRAMID"),
                    resultSet.getString("DESCRIPTION"));

            observableListProgam.add(program);
        }

        return Optional.of(observableListProgam);
    }


}
