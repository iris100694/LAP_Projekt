package com.lap.roomplaningsystem.repository;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Repository {

    static final String dbPrefix = "jdbc:mariadb:";
    //location = IP-Adresse + Port + / Datenbank-Name
    static final String location = "//localhost:3306/lap_roomplaningsystem";
    static final String dbUser = "root";
    static final String dbPw = "";

    Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location, dbUser, dbPw);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }


   byte[] createImageByteArray(Blob photo) {
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

}
