package com.lap.roomplaningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.lap.roomplaningsystem.app.Constants;
import com.lap.roomplaningsystem.controller.BaseController;
import com.lap.roomplaningsystem.model.Room;
import com.lap.roomplaningsystem.model.User;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserTableController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<User, String> userAuthorizationColumn;

    @FXML
    private TableColumn<User, String> userFirstnameColumn;

    @FXML
    private TableColumn<User, String> userIDColumn;

    @FXML
    private TableColumn<User, String> userLastnameColumn;

    @FXML
    private TableColumn<User, String> userStatusColumn;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> userUsernameColumn;

   ;

    @FXML
    void initialize() {
        assert userAuthorizationColumn != null : "fx:id=\"userAuthorizationColumn\" was not injected: check your FXML file 'userTable.fxml'.";
        assert userFirstnameColumn != null : "fx:id=\"userFirstnameColumn\" was not injected: check your FXML file 'userTable.fxml'.";
        assert userIDColumn != null : "fx:id=\"userIDColumn\" was not injected: check your FXML file 'userTable.fxml'.";
        assert userLastnameColumn != null : "fx:id=\"userLastnameColumn\" was not injected: check your FXML file 'userTable.fxml'.";
        assert userStatusColumn != null : "fx:id=\"userStatusColumn\" was not injected: check your FXML file 'userTable.fxml'.";
        assert userTableView != null : "fx:id=\"userTableView\" was not injected: check your FXML file 'userTable.fxml'.";
        assert userUsernameColumn != null : "fx:id=\"userUsernameColumn\" was not injected: check your FXML file 'userTable.fxml'.";

        initUserTable(model.getDataholder().getUsers());
        
    }

    private void initUserTable(ObservableList<User> users) {
        userTableView.setItems(users);

        userIDColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("U" + String.valueOf(dataFeatures.getValue().getId())));
        userFirstnameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getFirstname()));
        userLastnameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLastname()));
        userUsernameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getUsername()));
        userAuthorizationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().printAuthorization()));
        userStatusColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().printActiveState()));


        userTableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            model.setShowUser(nv);
            try {
                showNewView(Constants.PATH_TO_USER_DETAIL_VIEW);
//                Platform.runLater( ()-> {  userTableView.getSelectionModel().clearSelection();  });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
