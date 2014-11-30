package common;

/*
 * Add OpenCV:
 * http://docs.opencv.org/doc/tutorials/introduction/java_eclipse/java_eclipse.html#java-eclipse
 */

import org.opencv.core.*;
import org.opencv.highgui.Highgui;

public class Main 
{
	public static void main(String[] args) 
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat ele0 = Highgui.imread("ele0.jpg");
		Mat ele1 = Highgui.imread("ele1.jpg");
		Mat ele2 = Highgui.imread("ele2.jpg");
		Mat ele3 = Highgui.imread("ele3.jpg");
		
		System.out.println("ele0 compare ele0: " + imageCompare(ele0, ele0) + "%");
		System.out.println("ele1 compare ele1: " + imageCompare(ele1, ele1) + "%");
		System.out.println("ele0 compare ele1: " + imageCompare(ele0, ele1) + "%");
		System.out.println("ele1 compare ele0: " + imageCompare(ele1, ele0) + "%");
		System.out.println("ele2 compare ele3: " + imageCompare(ele2, ele3) + "%");
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
}