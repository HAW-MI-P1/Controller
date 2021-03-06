package common;

import java.util.*;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.core.Core.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.*;
import org.opencv.utils.Converters;

public class Test 
{
	public static void main(String[] args) 
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat src = new Mat();
		Mat tpl = new Mat();
		
		src = Imgcodecs.imread("test/search0.png");
		tpl = Imgcodecs.imread("images/lena/lena0.png");
		contourDetection(src, tpl);
		
		src = Imgcodecs.imread("test/search0.png");
		tpl = Imgcodecs.imread("images/lion/lion0.png");
		contourDetection(src, tpl);
		
		src = Imgcodecs.imread("test/search0.png");
		tpl = Imgcodecs.imread("images/elephant/elephant0.png");
		contourDetection(src, tpl);
		
		/*
		System.out.println("Lena with Lena");
		test(Imgcodecs.imread("test/search0.png"), Imgcodecs.imread("images/lena/lena0.png"));
		
		System.out.println("Lena with Lion");
		test(Imgcodecs.imread("test/search0.png"), Imgcodecs.imread("images/lion/lion0.png"));
		
		System.out.println("Lena with Elephant");
		test(Imgcodecs.imread("test/search0.png"), Imgcodecs.imread("images/elephant/elephant0.png"));
		
		System.out.println("Lion with Lion");
		//test(Imgcodecs.imread("test/search3.jpg"), Imgcodecs.imread("images/lion/lion0.png"));
		
		System.out.println("Lion with Lion");
		//test(Imgcodecs.imread("test/search3.jpg"), Imgcodecs.imread("images/lion/lion1.png"));
		
		System.out.println("Lion with Lion");
		//test(Imgcodecs.imread("test/search3.jpg"), Imgcodecs.imread("images/lion/lion2.png"));
		
		System.out.println("Lion with Elephant");
		//test(Imgcodecs.imread("test/search3.jpg"), Imgcodecs.imread("images/elephant/elephant0.png"));
		
		System.out.println("Lion with Lena");
		//test(Imgcodecs.imread("test/search3.jpg"), Imgcodecs.imread("images/lena/lena0.png"));*/
	}

	private static void contourDetection(Mat src, Mat tpl)
	{
		Mat srcCanny = new Mat();
		Mat tplCanny = new Mat();;
		List<MatOfPoint> srcContours = new ArrayList<MatOfPoint>();
		List<MatOfPoint> tplContours = new ArrayList<MatOfPoint>();
		Mat srcHierarchy = new Mat();
		Mat tplHierarchy = new Mat();
		
		Imgproc.Canny(src, srcCanny, 100, 200);
		Imgproc.Canny(tpl, tplCanny, 100, 200);
		
		Imgproc.findContours(srcCanny, srcContours, srcHierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
		Imgproc.findContours(tplCanny, tplContours, tplHierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
		
		Mat srcDrawing = Mat.zeros(srcCanny.size(), CvType.CV_8UC3);
		for(int i = 0; i < srcContours.size(); i++)
		{
			Imgproc.drawContours(srcDrawing, srcContours, i, new Scalar(255, 255, 255), 1, 8, srcHierarchy, 0, new Point());
		}
		
		Mat tplDrawing = Mat.zeros(tplCanny.size(), CvType.CV_8UC3);
		for(int i = 0; i < tplContours.size(); i++)
		{
			Imgproc.drawContours(tplDrawing, tplContours, i,  new Scalar(255, 255, 255), 1, 8, tplHierarchy, 0, new Point());
		}
		
		Imshow.show(srcDrawing);
		Imshow.show(tplDrawing);
		
		Mat srcDrawingPolar = new Mat();
		Mat tplDrawingPolar = new Mat();
		Imgproc.logPolar(srcDrawing, srcDrawingPolar, new Point(srcDrawing.cols() * .5, srcDrawing.rows() * .5), 
                30, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_FILL_OUTLIERS);
		Imgproc.logPolar(tplDrawing, tplDrawingPolar, new Point(tplDrawing.cols() * .5, tplDrawing.rows() * .5), 
				30, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_FILL_OUTLIERS);
		
		Imshow.show(srcDrawingPolar);
		Imshow.show(tplDrawingPolar);
		
		Mat matchResult = new Mat();
		MinMaxLocResult minMaxLocation = new MinMaxLocResult();
		Imgproc.matchTemplate(srcDrawingPolar, tplDrawingPolar, matchResult, Imgproc.TM_SQDIFF_NORMED);
		minMaxLocation = Core.minMaxLoc(matchResult);
		
		System.out.println(minMaxLocation.minVal);
		System.out.println(minMaxLocation.maxVal);
		//Core.normalize(matchResult, matchResult, 0, 1, Core.NORM_MINMAX, -1, new Mat());
	}
	
	private static void test(Mat src, Mat tpl) 
	{
		//Imshow.show(src);
		//Imshow.show(tpl);
		
		// generate log polar images
		Mat srcPolar = new Mat(src.rows(), src.cols(), src.type());
		Mat tplPolar = new Mat(tpl.rows(), tpl.cols(), tpl.type());
		Mat srcCart  = new Mat(src.rows(), src.cols(), src.type());
		Mat tplCart  = new Mat(tpl.rows(), tpl.cols(), tpl.type());
		
		Imgproc.logPolar(src, srcPolar, new Point(src.cols() * .5, src.rows() * .5), 
                30, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_FILL_OUTLIERS);
		Imgproc.logPolar(tpl, tplPolar, new Point(tpl.cols() * .5, tpl.rows() * .5), 
				30, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_FILL_OUTLIERS);
		
		//Imshow.show(srcPolar);
		//Imshow.show(tplPolar);
		
		///////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////
		/*
		FeatureDetector     surf      = FeatureDetector.create(FeatureDetector.FAST);
		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		DescriptorMatcher   matcher   = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		
		MatOfKeyPoint keyPointsSource       = new MatOfKeyPoint();
		MatOfKeyPoint keyPointsTemplate     = new MatOfKeyPoint();
		Mat           descriptorSource      = new Mat();
		Mat           descriptorTemplate    = new Mat();
	    MatOfDMatch   matchesSourceTemplate = new MatOfDMatch();
		
		surf.detect(srcPolar, keyPointsSource);
		surf.detect(tplPolar, keyPointsTemplate);
		
		extractor.compute(srcPolar, keyPointsSource, descriptorSource);
		extractor.compute(tplPolar, keyPointsTemplate, descriptorTemplate);
		
		matcher.match(descriptorSource, descriptorTemplate, matchesSourceTemplate);
		
		// reduce points...
		int minDistance = 100;
		int maxDistance = 0;
		
		List<DMatch> dMatches = matchesSourceTemplate.toList();
		
		for( int i = 0; i < dMatches.size(); i++)
		{ 
			float distance = dMatches.get(i).distance;
			
			if( distance < minDistance )
			{
				minDistance = (int)distance;
			}
			
			if( distance > maxDistance )
			{
				maxDistance = (int)distance;
			}
		}
		
		System.out.println("Max distance: " + maxDistance);
		System.out.println("Min distance: " + minDistance);
		
		List<DMatch> goodMatchesList = new ArrayList<DMatch>();
        double upperBound = 4.5 * minDistance;
        
        for (int i = 0; i < dMatches.size(); i++) 
        {
            if (dMatches.get(i).distance < upperBound) 
            {
                goodMatchesList.add(dMatches.get(i));
            }
        }
        
        MatOfDMatch goodMatches = new MatOfDMatch();
        goodMatches.fromList(goodMatchesList); 
		
		Mat output = new Mat();
		Features2d.drawMatches(srcPolar, keyPointsSource, tplPolar, keyPointsTemplate, goodMatches, output, 
				Scalar.all(-1), Scalar.all(-1), new MatOfByte(), Features2d.NOT_DRAW_SINGLE_POINTS);
		
		System.out.println(keyPointsSource.size());
		System.out.println(keyPointsTemplate.size());
		//System.out.println(goodMatches.dump());
		System.out.println(goodMatches.size());
		
		
		//next step...
		//List<Ma> object = new ArrayList<Point>();
		//List<Point> scene = new ArrayList<Point>();
		
		/*MatOfPoint2f object = new MatOfPoint2f();
		MatOfPoint2f scene = new MatOfPoint2f();
		List<KeyPoint> kpsL = keyPointsSource.toList();
		List<KeyPoint> kptL = keyPointsTemplate.toList();
		
		
		for( int i = 0; i < goodMatchesList.size(); i++ )
		{
			//-- Get the keypoints from the good matches
			object.push_back(kpsL.get(goodMatchesList.get(i).queryIdx).pt);
			scene.push_back(kptL.get(goodMatchesList.get(i).trainIdx).pt);
			
		}
		
		Mat h = Calib3d.findHomography(object, scene);
		
		/*Features2d.drawKeypoints(source, keyPointsSource, output, Scalar.all(-1), 
				                  Features2d.NOT_DRAW_SINGLE_POINTS);*
		
		Imshow.show(output);
		*/
		///////////////////////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////
		
		Imgproc.logPolar(srcPolar, srcCart, new Point(src.cols() * .5, src.rows() * .5), 
				30, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_INVERSE_MAP);
		Imgproc.logPolar(tplPolar, tplCart, new Point(tpl.cols() * .5, tpl.rows() * .5), 
				30, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_INVERSE_MAP);
		
		//Imshow.show(srcPolar);
		//Imshow.show(tplPolar);
		//Imshow.show(srcCart);
		//Imshow.show(tplCart);
		
		// check for template matching
		Mat matchResult = new Mat();
		Mat matchResultCart = new Mat();
		Imgproc.matchTemplate(srcPolar, tplPolar, matchResult, Imgproc.TM_SQDIFF_NORMED);
		//Core.normalize(matchResult, matchResult, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		
		/*
		Imgproc.logPolar(matchResult, matchResultCart, new Point(matchResult.cols() * .5, matchResult.rows() * .5), 
				30, Imgproc.INTER_LINEAR + Imgproc.CV_WARP_INVERSE_MAP);
		Core.normalize(matchResultCart, matchResultCart, 0, 255, Core.NORM_MINMAX, -1, new Mat());
		
		Mat matchResultCartShow = new Mat();
		matchResultCart.convertTo(matchResultCartShow, src.type());
		//Imshow.show(matchResultCartShow);
		
		MinMaxLocResult minMaxLocation = Core.minMaxLoc(matchResultCartShow);
		Point location = minMaxLocation.maxLoc;
		
		Imgproc.rectangle(matchResultCartShow, location, new Point(location.x + tpl.cols(), 
			      location.y + tpl.rows()), new Scalar(255, 255, 255));
		Imshow.show(matchResultCartShow);
		
		Imgproc.rectangle(src, location, new Point(location.x + tpl.cols(), 
			      location.y + tpl.rows()), new Scalar(255, 255, 255));
		Imshow.show(src);*/
		
		
		//generate binary view
		
		Mat threshold = new Mat();
		Mat thresholdShow = new Mat();
		
		
		// classify and rate the result
		MinMaxLocResult minMaxLocation = Core.minMaxLoc(matchResult);
		Imgproc.threshold(matchResult, threshold, (minMaxLocation.minVal + minMaxLocation.maxVal) / 2, 255, 0);
		threshold.convertTo(thresholdShow, src.type());
		
		//System.out.println(thresholdShow);
		//Imshow.show(thresholdShow);
		
		//System.out.printf("%f \n", minMaxLocation.minVal);
		System.out.printf("%15.2f \n", minMaxLocation.minVal);
		System.out.printf("%15.2f \n", minMaxLocation.maxVal);
		System.out.printf("%15.2f \n", (double)(matchResult.rows() * matchResult.cols()));
		
		Point location = minMaxLocation.maxLoc;
		
		//System.out.println(minMaxLocation.maxLoc);
		Imgproc.rectangle(thresholdShow, location, new Point(location.x + tpl.cols(), 
			      location.y + tpl.rows()), new Scalar(0, 255, 0));
		//Imshow.show(thresholdShow);
		
		Mat cut = new Mat(tpl.rows(), tpl.cols(), thresholdShow.type());
		
		// calculate safe width and height
		int width  = ((int)location.x + tpl.cols() > thresholdShow.cols() ? thresholdShow.cols() - (int)location.x : tpl.cols());
		int height = ((int)location.y + tpl.rows() > thresholdShow.rows() ? thresholdShow.rows() - (int)location.y : tpl.rows());
		Rect rect = new Rect((int)location.x, (int)location.y, width, height);
		
		cut = thresholdShow.submat(rect);
		//Imshow.show(cut);
		
		int white = 0;
		double percentWhite = 100;
		
		for (int i = 0; i < cut.cols(); i++) 
		{
			for (int j = 0; j < cut.rows(); j++) 
			{
				if((int)cut.get(j, i)[0] == 255)
				{
					white++;
				}
			}
		}
		
		percentWhite = (100.0 / ((double)cut.cols() * (double)cut.rows())) * (double)white;
		//System.out.println(percentWhite);
		//System.out.println();
	}
}