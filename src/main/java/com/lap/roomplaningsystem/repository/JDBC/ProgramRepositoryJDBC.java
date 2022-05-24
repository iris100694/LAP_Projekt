package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.repository.Repository;
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
    public boolean update(Program program) throws SQLException {

        Connection connection = connect();

        String query = "UPDATE program SET DESCRIPTION = ? WHERE PROGRAMID = ?";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query);

        stmt.setString(1, program.getDescription());
        stmt.setInt(2, program.getProgramID());


        int isUpdated = stmt.executeUpdate();

        return isUpdated != 0;

    }

    @Override
    public boolean delete(Program program) throws SQLException {

        Connection connection = connect();

        String query = "DELETE FROM program WHERE PROGRAMID = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, program.getProgramID());

        int isDeleted = stmt.executeUpdate();

        return isDeleted != 0;
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
