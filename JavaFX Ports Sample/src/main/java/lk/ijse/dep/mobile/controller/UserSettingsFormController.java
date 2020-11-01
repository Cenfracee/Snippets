package lk.ijse.dep.mobile.controller;

import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import lk.ijse.dep.mobile.AppInitializer;
import lk.ijse.dep.mobile.util.IOManager;
import lk.ijse.dep.mobile.util.Util;
import lk.ijse.dep.mobile.util.Validation;

public class UserSettingsFormController {
    public AnchorPane root;
    public TextField txtFullName;
    public TextField txtUsername;
    public TextField txtNewPassword;
    public TextField txtNewpasswordConfirm;
    public Label lblMessage;

    public void initialize() {
        this.root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Util.displayScreen("dashBoard",root);
            }
        });
        lblMessage.setVisible(false);
        Rectangle2D vb = Screen.getPrimary().getVisualBounds();
        root.setPrefHeight(vb.getHeight());
        root.setPrefWidth(vb.getWidth());
    }

    public void btnCancel_OnAction(ActionEvent actionEvent) {
        Util.displayScreen("dashBoard",root);
    }

    public void btnSaveUserSettings_OnAction(ActionEvent actionEvent) {
        String fullName = txtFullName.getText();
        String userName = txtUsername.getText();
        String password = txtNewPassword.getText();
        String[] userData=new String[3];
        if (validation(fullName, userName, password)) {
            userData[0]=fullName;
            userData[1]=userName;
            userData[2]=password;
            IOManager.writeUserToDB(userData);
            lblMessage.setVisible(false);
            new Alert(Alert.AlertType.INFORMATION, "Current user updated successfully").showAndWait();
            Util.displayScreen("dashBoard",root);
        }
    }

    private boolean validation(String fullName, String userName, String password) {
        if (
                (Validation.validateFullName(fullName).equals("valid")) &&
                        ((Validation.validateUsername(userName)).equals("valid")) &&
                        ((Validation.validatePassword(password)).equals("valid")) &&
                        (password.equals(txtNewpasswordConfirm.getText()))
        ) {
            return true;
        } else {
            switch (Validation.validateFullName(fullName)) {
                case "all_spaces":
                    lblMessage.setVisible(true);
                    lblMessage.setText("Full name cannot be empty or all spaces or blank");
                    txtFullName.requestFocus();
                    break;
                case "too_short":
                    lblMessage.setVisible(true);
                    lblMessage.setText("Full name cannot be less than 4 letters long");
                    txtFullName.requestFocus();
                    break;
                case "two_consecutive_dots":
                    lblMessage.setVisible(true);
                    lblMessage.setText("Full name cannot contain two or more consecutive symbols");
                    txtFullName.requestFocus();
                    break;
                case "valid":
                    lblMessage.setVisible(false);
                    break;
            }

            switch (Validation.validateUsername(userName)) {
                case "all_spaces":
                    lblMessage.setVisible(true);
                    lblMessage.setText("Username cannot be all spaces");
                    txtUsername.requestFocus();
                    break;
                case "too_short":
                    lblMessage.setVisible(true);
                    lblMessage.setText("Username cannot be less than 4 letters long");
                    txtUsername.requestFocus();
                    break;
                case "two_consecutive_dots":
                    lblMessage.setVisible(true);
                    lblMessage.setText("Username cannot contain two or more consecutive symbols");
                    txtUsername.requestFocus();
                    break;
                case "valid":
                    lblMessage.setVisible(false);
                    break;
            }

            if (!password.equals(txtNewpasswordConfirm.getText())) {
                lblMessage.setVisible(true);
                lblMessage.setText("Passwords mismatch");
                txtNewPassword.requestFocus();
                return false;
            } else {
                switch (Validation.validatePassword(password)) {
                    case "too_short":
                        lblMessage.setVisible(true);
                        lblMessage.setText("Password cannot be less than 6 characters long");
                        txtNewPassword.requestFocus();
                        break;
                    case "too_simple":
                        lblMessage.setVisible(true);
                        lblMessage.setText("Password must contain at least one of uppercase-letter,lowercase-letter,number and symbol");
                        txtNewPassword.requestFocus();
                        break;
                    case "valid":
                        lblMessage.setVisible(false);
                        break;
                }
            }
            return false;
        }
    }
}
