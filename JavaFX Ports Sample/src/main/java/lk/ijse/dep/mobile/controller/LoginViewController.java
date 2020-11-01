package lk.ijse.dep.mobile.controller;

import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import lk.ijse.dep.mobile.util.IOManager;
import lk.ijse.dep.mobile.util.Util;

public class LoginViewController {
    public AnchorPane root;
    public TextField txtUsername;
    public TextField txtPassword;
    String[] userData = IOManager.readUserFromDB();

    public void initialize() {
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(1);
            }
        });
        Rectangle2D vb= Screen.getPrimary().getVisualBounds();
        root.setPrefHeight(vb.getHeight());
        root.setPrefWidth(vb.getWidth());
    }

    public void btnLogin_OnAction(ActionEvent actionEvent) {
        if (userData == null) {
            new Alert(Alert.AlertType.INFORMATION, "User data doesn't exist. Create new user?").showAndWait();
            Util.displayScreen("WelcomeForm",root);
        } else {
            String actualUsername = userData[1];
            String actualPassword = userData[2];
            String providedUsername = txtUsername.getText();
            String providedPassword = txtPassword.getText();
            if ((providedUsername.equals(actualUsername)) && (providedPassword.equals(actualPassword))) {
                new Alert(Alert.AlertType.INFORMATION, "Login successful. Proceed to dashboard?").showAndWait();
                Util.displayScreen("dashBoard",root);
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect username/password!").show();
            }
        }
    }

    public void lblCreateNewUser(MouseEvent mouseEvent) {
        Util.displayScreen("WelcomeForm",root);
    }
}
