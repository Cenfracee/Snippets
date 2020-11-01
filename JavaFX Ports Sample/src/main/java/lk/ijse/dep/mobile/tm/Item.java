package lk.ijse.dep.mobile.tm;

import lk.ijse.dep.mobile.util.SerializableImage;

import java.io.Serializable;

public class Item implements Serializable {
    private String itemCode;
    private String itemDescription;
    private double unitPrice;
    private int qtyAvailable;
    private SerializableImage image;

    public Item(String itemCode, String itemDescription, double unitPrice, int qtyAvailable, SerializableImage image) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.qtyAvailable = qtyAvailable;
        this.image = image;
    }

    public Item() {
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyAvailable=" + qtyAvailable +
                '}';
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(int qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public SerializableImage getImage() {
        return image;
    }

    public void setImage(SerializableImage image) {
        this.image = image;
    }
}
