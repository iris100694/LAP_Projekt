module com.lap.roomplaningsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;


    opens com.lap.roomplaningsystem to javafx.fxml;
    exports com.lap.roomplaningsystem;
    exports com.lap.roomplaningsystem.controller;
    opens com.lap.roomplaningsystem.controller to javafx.fxml;
}