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
import lk.ijse.dep.mobile.tm.Item;
import lk.ijse.dep.mobile.util.IOManager;
import lk.ijse.dep.mobile.util.Util;
import lk.ijse.dep.mobile.util.Validation;

import static lk.ijse.dep.mobile.util.IOManager.writeImgToFile;


public class ManageItemController {
    public AnchorPane root;

    public TextField txtItemCode;
    public Label lblItemCode;
    public TextField txtItemDescription;
    public Label lblItemDescription;
    public TextField txtItemPrice;
    public Label lblItemPrice;
    public TextField txtItemQuantity;
    public Label lblQtyAvailable;

    public ImageView imgItem;

    public TableView<Item> tblItems;
    public TableColumn<Object, Object> colItmCode;
    public TableColumn<Object, Object> colItmDesc;
    public TableColumn<Object, Object> colItmPrice;
    public TableColumn<Object, Object> colItmQty;
    public Accordion accord;
    public TitledPane pnData;
    public TitledPane pnImage;

    ObservableList<Item> tbList = FXCollections.observableArrayList();

    public void initialize() {
        colItmCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItmDesc.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colItmPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colItmQty.setCellValueFactory(new PropertyValueFactory<>("qtyAvailable"));
        loadDataToTable();
        lblItemDescription.setVisible(false);
        lblItemCode.setVisible(false);
        lblItemPrice.setVisible(false);
        lblQtyAvailable.setVisible(false);
        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                imgItem.setImage(newValue.getImage().getImage());
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
        imgItem.setFitWidth(vb.getWidth()-40);
        accord.setExpandedPane(pnData);
    }

    private void loadDataToTable() {
        tbList = IOManager.readItemsFromDB();
        tblItems.setItems(tbList);
    }


    public void btnDelItm_OnAction(ActionEvent actionEvent) {
        try {
            Item selectedItem = tblItems.getSelectionModel().selectedItemProperty().get();
            System.out.println(selectedItem.toString());
            tbList.remove(selectedItem);
            tblItems.setItems(tbList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Cannot delete without selecting the Item first!").show();
        }
    }

    public void btnSaveItm_OnAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        String itemDescription = txtItemDescription.getText();
        double unitPrice = 0;
        int qtyAvailable = 0;
        try {
            unitPrice = Double.parseDouble(txtItemPrice.getText());
        } catch (Exception e) {
            //  e.printStackTrace();
        }
        try {
            qtyAvailable = Integer.parseInt(txtItemQuantity.getText());
        } catch (Exception e) {
            //  e.printStackTrace();
        }

        if (itemCode.trim().length() == 0) {
            lblItemCode.setVisible(true);
            lblItemCode.setText("Item code can't be empty");
            txtItemCode.requestFocus();
        } else if (duplicateItemCodeExists(itemCode)) {
            lblItemCode.setVisible(true);
            lblItemCode.setText("Duplicate Item Code exists in the database. Change the Item Code");
            txtItemCode.requestFocus();
        } else if (!Validation.validateItemCode(itemCode)) {
            lblItemCode.setVisible(true);
            lblItemCode.setText("Item Code must be in the form: IXXX where X are number digits.");
            txtItemCode.requestFocus();
        } else if (itemDescription.trim().length() == 0) {
            lblItemCode.setVisible(false);
            lblItemDescription.setVisible(true);
            lblItemDescription.setText("Description can't be empty");
            txtItemDescription.requestFocus();
        } else if (!Validation.validateUnitPrice(txtItemPrice.getText())) {
            lblItemDescription.setVisible(false);
            lblItemPrice.setVisible(true);
            lblItemPrice.setText("Unit price must be numerical");
            txtItemPrice.requestFocus();
        } else if (unitPrice <= 0) {
            lblItemDescription.setVisible(false);
            lblItemPrice.setVisible(true);
            lblItemPrice.setText("unit price can't be 0");
            txtItemPrice.requestFocus();
        } else if (!Validation.validateQty(txtItemQuantity.getText())) {
            lblItemPrice.setVisible(false);
            lblQtyAvailable.setVisible(true);
            lblQtyAvailable.setText("Quantity must be a whole number(Integer)");
            txtItemPrice.requestFocus();
        } else if (qtyAvailable <= 0) {
            lblItemPrice.setVisible(false);
            lblQtyAvailable.setVisible(true);
            lblQtyAvailable.setText("Quantity can't be 0");
            txtItemPrice.requestFocus();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please take a picture of item.").showAndWait();
            Item item = new Item(itemCode, itemDescription, unitPrice, qtyAvailable, writeImgToFile(imgItem));
            tbList.add(item);
            tblItems.setItems(tbList);
            IOManager.writeItemsToDB(tbList);
        }
    }

    private boolean duplicateItemCodeExists(String itemCode) {
        for (Item i : tbList) {
            if (i.getItemCode().equals(itemCode)) {
                return true;
            }
        }
        return false;
    }
}
