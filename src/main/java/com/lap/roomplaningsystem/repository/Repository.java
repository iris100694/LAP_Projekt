package com.lap.roomplaningsystem.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class Repository {

    static final String dbPrefix = "jdbc:mariadb:";
    //location = IP-Adresse + Port + / Datenbank-Name
    static final String location = "//localhost:3306/lap_roomplaningsystem";
    static final String dbUser = "root";
    static final String dbPw = "";

    protected Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location, dbUser, dbPw);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }


   protected byte[] createImageByteArray(Blob photo) {
        byte[] image = null;

        if(photo != null){
            try{
                int bloblength = (int) photo.length();
                image = photo.getBytes(1, bloblength);

            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return image;
   }

    public ArrayList<ObservableList<String>> listsForChoiceBox(String query) throws SQLException {
        ArrayList<ObservableList<String>> list = new ArrayList<>();


        Connection connection = connect();

        CallableStatement stmt;
        ResultSet resultSet;
        System.out.println(query);


        stmt = connection.prepareCall(query);

        stmt.executeQuery();
        boolean getMoreResults = true;

        while(getMoreResults){
            ObservableList<String> results = FXCollections.observableArrayList();
            results.add("");
            resultSet = stmt.getResultSet();

            while(resultSet.next()){
                results.add(resultSet.getString("RESULTS"));
            }

            list.add(results);
            getMoreResults = stmt.getMoreResults();


        }

        return  list;

    }

}
