package amigoinn.example.v4accapp;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import amigoinn.db_model.UserInfo;
import amigoinn.walkietalkie.DatabaseHandler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Maulik Patel on 10/15/2015.
 */
public class GcmMessageHandler extends GcmListenerService
{


    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    DatabaseHandler handler;
    public static int count=0;
    @Override
    public void onMessageReceived(String from, Bundle data)
    {
        String message = data.getString("price");

        if (message.contains("http:"))
        {
            try
            {
                handler = new DatabaseHandler(getApplicationContext());

                JSONObject json = new JSONObject(message);
                String title = "V4sales";
                String Message = json.getString("message");
                String image = json.getString("image");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String date1 = dateFormat.format(date);
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMMM");
                String monthname = dateFormat1.format(date);
                handler.addMessage(date1, Message, monthname);
                new generatePictureStyleNotification(this, title, Message, image).execute();
            }

            catch(Exception ex)
            {

            }
        }
        else
        {
            try
            {

                if(message.equalsIgnoreCase("Please update app"))
                {
                    UserInfo.DeleteUser();
                    try
                    {
                       // new updateapk().execute();
                    }catch (Exception ex)
                    {

                    }
                    SharedPreferences preferences=getSharedPreferences("update",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=preferences.edit();
                    edit.clear();
                    String messageSplitted = message.substring(17);
                    try
                    {
                        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        String version = pInfo.versionName;
                        edit.putString("version",messageSplitted);
                    }
                    catch (Exception ex)
                    {

                    }
                    edit.putString("update", "true");
                    edit.commit();
                    createNotification(from, message);
                }
            }
            catch (Exception ex)
            {

            }
            try
            {

                if(message.equalsIgnoreCase("stop app"))
                {
                    try
                    {
                     //   new updateapk().execute();
                    }catch (Exception ex)
                    {

                    }
                    UserInfo.DeleteUser();
                    SharedPreferences preferences=getSharedPreferences("stopapp",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=preferences.edit();
                    edit.clear();
                    try
                    {
//                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//                    String version = pInfo.versionName;
                        edit.putString("stop","true");
                    }
                    catch (Exception ex)
                    {

                    }
//                edit.putString("update","true");
                    edit.commit();
                    message="Your account has been deactivated. please contact support!";
                    createNotification(from, message);
                }
            }
            catch (Exception ex)
            {

            }
            try
            {

                if(message.equalsIgnoreCase("start app"))
                {

                    SharedPreferences preferences=getSharedPreferences("stopapp",Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit=preferences.edit();
                    edit.clear();
                    try
                    {
//                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//                    String version = pInfo.versionName;
                        edit.putString("stop","false");
                    }
                    catch (Exception ex)
                    {

                    }
//                edit.putString("update","true");
                    edit.commit();
                    message="Welcome to Emaxicom.";
                    createNotification(from, message);
                }
            }
            catch (Exception ex)
            {

            }
            try
            {
                String messageSplitted = message.substring(0, 5);
                String messagefinal = message.substring(6);

                String messageSplittedNotice = message.substring(0, 9);
                String messagefinalNotice = message.substring(9);

                handler = new DatabaseHandler(getApplicationContext());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String date1 = dateFormat.format(date);
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMMM");
                String monthname = dateFormat1.format(date);
                handler.addMessage(date1, message, monthname);
                if (messageSplitted.equalsIgnoreCase("alert"))
                {

                    SharedPreferences preferences = getSharedPreferences("notification", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.clear();
                    edit.putString("message", messagefinal);
                    edit.commit();
                    createNotificationAttendence(from, messagefinal);
                } else if (messageSplittedNotice.equalsIgnoreCase("Important")) {

                    SharedPreferences preferences = getSharedPreferences("notification", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.clear();
                    edit.putString("message", messagefinalNotice);
                    edit.commit();
                    createNotificationNotice(from, messagefinalNotice);
                } else
                {
                    createNotification(from, message);
                }
            }catch (Exception ex) {
//            Log.e("Error",ex.toString());
                try {

                    if (!(message.contains("http:"))) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        String date1 = dateFormat.format(date);
                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMMM");
                        String monthname = dateFormat1.format(date);
                        handler.addMessage(date1, message, monthname);
                        createNotification(from, message);
                    }
                } catch (Exception ex1) {

                }
            }
        }


    }

    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap>
    {

        private Context mContext;
        private String title, message, imageUrl;

        public generatePictureStyleNotification(Context context, String title, String message, String imageUrl) {
            super();
            this.mContext = context;
            this.title = title;
            this.message = message;
            this.imageUrl = imageUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            Intent intent = new Intent(mContext, LeftMenusActivity.class);
            intent.putExtra("key", "value");
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 100, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notif = new Notification.Builder(mContext)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLargeIcon(result)
                    .setStyle(new Notification.BigPictureStyle().bigPicture(result))
                    .build();
            notif.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.number=count;



            // Play default notification sound
            notif.defaults |= Notification.DEFAULT_SOUND;
            notif.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(1, notif);
        }
    }
    // Creates notification based on title and body received
    private void createNotification(String title, String body)
    {
        Context context = getBaseContext();
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, body, when);
        //  Notification notification = new Notification().Builder(getApplicationContext());
        String titleMy = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context, LeftMenusActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.putExtra("goto","notification");
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        count++;
        notification.number=count;

        notification.setLatestEventInfo(context, titleMy, body, intent);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.ic_launcher).setContentTitle("P.R.I.S.M")
//                .setContentText(body);
//        NotificationManager mNotificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());

    }
    private void createNotificationAttendence(String title, String body)
    {
        Context context = getBaseContext();
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, body, when);
        //  Notification notification = new Notification().Builder(getApplicationContext());
        String titleMy = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context, AttendenceActivity.class);
        // set intent so it does not start a new activity

//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        count++;
        notification.number=count;

        notification.setLatestEventInfo(context, titleMy, body, intent);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(1, notification);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.ic_launcher).setContentTitle("P.R.I.S.M")
//                .setContentText(body);
//        NotificationManager mNotificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());

    }
    private void createNotificationNotice(String title, String body) {
        Context context = getBaseContext();
        int icon = R.drawable.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, body, when);
        //  Notification notification = new Notification().Builder(getApplicationContext());
        String titleMy = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context, LeftMenusActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.putExtra("goto","notice");

//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        count++;
        notification.number=count;

        notification.setLatestEventInfo(context, titleMy, body, intent);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(2, notification);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.drawable.ic_launcher).setContentTitle("P.R.I.S.M")
//                .setContentText(body);
//        NotificationManager mNotificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());

    }

    public class updateapk extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener
    {
        String path="";
        ProgressDialog dialog;
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            try
            {

                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive" );
                Log.d("Lofting", "About to install new .apk");
                getApplicationContext().startActivity(i);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute()
        {

//            dialog = new ProgressDialog(LoginActivity.this);
//            dialog.setMessage("Please Wait for a moment");
//            dialog.setIndeterminate(true);
//            dialog.setCanceledOnTouchOutside(false);
//            Drawable customDrawable = getApplicationContext().getResources().getDrawable(R.drawable.custom_dialog);
//            dialog.setIndeterminateDrawable(customDrawable);
//            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // TODO Auto-generated method stub
            InputStream is = null;
            //fetchContacts();
            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);

            // String result = "";
            String path = "/sdcard/v4sales.apk";
            try
            {
                String url1= "http://prismeduware.com/democlass/v4sales.apk";
                URL url = new URL(url1);
                URLConnection connection = url.openConnection();
                connection.connect();

                int fileLength = connection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(path);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    publishProgress((int) (total * 100 / fileLength));
//                    output.write(data, 0, count);
//                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                Log.e("YourApp", "Well that didn't work out so well...");
                Log.e("YourApp", e.getMessage());
            }
            return null;
        }

    }
}
