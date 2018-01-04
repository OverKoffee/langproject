package redmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LobbyController {
    public JFXComboBox selectDeckComboBox;
    public JFXButton reviewDeckButton;
    public JFXButton addCardsButton;
    public JFXButton editDeckButton;
    public JFXButton signoutButton;
    public Label loginUser;

    Stage currentStage;

    //This method loads the Lobby Screen
    public void addCards(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("addcardsScreen.fxml"));
            currentStage = (Stage) addCardsButton.getScene().getWindow();
            currentStage.setScene(new Scene(root, 600, 400));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
