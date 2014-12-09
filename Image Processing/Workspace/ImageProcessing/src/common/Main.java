package common;

/*
 * Add OpenCV:
 * http://docs.opencv.org/doc/tutorials/introduction/java_eclipse/java_eclipse.html#java-eclipse
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.opencv.core.*;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Main 
{
	private static final int RANGE_UNDER = 25;
	private static final int RANGE_OVER  = 25;
	private static int gCount = 0;
	
	public static void main(String[] args) throws IOException 
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Point[]  locations;
		Mat[]    images;
		
		String searchString = "lena";
		Imshow window       = new Imshow("search");
		Mat    search       = Highgui.imread("test/search0.png");
		
		File folder = new File("images/" + searchString);
		
		if(!folder.exists())
		{
			System.out.println("Folder doesnt exist.");
			return;
		}
		
		if(folder.listFiles().length <= 0)
		{
			System.out.println("No files in folder.");
			return;
		}
		
		images    = new Mat[folder.listFiles().length];
		locations = new Point[folder.listFiles().length];
		
		for(int i = 0; i < folder.listFiles().length; i++)
		{
			System.out.println(folder.listFiles()[i].getPath());
			images[i]    = Highgui.imread(folder.listFiles()[i].getPath());
			locations[i] = imageSearch(search, images[i]);
			
			Core.rectangle(search, locations[i], new Point(locations[i].x + images[i].cols(), 
					       locations[i].y + images[i].rows()), new Scalar(0, 255, 0));
		}

		// search lena
		window.showImage(search);
		
		// search elephant		
		//Mat    search1 = Highgui.imread("test/search1.jpg");
		//window.showImage(search);
		
		// search elephant		
		//Mat    search2 = Highgui.imread("test/search2.jpg");
		//window.showImage(search);
		
		// search lion		
		//Mat    search3 = Highgui.imread("test/search3.jpg");
		//window.showImage(search);
		
		// close all...
		System.in.read();
		System.exit(0);
	}
	
	private static Point imageSearch(Mat source, Mat template)
	{
		//TODO source greater than template -> resize?
		//TODO same image type -> convert?
		
	
		if(source.cols() < template.cols())
		{
			//resize
		}
		
		if(source.rows() < template.rows())
		{
			//resize
		}
		
		int             cols           = source.cols() - template.cols() + 1;
		int             rows           = source.rows() - template.rows() + 1;
		Mat             result         = new Mat(cols, rows, CvType.CV_32FC1);
		Point           location       = null;
		MinMaxLocResult minMaxLocation = null;
		
		// matching an normalize
		Imgproc.matchTemplate(source, template, result, Imgproc.TM_CCOEFF);
		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		
		// find best match
		minMaxLocation = Core.minMaxLoc(result);
		
		// method = Imgproc.TM_SQDIFF || Imgproc.TM_SQDIFF_NORMED
		// location = minMaxLocation.minLoc;
		// else
		location = minMaxLocation.maxLoc;
		
		//TODO Check location, valid?
		// calculate percent
		// else return null

		return location;
	}
}