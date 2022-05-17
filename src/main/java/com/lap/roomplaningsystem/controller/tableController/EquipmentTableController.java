package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Equipment;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EquipmentTableController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Equipment, String> equipmentDescriptionColumn;

    @FXML
    private TableView<Equipment> equipmentTableView;

    @FXML
    private TableColumn<Equipment, String> equipmentNumberColumn;



    @FXML
    void initialize() {
        assert equipmentDescriptionColumn != null : "fx:id=\"equipmentDescriptionColumn\" was not injected: check your FXML file 'equipmentTable.fxml'.";
        assert equipmentTableView != null : "fx:id=\"equipmentTableView\" was not injected: check your FXML file 'equipmentTable.fxml'.";
        assert equipmentNumberColumn != null : "fx:id=\"roomNumberColumn\" was not injected: check your FXML file 'equipmentTable.fxml'.";

        equipmentTableView.setItems(model.getDataholder().getEquipments());

        equipmentNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("A" + String.valueOf(dataFeatures.getValue().getEquipmentID())));
        equipmentDescriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));

        equipmentTableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) ->  {
            try {
                if(nv != null){
                    model.setSelectedEquipmentProperty(nv);
                    showNewView(Constants.PATH_TO_EQUIPMENT_DETAIL_VIEW);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
