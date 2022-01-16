package login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * H KLASH POU EXEI TA PANTA GIA TIN VASI DEDOMENWN
 * KAI THN METHODO GIA TIN ENALLAGI TWN SCREENS
 * 
 * METHODOS POU ALLAZEI TA SCREENS MESA APO TIS STATIC METHODOUS TWN FXML
 * DILADI MESA APO TA FXML ANAGNWRIZEI SE POIO WINDOW THELOUME NA PAME 
 * (AN THELEIS TO SUZITAME PARAPANW ENNOEITAI)
 */
public class DBConnections {
    public static void changeScene(ActionEvent event, String fxml, String title, String username) {
        Parent root = null;
        
        ModuleData.setUserName(username);
        
        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBConnections.class.getResource(fxml));
                root = loader.load();
                LoggedUserController loggedUserController = loader.getController();
                loggedUserController.createLoggedUserScreen(username);
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBConnections.class.getResource(fxml));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * H LEITOURGIKOTHTA TOU  EVENT  SIGN UP BUTTON OSON AFORA TIN DB
     * OLA TA ANAGKAIA DATABASE THINGS GIA TIN APOTHIKEUSI TWN USERS
     * @param e
     * @param username
     * @param email
     * @param password
     */
    public static void userSignUp(ActionEvent e, String username, String email, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        PreparedStatement psCheckingExistance = null;
        ResultSet result = null;

        try {
          	/**
        	 *H DB EINAI HOSTING STO WEB MESA APO ENA FREE SERVICE GIA NA EINAI ANOIXTH SE OLOUS MAS
        	 *AN THELOUME NA SUNDETHOUME STO PHPMYADMIN USERNAME: sql11452008 , PASSWORD: lsngacLCVA , SERVERNAME : sql11.freemysqlhosting.net
        	 */
            connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452008", "sql11452008", "lsngacLCVA");
            psCheckingExistance = connection.prepareStatement("SELECT * FROM signup WHERE username = ?");
            psCheckingExistance.setString(1, username);
            result = psCheckingExistance.executeQuery();

            //ELEGXOI GIA TO AN UPARXEI HDH O XRHSTHS(DILADI TO USERNAME)
            if (result.isBeforeFirst()) {
                System.out.println("Username already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please use a different username!");
                alert.show();
            } else {
                ps = connection.prepareStatement("INSERT INTO signup (username, email, password) VALUES (?, ?, ?)");
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.executeUpdate();

                DBConnections.changeScene(e, "logged-user.fxml", "Welcome", username); //ALLAGH WINDOW
            }
        } catch (SQLException  ex) {
            ex.printStackTrace();
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (psCheckingExistance != null) {
                try {
                    psCheckingExistance.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }


        }
    }
    /**
     * H LEITOURGIKOTHTA TOU  EVENT  LOGIN BUTTON OSON AFORA TIN DB
     * OLA TA ANAGKAIA DATABASE THINGS GIA TIN APOTHIKEUSI TWN USERS
     * @param event
     * @param username
     * @param password
     */
    public static void userLogIn(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11452008", "sql11452008", "lsngacLCVA");
            ps = connection.prepareStatement("SELECT password FROM signup WHERE username = ?");
            ps.setString(1, username);
            result = ps.executeQuery();
            //AN PRWTI FORA ANAGNWRIZEI TO USERNAME (O XRISTSI DEN EXEI KANEI SIGN UP
            if (!result.isBeforeFirst()) {
                System.out.println("User does not exist!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Credentials are incorrect!");
                alert.show();
            } else {
                while (result.next()) {
                    String dbpassword = result.getString("password");
                    if (dbpassword.equals(password)) {
                        DBConnections.changeScene(event, "logged-user.fxml", "Welcome!", username);
                    } else {
                        System.out.println("Password does not exist!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Credentials are incorrect!");
                        alert.show();
                    }
                }
            }

        } catch (SQLException  e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}