package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


//O CONTROLLER POU XEIRIZETAI TO LOGIN.FXML
public class Controller {

    //INITIALIZE BUTTONS , LABELS KAI TEXTFIELDS , CHECKBOXES APO TO ANTISTOIXO FXML ARXEIO
    @FXML
    private Button exitButton;
    
    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private CheckBox showPasswordCB;

    @FXML
    private Label loginMessageL;

    @FXML
    private TextField userNameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private TextField showPasswordTF;


    //Login botton method
    public void loginButtonOnAction(ActionEvent e) {
        DBConnections.userLogIn(e, userNameTF.getText(), passwordTF.getText() );
    }

   // Exit button method.
    public void exitButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Boolean answer = AlertBox.display("EXIT", "Sure you wanna exit?");
        if (answer)
            stage.close();
    }


      //This checkbox allows user to see his/her password or not to.
    public void showPasswordCBOnAction(ActionEvent e) {
        if (showPasswordCB.isSelected()) {
            showPasswordTF.setText(passwordTF.getText());
            showPasswordTF.setVisible(true);
            passwordTF.setVisible(false);
        } else {
            passwordTF.setText(showPasswordTF.getText());
            passwordTF.setVisible(true);
            showPasswordTF.setVisible(false);
        }


    }


    //By this method, the action is transfered to the Sign-up window for the
    // non-existing User to sign up
    public void signUpButtonOnAction(ActionEvent e) {
        DBConnections.changeScene(e, "signup.fxml", "Sign Up!", null);
    }

}