package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


//H APLH MAIN MAS APO AUTIN KSEKINANE OLA
public class App extends Application {

    //kanei extend to Application , H methodos start stin javafx einai san tin main stin java
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED); // disable to border tou klasikou window me to miniize , maximize , X gia kleismo
        primaryStage.setTitle("Log in");
        primaryStage.getIcons().add(new Image("/logo_24.png"));
        primaryStage.setScene(new Scene(root, 520, 400)); // megethi tou window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}