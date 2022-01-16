package login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * KLASI GIA TO ALERT OTAN O XRHSTHS VGAINEI APO TO WINDOW
 * META POU TIN EFTIAKSA EIDA OTI UPARXOUN KAI HDH BUILT IN KLASEIS ALLA DOULEUEI TO IDIO KALA
 */
public class AlertBox {

    static boolean answer;
    /**
     * H METHODOS POU EMFANIZEI TO WINDOW GIA TO WARNING TOU EXIT
     * @param title
     * @param message
     * @return MIA BOOLEAN TIMI(TRUE -NAI / FALSE-OXI)
     * mexri na dwthei apantisi den mporei na kanei allh leitourgia 
     * o xrhsths se allo window
     */
    public static Boolean display(String title, String message) {
        Stage window = new Stage();
       
        window.initModality(Modality.APPLICATION_MODAL); // den uparxei interaction me allo window (oute click oute input)


        window.setTitle(title);
        
        window.getIcons().add(new Image("/logo_24.png"));
        window.setResizable(false);

        window.setMinWidth(250);
        
        /**
         * initialize ta label
         */
        Label label = new Label();

        label.setText(message);
        label.setFont(Font.font("Arial", 13));

        /**
         * initialize TA 2 BUTTONS
         */

        Button yesButton = new Button ("Yes");
        Button noButton = new Button ("No");

        /**
         * CLICK EVENTS GIA TA 2 BUTTONS
         */
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e2 -> {
            answer = false;
            window.close();
        });

        /**
         * scene-layout
         */
        HBox buttons = new HBox();
        buttons.setSpacing(20);
        buttons.getChildren().addAll(yesButton, noButton);
        buttons.setAlignment(Pos.CENTER);
        
        VBox layout = new VBox();
        layout.setPadding(new Insets(10, 10, 10, 10)); // (top, bottom, left, right)
        layout.setSpacing(10);
        layout.getChildren().addAll(label, buttons);
        layout.setAlignment(Pos.CENTER);       
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); //mexri na kleisei den mporoume na kanoume tipota allo

        return answer;

    }

}