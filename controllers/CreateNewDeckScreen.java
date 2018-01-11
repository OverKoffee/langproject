package redmal.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import redmal.classes.Main;
import java.io.IOException;
import java.sql.*;

public class CreateNewDeckScreen {
    public JFXTextField deckName;
    public JFXButton createNewDeckButton;
    public JFXButton cancelNewDeckButton;
    private Connection dbConnection = null;
    private String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3214145";
    private String user = "sql3214145";
    private String password = "EmrDHlTHv3";

    // create database connection
    public CreateNewDeckScreen(){
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

    public void createNewDeck(ActionEvent actionEvent) throws IOException {
        String query = "INSERT INTO UserDeckList (Username, DeckName) VALUES (?,?)";
        if (verifyIfDeckNameExists(Main.LoggedInUser, deckName.getText())){
            try {
                PreparedStatement pst = dbConnection.prepareStatement(query);
                pst.setString(1, Main.LoggedInUser);
                pst.setString(2, deckName.getText());
                pst.execute();
                System.out.println("New Deck added successfully.");
            }catch (SQLException exc){
                System.out.println(exc);
            }finally{
                try{
                    dbConnection.close();
                }catch(SQLException exc){
                    System.out.println(exc);
                }
            }
        } else {
            System.out.println("Deck name already exists.");
        }
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/LobbyScreen.fxml"));
        Stage reloadLobby = (Stage)deckName.getScene().getWindow();
        reloadLobby.setScene(new Scene(root, 600, 400));
        reloadLobby.show();
    }

    // this method checks if the deck name input by the user
    // already exists in the UserDeckList table, if not, it sends false to the above
    // 'createNewDeck' method and new account is created, otherwise, it sends
    // true and tells why account can't be created
    public boolean verifyIfDeckNameExists(String username, String deckname){
        Boolean doesNotExist = false;
        try {
            String selectSQL = "SELECT * FROM UserDeckList WHERE Username ='" + username +"'and DeckName ='" + deckname + "'";
            PreparedStatement pst = dbConnection.prepareStatement(selectSQL);

            //Execute select SQL statement
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                doesNotExist = false;
                return doesNotExist;
            }
            else {
                doesNotExist = true;
            }
        }catch(SQLException exc){
            System.out.println(exc);
        }
        return doesNotExist;
    }

}
