package opencvProject;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

class SimpleSample {

  static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

  public static void main(String[] args) {
  /*  System.out.println("Welcome to OpenCV " + Core.VERSION);
    Mat m = new Mat(5, 10, CvType.CV_8UC1, new Scalar(0)); 
    System.out.println("OpenCV Mat: " + m);
    Mat mr1 = m.row(1);
    mr1.setTo(new Scalar(1));
    Mat mc5 = m.col(5);
    mc5.setTo(new Scalar(5));
    System.out.println("OpenCV Mat data:\n" + m.dump());*/
	  
	  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
	// Read the image
		
	  Mat color = Imgcodecs.imread("C:\\eclipse-opencv3.4\\SudokuTest01\\src\\Solver\\111.jpg");
	  
	  // turn the image to black and white color
	  Mat bw = new Mat();
	  
	
	  Imgproc.cvtColor(color, bw, Imgproc.COLOR_BGR2GRAY);
	
	  bw.convertTo(color, CvType.CV_8UC1);
	  
	 // Mat smooth = new Mat();
	 // Mat threshold = new Mat();
	
	  Imgproc.GaussianBlur(color, color, new Size(11, 11), 0);
	  Imgproc.adaptiveThreshold(color, bw, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 2);

	
	  //Core.bitwise_not(bw, bw);
	  
	  Mat ukernel = new Mat(3,3, CvType.CV_8U);
	  ukernel.put(0, 0, (byte) 0);
      ukernel.put(0, 1, (byte) 1);
      ukernel.put(0, 2, (byte) 0);
      ukernel.put(1, 0, (byte) 1);
      ukernel.put(1, 1, (byte) 1);
      ukernel.put(1, 2, (byte) 1);
      ukernel.put(2, 0, (byte) 0);
      ukernel.put(2, 1, (byte) 1);
      ukernel.put(2, 2, (byte) 0);
      Imgproc.dilate(bw, bw, ukernel);
	  

	  Mat threshold = bw.clone();
	  displayImage("Smooth image", threshold);
	  
	
	

  }

private static void displayImage(String string, Mat threshold) {
	 String filename = "op.jpg";
	 Imgcodecs.imwrite("C:\\Users\\ss115\\Desktop\\"+filename, threshold);
}

}