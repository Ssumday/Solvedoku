package Solver;

import java.io.File;
import org.opencv.core.Core;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class DetectValidation
{
   public static int detectV()
   {
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
   
     
         // OCR
         File imageFile = new File("C:\\Users\\Brandon\\Desktop\\New folder (3)\\SDK\\src\\Solver\\007.jpg");
 		
 		ITesseract instance = new Tesseract();
 		instance.setDatapath("C:\\Users\\Brandon\\Desktop\\New folder (3)\\SDK\\tessdata");
 		String result2 = null;
 		int value = 0;
 		try {
 			result2 = instance.doOCR(imageFile);
 			//String aa = "";
 			
 			// Better method maybe
 			for(int i = 0; i < result2.length(); i ++) {
 				
 				char c = result2.charAt(i);
 				if(Character.isDigit(c)) {
 					value = Integer.parseInt(c+"");
 					break;
 				}
 			}
 			
 			
 			//value = Integer.parseInt(aa);
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