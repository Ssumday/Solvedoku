import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;


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
        Image image1 = new Image(getClass().getResourceAsStream("logo.png"));
        view.setImage(image1);
        pane.setAlignment(view, Pos.TOP_CENTER);
        view.setFitHeight(500);
        view.setFitWidth(500);


        view.setImage(image1);
        view.setFitHeight(500);
        view.setFitWidth(500);

        pane.getChildren().add(view);
        primaryStage.setScene(scene);
        primaryStage.show();

        //button management
        Button button = new Button("TextFile Reader");
        Button button2 = new Button("Quit");
        Button button3 = new Button("Example 1");
        Button button4 = new Button("Example 2");
        Button button5 = new Button("Example 3");
        Button button6 = new Button("Example 4");
        button.setTranslateY(250);
        button.setTranslateX(-140);
        button2.setTranslateY(250);
        button2.setTranslateX(150);
        button3.setTranslateY(280);
        button3.setTranslateX(-200);
        button4.setTranslateY(280);
        button4.setTranslateX(-80);
        button5.setTranslateY(280);
        button5.setTranslateX(80);
        button6.setTranslateY(280);
        button6.setTranslateX(200);


        pane.getChildren().add(button);
        pane.getChildren().add(button2);
        pane.getChildren().add(button3);
        pane.getChildren().add(button4);
        pane.getChildren().add(button5);
        pane.getChildren().add(button6);


        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.hide();
                Stage stage = new Stage();
                StackPane pane1 = new StackPane();
                Scene scene = new Scene(pane1, 700, 660);
                ImageView view = new ImageView();
                Image image = new Image(FileReader.FileChooser());

                view.setImage(image);
                view.setFitHeight(500);
                view.setFitWidth(500);

                pane1.getChildren().add(view);
                stage.setScene(scene);
                stage.show();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Image");
                alert.setHeaderText(null);
                alert.setContentText("This is the image you have entered.");

                alert.showAndWait();
            }//end of handle
        });//end of button 1

        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Are you sure you want to quit?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                   primaryStage.close();
                } else {
                    alert.close();// ... user chose CANCEL or closed the dialog
                }//end alert dialog closed
            }//end of handle
        });//end of button2


    }//end of start


}//end of application
