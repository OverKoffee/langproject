package redmal.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import redmal.classes.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCardController {
    public JFXButton clearCardButton;
    public JFXButton addCardToDeckButton;
    public JFXButton signoutButton;
    public JFXTextArea frontCardTextField;
    public JFXTextArea backCardTextField;
    public Label loggedInUserLabel;
    public Label backButton;
    private Connection dbConnection = null;
    private String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3214145";
    private String user = "sql3214145";
    private String password = "EmrDHlTHv3";
    Stage currentStage;

    // create database connection
    public AddCardController(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(url, user, password);
            if (dbConnection != null){
                System.out.println("Connected to database successfully.");
            }
        }catch (ClassNotFoundException | SQLException exc){
            System.out.println("An error occurred while trying to connect to database.");
            System.out.println(exc);
        }
    }

    @FXML
    public void initialize(){
        loggedInUserLabel.setText(Main.LoggedInUser);
    }

    //This method returns program to the Lobby Screen
    public void goBackButton(MouseEvent event) {
        loggedInUserLabel.setText(Main.LoggedInUser);
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/lobbyScreen.fxml"));
            currentStage = (Stage) addCardToDeckButton.getScene().getWindow();
            currentStage.setScene(new Scene(root, 600, 400));
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCardToDeck(ActionEvent actionEvent) {
        String query = "INSERT INTO UserCards (Username, DeckName, FrontCard, BackCard," +
                "                              GradeValue) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = dbConnection.prepareStatement(query);
            pst.setString(1, Main.LoggedInUser);
            pst.setString(2, Main.CurrentSelectedDeck);
            pst.setString(3, frontCardTextField.getText());
            pst.setString(4, backCardTextField.getText());
            pst.setString(5, "0.0");
            pst.execute();
            System.out.println("New Deck added successfully.");
            frontCardTextField.setText("");
            backCardTextField.setText("");
        }catch (SQLException exc){
            System.out.println(exc);
        }finally{
            try{
                dbConnection.close();
            }catch(SQLException exc){
                System.out.println(exc);
            }
        }
    }

    public void clearCard(ActionEvent actionEvent) {
        frontCardTextField.setText("");
        backCardTextField.setText("");
        System.out.println("Card textfields cleared.");
    }
}