package lk.ijse.dep.mobile.controller;

import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

import lk.ijse.dep.mobile.util.Util;

import java.io.IOException;

public class DashBoardController {
    public AnchorPane root;

    public void initialize(){
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
               Util.displayScreen("LoginView",root);
            }
        });
        Rectangle2D vb= Screen.getPrimary().getVisualBounds();
        root.setPrefHeight(vb.getHeight());
        root.setPrefWidth(vb.getWidth());
    }
    public void btnLogout_OnAction(ActionEvent actionEvent) throws IOException {
        Util.displayScreen("LoginView",root);
    }

    public void btnManageCustomer_OnAction(ActionEvent actionEvent) throws IOException {
        Util.displayScreen("ManageCustomer",root);
    }

    public void btn_ManageItem_OnAction(ActionEvent actionEvent) throws IOException {
        Util.displayScreen("ManageItem",root);
    }

    public void btnUserSettings_OnAction(ActionEvent actionEvent) {
        Util.displayScreen("UserSettingsForm",root);
    }



}
