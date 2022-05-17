package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.CourseRepository;
import com.lap.roomplaningsystem.repository.interfaces.ProgramRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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
    public Program add(String description) throws Exception{
        Connection connection = connect();

        String query = "INSERT INTO program (DESCRIPTION) VALUES (?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, description);

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Program program = null;

        while(resultSet.next()){
            int programID  = resultSet.getInt(1);
            program = new Program(programID, description);
        }

        return program;


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
