package lk.ijse.dep.mobile.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import lk.ijse.dep.mobile.tm.Customer;
import lk.ijse.dep.mobile.util.IOManager;
import lk.ijse.dep.mobile.util.Util;
import lk.ijse.dep.mobile.util.Validation;

import static lk.ijse.dep.mobile.util.IOManager.writeImgToFile;

public class ManageCustomerController {
    public AnchorPane root;
    public TextField txtCusID;
    public Label lblCusID;
    public TextField txtCusName;
    public Label lblCusName;
    public TextField txtCusAddress;
    public Label lblCusAddress;
    public TableView<Customer> tblCustomers;
    public TableColumn<Object, Object> colCusID;
    public TableColumn<Object, Object> colCusName;
    public TableColumn<Object, Object> colCusAddress;
    public ImageView imgCustomer;
    public Accordion accord;
    public TitledPane pnData;
    public TitledPane pnImage;
    ObservableList<Customer> tbList = FXCollections.observableArrayList();

    public void initialize() {
        colCusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        loadDataToTable();
        lblCusID.setVisible(false);
        lblCusAddress.setVisible(false);
        lblCusName.setVisible(false);
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                imgCustomer.setImage(newValue.getImage().getImage());
                accord.setExpandedPane(pnImage);
            }
        });

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                    Util.displayScreen("dashBoard", root);
            }
        });

        Rectangle2D vb= Screen.getPrimary().getVisualBounds();
        root.setPrefHeight(vb.getHeight());
        root.setPrefWidth(vb.getWidth());
        imgCustomer.setFitWidth(vb.getWidth()-40);
        accord.setExpandedPane(pnData);
    }
    private void loadDataToTable() {
       tbList = IOManager.readCustomersFromDB();
       tblCustomers.setItems(tbList);
    }


    public void btnDelCus_OnAction(ActionEvent actionEvent) {
        try {
            Customer selectedCustomer = tblCustomers.getSelectionModel().selectedItemProperty().get();
            System.out.println(selectedCustomer.toString());
            tbList.remove(selectedCustomer);
            tblCustomers.setItems(tbList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Cannot delete without selecting the customer first!").show();
        }
    }

    public void btnSaveCus_OnAction(ActionEvent actionEvent) {
        String customerID = txtCusID.getText();
        String customerName = txtCusName.getText();
        String customerAddress = txtCusAddress.getText();

        if (customerID.trim().length() == 0) {
            lblCusID.setVisible(true);
            lblCusID.setText("Customer id can't be empty");
            txtCusID.requestFocus();
        } else if (duplicateCustomerIDExists(customerID)) {
            lblCusID.setVisible(true);
            lblCusID.setText("Duplicate Customer ID exists in the database. Change the customer ID");
            txtCusID.requestFocus();
        } else if (!Validation.validateCustomerID(customerID)) {
            lblCusID.setVisible(true);
            lblCusID.setText("Customer ID must be in the form: CXXX where X are number digits.");
            txtCusID.requestFocus();
        } else if (customerName.trim().length() == 0) {
            lblCusID.setVisible(false);
            lblCusName.setVisible(true);
            lblCusName.setText("Customer name can't be empty");
            txtCusName.requestFocus();
        } else if (customerAddress.trim().length() == 0) {
            lblCusName.setVisible(false);
            lblCusAddress.setVisible(true);
            lblCusAddress.setText("Customer address can't be empty");
            txtCusAddress.requestFocus();
        } else {
            new Alert(Alert.AlertType.INFORMATION,"Please take a picture of customer.").showAndWait();
            Customer customer = new Customer(customerID, customerName, customerAddress,writeImgToFile(imgCustomer));
           tbList.add(customer);
            tblCustomers.setItems(tbList);
            IOManager.writeCustomersToDB(tbList);
        }

    }

    private boolean duplicateCustomerIDExists(String customerID) {
        for (Customer c : tbList) {
            if (c.getCustomerID().equals(customerID)) {
                return true;
            }
        }
        return false;
    }



}
