package redmal.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import redmal.classes.Main;

import java.io.IOException;

public class Controller {
    public JFXTextField userName;
    public JFXPasswordField userPassword;

    //Current Stage variable - to pass it to each scene class to change scenes
    Stage currentStage;
    //Login button on login splash screen will authenticate username/pw
    //and then load Lobby Screen
    public void clickLogin(ActionEvent actionEvent) {
        try {
            // setting static var as logged-in user for the app
            Main.LoggedInUser = userName.getText();
            LoginController connect = new LoginController();
            if (connect.verifyLogin(userName.getText(), userPassword.getText())){
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/lobbyscreen.fxml"));
                currentStage = (Stage)userName.getScene().getWindow();
                currentStage.setScene(new Scene(root, 600, 400));
                currentStage.show();
            }else{
                System.out.println("Login Failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickSignUp(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/signupscreen.fxml"));
            currentStage = (Stage)userName.getScene().getWindow();
            currentStage.setScene(new Scene(root, 600, 400));
            currentStage.show();
    }

}
