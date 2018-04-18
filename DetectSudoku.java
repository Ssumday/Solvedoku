package solver;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.sun.prism.paint.Color;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import static solver.FeautureDetector.CONTAIN_DIGIT_SUB_MATRIX_DENSITY;

public class DetectSudoku {
	 private Size FOUR_CORNERS = new Size(1, 4);
	 
	 Tess4j tess4j = new Tess4j();

	    public DetectSudoku(){
	    	
	    }
	    public static final Function<MatOfPoint, Integer> AREA = new Function<MatOfPoint, Integer>() {
	        @Override
	        public Integer apply(MatOfPoint input) {
	            return (int)Imgproc.contourArea(input);
	        }
	    };

	    public static final Ordering<MatOfPoint> ORDERING_BY_AREA = Ordering.natural().onResultOf(AREA);
	    public static final Mat crossKernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3));

	    /**
	     * This method does:
	     * - Gaussian blur in order to reduce noise
	     * - Threshold transformation
	     * - A dilate with a 3x3 crossKernel to augment the
	     *
	     * @param image
	     * @return
	     */
	    private Mat preprocess(Mat image) {
	        Mat bw = new Mat();

	        Imgproc.cvtColor(image, bw, Imgproc.COLOR_RGB2GRAY);
	        Imgproc.GaussianBlur(bw, bw, new Size(11, 11), 0);

	        Imgproc.adaptiveThreshold(bw, bw, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 2);

	        Imgproc.dilate(bw, bw, crossKernel);

	        return bw;
	    }

	    /**
	     * This method does:
	     * - Gaussian blur in order to reduce noise
	     * - Threshold transformation
	     * - A dilate/erode to improve conectivity between conected elements
	     */
	     private Mat preprocess2(Mat image) {
	        Mat bw = preprocess(image);
	        Imgproc.erode(bw, bw, crossKernel);

	        return bw;
	    }


	    private MatOfPoint findBiggerPolygon(Mat image) {
	        List<MatOfPoint> contours = Lists.newArrayList();
	        Mat hierarchy = new Mat();

	        Imgproc.findContours(image.clone(), contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

	        if (contours.isEmpty()) {
	            return new MatOfPoint();
	        }

	        MatOfPoint max = ORDERING_BY_AREA.max(contours);

	        return max;
	    }

	    private MatOfPoint2f aproxPolygon(MatOfPoint poly) {

	        MatOfPoint2f dst = new MatOfPoint2f();
	        MatOfPoint2f src = new MatOfPoint2f();
	        poly.convertTo(src, CvType.CV_32FC2);

	        double arcLength = Imgproc.arcLength(src, true);
	        Imgproc.approxPolyDP(src, dst, 0.02 * arcLength, true);
	        return dst;
	    }

	    private static final Ordering<Point> SORT = Ordering.natural().nullsFirst().onResultOf(
	            new Function<Point, Integer>() {
	                public Integer apply(Point foo) {
	                    return (int) (foo.x+foo.y);
	                }
	            }
	    );

	    private MatOfPoint2f orderPoints(MatOfPoint2f mat) {
	        List<Point> pointList = SORT.sortedCopy(mat.toList());

	        if (pointList.get(1).x > pointList.get(2).x) {
	            Collections.swap(pointList, 1, 2);
	        }

	        MatOfPoint2f s = new MatOfPoint2f();
	        s.fromList(pointList);

	        return s;
	    }

	    private Mat wrapPerspective(int size, MatOfPoint2f src, Mat image) {
	        Size reshape = new Size(size, size);

	        Mat undistorted = new Mat(reshape, CvType.CV_8UC1);

	        MatOfPoint2f d = new MatOfPoint2f();
	        d.fromArray(new Point(0, 0), new Point(0, reshape.width), new Point(reshape.height, 0),
	                new Point(reshape.width, reshape.height));

	        Imgproc.warpPerspective(image, undistorted, Imgproc.getPerspectiveTransform(src, d), reshape);

	        return undistorted;
	    }

	    private Mat applyMask(Mat image, MatOfPoint poly) {
	        Mat mask = Mat.zeros(image.size(), CvType.CV_8UC1);

	        Imgproc.drawContours(mask, ImmutableList.of(poly), 0, Scalar.all(255), -1);
	        Imgproc.drawContours(mask, ImmutableList.of(poly), 0, Scalar.all(0), 2);

	        Mat dst = new Mat();
	        image.copyTo(dst, mask);

	        return dst;
	    }


	    public Mat getSudokuArea(Mat image) {
	        Mat preprocessed = preprocess(image);
	        displayImage("preprocess", preprocessed);
	        MatOfPoint poly = findBiggerPolygon(preprocessed);

	        MatOfPoint2f aproxPoly = aproxPolygon(poly);
	//
	        if (Objects.equals(aproxPoly.size(), FOUR_CORNERS)) {
	            int size = distance(aproxPoly);

	            Mat cutted = applyMask(image, poly);

	            Mat wrapped = wrapPerspective(size, orderPoints(aproxPoly), cutted);
	            Mat preprocessed2 = preprocess2(wrapped);
	            Mat withOutLines = cleanLines(preprocessed2);
	            displayImage("clean lines", preprocessed2);
	            return withOutLines;
	        }

	        return preprocessed;
	    }

	    private Mat cleanLines(Mat image) {
	        Mat m = image.clone();
	        Mat lines = new Mat();

	        int threshold = 50;
	        int minLineSize = 200;
	        int lineGap = 20;

	        Imgproc.HoughLinesP(m, lines, 1, Math.PI / 180, threshold, minLineSize, lineGap);

	        for (int x = 0; x < lines.cols(); x++) {
	            double[] vec = lines.get(0, x);
	            double x1 = vec[0],
	                    y1 = vec[1],
	                    x2 = vec[2],
	                    y2 = vec[3];
	            Point start = new Point(x1, y1);
	            Point end = new Point(x2, y2);

	            Imgproc.line(m, start, end, Scalar.all(0), 3);

	        }
	        return m;
	    }

	    public List<Integer> extractDigits(Mat m) {
	        Mat sudoku = getSudokuArea(m);
	        System.out.println("extract didgits");
	        if (sudoku == null) {
	            return null;
	        }

	        return extractCells(sudoku);
	    }


	    private int distance(MatOfPoint2f poly) {
	        Point[] a =  poly.toArray();
	        return (int)Math.sqrt((a[0].x - a[1].x)*(a[0].x - a[1].x) +
	        (a[0].y - a[1].y)*(a[0].y - a[1].y));
	    }


	    private List<Mat> getCells(Mat m) {
	    	
	        int size = m.height() / 9;
	        
	        //add -5 to the sizes to get rectangle
	        Size cellSize = new Size(size, size);
	        List<Mat> cells = Lists.newArrayList();
	        
	        List<Integer> list = new ArrayList<Integer>();

	        for (int row = 0; row < 9; row++) {
	            for (int col = 0; col < 9; col++) {
	                Rect rect = new Rect(new Point(col * size, row * size), cellSize);
	                
	                
	                Mat digit = new Mat(m, rect).clone();
	                //displayImage("digit", digit);
	        
	                cells.add(digit);
	                
	                
	                if(row == 0 && col==0)
	                {
	                	displayImage("digit #0 0", digit);
	                	//Imgcodecs.imwrite("digit [0][0]", digit);
	                }
	            }
	        }
	       

	        return cells;
	    }

	    private List<Integer> extractCells(Mat m) {
	        List<Mat> cells = getCells(m);
	        List<Optional<Rect>> digitBoxes = Lists.transform(cells, FeautureDetector.GET_DIGIT_BOX_BYTE_SUM);
	       
	        List<Integer> result = Lists.newArrayList();
	        List<Mat> cuts = Lists.newArrayList();
	        /* zip... zip! :'( */
	        
	        for(int i = 0; i < cells.size(); i++ ) {
	            Mat cell = cells.get(i);
	            Optional<Rect> box = digitBoxes.get(i);
	            
	            
	            int value = 0;

	            if (box.isPresent() && FeautureDetector.CONTAIN_DIGIT_SUB_MATRIX_DENSITY.apply(cell)) {
	                /* cut current cell to the finded box */
	                Mat cutted = new Mat(cell, box.get()).clone();
	                //image all cutted no number seen
	                Imgproc.rectangle(cell, box.get().tl(), box.get().br(), Scalar.all(255));
	                cuts.add(cutted);
	                //displayImage("cutted", cutted);
	                displayImage("cell ", cell);
	                Imgcodecs.imwrite("number.jpg",  cells.get(0));
	                
	               // cell = cropImage(cell);
	                
	               Mat image = crop(cell);
	                //crop(cells.get(0));
	               Imgcodecs.imwrite("digit.jpg", image);
	               value = tess4j.decode();
	                
	                //instead of 
	                //d = detect.detect(cutted);
	            }

	            Imgproc.rectangle(cell, new Point(0,0), new Point(100,100), Scalar.all(255));

	            result.add(value);
	           

	        }
	       


	        Mat m2 = new Mat(0, cells.get(0).cols(), CvType.CV_8SC1);

	        for(Mat digit: cells) {
	            m2.push_back(digit.clone());
	        }

	        //Imgcodecs.imwrite("cells_boxed.jpg", m2);
	        System.out.println("writing image");
	        //Imgcodecs.imwrite("checkdigit.jpg", cells.get(39));
	     //   crop(cells.get(0));
	        //check 39, 40, 41, 44, 47, 49, 58, 65, 67, 69, 75, 76, 77
	        //
	        return result;
	    }
	    public static Mat crop(Mat mat)
	    {
	    	Mat image = mat.clone();
	    	
	    	
	    	Mat original = image.clone();
		      
		      //*******************
		      System.out.println("original begin size:" + original.size());
		      
		      
		      
		      // find the center of the image
		      double[] centers = {(double)image.width()/2, (double)image.height()/2};
		      Point image_center = new Point(centers);

		      // finding the contours
		      ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		      Mat hierarchy = new Mat();
		      Imgproc.findContours(image, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		      
		      //System.out.println(hierarchy.size().width);
		     
		      //*********************
		     //System.out.println("hierarchy size:" + hierarchy.size());

		      // Maybe that hierarchy width is the way to solve the empty problem
		      
		  /*   if(hierarchy.size().width < 30){
		    	 System.out.println("0");
		     }
		     else {*/
		    	 // finding best bounding rectangle for a contour whose distance is closer to the image center that other ones
		         double d_min = Double.MAX_VALUE;      
		         Rect rect_min = new Rect();
		         for (MatOfPoint contour : contours) {
		             Rect rec = Imgproc.boundingRect(contour);
		             // find the best candidates
		             if (rec.height > image.height()/2 & rec.width > image.width()/2)            
		                 continue;
		             //detects bottom line
		             Point pt1 = new Point((double)rec.x, (double)rec.y);
		             //detects center
		             Point center = new Point(rec.x+(double)(rec.width)/2, rec.y + (double)(rec.height)/2);
		             double d = Math.sqrt(Math.pow((double)(pt1.x-image_center.x),2) + Math.pow((double)(pt1.y -image_center.y), 2));            
		             if (d < d_min)
		             {
		                 d_min = d;
		                 rect_min = rec;
		             }                   
		         }
		         
		         // slicing the image for result region
		         System.out.println("m cols "+ mat.cols());
		         //pad is the region that you need from the rect_min
		         //bigger pad, bigger rectangle that you could get
		         //pad 6 +20 for rest
		         int pad = 5;        
		         rect_min.x = rect_min.x - pad;
		         rect_min.y = rect_min.y - pad;
		         System.out.println("mins " + rect_min.x + " " + rect_min.y);
		         rect_min.width = rect_min.width + 2*pad;
		         rect_min.height = rect_min.height + 2*pad;
		         System.out.println("rect mins " + rect_min.width + " " + rect_min.height);
		         if(rect_min.x <= 0 || rect_min.y <= 0 || rect_min.x + rect_min.width > mat.cols() || rect_min.y + rect_min.height > mat.rows())
		         {
		        	 System.out.println("greater ");
		        	 pad = 6;
		        	 rect_min.x += 5;
		        	 rect_min.y += 5;
		        	 rect_min.width -= 2*5;
		        	 rect_min.height -= 2*5;
		        	 int space = mat.cols()/5 + 9;
		        	 rect_min.x = rect_min.x - pad+space;
			         rect_min.y = rect_min.y - pad+space;
			         System.out.println("mins " + rect_min.x + " " + rect_min.y);
			         rect_min.width = rect_min.width + 2*pad+space;
			         rect_min.height = rect_min.height + 2*pad+space;
			         System.out.println("rect mins " + rect_min.width + " " + rect_min.height);
		        	 
		         }
		      
		         Mat result = original.submat(rect_min);  
		         displayImage("result",result);
		         //Imgcodecs.imwrite("fixcheckdigit4.jpg", result);
		    // }
		         return result;
	    	
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
