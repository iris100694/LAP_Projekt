package com.lap.roomplaningsystem.controller.updateController;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserOnEditController extends BaseController {

    @FXML
    private TextField emailInput;

    @FXML
    private Label errorLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField phoneInput;

    @FXML
    private Button saveButton;

    @FXML
    private TextArea textInput;

    private User user;




    @FXML
    void initialize() {
        assert emailInput != null : "fx:id=\"emailInput\" was not injected: check your FXML file 'userDetailOnEdit.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'userDetailOnEdit.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'userDetailOnEdit.fxml'.";
        assert phoneInput != null : "fx:id=\"phoneInput\" was not injected: check your FXML file 'userDetailOnEdit.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'userDetailOnEdit.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'userDetailOnEdit.fxml'.";

        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(user -> user.getId() == model.getUser().getId()).findAny();

        if(optionalUser.isPresent()){
            user = optionalUser.get();

            numberLabel.setText("B" + String.valueOf(user.getId()));
            phoneInput.setText(user.getPhone());
            emailInput.setText(user.getEmail());
            textInput.setText(user.getText());

        }

    }

    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        user.setPhone(phoneInput.getText());
        user.setEmail(emailInput.getText());
        user.setText(textInput.getText());

        boolean isEdited = Dataholder.userRepositoryJDBC.edit(user);

        if (isEdited) {
            int index = model.getDataholder().getUsers().indexOf(user);
            model.getDataholder().updateUser(index, user);

            if(user.getId() == model.getUser().getId()){
                model.setUser(user);
            }
        }


        Optional<ObservableList<Event>> optionalEvents = Dataholder.eventRepositoryJDBC.readAll();
        optionalEvents.ifPresent(events -> model.getDataholder().setEvents(events));


        Stage detailStage = (Stage) numberLabel.getScene().getWindow();
        detailStage.close();
    }


}
