package amigoinn.example.v4accapp;

import android.content.Context;
import android.content.Intent;

public final class Config 
{
	static final String SENDER_ID = "364805738559";
	static final String DISPLAY_MESSAGE_ACTION = "com.example.kauveryadmin.DISPLAY_MESSAGE";
	// File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL = "http://amigoinnovations.co.in/bhargav/calllog.php";
	public static final String Mapkey ="AIzaSyBvoFBSIwXxwsp7QjT95KMY2jhofNekAkY";
    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Insurance Portal";
	public static  int SELECTEDPOSITION = 0;
	public static  String filterfrom = "";
	public static  String FilteredValue = "";
	public static void DisplayMessage(Context context,String message)
	{
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra("Message", message);
		context.sendBroadcast(intent);
	}
}
