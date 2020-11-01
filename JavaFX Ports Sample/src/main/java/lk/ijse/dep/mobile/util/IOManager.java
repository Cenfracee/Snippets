package lk.ijse.dep.mobile.util;


import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.PicturesService;
import com.gluonhq.charm.down.plugins.StorageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.dep.mobile.tm.Customer;
import lk.ijse.dep.mobile.tm.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

//General IO Functions ------------------------------------------------------------
public class IOManager {
    public static void writeObjectToDB(String fileName, Object data) {
        Optional<StorageService> optStorageService = Services.get(StorageService.class);
        if (optStorageService.isPresent()) {
            StorageService storageService = optStorageService.get();
            if (storageService.isExternalStorageWritable()) {
                Optional<File> publicStorage = storageService.getPublicStorage("/dep-fx");
                publicStorage.get().mkdir();
                File file = new File(publicStorage.get(), fileName);
                try (FileOutputStream fos = new FileOutputStream(file);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(data);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "failed to write").show();
                    e.printStackTrace();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "App doesn't have permission to write").show();
            }
        }
    }


    public static Object readObjectFromDB(String fileName) {
        Object ob = null;
        Optional<StorageService> optStorageService = Services.get(StorageService.class);
        if (optStorageService.isPresent()) {
            StorageService storageService = optStorageService.get();
            if (storageService.isExternalStorageReadable()) {
                Optional<File> publicStorage = storageService.getPublicStorage("/dep-fx");
                File file = new File(publicStorage.get(), fileName);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    ob = ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ob;
    }

    public static SerializableImage writeImgToFile(ImageView img) {

        SerializableImage serializableImage = new SerializableImage();
        Optional<PicturesService> optPicturesService = Services.get(PicturesService.class);
        Optional<StorageService> optStorageService = Services.get(StorageService.class);
        if (optPicturesService.isPresent()) {
            PicturesService picturesService = optPicturesService.get();
            Optional<Image> optImage = picturesService.takePhoto(true);

            if (optImage.isPresent()) {
                Image image = Util.resize(optImage.get(), (int) optImage.get().getWidth() / 15, (int) optImage.get().getHeight() / 15);
                img.setImage(image);
                serializableImage.setImage(image);

                File srcFile = picturesService.getImageFile().get();
                String destPath = "/dep-fx" + File.separator + srcFile.getName();
                File destFile = optStorageService.get().getPublicStorage(destPath).get();

                try (FileInputStream fis = new FileInputStream(srcFile);
                     BufferedInputStream bis = new BufferedInputStream(fis);
                     FileOutputStream fos = new FileOutputStream(destFile);
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                    byte[] bytes = new byte[fis.available()];
                    bis.read(bytes);
                    bos.write(bytes);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to write the image").show();
                    e.printStackTrace();
                }

            } else {
                img.setImage(null);
            }
        }
        return serializableImage;
    }

    // ---------------------------------------------------------------------------------------------------------------

    //Custom implementations of the general IO functions ---------------------------------------------------------------
    public static ObservableList<Customer> readCustomersFromDB() {
        ObservableList<Customer> tbList = FXCollections.observableArrayList();
        if (readObjectFromDB("customerDB") != null) {
            Object customerDB = readObjectFromDB("customerDB");
            ArrayList<Customer> customers = (ArrayList<Customer>) customerDB;
            tbList = FXCollections.observableArrayList(customers);
        }
        return tbList;
    }

    public static ObservableList<Item> readItemsFromDB() {
        ObservableList<Item> tbList = FXCollections.observableArrayList();
        if (readObjectFromDB("itemDB") != null) {
            Object itemDB = readObjectFromDB("itemDB");
            ArrayList<Item> items = (ArrayList<Item>) itemDB;
            tbList = FXCollections.observableArrayList(items);
        }
        return tbList;
    }

    public static String[] readUserFromDB() {
        return (readObjectFromDB("userDB") != null) ? (String[]) readObjectFromDB("userDB") : null;
    }

    public static void writeCustomersToDB(ObservableList<Customer> tbList) {
        ArrayList<Customer> customers = new ArrayList<>(tbList);
        writeObjectToDB("customerDB", customers);
    }

    public static void writeItemsToDB(ObservableList<Item> tbList) {
        ArrayList<Item> items = new ArrayList<>(tbList);
        writeObjectToDB("itemDB", items);
    }

    public static void writeUserToDB(String[] userData) {
        writeObjectToDB("userDB", userData);
    }
    // ---------------------------------------------------------------------------------------------------------------

    //Obsolete features
    /*    public static void readImgFromFile(ImageView img, String path) {
        FileInputStream fis = null;
        try {
            new Alert(Alert.AlertType.ERROR, "fis is not ok").showAndWait();
            fis = new FileInputStream("path");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail").show();
        }
        img.setImage(new Image(fis));
    }*/
    /*    public static String readTxtFromDB(String fileName) {
            Optional<StorageService> optStorageService = Services.get(StorageService.class);
            if (optStorageService.isPresent()) {
                StorageService storageService = optStorageService.get();
                if (storageService.isExternalStorageReadable()) {
                    Optional<File> publicStorage = storageService.getPublicStorage("/dep-fx/" + fileName);
                    if (publicStorage.get().exists()) {
                        try {
                            FileReader fileReader = new FileReader(publicStorage.get());
                            BufferedReader bufferedReader = new BufferedReader(fileReader);

                            String content = "";
                            String line = null;
                            while ((line = bufferedReader.readLine()) != null) {
                                content += line;
                            }
                            return content;

                        } catch (IOException e) {
                            new Alert(Alert.AlertType.ERROR, "failed to read").show();
                            e.printStackTrace();
                            return "";
                        }

                    } else {
                        new Alert(Alert.AlertType.ERROR, "File doesn't exist yet").show();
                        return "";
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "App doesn't have permission to read").show();
                    return "";
                }
            } else return "";
        }*/

}
