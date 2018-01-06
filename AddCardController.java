package redmal;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddCardController {
    public JFXButton clearCardButton;
    public JFXButton addCardToDeckButton;
    public JFXButton signoutButton;
    public Label loggedInUserLabel;
    public Label backButton;
    Stage currentStage;

    public AddCardController(){

    }

    //This method returns program to the Lobby Screen
    public void goBackButton(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("lobbyScreen.fxml"));
            currentStage = (Stage) addCardToDeckButton.getScene().getWindow();
            currentStage.setScene(new Scene(root, 600, 400));
            currentStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCardToDeck(ActionEvent actionEvent) {
        System.out.println("Card add test.");
    }

    public void clearCard(ActionEvent actionEvent) {
        System.out.println("Card clear test.");
    }
}