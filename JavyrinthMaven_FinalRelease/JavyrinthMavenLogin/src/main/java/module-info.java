module loginModule {

	requires transitive javafx.graphics;
	
    requires javafx.controls;
    requires javafx.fxml;
    
    requires java.sql;
	
    opens login to javafx.fxml;
    exports login;
}
