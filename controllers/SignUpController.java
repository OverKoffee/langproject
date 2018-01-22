package redmal.controllers;

import com.jfoenix.controls.JFXTextField;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import redmal.classes.Main;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class SignUpController {
    public JFXTextField signupUsername;
    public JFXTextField signupPassword;
    public JFXTextField signupEmail;
    private Connection dbConnection = null;
    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;


    public SignUpController(){
        mongo = new MongoClient("localhost", 27017);
        database = mongo.getDatabase("local");
        collection = database.getCollection("UserDatabase");
        System.out.println("running constructor");
    }

    // this method adds account information input by user to the database
    // and creates account, after it's verified username and email aren't
    // already being used (through the 'verifyIfUserExists' method down below
    public void createNewUser() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        try {
            //at some point I'll a some checks and rules to what can be used for username/password etc.
            collection.insertOne(new Document("User", Main.cleanInput(signupUsername.getText()))
                    .append("Password", Main.hash(signupPassword.getText()))
                    .append("Email", Main.cleanInput(signupEmail.getText())));
        } catch (MongoException | NoSuchAlgorithmException exc){
            System.out.println(exc);
        }
        mongo.close();
    }
    /* public void createNewUser() throws IOException {
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
    */

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
