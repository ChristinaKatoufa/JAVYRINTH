package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
/**
 * O CONTROLLER GIA TO SIGNUP FXML ARXEIO MAS
 *
 */
public class SignUpController {

    //INITIALIZE
    @FXML
    private Button exitButton;

    @FXML
    private Button goToLoginButton;

    @FXML
    private Label signUpMessageL;

    @FXML
    private TextField userNameTF;

    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private PasswordField repasswordTF;

    @FXML
    private CheckBox showPasswordFieldsCB;

    @FXML
    private TextField showPasswordTF;

    @FXML
    private TextField showRePasswordTF;
    /**
     * GIA NA KANEI SIGN UP PREPEI NA MIN EINAI KENA TA PEDIA ,
     * KAI NA EINAI TO IDIO TO PASSWORDFIELD TOU PASSWORD ME TO RE ENTER
     * @param e
     */
    public void signUpButtonOnAction(ActionEvent e) {
        if (userNameTF.getText().isBlank() == false && emailTF.getText().isBlank() == false
                && passwordTF.getText().isBlank() == false && repasswordTF.getText().isBlank() == false && passwordTF.getText().equals(repasswordTF.getText())) {
            DBConnections.userSignUp(e, userNameTF.getText(), emailTF.getText(), passwordTF.getText());
        } else if (!passwordTF.getText().equals(repasswordTF.getText())) {
            signUpMessageL.setText("Password and Re-enter not match!");
        } else {
            System.out.println("Please fill in all information");
            Alert alert = new Alert(Alert.AlertType.ERROR); // TA BUILT IN ALERT BOXES POU ANEFERA PRIN
            alert.setContentText("Please fill in all information to sign up!");
            alert.show();
        }
    }
    /**
     * ACTION EVENT KATA TO CLICK TOU BUTTON POU SE METAFEREI STO WINDOW TOU LOG IN
     * (AN PROFANWS EISAI HDH XRHSTHS)
     * @param e
     */
    public void goToLoginButtonAction(ActionEvent e) {
        DBConnections.changeScene(e, "login.fxml", "Log in!", null);
    }
    /**
     * EXIT BUTTON GIA EKSODO APO TO APP
     * @param e
     */
    public void exitButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Boolean answer = AlertBox.display("EXIT", "Sure you wanna exit?");
        if (answer)
            stage.close();
    }
    /**
     * TO CHECKBOX SELECT GIA NA FAINONTAI 'H OXI TA PEDIA ME TA PASSWORDS
     * @param e
     */
    public void showPasswordFieldsCBOnAction(ActionEvent e) {
        if (showPasswordFieldsCB.isSelected()) {
            //GIA TO PASSWORD
            showPasswordTF.setText(passwordTF.getText());
            showPasswordTF.setVisible(true);
            passwordTF.setVisible(false);

            //GIA TO RE-ENTER
            showRePasswordTF.setText(repasswordTF.getText());
            showRePasswordTF.setVisible(true);
            repasswordTF.setVisible(false);


        } else {
            //GIA TO PASSWORD
            passwordTF.setText(showPasswordTF.getText());
            passwordTF.setVisible(true);
            showPasswordTF.setVisible(false);

            //GIA TO RE-ENTER
            repasswordTF.setText(showRePasswordTF.getText());
            repasswordTF.setVisible(true);
            showRePasswordTF.setVisible(false);
        }

    }
}
