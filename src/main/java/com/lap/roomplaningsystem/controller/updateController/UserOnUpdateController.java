package com.lap.roomplaningsystem.controller.updateController;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
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
import javafx.util.StringConverter;

public class UserOnUpdateController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button saveUser;

    @FXML
    private ComboBox<Boolean> userActivComboBox;

    @FXML
    private ComboBox<String> userAuthorizationCombobox;

    @FXML
    private Button userChangeImage;

    @FXML
    private TextField userEMailInput;

    @FXML
    private ComboBox<Boolean> userEMailVisableComboBox;

    @FXML
    private TextField userFirstnameInput;

    @FXML
    private ImageView userImageView;

    @FXML
    private TextField userLastnameInput;

    @FXML
    private Label userNumberLabel;

    @FXML
    private TextField userPhoneInput;

    @FXML
    private ComboBox<Boolean> userPhoneVisableComboBox;

    @FXML
    private ComboBox<Boolean> userPhotoVisableComboBox;

    @FXML
    private TextArea userTextInput;

    @FXML
    private ComboBox<Boolean> userTextVisableComboBox;

    @FXML
    private TextField userTitleInput;

    @FXML
    private ComboBox<Boolean> userTrainerComboBox;

    @FXML
    private TextField userUsernameInput;

    @FXML
    void initialize() {
        assert saveUser != null : "fx:id=\"saveUser\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userActivComboBox != null : "fx:id=\"userActivComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userAuthorizationCombobox != null : "fx:id=\"userAuthorizationCombobox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userChangeImage != null : "fx:id=\"userChangeImage\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userEMailInput != null : "fx:id=\"userEMailInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userEMailVisableComboBox != null : "fx:id=\"userEMailVisableComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userFirstnameInput != null : "fx:id=\"userFirstnameInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userImageView != null : "fx:id=\"userImageView\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userLastnameInput != null : "fx:id=\"userLastnameInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userNumberLabel != null : "fx:id=\"userNumberLabel\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userPhoneInput != null : "fx:id=\"userPhoneInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userPhoneVisableComboBox != null : "fx:id=\"userPhoneVisableComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userPhotoVisableComboBox != null : "fx:id=\"userPhotoVisableComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userTextInput != null : "fx:id=\"userTextInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userTextVisableComboBox != null : "fx:id=\"userTextVisableComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userTitleInput != null : "fx:id=\"userTitleInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userTrainerComboBox != null : "fx:id=\"userTrainerComboBox\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";
        assert userUsernameInput != null : "fx:id=\"userUsernameInput\" was not injected: check your FXML file 'userDetailOnUpdate-view.fxml'.";

        initView();
    }

    private void initView() {
        User u = model.getShowUser();

        userNumberLabel.setText("B" + String.valueOf(u.getId()));
        userFirstnameInput.setText(u.getFirstname());
        userLastnameInput.setText(u.getLastname());
        userTitleInput.setText(u.getTitle());
        userUsernameInput.setText(u.getUsername());
        userPhoneInput.setText(u.getPhone());
        userEMailInput.setText(u.getEmail());
        userTextInput.setText(u.getText());

        if(u.getPhoto() != null){
            userImageView.setImage(new Image(new ByteArrayInputStream(u.getPhoto())));
        }

        ObservableList<Boolean> booleanList = booleanList();

        userActivComboBox.setItems(booleanList);
        userTrainerComboBox.setItems(booleanList);
        userTextVisableComboBox.setItems(booleanList);
        userEMailVisableComboBox.setItems(booleanList);
        userPhoneVisableComboBox.setItems(booleanList);
        userPhotoVisableComboBox.setItems(booleanList);
        userAuthorizationCombobox.setItems(authorizationList());

        userActivComboBox.getSelectionModel().select(u.isActive());
        userTrainerComboBox.getSelectionModel().select(u.isTrainer());
        userTextVisableComboBox.getSelectionModel().select(u.isTextVisable());
        userEMailVisableComboBox.getSelectionModel().select(u.isEmailVisable());
        userPhoneVisableComboBox.getSelectionModel().select(u.isPhoneVisable());
        userPhotoVisableComboBox.getSelectionModel().select(u.isPhotoVisable());
        userAuthorizationCombobox.getSelectionModel().select(u.getAuthorization() == "admin" ? "Administrator" : "Trainer");

        initStringConverter(userActivComboBox);
        initStringConverter(userTrainerComboBox);
        initStringConverter(userTextVisableComboBox);
        initStringConverter(userEMailVisableComboBox);
        initStringConverter(userPhoneVisableComboBox);
        initStringConverter(userPhotoVisableComboBox);


    }

    private void initStringConverter(ComboBox<Boolean> box) {
        box.setConverter(new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean b) {
                return b ? "ja" : "nein";
            }

            @Override
            public Boolean fromString(String s) {
                return s.equals("ja")? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }

    private ObservableList<Boolean> booleanList() {
        ObservableList<Boolean> booleanList = FXCollections.observableArrayList();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        return booleanList;
    }

    private ObservableList<String> authorizationList() {
        ObservableList<String> authorizationList = FXCollections.observableArrayList();
        authorizationList.add("Administrator");
        authorizationList.add("Trainer");
        return authorizationList;
    }

    @FXML
    void onUserButtonClicked(MouseEvent event) {

    }

    @FXML
    void onUserChangeImageButtonClicked(MouseEvent event) {

    }

}