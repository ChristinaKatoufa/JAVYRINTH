module com.example.finaljavyrinth {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.finaljavyrinth to javafx.fxml;
    exports com.example.finaljavyrinth;
}