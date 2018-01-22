package redmal.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import redmal.classes.Main;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;



public class LoginController {
    public JFXTextField userName;
    public JFXPasswordField userPassword;
    private Connection dbConnection = null;
    private String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3214145";
    private String user = "sql3214145";
    private String password = "EmrDHlTHv3";
    private PreparedStatement preparedStatement;

    public LoginController(){
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

    //Current Stage variable - to pass it to each scene class to change scenes
    Stage currentStage;
    //Login button on login splash screen will authenticate username/pw
    //and then load Lobby Screen
    public void clickLogin(ActionEvent actionEvent) {
        try {
            // setting static var as logged-in user for the app
            Main.LoggedInUser = userName.getText();
            if (verifyLogin(userName.getText(), userPassword.getText())){
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
        currentStage.setScene(new Scene(root, 923, 551));
        currentStage.show();
    }

    public boolean verifyLogin(String username, String pw) {
        boolean userVerified = false;
        //MongoClient mongo = new MongoClient();
        //MongoDatabase database = mongo.getDatabase("local");

        try {
            String selectSQL = "SELECT * FROM UserDatabase WHERE Username ='" + username +
                    "' and Password='" + Main.hash(pw) + "'";
            preparedStatement = dbConnection.prepareStatement(selectSQL);

            //Execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                userVerified = true;
                System.out.println("Logged in successfully.");
            }
            else {
                userVerified = false;
            }
        }catch(SQLException exc){
            System.out.println(exc);
        }finally{
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            return userVerified;
        }
    }

    public List<String> getDeckList(String username) {
        List<String> items = new ArrayList<>();
        try {
            String selectSQL = "SELECT * FROM UserDeckList WHERE Username ='" + username + "'";
            preparedStatement = dbConnection.prepareStatement(selectSQL);

            //Execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                System.out.println("Found a deck successfully.");
                items.add(rs.getString("DeckName"));
                System.out.println("Added a deck successfully.");
            }
        }catch(SQLException exc){
            System.out.println(exc);
        }finally{
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return items;
    }

}
