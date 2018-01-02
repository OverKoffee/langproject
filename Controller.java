package redmal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.awt.*;

public class Controller {
    public JFXTextField userName;
    public JFXTextField userPassword;
    public void submitLogin(ActionEvent actionEvent) {
        System.out.println("Test " + userName.getText() + " " + userPassword.getText());
    }
}
