package com.lap.roomplaningsystem.controller.detailController;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Location;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LocationDetailViewController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteEvent;

    @FXML
    private Button editLocation;

    @FXML
    private Label locationDetailViewAdressLabel;

    @FXML
    private Label locationDetailViewCityLabel;

    @FXML
    private Label locationDetailViewDescriptionLabel;

    @FXML
    private Label locationDetailViewNumberLabel;

    @FXML
    private Label locationDetailViewPostCodeLabel;


    @FXML
    void initialize() {
        assert deleteEvent != null : "fx:id=\"deleteEvent\" was not injected: check your FXML file 'locationDetail-view.fxml'.";
        assert editLocation != null : "fx:id=\"editLocation\" was not injected: check your FXML file 'locationDetail-view.fxml'.";
        assert locationDetailViewAdressLabel != null : "fx:id=\"locationDetailViewAdressLabel\" was not injected: check your FXML file 'locationDetail-view.fxml'.";
        assert locationDetailViewCityLabel != null : "fx:id=\"locationDetailViewCityLabel\" was not injected: check your FXML file 'locationDetail-view.fxml'.";
        assert locationDetailViewDescriptionLabel != null : "fx:id=\"locationDetailViewDescriptionLabel\" was not injected: check your FXML file 'locationDetail-view.fxml'.";
        assert locationDetailViewNumberLabel != null : "fx:id=\"locationDetailViewNumberLabel\" was not injected: check your FXML file 'locationDetail-view.fxml'.";
        assert locationDetailViewPostCodeLabel != null : "fx:id=\"locationDetailViewPostCodeLabel\" was not injected: check your FXML file 'locationDetail-view.fxml'.";

        Optional<Location> optionalLocation = model.getDataholder().getLocations().stream().filter(location -> location == model.getSelectedLocationProperty()).findAny();

        if(optionalLocation.isPresent()) {
            Location l = optionalLocation.get();
            locationDetailViewNumberLabel.setText("S" + String.valueOf(l.getLocationID()));
            locationDetailViewDescriptionLabel.setText(l.getDescription());
            locationDetailViewAdressLabel.setText(l.getAdress());
            locationDetailViewPostCodeLabel.setText(l.getPostCode());
            locationDetailViewCityLabel.setText(l.getCity());
        }
    }



    @FXML
    void onLocationDeleteButtonClicked(MouseEvent event) throws IOException {

    }

    @FXML
    void onLocationEditButtonClicked(MouseEvent event) throws IOException {
        showNewView(Constants.PATH_TO_LOCATION_UPDATE_VIEW);

        Stage detailStage = (Stage) editLocation.getScene().getWindow();
        detailStage.close();

    }


}
