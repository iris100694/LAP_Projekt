package com.lap.roomplaningsystem.controller.updateController;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.Program;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
import javafx.collections.FXCollections;
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
import javafx.util.StringConverter;

public class UserOnUpdateController extends BaseController {

    @FXML
    private ComboBox<Boolean> activComboBox;

    @FXML
    private ComboBox<String> authorizationCombobox;

    @FXML
    private Button changeImageButton;

    @FXML
    private TextField emailInput;

    @FXML
    private ComboBox<Boolean> emailVisableComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private Label errorUsernameLabel;

    @FXML
    private TextField firstnameInput;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField lastnameInput;

    @FXML
    private Label numberLabel;

    @FXML
    private Button passwordChangeButton;

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
    private TextField usernameInput;

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;



    @FXML
    void initialize() {
        assert activComboBox != null : "fx:id=\"activComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert authorizationCombobox != null : "fx:id=\"authorizationCombobox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert changeImageButton != null : "fx:id=\"changeImageButton\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert emailInput != null : "fx:id=\"emailInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert emailVisableComboBox != null : "fx:id=\"emailVisableComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert errorUsernameLabel != null : "fx:id=\"errorUsernameLabel\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert firstnameInput != null : "fx:id=\"firstnameInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert lastnameInput != null : "fx:id=\"lastnameInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert passwordChangeButton != null : "fx:id=\"passwordChangeButton\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert phoneInput != null : "fx:id=\"phoneInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert phoneVisableComboBox != null : "fx:id=\"phoneVisableComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert photoVisableComboBox != null : "fx:id=\"photoVisableComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert saveButton != null : "fx:id=\"saveButton\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert textVisableComboBox != null : "fx:id=\"textVisableComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert titleInput != null : "fx:id=\"titleInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert trainerComboBox != null : "fx:id=\"trainerComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert usernameInput != null : "fx:id=\"usernameInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";

        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(user -> user == model.getSelectedUserProperty()).findAny();

        if(optionalUser.isPresent()){
            User u = optionalUser.get();

            numberLabel.setText("B" + String.valueOf(u.getId()));
            firstnameInput.setText(u.getFirstname());
            lastnameInput.setText(u.getLastname());
            titleInput.setText(u.getTitle());
            usernameInput.setText(u.getUsername());
            phoneInput.setText(u.getPhone());
            emailInput.setText(u.getEmail());
            textInput.setText(u.getText());

            if(u.getPhoto() != null){
                imageView.setImage(new Image(new ByteArrayInputStream(u.getPhoto())));
            }

            activComboBox.getSelectionModel().select(u.isActive());
            trainerComboBox.getSelectionModel().select(u.isTrainer());
            textVisableComboBox.getSelectionModel().select(u.isTextVisable());
            emailVisableComboBox.getSelectionModel().select(u.isEmailVisable());
            phoneVisableComboBox.getSelectionModel().select(u.isPhoneVisable());
            photoVisableComboBox.getSelectionModel().select(u.isPhotoVisable());
            authorizationCombobox.getSelectionModel().select(u.getAuthorization() == "admin" ? "Administrator" : "Trainer");

        }

        ObservableList<Boolean> booleanList = booleanList();

        activComboBox.setItems(booleanList);
        trainerComboBox.setItems(booleanList);
        textVisableComboBox.setItems(booleanList);
        emailVisableComboBox.setItems(booleanList);
        phoneVisableComboBox.setItems(booleanList);
        photoVisableComboBox.setItems(booleanList);
        authorizationCombobox.setItems(authorizationList());

        initStringConverter(activComboBox);
        initStringConverter(trainerComboBox);
        initStringConverter(textVisableComboBox);
        initStringConverter(emailVisableComboBox);
        initStringConverter(phoneVisableComboBox);
        initStringConverter(photoVisableComboBox);


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
    void onChangeImageButtonClicked(MouseEvent event) throws IOException {

        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        try{
            inputStream = new FileInputStream(file);
            imageView.setImage(new Image(inputStream));
        } catch (Exception e){
            System.out.println("Kein Bild ausgewählt!");
        }

    }

    @FXML
    void onChangePasswordButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_CREATE_PASSWORD_VIEW);
    }

    @FXML
    void onSaveButtonClicked(MouseEvent event) throws Exception {
        if(isBlank(firstnameInput.getText()) || isBlank(lastnameInput.getText()) || isBlank(usernameInput.getText())
                || authorizationCombobox.getValue() == null || trainerComboBox.getValue() == null){
            errorLabel.setText("Bitte Pflichtfelder ausfüllen!");
        } else {
            boolean exist = model.getDataholder().getUsers().stream().anyMatch(u-> u.getId() != model.getSelectedUserProperty().getId() && u.getUsername() == usernameInput.getText());

            if(exist){
                errorLabel.setText("Username bereits vergeben!");
            } else {
                Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(u -> u.getId() == model.getSelectedUserProperty().getId()).findAny();

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    System.out.println(authorizationCombobox.getValue());
                    String authorize = authorizationCombobox.getValue().equals("Administrator") ? "admin" : "coach";
                    System.out.println(authorize);
                    user.setFirstname(firstnameInput.getText());
                    user.setLastname(lastnameInput.getText());
                    user.setActive(activComboBox.getValue());
                    user.setTitle(titleInput.getText());
                    user.setUsername(usernameInput.getText());
                    user.setAuthorization(authorize);
                    user.setTrainer(trainerComboBox.getValue());
                    user.setText(textInput.getText());
                    user.setTextVisable(textVisableComboBox.getValue());
                    user.setEmail(emailInput.getText());
                    user.setEmailVisable(emailVisableComboBox.getValue());
                    user.setPhone(phoneInput.getText());
                    user.setPhoneVisable(phoneVisableComboBox.getValue());
                    user.setPhotoVisable(photoVisableComboBox.getValue());

                    if(inputStream != null){
                        user.setPhoto(inputStream.readAllBytes());
                    }


                    boolean isUpdated = Dataholder.userRepositoryJDBC.update(user, model.getHashedPassword(), inputStream);

                    if (isUpdated) {
                        showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                        int index = model.getDataholder().getUsers().indexOf(user);
                        System.out.println(index);
                        model.getDataholder().updateUser(index, user);

                        if(user.getId() == model.getUser().getId()){
                            model.setUser(user);
                        }
                    }

                }

                Stage detailStage = (Stage) firstnameInput.getScene().getWindow();
                detailStage.close();
            }


        }

    }

}
