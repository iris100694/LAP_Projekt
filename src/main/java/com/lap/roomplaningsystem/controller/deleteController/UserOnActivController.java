package com.lap.roomplaningsystem.controller.deleteController;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.Optional;

public class UserOnActivController extends BaseController {

    @FXML
    private Label authoricationLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label usernameLabel;


    @FXML
    void initialize() {
        assert authoricationLabel != null : "fx:id=\"authoricationLabel\" was not injected: check your FXML file 'userDetailOnActiv-view.fxml'.";
        assert firstnameLabel != null : "fx:id=\"firstnameLabel\" was not injected: check your FXML file 'userDetailOnActiv-view.fxml'.";
        assert lastnameLabel != null : "fx:id=\"lastnameLabel\" was not injected: check your FXML file 'userDetailOnActiv-view.fxml'.";
        assert usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'userDetailOnActiv-view.fxml'.";

        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(u -> u.getId() == model.getSelectedUserProperty().getId()).findAny();

        if(optionalUser.isPresent()){
            User user = optionalUser.get();

            firstnameLabel.setText(user.getFirstname());
            lastnameLabel.setText(user.getLastname());
            usernameLabel.setText(user.getUsername());

            String authorication = user.getAuthorization() == "admin" ? "Administrator" : "Trainer";
            authoricationLabel.setText(authorication);

        }

    }

    @FXML
    void onNoButtonClicked(MouseEvent event) {
        Stage stage = (Stage) firstnameLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onYesButtonClicked(MouseEvent event) throws Exception {
        Optional <User> optionalUser = model.getDataholder().getUsers().stream().filter(u -> u.getId() == model.getSelectedUserProperty().getId()).findAny();
        model.setSelectedUserProperty(null);

        optionalUser.ifPresent(u -> {
            try {
                boolean isActiv = Dataholder.userRepositoryJDBC.inActiv(u);
                if(isActiv){
                    int index = model.getDataholder().getUsers().indexOf(u);
                    model.getDataholder().updateUser(index, u);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Optional<ObservableList<Event>> optionalEvents = Dataholder.eventRepositoryJDBC.readAll();
        optionalEvents.ifPresent(events -> model.getDataholder().setEvents(events));


        Stage stage = (Stage) firstnameLabel.getScene().getWindow();
        stage.close();


    }

}
