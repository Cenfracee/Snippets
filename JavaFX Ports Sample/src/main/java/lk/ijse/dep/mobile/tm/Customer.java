package lk.ijse.dep.mobile.tm;

import lk.ijse.dep.mobile.util.SerializableImage;

import java.io.Serializable;

public class Customer implements Serializable {
    private String customerID;
    private String name;
    private String address;
    private SerializableImage image;

    public Customer() {
    }

    public Customer(String customerID, String name, String address, SerializableImage image) {
        this.setCustomerID(customerID);
        this.setName(name);
        this.setAddress(address);
        this.setImage(image);
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SerializableImage getImage() {
        return image;
    }

    public void setImage(SerializableImage image) {
        this.image = image;
    }
}
