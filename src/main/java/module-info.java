module com.lap.roomplaningsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires org.apache.commons.lang3;


    opens com.lap.roomplaningsystem to javafx.fxml;
    exports com.lap.roomplaningsystem;
    exports com.lap.roomplaningsystem.controller;
    exports com.lap.roomplaningsystem.controller.tableController;
    opens com.lap.roomplaningsystem.controller to javafx.fxml;
    opens com.lap.roomplaningsystem.controller.tableController to javafx.fxml;
    exports com.lap.roomplaningsystem.controller.detailController;
    opens com.lap.roomplaningsystem.controller.detailController to javafx.fxml;
    exports com.lap.roomplaningsystem.controller.updateController;
    opens com.lap.roomplaningsystem.controller.updateController to javafx.fxml;
    exports com.lap.roomplaningsystem.controller.deleteController;
    opens com.lap.roomplaningsystem.controller.deleteController to javafx.fxml;
}