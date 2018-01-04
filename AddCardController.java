package redmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AddCardController {
    public JFXButton clearCardButton;
    public JFXButton addCardButton;
    public JFXButton signoutButton;
    public Label loginUser;
    public Label backButton;

    Stage currentStage;

    //This method returns program to the Lobby Screen
    private void goBackButton(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("lobbyScreen.fxml"));
            currentStage = (Stage) addCardButton.getScene().getWindow();
            currentStage.setScene(new Scene(root, 600, 400));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCard(ActionEvent actionEvent) {
        System.out.println("Card add test.");
    }

    private void clearCard(ActionEvent actionEvent) {
        System.out.println("Card clear test.");
    }
}