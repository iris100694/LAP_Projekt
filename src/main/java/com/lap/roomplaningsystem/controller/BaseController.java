package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Model;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.repository.JDBC.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {
    public static Model model = new Model();


    protected void showNewView(String pathToView) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RoomplaningsystemApplication.class.getResource(pathToView));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}
