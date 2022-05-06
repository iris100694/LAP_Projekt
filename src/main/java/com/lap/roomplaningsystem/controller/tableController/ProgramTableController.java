package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Program;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProgramTableController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Program, String> programDescriptionColumn;

    @FXML
    private TableColumn<Program, String> programNumberColumn;

    @FXML
    private TableView<Program> programTableView;

    private ObjectProperty<Program> selectedEvent = new SimpleObjectProperty<>();

    @FXML
    void initialize() {
        assert programDescriptionColumn != null : "fx:id=\"programDescriptionColumn\" was not injected: check your FXML file 'programTable.fxml'.";
        assert programNumberColumn != null : "fx:id=\"programNumberColumn\" was not injected: check your FXML file 'programTable.fxml'.";
        assert programTableView != null : "fx:id=\"programTableView\" was not injected: check your FXML file 'programTable.fxml'.";

        initProgramTable(model.getDataholder().getPrograms());
    }

    private void initProgramTable(ObservableList<Program> programs) {
        programTableView.setItems(programs);

        programNumberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("P" + String.valueOf(dataFeatures.getValue().getProgramID())));
        programDescriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));

        programTableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> selectedEvent.set(nv));

        selectedEvent.addListener((o, ov, nv) -> {
            model.setShowProgram(nv);
            try {
                showNewView(Constants.PATH_TO_PROGRAM_DETAIL_VIEW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
