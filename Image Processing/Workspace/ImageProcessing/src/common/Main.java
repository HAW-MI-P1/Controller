package common;

/*
 * Add OpenCV:
 * http://docs.opencv.org/doc/tutorials/introduction/java_eclipse/java_eclipse.html#java-eclipse
 */

import java.io.File;
import java.io.IOException;

import org.opencv.core.*;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Point[]  locations;
		
		String searchString = "elephant";
		Imshow window       = new Imshow("search");
		Mat    search       = Imgcodecs.imread("test/search1.jpg");
		
		File folder = new File("images/" + searchString);
		
		if(!folder.exists())
		{
			throw new IllegalArgumentException("No images for the search string available.");
		}
		
		if(folder.listFiles().length <= 0)
		{
			throw new IllegalArgumentException("No images for the search string available.");
		}
		
		locations = new Point[folder.listFiles().length];
		
		for(int i = 0; i < folder.listFiles().length; i++)
		{
			System.out.println(folder.listFiles()[i].getPath());
			Mat image = Imgcodecs.imread(folder.listFiles()[i].getPath());

			//Logpolar -> Normalize for scale/rotation
			Mat logPolarSearch = new Mat(search.rows(), search.cols(), CvType.CV_32FC1);
			Mat logPolarImage = new Mat(image.rows(), image.cols(), CvType.CV_32FC1);
			Imgproc.logPolar(search, logPolarSearch, 
					         new Point(logPolarSearch.cols() / 2, logPolarSearch.rows() / 2), 
					         1, Imgproc.CV_WARP_INVERSE_MAP);
			
			Imgproc.logPolar(image, logPolarImage, 
			         new Point(logPolarImage.cols() / 2, logPolarImage.rows() / 2), 
			         1, Imgproc.CV_WARP_INVERSE_MAP);
			
			locations[i] = imageSearch(logPolarSearch, logPolarImage);
			
			if(locations[i] != null)
			{
				Imgproc.rectangle(search, locations[i], new Point(locations[i].x + image.cols(), 
					          	locations[i].y + image.rows()), new Scalar(0, 255, 0));
				
			    System.out.println("Match!");
				break;
			}
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
	}
	
	private static Point imageSearch(Mat source, Mat template)
	{
		//int hits = 0;
		
		//TODO source greater than template -> resize?
		if(source.cols() < template.cols())
		{}
		
		if(source.rows() < template.rows())
		{}
		
		int             cols           = source.cols() - template.cols() + 1;
		int             rows           = source.rows() - template.rows() + 1;
		Mat             result         = new Mat(cols, rows, CvType.CV_32FC1);
		Point           location       = null;
		MinMaxLocResult minMaxLocation = null;
		Mat resultThreshold = new Mat(result.rows(), result.cols(), CvType.CV_32FC1);
		
		// matching, normalize
		Imgproc.matchTemplate(source, template, result, Imgproc.TM_CCOEFF);
		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		Imgproc.threshold(result, resultThreshold, 0.8, 1, 0);
		
		/* TODO Calculate threshold
		System.out.println(template.cols());
		System.out.println(template.rows());
		for (int i = 0; i < resultThreshold.cols(); i++) 
		{
			for (int j = 0; j < resultThreshold.rows(); j++) 
			{
				if((int)resultThreshold.get(j, i)[0] == 1)
				{
					hits++;
				}
				else
				{
					if(hits >= (template.cols() * template.rows()) - 4000)
					{
						System.out.println(hits);
						break;
					}
					
					hits = 0;
				}
			}
		}
		System.out.println(hits);
		*/
		
		// find best match
		minMaxLocation = Core.minMaxLoc(result);
		
		// method = Imgproc.TM_SQDIFF || Imgproc.TM_SQDIFF_NORMED
		// location = minMaxLocation.minLoc;
		// else
		location = minMaxLocation.maxLoc;
		
		return location;
	}
}