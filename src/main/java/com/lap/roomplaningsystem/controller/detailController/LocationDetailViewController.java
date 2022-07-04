package com.lap.roomplaningsystem.controller.detailController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LocationDetailViewController extends BaseController {

    @FXML
    private Label adressLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label postCodeLabel;

    private Location location;




    @FXML
    void initialize() {

        Optional<Location> optionalLocation = model.getDataholder().getLocations().stream().filter(location -> location == model.getSelectedLocationProperty()).findAny();

        if(optionalLocation.isPresent()) {
            location = optionalLocation.get();

            numberLabel.setText("S" + String.valueOf(location.getLocationID()));
            descriptionLabel.setText(location.getDescription());
            adressLabel.setText(location.getAdress());
            postCodeLabel.setText(location.getPostCode());
            cityLabel.setText(location.getCity());
        }
    }



    @FXML
    void onDeleteButtonClicked(ActionEvent event)  throws IOException {
        showNewView(Constants.PATH_TO_LOCATION_ON_DELETE_VIEW);
        closeStage(numberLabel);
    }

    @FXML
    void  onUpdateButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_LOCATION_UPDATE_VIEW);
        closeStage(numberLabel);
    }


}
