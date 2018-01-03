package redmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
    public JFXTextField userName;
    public JFXTextField userPassword;

    //Various Scenes
    LobbyScreen lobbyScreen;

    //Current Stage variable - to pass it to each scene class to change scenes
    Stage currentStage;



    //Login button on login splash screen will authenticate username/pw
    //and then run 'loadLobbyScreen' method to set the Scene to the Lobby Screen
    public void submitLogin(ActionEvent actionEvent) {
        System.out.println("Test " + userName.getText() + " " + userPassword.getText());
        try {
            loadLobbyScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //This method loads the Lobby Screen
    private void loadLobbyScreen() throws Exception {
        lobbyScreen = new LobbyScreen();
        currentStage = (Stage)userName.getScene().getWindow();
        lobbyScreen.getStage().show();
    }
}
