package application;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.google.common.base.Joiner;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import solver.Board;
import solver.DetectSudoku;
import solver.SudokuSolver;

public class Driver extends BorderPane{
	
	public Driver()
	{
		 ImageView view = new ImageView();
	        Image image1 = new Image(getClass().getResourceAsStream("logo.png"));
	        view.setImage(image1);
	        
	        view.setFitHeight(500);
	        view.setFitWidth(500);


	        view.setImage(image1);
	        view.setFitHeight(500);
	        view.setFitWidth(500);
	        
	        setCenter(view);
	       

	        //button management
	        Button button = new Button("File Reader");
	        Button button2 = new Button("Quit");
	        Button button3 = new Button(" ");
	        Button button4 = new Button(" ");
	        Button button5 = new Button(" ");
	        


	        Image image3 = new Image(getClass().getResourceAsStream("puzzle1.jpg"), 100, 100, true, true);
	        button3.setGraphic(new ImageView(image3));
	        Image image4 = new Image(getClass().getResourceAsStream("puzzle5.png"), 100, 100, true, true);
	        button4.setGraphic(new ImageView(image4));
	        Image image5 = new Image(getClass().getResourceAsStream("puzzle7.png"), 100, 100, true, true);
	        button5.setGraphic(new ImageView(image5));

	        
	        HBox hbox1 = new HBox(button, button2);
	        setTop(hbox1);
			
			hbox1.setAlignment(Pos.CENTER);
			HBox hbox2 = new HBox(button3, button4, button5);
			hbox2.setAlignment(Pos.CENTER);
			setBottom(hbox2);


	        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	             
	                ImageView view = new ImageView();
	                String path = fileChooser();
	                Image image = new Image(path);

	                view.setImage(image);
	                view.setFitHeight(500);
	                view.setFitWidth(500);
	                
	                Button solve = new Button("Solve");
	                solve.setPrefSize(100, 50);
	                 solve.setAlignment(Pos.CENTER);
	                 solve.getStyleClass().add("buttonStyle");
	                 setRight(solve);
	                 solve.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                	 @Override
	     	        	public void handle(MouseEvent event) {
	                		 String newpath = path.substring(5, path.length());
	                		 solve(newpath);
	                	 }
	                 });
	                setCenter(view);
	                
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
	                   System.exit(0);
	                } else {
	                    alert.close();// ... user chose CANCEL or closed the dialog
	                }//end alert dialog closed
	            }//end of handle
	        });//end of button2
	        
	        button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        	@Override
	        	public void handle(MouseEvent event) {
	        		
	                 ImageView view = new ImageView();
	                 Image image = new Image(getClass().getResourceAsStream("puzzle1.jpg"));

	                 view.setImage(image);
	                 view.setFitHeight(500);
	                 view.setFitWidth(500);
	                 
	                 Button solve = new Button("Solve");
	                 solve.setPrefSize(100, 50);
	                 solve.setAlignment(Pos.CENTER);
	                 solve.getStyleClass().add("buttonStyle");
	                 setRight(solve);
	                 
	                 solve.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                	 @Override
	     	        	public void handle(MouseEvent event) {
	                		 String path = "/Users/Nikita/Downloads/" + "puzzle1.jpg";
	                		 solve(path);
	                	 }
	                 });

	                setCenter(view);
	        	}
	        });
	        button4.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        	@Override
	        	public void handle(MouseEvent event) {
	        		
	                 ImageView view = new ImageView();
	                 Image image = new Image(getClass().getResourceAsStream("puzzle5.png"));

	                 view.setImage(image);
	                 view.setFitHeight(500);
	                 view.setFitWidth(500);
	                 
	                 Button solve = new Button("Solve");
	                 solve.setPrefSize(100, 50);
	                 solve.setAlignment(Pos.CENTER);
	                 solve.getStyleClass().add("buttonStyle");
	                 setRight(solve);
	                 
	                 solve.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                	 @Override
	     	        	public void handle(MouseEvent event) {
	                		 String path = "/Users/Nikita/Downloads/" + "puzzle5.png";
	                		 solve(path);
	                	 }
	                 });
	                setCenter(view);
	        	}
	        });
	        button5.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        	@Override
	        	public void handle(MouseEvent event) {
	        		
	                 ImageView view = new ImageView();
	                 Image image = new Image(getClass().getResourceAsStream("puzzle7.png"));

	                 view.setImage(image);
	                 view.setFitHeight(500);
	                 view.setFitWidth(500);
	                 
	                 Button solve = new Button("Solve");
	                 solve.setPrefSize(100, 50);
	                 solve.setAlignment(Pos.CENTER);
	                 solve.getStyleClass().add("buttonStyle");
	                 setRight(solve);
	                 
	                 solve.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                	 @Override
	     	        	public void handle(MouseEvent event) {
	                		 String path = "/Users/Nikita/Downloads/" + "puzzle7.png";
	                		 solve(path);
	                	 }
	                 });

	                setCenter(view);
	        	}
	        });
	}
	public void solve(String path)
	{
		Mat m = Imgcodecs.imread(path);
	     
        Imgproc.resize(m, m, new Size(749,749));
       
        DetectSudoku sudoku = new DetectSudoku();
        List<Integer> l = sudoku.extractDigits(m);
        
        Board b = Board.of(9, Joiner.on(" ").join(l));


        SudokuSolver s = new SudokuSolver(b);
        int[][] array = new int[9][9];
        array = s.solve();
        ShowGrid grid = new ShowGrid(array);
        
        Scene scene = new Scene(grid,675,725);
		
        scene.getStylesheets().add("styles/style.css");
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
	}
	public String fileChooser()
	{
		FileChooser fileChoose = new FileChooser();
        fileChoose.setTitle("Choose an image file");
        File data = fileChoose.showOpenDialog(null);
        if(data != null){
            String imagePath = data.toURI().toString();
            return imagePath;
        }
        else{
            return null;
        }
	}

}
