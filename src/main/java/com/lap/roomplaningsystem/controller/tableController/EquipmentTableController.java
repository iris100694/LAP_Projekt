package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Equipment;
import com.lap.roomplaningsystem.model.Room;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EquipmentTableController extends BaseController {

    @FXML
    private TableColumn<Equipment, String> descriptionColumn;

    @FXML
    private TableColumn<Equipment, String> numberColumn;

    @FXML
    private TableView<Equipment> tableView;


    @FXML
    void initialize() {
        tableView.setItems(model.getDataholder().getEquipments());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("A" + String.valueOf(dataFeatures.getValue().getEquipmentID())));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) ->  {
            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedEquipmentProperty(nv);
                    showNewView(Constants.PATH_TO_EQUIPMENT_DETAIL_VIEW);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedEquipmentProperty().addListener(new ChangeListener<Equipment>() {
            @Override
            public void changed(ObservableValue<? extends Equipment> observableValue, Equipment oldEquipment, Equipment newEquipment) {
                if(newEquipment == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });
    }


}
