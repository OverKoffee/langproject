package redmal.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import redmal.classes.Main;
import java.io.IOException;
import java.sql.*;
import java.security.NoSuchAlgorithmException;

public class SignUpController {
    public JFXTextField signupUsername;
    public JFXTextField signupPassword;
    public JFXTextField signupEmail;
    private Connection dbConnection = null;
    private String url = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3214145";
    private String user = "sql3214145";
    private String password = "EmrDHlTHv3";


    public SignUpController(){
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

    // this method adds account information input by user to the database
    // and creates account, after it's verified username and email aren't
    // already being used (through the 'verifyIfUserExists' method down below
    public void createNewUser() throws IOException {
        String query = "INSERT INTO UserDatabase (Username, Password, Email) VALUES (?,?,?)";

        if (verifyIfUserExists(signupUsername.getText(), signupEmail.getText())){
            try {
                PreparedStatement pst = dbConnection.prepareStatement(query);
                pst.setString(1, Main.cleanInput(signupUsername.getText()));
                pst.setString(2, Main.hash(signupPassword.getText()));
                pst.setString(3, Main.cleanInput(signupEmail.getText()));
                pst.execute();
                System.out.println("Account created successfully.");

                // if the user is verified and account is created, we return to login screen
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/loginscreen.fxml"));
                Stage currentStage = (Stage)signupUsername.getScene().getWindow();
                currentStage.setScene(new Scene(root, 600, 400));
                currentStage.show();
            }catch(SQLException | NoSuchAlgorithmException exc){
                System.out.println("An error occurred while trying to connect to database.");
                System.out.println(exc);
            }finally{
                try {
                    dbConnection.close();
                }catch(SQLException e){
                    System.out.println(e);
                }
            }
        } else {
            System.out.println("Unable to create account.");
        }
    }

    // this method checks if the Username and E-mail input by the user
    // already exists in the UserDatabase, if not, it sends false to the above
    // 'createNewUser' method and new account is created, otherwise, it sends
    // true and tells why account can't be created
    public boolean verifyIfUserExists(String username, String email){
        Boolean doesNotExist = false;
        try {
            String selectSQL = "SELECT * FROM UserDatabase WHERE Username ='" + username +"'";
            String selectSQL2 = "SELECT * FROM UserDatabase WHERE Email ='" + email + "'";
            PreparedStatement pst = dbConnection.prepareStatement(selectSQL);
            PreparedStatement pst2 = dbConnection.prepareStatement(selectSQL2);

            //Execute select SQL statement
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                doesNotExist = false;
                System.out.println("User already exists.");
                return doesNotExist;
            }
            else {
                doesNotExist = true;
            }

            rs = pst2.executeQuery();
            if (rs.next()){
                doesNotExist = false;
                System.out.println("E-mail already in use.");
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
