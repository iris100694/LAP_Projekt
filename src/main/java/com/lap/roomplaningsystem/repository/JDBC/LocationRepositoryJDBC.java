package com.lap.roomplaningsystem.repository.JDBC;

import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.repository.Repository;
import com.lap.roomplaningsystem.repository.interfaces.LocationRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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
    public Location add(String description, String adress, String postCode, String city) throws Exception {
        Connection connection = connect();

        String query = "INSERT INTO locations (DESCRIPTION, ADRESS, POSTCODE, CITY) VALUES (?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, description);
        stmt.setString(2, adress);
        stmt.setString(3, postCode);
        stmt.setString(4, city);

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Location location = null;

        while(resultSet.next()){
            int locationID  = resultSet.getInt(1);
            location = new Location(locationID, description, adress, postCode, city);
        }

        return location;

    }


    @Override
    public Boolean update(Location location) throws SQLException {
        Connection connection = connect();

        String query = "UPDATE locations SET DESCRIPTION = ?, ADRESS = ?, POSTCODE = ?, CITY = ? WHERE LOCATIONID = ?";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, location.getDescription());
        stmt.setString(2, location.getAdress());
        stmt.setString(3, location.getPostCode());
        stmt.setString(4, location.getCity());
        stmt.setInt(5, location.getLocationID());



        int isUpdated = stmt.executeUpdate();

        return isUpdated != 0;

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
