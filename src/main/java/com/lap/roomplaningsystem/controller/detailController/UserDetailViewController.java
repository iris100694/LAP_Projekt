package com.lap.roomplaningsystem.controller.detailController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserDetailViewController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteUser;

    @FXML
    private Button editUser;

    @FXML
    private Label userDetailViewActivLabel;

    @FXML
    private Label userDetailViewAuthorizationLabel;

    @FXML
    private Label userDetailViewFirstnameLabel;

    @FXML
    private ImageView userDetailViewImageView;

    @FXML
    private Label userDetailViewLastnameLabel;

    @FXML
    private Label userDetailViewMailLabel;

    @FXML
    private Label userDetailViewMailVisableLabel;

    @FXML
    private Label userDetailViewNumberLabel;

    @FXML
    private Label userDetailViewPhoneLabel;

    @FXML
    private Label userDetailViewPhoneVisableLabel;

    @FXML
    private Label userDetailViewPhotoVisableLabel;

    @FXML
    private Label userDetailViewTextLabel;

    @FXML
    private Label userDetailViewTextVisableLabel;

    @FXML
    private Label userDetailViewTitleLabel;

    @FXML
    private Label userDetailViewTrainerLabel;

    @FXML
    private Label userDetailViewUsernameLabel;



    @FXML
    void initialize() {
        assert deleteUser != null : "fx:id=\"deleteUser\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert editUser != null : "fx:id=\"editUser\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewActivLabel != null : "fx:id=\"userDetailViewActivLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewAuthorizationLabel != null : "fx:id=\"userDetailViewAuthorizationLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewFirstnameLabel != null : "fx:id=\"userDetailViewFirstnameLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewImageView != null : "fx:id=\"userDetailViewImageView\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewLastnameLabel != null : "fx:id=\"userDetailViewLastnameLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewMailLabel != null : "fx:id=\"userDetailViewMailLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewMailVisableLabel != null : "fx:id=\"userDetailViewMailVisableLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewNumberLabel != null : "fx:id=\"userDetailViewNumberLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewPhoneLabel != null : "fx:id=\"userDetailViewPhoneLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewPhoneVisableLabel != null : "fx:id=\"userDetailViewPhoneVisableLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewPhotoVisableLabel != null : "fx:id=\"userDetailViewPhotoVisableLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewTextLabel != null : "fx:id=\"userDetailViewTextLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewTextVisableLabel != null : "fx:id=\"userDetailViewTextVisableLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewTitleLabel != null : "fx:id=\"userDetailViewTitleLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewTrainerLabel != null : "fx:id=\"userDetailViewTrainerLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";
        assert userDetailViewUsernameLabel != null : "fx:id=\"userDetailViewUsernameLabel\" was not injected: check your FXML file 'userDetail-view.fxml'.";

        initView();
    }

    private void initView() {
        User u = model.getShowUser();

        userDetailViewFirstnameLabel.setText(u.getFirstname());
        userDetailViewLastnameLabel.setText(u.getLastname());
        userDetailViewTitleLabel.setText(u.getTitle());
        userDetailViewActivLabel.setText(u.isActive()? "aktiv" : "inaktiv");
        userDetailViewAuthorizationLabel.setText(u.getAuthorization() == "admin"? "Administator" : "Trainer");
        userDetailViewUsernameLabel.setText(u.getUsername());
        userDetailViewTrainerLabel.setText(u.isTrainer()? "ja" : "nein");
        userDetailViewTextLabel.setText(u.getText());
        userDetailViewTextVisableLabel.setText(u.isTextVisable()? "ja" : "nein");
        userDetailViewPhoneLabel.setText(u.getPhone());
        userDetailViewPhoneVisableLabel.setText(u.isPhoneVisable()? "ja" : "nein");
        userDetailViewMailLabel.setText(u.getEmail());
        userDetailViewMailVisableLabel.setText(u.isEmailVisable()? "ja" : "nein");
        userDetailViewPhotoVisableLabel.setText(u.isPhotoVisable()? "ja" : "nein");

        if(u.getPhoto() != null){
            userDetailViewImageView.setImage(new Image(new ByteArrayInputStream(u.getPhoto())));
        }
    }

    @FXML
    void onUserDeleteButtonClicked(MouseEvent event) {

    }

    @FXML
    void onUserEditButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_USER_UPDATE_VIEW);

        Stage detailStage = (Stage) editUser.getScene().getWindow();
        detailStage.close();
    }

}
