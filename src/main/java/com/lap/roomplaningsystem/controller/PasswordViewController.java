package com.lap.roomplaningsystem.controller;

import com.lap.roomplaningsystem.app.Password;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PasswordViewController extends BaseController{

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private Button saveButton;



    @FXML
    void initialize() {
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'createPassword-view.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'createPassword-view.fxml'.";
        assert passwordField2 != null : "fx:id=\"passwordField2\" was not injected: check your FXML file 'createPassword-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'createPassword-view.fxml'.";

    }


    @FXML
    void onSaveButtonClicked(MouseEvent event) {
        if(isBlank(passwordField.getText()) || isBlank(passwordField2.getText())){
            errorLabel.setText("Bitte Felder ausfüllen!");
        } else if (!passwordField.getText().equals(passwordField2.getText())){
            errorLabel.setText("Bitte Passwort wiederholen!");
        } else {
            if(!Password.validate(passwordField.getText())){
                errorLabel.setText("Passwort entspricht nicht den Anforderungen!");
            } else {
                model.setHashedPassword(Password.hash(passwordField.getText()));

                Stage detailStage = (Stage) passwordField.getScene().getWindow();
                detailStage.close();
            }
        }

    }

}
