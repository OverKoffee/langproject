package redmal.controllers;

import com.jfoenix.controls.JFXTextField;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.Document;
import redmal.classes.Main;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class SignUpController {
    public JFXTextField signupUsername;
    public JFXTextField signupPassword;
    public JFXTextField signupEmail;
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
    public void createNewUser() throws IOException {
        if (verifyIfUserExists(signupUsername.getText(), signupEmail.getText())){
            MongoClient mongo = new MongoClient("localhost", 27017);
            try {
                //at some point I'll add some checks and rules to what can be used for username/password etc.
                collection.insertOne(new Document("User", Main.cleanInput(signupUsername.getText()))
                        .append("Password", Main.hash(signupPassword.getText()))
                        .append("Email", Main.cleanInput(signupEmail.getText())));

                // if the user is verified and account is created, we return to login screen
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/loginscreen.fxml"));
                Stage currentStage = (Stage)signupUsername.getScene().getWindow();
                currentStage.setScene(new Scene(root, 600, 400));
                currentStage.show();
            } catch (MongoException | NoSuchAlgorithmException exc){
                System.out.println(exc);
            }
            mongo.close();
        } else {
            System.out.println("Unable to create account.");
        }
    }


    // this method checks if the Username and E-mail input by the user
    // already exists in the UserDatabase, if not, it sends false to the above
    // 'createNewUser' method and new account is created, otherwise, it sends
    // true and tells why account can't be created
    public boolean verifyIfUserExists(String username, String email){
        boolean doesNotExist = true;
        String validateString = "";
        try {
            mongo = new MongoClient("localhost", 27017);
            database = mongo.getDatabase("local");
            //collection = database.getCollection("UserDatabase");

            FindIterable<Document> search = database.getCollection("UserDatabase").find();

            for (Document current : search) {
                validateString = current.getString("User");
                if (validateString.equals(username)) {
                    doesNotExist = false;
                    System.out.println("Username already being used.");
                }
            }

            for (Document current : search) {
                validateString = current.getString("Email");
                if (validateString.equals(email)) {
                    doesNotExist = false;
                    System.out.println("Email already being used.");
                }
            }
            /*for (Document current : search) {
                validateString = current.getString("Email");
                if (validateString == email){
                    System.out.println("Email already being used.");
                    doesNotExist = false;
                }else {
                    doesNotExist = true;
                }
            }*/

        }catch (MongoException exc){
            System.out.println(exc);
        }finally{
            System.out.println("doesNotExist is " + doesNotExist);
            return doesNotExist;
        }
    }

}
