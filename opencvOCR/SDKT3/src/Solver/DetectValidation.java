package Solver;

import java.io.File;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class DetectValidation
{
   public static int detectV()
   {
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
     /* Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
      System.out.println( "mat = " + mat.dump() );*/
      
      
      // reading image 
      //Mat image = Imgcodecs.imread("C:\\eclipse_opencv&tess4j\\SDKT3\\src\\Solver\\007.jpg", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

      // clone the image 
     // Mat original = image.clone();
      
     
         // OCR
         File imageFile = new File("C:\\eclipse_opencv&tess4j\\SDKT3\\src\\Solver\\007.jpg");
 		
 		ITesseract instance = new Tesseract();
 		instance.setDatapath("C:\\eclipse_opencv&tess4j\\SDKT3\\tessdata");
 		String result2 = null;
 		int value = 0;
 		try {
 			result2 = instance.doOCR(imageFile);
 			value = Integer.parseInt(result2.trim());
 			//System.out.print(result2);
 		} catch(TesseractException e) {
 			System.err.println(e.getMessage());
 		}
		return value;
         
         
         //********************
         //System.out.println("original final size:" + original.size());
         //System.out.println("result size:" + result.size());

         //Imgcodecs.imwrite("C:\\eclipse_opencv&tess4j\\SDKT2\\src\\Solver\\008.jpg", result);
    // }
      
   }
}