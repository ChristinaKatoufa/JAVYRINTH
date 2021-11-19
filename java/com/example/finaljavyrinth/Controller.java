package com.example.finaljavyrinth;

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


    //METHODOS GIA TO LOGIN BUTTON EVENT
    public void loginButtonOnAction(ActionEvent e) {
        DBConnections.userLogIn(e, userNameTF.getText(), passwordTF.getText() );
    }

    //METHODOS GIA TO BUTTON POU KANEI EXIT
    public void exitButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Boolean answer = AlertBox.display("EXIT", "Sure you wanna exit?");
        if (answer)
            stage.close();
    }


        // TO CHECKBOX GIA NA DEIXNEI TO PASSWORD H OXI / ANALOGWS THN EPILOGH TOU XRHSTH
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


    //METHODOS POU SE ODIGEI STO WINDOW GIA NA KANEIS SIGNUP (TOU SIGN UP BUTTON)
    public void signUpButtonOnAction(ActionEvent e) {
        DBConnections.changeScene(e, "signup.fxml", "Sign Up!", null);
    }

}