package application;
	
import org.opencv.core.Core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			Driver root = new Driver();
			Scene scene = new Scene(root, 675, 725);
			scene.getStylesheets().add("styles/style.css");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
	public static void main(String[] args) {
			launch(args);
		
		
	}
	
}

