import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Driver extends Application{

    public static void main(String[] args) {
        launch(args);
    }//end of main

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane, 700, 660);
        primaryStage.setTitle("Solvedoku");
        ImageView view = new ImageView();
        Image image = new Image(FileReader.FileChooser());

        view.setImage(image);
        view.setFitHeight(500);
        view.setFitWidth(500);

        pane.getChildren().add(view);
        primaryStage.setScene(scene);
        primaryStage.show();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Image");
        alert.setHeaderText(null);
        alert.setContentText("This is the image you have entered.");

        alert.showAndWait();
    }//end of start


}//end of application
