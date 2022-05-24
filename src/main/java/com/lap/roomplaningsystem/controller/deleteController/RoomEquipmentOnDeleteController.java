
package com.lap.roomplaningsystem.controller.deleteController;

        import java.net.URL;
        import java.sql.SQLException;
        import java.util.Optional;
        import java.util.ResourceBundle;

        import com.lap.roomplaningsystem.controller.BaseController;
        import com.lap.roomplaningsystem.model.Dataholder;
        import com.lap.roomplaningsystem.model.Event;
        import com.lap.roomplaningsystem.model.RoomEquipment;
        import javafx.fxml.FXML;
        import javafx.scene.control.Label;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Stage;

public class RoomEquipmentOnDeleteController extends BaseController {


    @FXML
    private URL location;

    @FXML
    private Label equipmentLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label roomLabel;



    @FXML
    void initialize() {
        assert equipmentLabel != null : "fx:id=\"equipmentLabel\" was not injected: check your FXML file 'roomEquipmentOnDelete-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'roomEquipmentOnDelete-view.fxml'.";
        assert roomLabel != null : "fx:id=\"roomLabel\" was not injected: check your FXML file 'roomEquipmentOnDelete-view.fxml'.";

        Optional<RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(e -> e.getRoomEquipmentID() == model.getSelectedRoomEquipmentProperty().getRoomEquipmentID()).findAny();

        if(optionalRoomEquipment.isPresent()){
            RoomEquipment roomEquipment = optionalRoomEquipment.get();
            numberLabel.setText("RA" + roomEquipment.getRoomEquipmentID());
            roomLabel.setText(roomEquipment.getRoom().getDescription());
            equipmentLabel.setText(roomEquipment.getEquipment().getDescription());
        }

    }

    @FXML
    void onNoButtonClicked(MouseEvent event) {
        Stage stage = (Stage) numberLabel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void onYesButtonClicked(MouseEvent event) {

        Optional <RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(e -> e.getRoomEquipmentID() == model.getSelectedRoomEquipmentProperty().getRoomEquipmentID()).findAny();
        model.setSelectedRoomEquipmentProperty(null);

        optionalRoomEquipment.ifPresent(e -> {
            try {
                boolean isDeleted = Dataholder.roomEquipmentRepositoryJDBC.delete(e);
                if(isDeleted){
                    model.getDataholder().deleteRoomEquipment(e);

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Stage stage = (Stage) numberLabel.getScene().getWindow();
        stage.close();

    }

}
