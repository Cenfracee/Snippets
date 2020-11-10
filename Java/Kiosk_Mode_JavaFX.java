
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Rectangle2D visualBounds= Screen.getPrimary().getVisualBounds(); //Get the screen size
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());//Make sure the program fills length and width of the screen
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(true);//Keeps it always on top
        primaryStage.initStyle(StageStyle.UNDECORATED);//Remove the handy close/minimize buttons
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
