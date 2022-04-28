package com.lap.roomplaningsystem.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RoomDetailViewController extends BaseController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteRoom;

    @FXML
    private Button editRoom;


    @FXML
    private Label roomDetailViewNumberLabel;

    @FXML
    private ImageView roomDetailImageView;

    @FXML
    private Label roomDetailLocationLabel;

    @FXML
    private Label roomDetailMembersLabel;

    @FXML
    private Label roomDetailTitleLabel;

    @FXML
    void onRoomDeleteButtonClicked(MouseEvent event) {

    }

    @FXML
    void onRoomEditButtonClicked(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert deleteRoom != null : "fx:id=\"deleteRoom\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert editRoom != null : "fx:id=\"editRoom\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailViewNumberLabel != null : "fx:id=\"eventDetailViewNumberLabel\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailImageView != null : "fx:id=\"roomDetailImageView\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailLocationLabel != null : "fx:id=\"roomDetailLocationLabel\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailMembersLabel != null : "fx:id=\"roomDetailMembersLabel\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailTitleLabel != null : "fx:id=\"roomDetailTitleLabel\" was not injected: check your FXML file 'roomDetail-view.fxml'.";

        initData();
        editAuthorization();

    }


    private void initData() {
        Room r = model.getShowRoom();

        roomDetailViewNumberLabel.setText("V" + r.getRoomID());
        roomDetailTitleLabel.setText(r.getDescription());
        roomDetailLocationLabel.setText(r.getLocation().getDescription());
        roomDetailMembersLabel.setText(String.valueOf(r.getMaxPersons()));
        roomDetailImageView.setImage(new Image(new ByteArrayInputStream(r.getPhoto())));
    }

    private void editAuthorization() {
        if(model.getAuthorization().equals("admin")){
        editRoom.setVisible(true);
        deleteRoom.setVisible(true);
        }
    }

}
