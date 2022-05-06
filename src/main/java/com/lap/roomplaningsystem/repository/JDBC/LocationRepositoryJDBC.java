package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.LocationRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LocationRepositoryJDBC extends Repository implements LocationRepository {
    @Override
    public Optional<ObservableList<Location>> readAll() throws Exception {
        Connection connection = connect();

        String query = "{CALL locationListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        return createLocation(resultSet);
    }

    @Override
    public void add(Location location) throws SQLException {

    }

    @Override
    public void update(Location location) throws SQLException {

    }

    @Override
    public void delete(Location location) throws SQLException {

    }

    private Optional<ObservableList<Location>> createLocation(ResultSet resultSet) throws SQLException {
        ObservableList<Location> observableListLocation = FXCollections.observableArrayList();

        while (resultSet.next()) {

            Location location = new Location(resultSet.getInt("LOCATIONID"),
                    resultSet.getString("DESCRIPTION"),
                    resultSet.getString("ADRESS"),
                    resultSet.getString("POSTCODE"),
                    resultSet.getString("CITY"));


            observableListLocation.add(location);
        }

        return Optional.of(observableListLocation);
    }
}
