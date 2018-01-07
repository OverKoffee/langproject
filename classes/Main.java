package redmal.classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static String LoggedInUser = ""; // static variable to manage app in respective scenes
                                            // based on logged-in user
    public static String CurrentSelectedDeck = ""; // static variable to manage app in respective scenes
                                                   // based on current selected deck

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/loginscreen.fxml"));
        primaryStage.setTitle("Dustin's App");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
