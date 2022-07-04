package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.RoomplaningsystemApplication;
import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Model;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class BaseController {
    public static Model model = new Model();


    protected void showNewView(String pathToView) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RoomplaningsystemApplication.class.getResource(pathToView));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    protected Parent loadFXMLRootNode(String pathToFXML) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(RoomplaningsystemApplication.class.getResource(pathToFXML));
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected void setProfilImage(ImageView imageView){
        if(model.getUser().getPhoto() != null && model.getUser().isPhoneVisable()){
            imageView.setImage(new Image(new ByteArrayInputStream(model.getUser().getPhoto())));
        }
    }

    protected void closeStage(Label label){
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
    }



    protected boolean isBlank(String s) {
        return s.equals("");
    }



    protected void logout(){
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);
        model.setLogout(true);

    }

    protected void removeProfilImage(ImageView imageView){
        File file = new File(Constants.PATH_TO_PROFILICON);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }



}
