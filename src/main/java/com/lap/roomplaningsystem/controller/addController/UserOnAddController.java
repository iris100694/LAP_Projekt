package com.lap.roomplaningsystem.controller.addController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UserOnAddController extends BaseController {

    @FXML
    private ComboBox<Boolean> activComboBox;

    @FXML
    private Button addImageButton;

    @FXML
    private ComboBox<String> authorizationCombobox;

    @FXML
    private TextField emailInput;

    @FXML
    private ComboBox<Boolean> emailVisableComboBox;

    @FXML
    private Label errorUsernameLabel;

    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private TextField phoneInput;

    @FXML
    private ComboBox<Boolean> phoneVisableComboBox;

    @FXML
    private ComboBox<Boolean> photoVisableComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private TextArea textInput;

    @FXML
    private ComboBox<Boolean> textVisableComboBox;

    @FXML
    private TextField titleInput;

    @FXML
    private ComboBox<Boolean> trainerComboBox;

    @FXML
    private ImageView userImageView;

    @FXML
    private TextField usernameInput;

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;
    @FXML
    private Label errorSaveLabel;
    @FXML
    private TextField password2Input;
    @FXML
    private TextField passwordInput;


    @FXML
    void initialize() {
        assert activComboBox != null : "fx:id=\"activComboBox\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert addImageButton != null : "fx:id=\"addImageButton\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert authorizationCombobox != null : "fx:id=\"authorizationCombobox\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert emailInput != null : "fx:id=\"emailInput\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert emailVisableComboBox != null : "fx:id=\"emailVisableComboBox\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert errorUsernameLabel != null : "fx:id=\"errorUsernameLabel\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert firstnameInput != null : "fx:id=\"firstnameInput\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert lastnameInput != null : "fx:id=\"lastnameInput\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert phoneInput != null : "fx:id=\"phoneInput\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert phoneVisableComboBox != null : "fx:id=\"phoneVisableComboBox\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert photoVisableComboBox != null : "fx:id=\"photoVisableComboBox\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert textVisableComboBox != null : "fx:id=\"textVisableComboBox\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert titleInput != null : "fx:id=\"titleInput\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert trainerComboBox != null : "fx:id=\"trainerComboBox\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert userImageView != null : "fx:id=\"userImageView\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";
        assert usernameInput != null : "fx:id=\"usernameInput\" was not injected: check your FXML file 'userDetailOnAdd-view.fxml'.";

        ObservableList<Boolean> booleanList = booleanList();

        activComboBox.setItems(booleanList);
        trainerComboBox.setItems(booleanList);
        textVisableComboBox.setItems(booleanList);
        emailVisableComboBox.setItems(booleanList);
        phoneVisableComboBox.setItems(booleanList);
        photoVisableComboBox.setItems(booleanList);
        authorizationCombobox.setItems(authorizationList());

        activComboBox.getSelectionModel().select(0);
        trainerComboBox.getSelectionModel().select(0);
        textVisableComboBox.getSelectionModel().select(0);
        emailVisableComboBox.getSelectionModel().select(0);
        phoneVisableComboBox.getSelectionModel().select(0);
        photoVisableComboBox.getSelectionModel().select(0);


        initStringConverter(activComboBox);
        initStringConverter(trainerComboBox);
        initStringConverter(textVisableComboBox);
        initStringConverter(emailVisableComboBox);
        initStringConverter(phoneVisableComboBox);
        initStringConverter(photoVisableComboBox);

        fileChooser.setTitle("Profilbild hinzufügen");
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        usernameInput.setOnKeyTyped(event -> {
            List<String> usernames = model.getDataholder().getUsers().stream().map(User::getUsername).toList();
            boolean exist = usernames.stream().anyMatch(username -> username.equals(usernameInput.getText()));
            if (exist) {
                errorUsernameLabel.setText("Username bereits vergeben!");
            } else {
                errorUsernameLabel.setText("");
            }
        });


    }


    @FXML
    void onAddImageButtonClicked(MouseEvent event) throws FileNotFoundException {
        File file = fileChooser.showOpenDialog(userImageView.getScene().getWindow());
        inputStream = new FileInputStream(file);
        userImageView.setImage(new Image(inputStream));

    }

    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        String emtpyfields = "";
        emtpyfields = isBlank(firstnameInput.getText()) ? "Vorname" : "";
        emtpyfields =  isBlank(emtpyfields) ? isBlank(lastnameInput.getText()) ? "Nachname" : "" : isBlank(lastnameInput.getText()) ? emtpyfields + ", Nachname" : emtpyfields;
        emtpyfields =  isBlank(emtpyfields) ? isBlank(usernameInput.getText()) ? "Username" : "" : isBlank(usernameInput.getText()) ? emtpyfields + ", Username" : emtpyfields;
        emtpyfields =  isBlank(emtpyfields) ? isBlank(passwordInput.getText()) ? "Passwort" : "" : isBlank(passwordInput.getText()) ? emtpyfields + ", Passwort" : emtpyfields;
        emtpyfields =  isBlank(emtpyfields) ? isBlank(password2Input.getText()) ? "Passwort wiederholen" : "" : isBlank(password2Input.getText()) ? emtpyfields + ", Passwort wiederholen" : emtpyfields;
        emtpyfields =  isBlank(emtpyfields) ? authorizationCombobox.getValue() == null ? "Authorisation" : "" : authorizationCombobox.getValue() == null ? emtpyfields + ", Authorisation" : emtpyfields;

        if(!isBlank(emtpyfields)){
            errorSaveLabel.setText("Bitte ausfüllen: " + emtpyfields + "!");

        } else if (!passwordInput.getText().equals(password2Input.getText())) {
            errorSaveLabel.setText("Bitte das Passwort wiederholen");

        } else {
                String authorize = authorizationCombobox.getValue().equals("Administator") ? "admin" : "coach";

                User user = Dataholder.userRepositoryJDBC.add(firstnameInput.getText(), lastnameInput.getText(), titleInput.getText(), usernameInput.getText(),
                        authorize, passwordInput.getText(), trainerComboBox.getValue(), textVisableComboBox.getValue(),
                        phoneInput.getText(), phoneVisableComboBox.getValue(), emailInput.getText(), emailVisableComboBox.getValue(),
                        photoVisableComboBox.getValue(), textInput.getText(), inputStream);
                model.getDataholder().addUser(user);

                Stage detailStage = (Stage) firstnameInput.getScene().getWindow();
                detailStage.close();
        }
    }
}
