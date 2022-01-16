package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InstructionsController {
	/**
	 * initiliaze to button apo to fxml arxeio
	 */
    @FXML
    private Button gotItButton;
    /**
     * CLICK STO BUTTON GOT IT GIA NA EPIVEVAIWSEIS OTI EISAI OK ME TO PWS PAIZETAI TO GAME
     * @param event
     */
    public void gotItButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) gotItButton.getScene().getWindow();
        
        //EMFANIZEI SECURE ERWTHMA GIA TO AN SIGOURA EISAI OK KAI ANTISTOIXWS KLEINEI TO WINDOW ME TA INSTRUCTIONS
        Boolean answer = AlertBox.display("EXIT", "Are you okay with the instructions?");
        if (answer)
            stage.close();
    }
}
