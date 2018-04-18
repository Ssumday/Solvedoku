package application;
	
import javafx.application.Application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.google.common.base.Joiner;

import solver.Board;
import solver.DetectSudoku;
import solver.SudokuSolver;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		/*try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
	public static void main(String[] args) {
		
			System.out.println("getting file");
	       
	        Mat m = Imgcodecs.imread("/Users/Nikita/Downloads/puzzle1.jpg");
	        
	        System.out.println("mat size " + m.height());
	        //Imgproc.resize(m, m, new Size(499,499));
	        displayImage("original", m);
	        DetectSudoku sudoku = new DetectSudoku();
	        List<Integer> l = sudoku.extractDigits(m);

	        Board b = Board.of(9, Joiner.on(" ").join(l));

	        System.out.println("Grabbed sudoku\n==============\n\n");
	        System.out.println(b);

	        SudokuSolver s = new SudokuSolver(b);
	        Board solved = s.solve();

	        System.out.println("Solved sudoku\n=============\n\n");
	        System.out.println(solved);
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

