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
import javafx.event.ActionEvent;
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
    private TextArea textInput;

    private User user;




    @FXML
    void initialize() {
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
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        user.setPhone(phoneInput.getText());
        user.setEmail(emailInput.getText());
        user.setText(textInput.getText());

        boolean isUpdated = updateUserByJDBC();

        if (isUpdated) {
            int index = model.getDataholder().getUsers().indexOf(user);
            model.getDataholder().updateUser(index, user);

            //TODO: Test ob notwendig!!!
            if(user.getId() == model.getUser().getId()){
                model.setUser(user);
            }

            model.getDataholder().updateEvents();
            closeStage(errorLabel);
        }


    }

    private boolean updateUserByJDBC() throws Exception {
        return Dataholder.userRepositoryJDBC.edit(user);
    }


}
