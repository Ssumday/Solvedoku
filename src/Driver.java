import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Driver extends Application{
    public static void main(String[] args) {
        launch(args);
    }//end of main

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileReader fileR = new FileReader();
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane, 700, 660);
        primaryStage.setTitle("Choice Giveaways");
    }//end of start
}//end of application
