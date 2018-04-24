package application;
	
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import Solver.DetectSudoku;
import Solver.Sudoku;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

	
	public static int[][] myGrid;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Solvedoku");
	    GridPane board = new GridPane();
	
		System.out.println("getting file");
        Mat m = Imgcodecs.imread("C:\\Users\\Brandon\\Desktop\\puzzle1.jpg");
        
        Imgproc.resize(m, m, new Size(749, 749));
        displayImage("original", m);
        
        
        DetectSudoku sudoku = new DetectSudoku();
        List<Integer> l = sudoku.extractDigits(m);
        
      /*  for(int i = 0, j = 0; i < 81; i++, j++) {
        	if(j % 9 == 0) {
        		System.out.println();
        	}
        	System.out.print(l.get(i) + " ");
        	
        }
*/
     
        myGrid = new int[9][9];
		
        int count = 0;
        
        // Original input
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++, count++) {
				myGrid[i][j] = l.get(count);	
				System.out.print(myGrid[i][j] + " ");
			}
			System.out.println();
		}  
		
		// Return the answer of sudoku as 2D Array
		// So you can use it later on
		int[][] answer = Sudoku.Solver();
		
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++)
			{
				
				System.out.print(answer[i][j] + " ");
			}
			System.out.println();
		}  
		
		for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                StackPane cell = new StackPane();
                cell.getStyleClass().add("cell");
//                cell.pseudoClassStateChanged(right, col == 2 || col == 5);
//                cell.pseudoClassStateChanged(bottom, row == 2 || row == 5);
                
//                String x = obj[col][row];
                String x = answer[row][col] + ""; 
                
                cell.getChildren().add(createTextField(x));

                board.add(cell, col, row);
            }
        }
		
		 Scene scene = new Scene(board);
	        scene.getStylesheets().add("application.css");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		
	}
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
	public static void main(String[] args) {
	
		launch(args);
	
	}
	private Label createTextField(String x ) {
		  

    	// nextInt as provided by Random is exclusive of the top value so you need to add 1 


        Label textField = new Label(x);
  
    
        
        return textField ;
    }

	
	 public static void displayImage(String label, Mat mat)
	    {
	    	//show image
	   		Mat temp = mat;
	   		MatOfByte matOfByte = new MatOfByte();
	   		
	   		Imgcodecs.imencode(".jpg", temp, matOfByte);
	   		
	   		byte[] byteArray = matOfByte.toArray();
	   		BufferedImage buffImage = null;
	   		try
	   		{
	   			InputStream in = new ByteArrayInputStream(byteArray);
	   			buffImage = ImageIO.read(in);
	   		//Instantiate JFrame 
	   	      JFrame frame = new JFrame(); 

	   	      //Set Content to the JFrame 
	   	      frame.getContentPane().add(new JLabel(new ImageIcon(buffImage))); 
	   	      frame.pack(); 
	   	      frame.setVisible(true);
	   	      
	   	      System.out.println(label + "Image Loaded");    
	   		} catch (Exception e)
	   		{
	   			e.printStackTrace();
	   		}
	   		//Graphics g = 
	   		//g.drawImage(buffImage, 0, 0, null);
	    }
}
