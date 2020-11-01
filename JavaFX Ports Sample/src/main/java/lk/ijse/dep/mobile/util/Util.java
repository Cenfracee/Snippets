package lk.ijse.dep.mobile.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Util {

    public static Image resize(Image image, int w, int h) {
        if (w <= 0 || h <= 0) {
            new Alert(Alert.AlertType.ERROR,"Invalid argument").show();
        }
        PixelReader reader = image.getPixelReader();
        WritableImage writableImage = new WritableImage(w, h);
        PixelWriter writer = writableImage.getPixelWriter();
        double wf = image.getWidth() / w;
        double hf = image.getHeight() / h;
        int xToWrite = 0;
        while (xToWrite < w) {
            int yToWrite = 0;
            while (yToWrite < h) {
                writer.setArgb(xToWrite, yToWrite, reader.getArgb((int) (xToWrite * wf), (int) (yToWrite * hf)));
                yToWrite++;
            }
            xToWrite++;
        }
        return writableImage;
    }

    public static void displayScreen(String s, AnchorPane root)  {
        Stage stage = (Stage) root.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(root.getClass().getResource("/view/"+s+".fxml"))));
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
