package com.lap.roomplaningsystem.controller.detailController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Event;
import com.lap.roomplaningsystem.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RoomDetailViewController extends BaseController {

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
    void initialize() {
        assert deleteRoom != null : "fx:id=\"deleteRoom\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert editRoom != null : "fx:id=\"editRoom\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailViewNumberLabel != null : "fx:id=\"eventDetailViewNumberLabel\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailImageView != null : "fx:id=\"roomDetailImageView\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailLocationLabel != null : "fx:id=\"roomDetailLocationLabel\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailMembersLabel != null : "fx:id=\"roomDetailMembersLabel\" was not injected: check your FXML file 'roomDetail-view.fxml'.";
        assert roomDetailTitleLabel != null : "fx:id=\"roomDetailTitleLabel\" was not injected: check your FXML file 'roomDetail-view.fxml'.";

        Optional<Room> optionalRoom = model.getDataholder().getRooms().stream().filter(room -> room == model.getSelectedRoomProperty()).findAny();

        if (optionalRoom.isPresent()){
            Room r = optionalRoom.get();

            roomDetailViewNumberLabel.setText("R" + r.getRoomID());
            roomDetailTitleLabel.setText(r.getDescription());
            roomDetailLocationLabel.setText(r.getLocation().getDescription());
            roomDetailMembersLabel.setText(String.valueOf(r.getMaxPersons()));
            if(r.getPhoto() != null){
                roomDetailImageView.setImage(new Image(new ByteArrayInputStream(r.getPhoto())));
            }

        }

        editAuthorization();

    }



    private void editAuthorization() {
        if(model.getAuthorization().equals("admin")){
        editRoom.setVisible(true);
        deleteRoom.setVisible(true);
        }
    }


    @FXML
    private void onRoomDeleteButtonClicked(MouseEvent mouseEvent) {
    }

    @FXML
    private void onRoomEditButtonClicked(MouseEvent mouseEvent) throws IOException {
        showNewView(Constants.PATH_TO_ROOM_UPDATE_VIEW);

        Stage detailStage = (Stage) editRoom.getScene().getWindow();
        detailStage.close();
    }
}
