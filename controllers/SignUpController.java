package redmal.controllers;

import com.jfoenix.controls.JFXTextField;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bson.Document;
import redmal.classes.Main;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import com.mongodb.DBObject;

import static com.mongodb.client.model.Filters.eq;

public class SignUpController {
    public JFXTextField signupUsername;
    public JFXTextField signupPassword;
    public JFXTextField signupEmail;
    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private DBObject r, found;


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
                //at some point I'll a some checks and rules to what can be used for username/password etc.
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
        boolean doesNotExist = false;
        try {
            mongo = new MongoClient("localhost", 27017);
            database = mongo.getDatabase("local");
            collection = database.getCollection("UserDatabase");

            FindIterable<Document> findIterable = collection.find(eq("User", username));

            if (findIterable!=null){
                System.out.println("User already exists.");
                return false;
            }else{
                doesNotExist = true;
            }

            FindIterable<Document> findIterable2 = collection.find(eq("Email", email));

            if (findIterable2!=null){
                System.out.println("Email already being used.");
                return false;
            }else{
                doesNotExist = true;
            }
        }catch(MongoException exc){
            System.out.println(exc);
        }
        return doesNotExist;
    }

}
