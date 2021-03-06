package common;

/*
 * Add OpenCV:
 * http://docs.opencv.org/doc/tutorials/introduction/java_eclipse/java_eclipse.html#java-eclipse
 */

import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opencv.core.*;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//TODO Loop for persons, inner loop images of persons
		/*List matchedPersons = new ArrayList<Person>();
		
		// person loop
		for (Person person : result) 
		{
			// image loop
			for (URL url : person.images()) 
			{
				
			}
		}*/
		
		//////////////////////////////////
		// BEGIN Test 1: lion on lion picture
		//////////////////////////////////
		String searchString = "lion";
		URL    url          = new URL("http://gowild.wwf.org.uk/wp-content/uploads/factfiles_lion_01.jpg");
		try
		{
			System.out.println("[IMAGE_DECODER] Search for: "           + searchString);
			System.out.println("[IMAGE_DECODER] Found enaugh matches: " + foundObjectInImage(downloadImage(url), searchString));
		}
		catch(IOException e)
		{
			System.out.println("[IMAGE_DECODER] Source imgae cant be readed from the internet.");
			throw new IllegalArgumentException("[IMAGE_DECODER]  Source imgae cant be readed from the internet.");
		}
		System.out.println();
		//////////////////////////////////
		// END Test 1: lion on lion picture
		//////////////////////////////////
		
		
		
		//////////////////////////////////
		// BEGIN Test 2: lena on car picture
		//////////////////////////////////
		searchString = "lena";
		url = new URL("http://www.sixt.com/uploads/pics/mercedes_slk-sixt_rent_a_car.png");
		try
		{
			System.out.println("[IMAGE_DECODER] Search for: "           + searchString);
			System.out.println("[IMAGE_DECODER] Found enaugh matches: " + foundObjectInImage(downloadImage(url), searchString));
		}
		catch(IOException e)
		{
			System.out.println("[IMAGE_DECODER] Source imgae cant be readed from the internet.");
			throw new IllegalArgumentException("[IMAGE_DECODER]  Source imgae cant be readed from the internet.");
		}
		System.out.println();
		//////////////////////////////////
		// END Test 2: lena on car picture
		//////////////////////////////////
		
		
		
		//////////////////////////////////
		// BEGIN Test 3: lena on lena picture
		//////////////////////////////////
		searchString = "lena";
		url = new URL("http://www.cs.cmu.edu/~chuck/lennapg/len_std.jpg");
		try
		{
			System.out.println("[IMAGE_DECODER] Search for: "           + searchString);
			System.out.println("[IMAGE_DECODER] Found enaugh matches: " + foundObjectInImage(downloadImage(url), searchString));
		}
		catch(IOException e)
		{
			System.out.println("[IMAGE_DECODER] Source imgae cant be readed from the internet.");
			throw new IllegalArgumentException("[IMAGE_DECODER]  Source imgae cant be readed from the internet.");
		}
		System.out.println();
		//////////////////////////////////
		// END Test 3: lena on lena picture
		//////////////////////////////////
		
		
		
		//////////////////////////////////
		// BEGIN Test 4: elephant on lena picture
		//////////////////////////////////
		searchString = "elephant";
		url = new URL("http://www.cs.cmu.edu/~chuck/lennapg/len_std.jpg");
		try
		{
			System.out.println("[IMAGE_DECODER] Search for: "           + searchString);
			System.out.println("[IMAGE_DECODER] Found enaugh matches: " + foundObjectInImage(downloadImage(url), searchString));
		}
		catch(IOException e)
		{
			System.out.println("[IMAGE_DECODER] Source imgae cant be readed from the internet.");
			throw new IllegalArgumentException("[IMAGE_DECODER]  Source imgae cant be readed from the internet.");
		}
		//////////////////////////////////
		// END Test 4: elephant on lena picture
		//////////////////////////////////
	}
	
	// check for matches
	private static boolean foundObjectInImage(String path, String object)
	{
		Point[]  locations;
		File     folder    = new File("images/" + object);
		Mat      source    = Imgcodecs.imread(path);
		
		if(source.empty())
		{
			System.out.println("[IMAGE_DECODER] Empty source image.");
			throw new IllegalArgumentException("[IMAGE_DECODER] Empty source image.");
		}
		
		if(!folder.exists())
		{
			System.out.println("[IMAGE_DECODER] No images for the search string available.");
			throw new IllegalArgumentException("[IMAGE_DECODER] No images for the search string available.");
		}
		
		if(folder.listFiles().length <= 0)
		{
			System.out.println("[IMAGE_DECODER] No images for the search string available.");
			throw new IllegalArgumentException("[IMAGE_DECODER] No images for the search string available.");
		}
		
		locations = new Point[folder.listFiles().length];
		
		for(int i = 0; i < folder.listFiles().length; i++)
		{
			System.out.println("[IMAGE_DECODER] " + folder.listFiles()[i].getPath());
			Mat template = Imgcodecs.imread(folder.listFiles()[i].getPath());
			
			if(template.empty())
			{
				System.out.println("[IMAGE_DECODER] Empty template image.");
				throw new IllegalArgumentException("[IMAGE_DECODER] Empty template image.");
			}
			
			locations[i] = imageMatching(source, template);
		}
		
		// check match count
		int    matchCount          = 0;
		double matchCountInPercent = 0.0;
		for (int i = 0; i < locations.length; i++) 
		{
			if(locations[i] != null)
			{
				matchCount++;
			}
		}
		
		matchCountInPercent = (100.0 / (double)folder.listFiles().length) * matchCount;
		System.out.println("[IMAGE_DECODER] " + matchCountInPercent + " % matches (as count: " + matchCount + ")");
		
		if(matchCountInPercent >= 70)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// return location when something was found and it was a good match, else null
	private static Point imageMatching(Mat source, Mat template)
	{
		//TODO source greater than template -> resize?
		if(source.cols() < template.cols())
		{}
		
		if(source.rows() < template.rows())
		{}
		
		int             cols             = source.cols() - template.cols() + 1;
		int             rows             = source.rows() - template.rows() + 1;
		Mat             result           = new Mat(cols, rows, CvType.CV_32FC1);
		Point           location         = null;
		MinMaxLocResult minMaxLocation   = null;
		Mat             resultThreshold  = new Mat(result.rows(),   result.cols(),   CvType.CV_32FC1);
		Mat             logPolarSource   = new Mat(source.rows(),   source.cols(),   CvType.CV_32FC1);
		Mat             logPolarTemplate = new Mat(template.rows(), template.cols(), CvType.CV_32FC1);
		
		// transform data to polar coordinates
		Imgproc.logPolar(source, logPolarSource, 
		                 new Point(logPolarSource.cols() / 2, logPolarSource.rows() / 2), 
		                 0.1, Imgproc.CV_WARP_INVERSE_MAP + Imgproc.CV_WARP_FILL_OUTLIERS);

        Imgproc.logPolar(template, logPolarTemplate, 
                         new Point(logPolarTemplate.cols() / 2, logPolarTemplate.rows() / 2), 
                         0.1, Imgproc.CV_WARP_INVERSE_MAP + Imgproc.CV_WARP_FILL_OUTLIERS);
		
		// matching, normalize
		Imgproc.matchTemplate(logPolarSource, logPolarTemplate, result, Imgproc.TM_CCOEFF);
		Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		Imgproc.threshold(result, resultThreshold, 0.8, 255, 0);

		// find best match
		minMaxLocation = Core.minMaxLoc(result);
		
		// method = Imgproc.TM_SQDIFF || Imgproc.TM_SQDIFF_NORMED
		// location = minMaxLocation.minLoc;
		// else
		location = minMaxLocation.maxLoc;

		// rate match
		int white = 0;
		double percentWhite = 100;
		
		for (int i = 0; i < resultThreshold.cols(); i++) 
		{
			for (int j = 0; j < resultThreshold.rows(); j++) 
			{
				if((int)resultThreshold.get(j, i)[0] == 255)
				{
					white++;
				}
			}
		}
		
		percentWhite = (100.0 / ((double)resultThreshold.cols() * (double)resultThreshold.rows())) * (double)white;

		// Complete image dump?
		//Imgcodecs.imwrite("source.jpg",           source);
		//Imgcodecs.imwrite("template.jpg",         template);
		//Imgcodecs.imwrite("logPolarSource.jpg",   logPolarSource);
		//Imgcodecs.imwrite("logPolarTemplate.jpg", logPolarTemplate);
		//Imgcodecs.imwrite("resultThreshold.jpg",  resultThreshold);
		
		//System.out.println(percentWhite);
		
		if(percentWhite > 5 || percentWhite <= 0.0001)
		{
			return null;
		}
		
		/*// Marked image
		Imgproc.rectangle(source, location, new Point(location.x + template.cols(), 
					      location.y + template.rows()), new Scalar(0, 255, 0));
		Imgcodecs.imwrite("markedMatch.jpg",  source);
		*/
		
		return location;
	}
	
	//Download an image an save the image as file on your harddisk
	private static String downloadImage(URL url) throws IOException
	{
		InputStream  inputStream  = new BufferedInputStream(url.openStream());
		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("temp.dat"));

		for (int i = 0; (i = inputStream.read()) != -1;) 
		{
			outputStream.write(i);
		}
		
		inputStream.close();
		outputStream.close();
		
		return "temp.dat";
	}
}