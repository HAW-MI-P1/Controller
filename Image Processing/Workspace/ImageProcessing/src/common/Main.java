package common;

/*
 * Add OpenCV:
 * http://docs.opencv.org/doc/tutorials/introduction/java_eclipse/java_eclipse.html#java-eclipse
 */

import java.util.Arrays;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;

public class Main 
{
	private static final int RANGE_UNDER = 25;
	private static final int RANGE_OVER  = 25;
	private static int gCount = 0;
	
	public static void main(String[] args) 
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat test        = Highgui.imread("test.png");
		Mat testSection = Highgui.imread("test_section.png");
		//Mat ele0        = Highgui.imread("ele0.jpg");
		//Mat ele0Section = Highgui.imread("ele0_section.jpg");
		//Mat ele1        = Highgui.imread("ele1.jpg");
		//Mat ele1Section = Highgui.imread("ele1_section.jpg");
		//Mat ele2 = Highgui.imread("ele2.jpg");
		//Mat ele3 = Highgui.imread("ele3.jpg");
		
		//System.out.println("ele0 compare ele0: " + imageCompare(ele0, ele0) + " %");
		//System.out.println("ele1 compare ele1: " + imageCompare(ele1, ele1) + " %");
		//System.out.println("ele0 compare ele1: " + imageCompare(ele0, ele1) + " %");
		//System.out.println("ele1 compare ele0: " + imageCompare(ele1, ele0) + " %");
		//System.out.println("ele2 compare ele3: " + imageCompare(ele2, ele3) + " %");
		
		System.out.println("test compare testSection: " + imageSearch(test, testSection) + " %");
		//System.out.println("ele0 compare ele0Section: " + imageSearch(ele0, ele0Section) + " %");
		//System.out.println("ele0 compare ele1Section: " + imageSearch(ele0, ele1Section) + " %");
		//System.out.println("ele1 compare ele0Section: " + imageSearch(ele1, ele0Section) + " %");
		//System.out.println("ele1 compare ele1Section: " + imageSearch(ele1, ele1Section) + " %");
	}
	
	/*
	 * Must be same size
	 * TODO: Different size
	 * TODO: Different position
	 */
	private static int imageCompare(Mat image0, Mat image1)
	{
		int hits = 0;
		
		if(image0.rows() != image1.rows() || image0.cols() != image1.cols())
		{
			return -1;
		}
		
		for (int i = 0; i < image0.rows(); i++) 
		{
			for (int j = 0; j < image0.cols(); j++) 
			{
				if((int)image0.get(i, j)[0] == (int)image1.get(i, j)[0])
				{
					hits++;
				}
			}
		}
		
		return (int)(((double)hits / (image0.rows() * image0.cols())) * 100);
	}
	
	/*
	 * Must be same size
	 * TODO: Different size
	 */
	private static int imageSearch(Mat image, Mat section)
	{
		int lineSection = 0;
		boolean linesFound[] = new boolean[image.rows()];
		
		Arrays.fill(linesFound, false);

		for(int lineImage = 0; lineImage < image.rows(); lineImage++)
		{
			if(foundLine(image.row(lineImage), section.row(lineSection)))
			{
				linesFound[lineImage] = true;
				lineSection++;
			}
			else
			{
				lineSection = 0;
			}
			
			if(lineSection > section.rows())
			{
				break;
			}
		}
		
		//analyze array....
		
		return 0;
	}
	
	private static boolean foundLine(Mat image, Mat section)
	{
		int hits        = 0;
		//int sectionCell = 0;
		
		/*
		 * TODO: check line 
		 * TODO: begin at 0 and check search line
		 *       => not found begin next pixel and check search line
		 *       => exp.
		 */
		
		gCount++;
		
		if(gCount >= 10)
		{
			gCount = gCount + 1 - 1;
		}
		
		for(int imageCell = 0; imageCell < image.cols(); imageCell++)
		{
			int before = imageCell;
			
			hits = 0;
			
			for(int sectionCell = 0; sectionCell < section.cols(); sectionCell++)
			{
				int imageBlue    = (int)image.get(0,  imageCell)[0];
				int imageGreen   = (int)image.get(0,  imageCell)[1];
				int imageRed     = (int)image.get(0,  imageCell)[2];
				int sectionBlue  = (int)section.get(0, sectionCell)[0];
				int sectionGreen = (int)section.get(0, sectionCell)[1];
				int sectionRed   = (int)section.get(0, sectionCell)[2];
				
				if(sectionRed   != imageRed   ||
				   sectionGreen != imageGreen ||
				   sectionBlue  != imageBlue)
				{
					imageCell = before;
					break;
				}
				
				imageCell++;
				hits++;
			}
			
			imageCell = before;
			
			if(hits == section.cols())
			{
				break;
			}
		}
		
		/*System.out.println(image.dump());
		System.out.println(section.dump());
		System.out.println();
		
		int cols = image.cols();
		
		for(int imageCell = 0; imageCell < image.cols(); imageCell++)
		{
			int imageRed     = (int)image.get(0,  imageCell)[0];
			int imageGreen   = (int)image.get(0,  imageCell)[1];
			int imageBlue    = (int)image.get(0,  imageCell)[2];
			int sectionRed   = (int)section.get(0, sectionCell)[0];
			int sectionGreen = (int)section.get(0, sectionCell)[1];
			int sectionBlue  = (int)section.get(0, sectionCell)[2];
			
			
			
			//if(sectionRed   >= imageRed   - RANGE_UNDER && sectionRed   <= imageRed   + RANGE_OVER &&
			//   sectionGreen >= imageGreen - RANGE_UNDER && sectionGreen <= imageGreen + RANGE_OVER &&
			//   sectionBlue  >= imageBlue  - RANGE_UNDER && sectionBlue  <= imageBlue  + RANGE_OVER)
			if(sectionRed   == imageRed   &&
			   sectionGreen == imageGreen &&
			   sectionBlue  == imageBlue)
			{
				sectionCell++;
				hits++;
				
				if(hits == section.cols())
				{
					break;
				}
			}
			else
			{
				sectionCell = 0;
				hits        = 0;
			}
		}*/
		
		return false;//(hits == section.cols());
	}
}