package solver;
import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Tess4j {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		


	}
	public int decode()
	{
		File imageFile = new File("/Users/Nikita/Documents/workspace/Attempt16/digit.jpg");
		
		ITesseract instance = new Tesseract();
		instance.setDatapath("/Users/Nikita/Documents/workspace/Attempt16/tessdata");
		
		try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
			
			int value = Integer.parseInt(result.trim());
			System.out.println("value "+ value);
			return value;
		} catch(TesseractException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}

}
