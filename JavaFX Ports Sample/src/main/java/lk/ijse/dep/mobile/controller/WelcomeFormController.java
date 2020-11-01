package lk.ijse.dep.mobile.controller;

import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import lk.ijse.dep.mobile.AppInitializer;
import lk.ijse.dep.mobile.util.IOManager;
import lk.ijse.dep.mobile.util.Util;
import lk.ijse.dep.mobile.util.Validation;

import java.io.IOException;

public class WelcomeFormController {
    public AnchorPane root;
    public TextField txtFullName;
    public Label lblFullName;
    public TextField txtUsername;
    public Label lblUsername;
    public TextField txtPassword;
    public TextField txtPasswordConfirm;
    public Label lblPassword;

    public void initialize() {
        lblPassword.setVisible(false);
        lblUsername.setVisible(false);
        lblFullName.setVisible(false);
        Rectangle2D vb= Screen.getPrimary().getVisualBounds();
        root.setPrefHeight(vb.getHeight());
        root.setPrefWidth(vb.getWidth());
    }

    public void btnCreateAccount_OnAction(ActionEvent actionEvent) throws IOException {
        String fullName = txtFullName.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String[] userData=new String[3];
        if (validation(fullName, userName, password)) {//proceed only if all fields are valid
            userData[0]=fullName;
            userData[1]=userName;
            userData[2]=password;
            IOManager.writeUserToDB(userData);
            new Alert(Alert.AlertType.INFORMATION, "New user account registered successfully").showAndWait();
            Util.displayScreen("LoginView",root);
        }
    }

    //validation
    private boolean validation(String fullName, String userName, String password) {
        if (
                (Validation.validateFullName(fullName).equals("valid")) &&
                        ((Validation.validateUsername(userName)).equals("valid")) &&
                        ((Validation.validatePassword(password)).equals("valid")) &&
                        (password.equals(txtPasswordConfirm.getText()))
        ) {
            return true;
        } else {
            switch (Validation.validateFullName(fullName)) {
                case "all_spaces":
                    lblFullName.setVisible(true);
                    lblFullName.setText("Full name cannot be empty or all spaces");
                    txtFullName.requestFocus();
                    break;
                case "too_short":
                    lblFullName.setVisible(true);
                    lblFullName.setText("Full name cannot be less than 4 letters long");
                    txtFullName.requestFocus();
                    break;
                case "two_consecutive_dots":
                    lblFullName.setVisible(true);
                    lblFullName.setText("Full name cannot contain two or more consecutive symbols");
                    txtFullName.requestFocus();
                    break;
                case "valid":
                    lblFullName.setVisible(false);
                    break;
            }

            switch (Validation.validateUsername(userName)) {
                case "all_spaces":
                    lblUsername.setVisible(true);
                    lblUsername.setText("Username cannot be all spaces");
                    txtUsername.requestFocus();
                    break;
                case "too_short":
                    lblUsername.setVisible(true);
                    lblUsername.setText("Username cannot be less than 4 letters long or blank.");
                    txtUsername.requestFocus();
                    break;
                case "two_consecutive_dots":
                    lblUsername.setVisible(true);
                    lblUsername.setText("Username cannot contain two or more consecutive symbols");
                    txtUsername.requestFocus();
                    break;
                case "valid":
                    lblUsername.setVisible(false);
                    break;
            }

            if (!password.equals(txtPasswordConfirm.getText())) {
                lblPassword.setVisible(true);
                lblPassword.setText("Passwords mismatch");
                txtPassword.requestFocus();
                return false;
            } else {
                switch (Validation.validatePassword(password)) {
                    case "too_short":
                        lblPassword.setVisible(true);
                        lblPassword.setText("Password cannot be less than 6 characters long");
                        txtPassword.requestFocus();
                        break;
                    case "too_simple":
                        lblPassword.setVisible(true);
                        lblPassword.setText("Password must contain at least one of uppercase-letter,lowercase-letter,number and symbol");
                        txtPassword.requestFocus();
                        break;
                    case "valid":
                        lblPassword.setVisible(false);
                        break;
                }
            }
            return false;
        }
    }
}
