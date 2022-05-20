package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Location;
import com.lap.roomplaningsystem.model.Model;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.repository.JDBC.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;

public class BaseController {
    public static Model model = new Model();


    protected void showNewView(String pathToView) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RoomplaningsystemApplication.class.getResource(pathToView));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    protected ObservableList<Room> availableRooms(Location newLocation) {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        for(Room r : model.getDataholder().getRooms()){
            if(r.getLocation().getLocationID() == newLocation.getLocationID()){
                rooms.add(r);
            }
        }
        return rooms;
    }

    protected ObservableList<LocalTime> createTimeList() {
        ObservableList<LocalTime> timeList = FXCollections.observableArrayList();

        for(int i = 0; i < 24; i++){
            timeList.add(LocalTime.of(i,0));
            timeList.add(LocalTime.of(i,30));
        }

        return timeList;
    }

    protected boolean isBlank(String s) {
        return s.equals("");
    }

    protected void initStringConverter(ComboBox<Boolean> box) {
        box.setConverter(new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean b) {
                return b ? "ja" : "nein";
            }

            @Override
            public Boolean fromString(String s) {
                return s.equals("ja")? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }

    protected ObservableList<Boolean> booleanList() {
        ObservableList<Boolean> booleanList = FXCollections.observableArrayList();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        return booleanList;
    }

    protected ObservableList<String> authorizationList() {
        ObservableList<String> authorizationList = FXCollections.observableArrayList();
        authorizationList.add("Administrator");
        authorizationList.add("Trainer");
        return authorizationList;
    }

    protected Integer getInt(String s) {
        try{
            return Integer.parseInt(s);
        } catch (Exception e){
            return null;
        }
    }
}
