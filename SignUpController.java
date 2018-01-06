package redmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.sql.*;

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


    public boolean createNewUser(){
        Boolean createdNewUser = false;
        String query = "INSERT INTO UserDatabase (Username, Password, Email) VALUES (?,?,?)";
        if (verifyIfUserExists(signupUsername.getText(), signupPassword.getText(), signupEmail.getText())){
            createdNewUser = true;
            try {
                PreparedStatement pst = dbConnection.prepareStatement(query);
                pst.setString(1, signupUsername.getText());
                pst.setString(2, signupPassword.getText());
                pst.setString(3, signupEmail.getText());
                pst.execute();
            }catch(SQLException exc){
                System.out.println("An error occurred while trying to connect to database.");
                System.out.println(exc);
            }finally{
                try{
                    dbConnection.close();
                }catch(SQLException e){
                    System.out.println(e);
                }
                System.out.println("Account created successfully.");

                return createdNewUser;
            }
        } else {
            return createdNewUser;
        }
    }


    public boolean verifyIfUserExists(String username, String pw, String email){
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
        }finally{
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            return doesNotExist;
        }
    }

}
