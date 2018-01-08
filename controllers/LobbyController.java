package redmal.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import redmal.classes.Main;
import redmal.classes.MySQLConnect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LobbyController {
    public JFXComboBox selectDeckComboBox;
    public JFXButton reviewDeckButton;
    public JFXButton addCardsButton;
    public JFXButton editDeckButton;
    public JFXButton signoutButton;
    public Label loggedInUserLabel;
    public List<String> items;

    Stage currentStage;

    // this method initializes the deck list from database for the current user
    // in the combobox for the user to select, as well as initialize prompt text
    // and 'New Deck' item
    @FXML
    public void initialize() {
        loggedInUserLabel.setText(Main.LoggedInUser);
        items = new ArrayList<>();
        MySQLConnect dbConnection = new MySQLConnect();
        items = dbConnection.getDeckList(Main.LoggedInUser);
        selectDeckComboBox.getItems().removeAll(selectDeckComboBox.getItems());
        selectDeckComboBox.setPromptText("Select Deck");
        selectDeckComboBox.getItems().add("New Deck");
        for (ListIterator<String> iter = items.listIterator(); iter.hasNext(); ){
            selectDeckComboBox.getItems().add(iter.next());
        }
    }

    public void deckSelection(ActionEvent actionEvent) throws IOException {
        if (selectDeckComboBox.getValue().equals("New Deck")){
            //add code here to open scene that prompts user for new deck
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/createnewdeckscreen.fxml"));
            Stage createDeckStage = (Stage)selectDeckComboBox.getScene().getWindow();
            createDeckStage.setScene(new Scene(root, 468, 282));
            createDeckStage.show();
        }
    }

    //This method loads the Add Cards Screen
    public void addCards(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/addcardtodeckscreen.fxml"));
            currentStage = (Stage)reviewDeckButton.getScene().getWindow();
            currentStage.setScene(new Scene(root, 600, 400));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reviewDeck(ActionEvent actionEvent){
        System.out.println("Review Cards Button test.");
    }

    public void editDeck(ActionEvent actionEvent){
        System.out.println("Edit Deck Button test.");
    }
}
