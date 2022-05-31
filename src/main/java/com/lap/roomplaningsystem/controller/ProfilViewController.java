package com.lap.roomplaningsystem.controller;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.model.Dataholder;
import com.lap.roomplaningsystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class ProfilViewController extends BaseController{

    @FXML
    private Label authorizationLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label textLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView imageView;

    @FXML
    private Label errorLabel;

    User user;

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;



    @FXML
    void initialize() {
        assert authorizationLabel != null : "fx:id=\"authorizationLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert lastnameLabel != null : "fx:id=\"lastnameLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert phoneLabel != null : "fx:id=\"phoneLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert textLabel != null : "fx:id=\"textLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert firstnameLabel != null : "fx:id=\"fristnameLabel\" was not injected: check your FXML file 'profile-view.fxml'.";
        assert usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'profile-view.fxml'.";


        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(user -> user.getId() == model.getUser().getId()).findAny();
        System.out.println(optionalUser.isPresent());
        if (optionalUser.isPresent()) {
            user = optionalUser.get();

            firstnameLabel.setText(user.getFirstname());
            lastnameLabel.setText(user.getLastname());
            titleLabel.setText(!user.getTitle().equals("") ? user.getTitle() : "ohne");
            usernameLabel.setText(user.getUsername());
            authorizationLabel.setText(user.getAuthorization().equals("admin") ? "Administrator" : "Trianer");
            phoneLabel.setText(user.isPhoneVisable()? user.getPhone() : "nicht sichtbar");
            emailLabel.setText(user.isEmailVisable()? user.getEmail() : "nicht sichtbar");
            textLabel.setText(user.isTextVisable()? user.getText() : "nicht sichtbar");

            if (user.getPhoto() != null) {
                imageView.setImage(new Image(new ByteArrayInputStream(user.getPhoto())));
            }
        }
    }

    @FXML
    void onEditUserClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EDIT_USER);
    }

    @FXML
    void onLogoutButtonClicked(ActionEvent actionEvent) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);
    }

    @FXML
    private void onImageChangeClicked(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        try{
            inputStream = new FileInputStream(file);
            user.setPhoto(inputStream.readAllBytes());
            boolean isChanged = Dataholder.userRepositoryJDBC.changeProfileImage(user, inputStream);

            if (isChanged) {
                imageView.setImage(new Image(inputStream));
            } else {
                errorLabel.setText("Foto konnte nicht aktualisiert werden! (max. Größe: 16 Mb)");
            }


        } catch (Exception e){
            System.out.println("Kein Bild ausgewählt!");
        }

    }



}


