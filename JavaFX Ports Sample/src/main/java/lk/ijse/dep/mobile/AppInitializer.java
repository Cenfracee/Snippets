package lk.ijse.dep.mobile;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.dep.mobile.util.IOManager;

import java.io.IOException;

public class AppInitializer extends Application {
    private static Stage stage;
    public static void navigate(String view) {
        final String fxmlPath;
        final String title;

        try {
            switch (view) {
                case "welcome":
                    fxmlPath = "/view/WelcomeForm.fxml";
                    title = "POS - Welcome";
                    break;
                case "login":
                    fxmlPath = "/view/LoginView.fxml";
                    title = "POS - Login";
                    break;
                default:
                    throw new RuntimeException("Invalid view");
            }
            Parent root = FXMLLoader.load(AppInitializer.class.getResource(fxmlPath));
            Scene scene = new Scene(root);

            AppInitializer.stage.setScene(scene);
            AppInitializer.stage.centerOnScreen();
            if (!AppInitializer.stage.isShowing()) {
                AppInitializer.stage.show();
            }
            Platform.runLater(() -> {
                AppInitializer.stage.setResizable(false);
            });
        } catch (IOException exception) {
            new Alert(Alert.AlertType.ERROR, "Failed to initialize the app", ButtonType.OK).show();
            exception.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AppInitializer.stage = primaryStage;
        if(userDataExists()){
        navigate("login");}
        else{
            navigate("welcome");
            new Alert(Alert.AlertType.INFORMATION,"Welcome to this app. Please enter new user information.").show();
        }
    }

    private boolean userDataExists() {
        return IOManager.readUserFromDB() != null;
    }


}
