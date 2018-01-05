package redmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class Controller {
    public JFXTextField userName;
    public JFXPasswordField userPassword;

    //Current Stage variable - to pass it to each scene class to change scenes
    Stage currentStage;
    //Login button on login splash screen will authenticate username/pw
    //and then load Lobby Screen
    public void submitLogin(ActionEvent actionEvent) {
        System.out.println("Test " + userName.getText() + " " + userPassword.getText());
        try {
            MySQLConnect connect = new MySQLConnect();
            connect.verifyLogin();

            Parent root = FXMLLoader.load(getClass().getResource("lobbyscreen.fxml"));
            currentStage = (Stage)userName.getScene().getWindow();
            currentStage.setScene(new Scene(root, 600, 400));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToSignUp(ActionEvent actionEvent) {
        System.out.println("Test " + userName.getText() + " " + userPassword.getText());
        try {
            Parent root = FXMLLoader.load(getClass().getResource("signupscreen.fxml"));
            currentStage = (Stage)userName.getScene().getWindow();
            currentStage.setScene(new Scene(root, 600, 400));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
