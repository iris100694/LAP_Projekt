package com.lap.roomplaningsystem.controller.updateController;

import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.matcher.LocationMatcher;
import com.lap.roomplaningsystem.model.Location;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LocationOnUpdateController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField locationAdressInput;

    @FXML
    private TextField locationCityInput;

    @FXML
    private TextField locationDescriptionInput;

    @FXML
    private Label locationNumberLabel;

    @FXML
    private TextField locationPostCodeInput;

    @FXML
    private Button saveLocation;



    @FXML
    void initialize() {
        assert locationAdressInput != null : "fx:id=\"locationAdressInput\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert locationCityInput != null : "fx:id=\"locationCityInput\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert locationDescriptionInput != null : "fx:id=\"locationDescriptionInput\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert locationNumberLabel != null : "fx:id=\"locationNumberLabel\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert locationPostCodeInput != null : "fx:id=\"locationPostCodeInput\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";
        assert saveLocation != null : "fx:id=\"saveLocation\" was not injected: check your FXML file 'locationDetailOnUpdate-view.fxml'.";

        initView();
    }

    private void initView() {
        Location location = model.getShowLocation();

        locationNumberLabel.setText("S" + String.valueOf(location.getLocationID()));
        locationDescriptionInput.setText(location.getDescription());
        locationAdressInput.setText(location.getAdress());
        locationPostCodeInput.setText(location.getPostCode());
        locationCityInput.setText(location.getCity());
    }

    @FXML
    void onLocationSaveButtonClicked(MouseEvent event) {

    }
}
