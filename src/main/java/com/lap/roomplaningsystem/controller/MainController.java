package com.lap.roomplaningsystem.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController extends BaseController{


    @FXML
    private BorderPane mainView;


    private void loadFXMLInBorderPaneCenter(String fxmlPath) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
        mainView.setCenter(view);
    }

    @FXML
    private void initialize(){
        model.pathToViewProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue != null){
                    try {
                        loadFXMLInBorderPaneCenter(newValue);

                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
