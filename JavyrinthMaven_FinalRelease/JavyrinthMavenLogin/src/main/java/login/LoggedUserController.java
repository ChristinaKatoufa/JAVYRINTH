package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * O CONTROLLER GIA TO WINDOW OTAN O XRISTIS EXEI KANEI LOGIN (LOGGED-USER FXML)
 *INITIALIZE BUTTONS KAI TOU WELCOME TEXTFIELD
 *
 */
public class LoggedUserController   {

    @FXML
    private Button logOutButton;

    @FXML
    private Button playButton;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button instructionsButton;

    @FXML
    private Label welcomeL;
    /**
     * LOG OUT TOU XRISTI KAI EPISTROFI STO PARATHURO TIS SUNDESIS
     * @param event
     */
    public void logOutButtonOnAction(ActionEvent event) {
        DBConnections.changeScene(event, "login.fxml", "Log in!", null);
    }
    /**
     * H METHODOS OPOU KANEI PIO INTERACTIVE TO WINDOW KAI EMFANIZEI ENA WELCOME ME TO USERNAME TOU XRISTI
     * @param username
     */
    public void createLoggedUserScreen(String username) {
        welcomeL.setText("Welcome " + username +"!");
    }
    /**
     * CLICK STO INSTRUCTIONS GIA TO HOW TO PLAY THE GAME
     * @param event
     */
    public void instructionsButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader =  new FXMLLoader(getClass().getResource("instructions.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("How To Play");
            stage.getIcons().add(new Image("/logo_24.png"));
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void playButtonOnAction(ActionEvent event) {
    	Stage stage = (Stage) playButton.getScene().getWindow();
    	stage.close();
    	
    	//new GameWindow();
    	//game.main.Main.main(null);
    }

}