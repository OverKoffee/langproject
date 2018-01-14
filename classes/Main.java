package redmal.classes;
/*
Idea - keep review count hidden from user, utilize a health/damage bar
and when the user died (health is dead), the user's deck is erased...
motivation to keep up on reviews
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main extends Application {
    public static String LoggedInUser = null; // static variable to manage app in respective scenes
                                            // based on logged-in user
    public static String CurrentSelectedDeck = null; // static variable to manage app in respective scenes
                                                   // based on current selected deck

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/loginscreen.fxml"));
        primaryStage.setTitle("Dustin's App");
        primaryStage.setScene(new Scene(root, 923, 551));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

    // method to hash passwords
    public static String hash(String pwd) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = digest.digest(pwd.getBytes(StandardCharsets.UTF_8));
        StringBuffer hashed = new StringBuffer();
        for(byte b: bytes)
            hashed.append(Integer.toHexString(0xff & b));
        return hashed.toString();
    }

    // method to clean input before sending to database
    public static String cleanInput(String dirty){
        return dirty.replaceAll("[\\\\({});|]", "");
    }
}
