package common;

import java.io.*;
import java.net.*;

public class WebPicture 
{
	private static final String TEMP_PATH = "temp.dat";
	
	private URL     url;
	private byte[]  picture;
	private boolean isDownloaded;
	
	public WebPicture(URL url)
	{
		this.url          = url;
		this.isDownloaded = false;
		this.picture      = null;
	}
	
	public URL getUrl()
	{
		return this.url;
	}
	
	public byte[] getPicture() throws IOException
	{
		if(!this.isDownloaded)
		{
			downloadImage();
		}
		
		return this.picture;
	}
	
	public boolean isDownloaded()
	{
		return this.isDownloaded;
	}
	
	public String pictureToFile() throws IOException
	{
		FileOutputStream fileStream = new FileOutputStream(WebPicture.TEMP_PATH);
		
		// byte array to file
		fileStream.write(this.getPicture());
		fileStream.close();
		
		return TEMP_PATH;
	}
	
	private void downloadImage() throws IOException
	{
		InputStream           inputStream  = new BufferedInputStream(this.url.openStream());
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

		for (int i = 0; (i = inputStream.read()) != -1;) 
		{
			byteStream.write(i);
		}
		
		inputStream.close();
		byteStream.close();
		
		System.out.println("[WEB_PICTURE] Download image from " + this.url.toString() + " completed.");
		
		// download ok!
		this.isDownloaded = true;
		this.picture = byteStream.toByteArray();
	}
}
