
package redmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LobbyScreen {
    JFXComboBox selectDeckComboBox;
    JFXButton reviewDeckButton;
    JFXButton addCardsButton;
    JFXButton editDeckButton;
    JFXButton signoutButton;
    Label loginUser;


    public Stage getStage() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("lobbyscreen.fxml"));
        Stage lobbyStage = new Stage();
        lobbyStage.setTitle("Dustin's App");
        lobbyStage.setScene(new Scene(root, 600, 400));
        return lobbyStage;
    }
}
