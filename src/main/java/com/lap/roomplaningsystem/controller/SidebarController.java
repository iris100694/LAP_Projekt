package com.lap.roomplaningsystem.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.app.Password;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SidebarController extends BaseController{


    @FXML
    private Label createEventLabel;

    @FXML
    private Label eventLabel;

    @FXML
    private Label homeLabel;

    @FXML
    private Label roomsLabel;

    @FXML
    private Label stammdataLabel;

    ArrayList<Label> menuItems;



    @FXML
    void initialize() {
        menuItems = new ArrayList<>(List.of(roomsLabel, stammdataLabel, homeLabel, eventLabel, createEventLabel));
        model.authorizationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldAuthorization, String newAuthorization) {
                if (newAuthorization != null) {
                    updateSidebarMenu(newAuthorization);
                }
            }
        });

        for(Label label : menuItems){

            label.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                menuItems.forEach(l -> {
                        if(l.getStyleClass().stream().anyMatch(classes -> classes.equals("clicked"))){
                            l.getStyleClass().remove("clicked");
                    }
                });

                menuItems.forEach(l -> l.setBackground(null));
                Label clickedLabel = (Label) e.getSource();
                clickedLabel.getStyleClass().add("clicked");

            });

        }

    }

    private void updateSidebarMenu(String newAuthorization) {
        switch(newAuthorization){
            case "coach" -> coachAuthorization();
            case "admin" -> adminAuthorization();
            default -> standardAuthorization();
        }
    }

    private void standardAuthorization() {
        createEventLabel.setVisible(false);
        stammdataLabel.setVisible(false);

    }

    private void adminAuthorization() {
        createEventLabel.setVisible(true);
        stammdataLabel.setVisible(true);
    }

    private void coachAuthorization() {
        createEventLabel.setVisible(true);
    }


    @FXML
    void onMouseClickedCreateEvents(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_CREATE_EVENT_VIEW);
    }

    @FXML
    void onMouseClickedEvents(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_EVENT_VIEW);

    }

    @FXML
    void onMouseClickedHome(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }

    @FXML
    void onMouseClickedRooms(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_ROOM_VIEW);
    }

    @FXML
    void onMouseClickedStammdata(MouseEvent event) {
        model.setPathToView(Constants.PATH_TO_STAMMDATA_VIEW);

    }


    @FXML
    private void testLogin(MouseEvent mouseEvent) throws SQLException {
        System.out.println(Password.hash("test"));
        model.validateLogin("admin", "test");
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);
    }

    @FXML
    private void menuItemHover(MouseEvent mouseEvent) {
        Label label = (Label) mouseEvent.getSource();

        if(label.getStyleClass().stream().noneMatch(classes -> classes.equals("clicked"))){
            label.setBackground(new Background(new BackgroundFill(Color.rgb(48,60,82, 0.6), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    @FXML
    private void menuItemExited(MouseEvent mouseEvent) {
        Label label = (Label) mouseEvent.getSource();
        label.setBackground(null);

    }
}
