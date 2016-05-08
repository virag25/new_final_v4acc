package amigoinn.example.v4sales;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



import amigoinn.db_model.LoginInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.UserInfo;
import amigoinn.servicehelper.ApiHandler;
import amigoinn.walkietalkie.DatabaseHandler1;

public class LoginActivity extends BaseActivity  {
    FloatLabeledEditText loginText, passText;
    RobotoTextView btnLogin, btnRegister;
    int myIp;
    int intMyIp3;
    int intMyIp3mod;
    public ProgressDialog mProgressDialog;
    String myipfinal;
    int intMyIp2;
    int intMyIp2mod;
    int length = 0;
    String result = "";
    String results = "";
    ProgressDialog dialog;
    ArrayList<String> list;
    JSONObject json_data;
    int intMyIp1;
    int intMyIp0;
    static String imeiNo;
    JSONArray jArray;
    String str = "";
    String email;
    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            setContentView(R.layout.activity_login_page_light);
            loginText = (FloatLabeledEditText) findViewById(R.id.user);
            passText = (FloatLabeledEditText) findViewById(R.id.Password);
            btnLogin = (RobotoTextView) findViewById(R.id.login);
            loginText.setText("1");
            passText.setText("145628");

            btnLogin.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        String login_text = loginText.getText().toString();
                        String password = passText.getText().toString();
                        boolean flag = true;
                        String msg = "";
                        SharedPreferences preferencesStop = getSharedPreferences("stopapp", Context.MODE_PRIVATE);
                        String stopapp=preferencesStop.getString("stop","false");
                        if (login_text != null && login_text.length() == 0)
                        {
                            flag = false;
                            msg = "Pleasae add your username";
                        } else if (password != null && password.length() == 0) {
                            flag = false;
                            msg = "Please add your password";
                        }
                        if (stopapp.equalsIgnoreCase("true") )
                        {
                            flag = false;
                            msg = "Please contact support";
                        }



                        if (flag)
                        {
                            showProgress();
                            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            String version = pInfo.versionName;
                            LoginInfo.Instance().email = login_text;
                            LoginInfo.Instance().password = password;
                            LoginInfo.Instance().versionname = version;
                            LoginInfo.Instance().doLogin(new ModelDelegates.LoginDelegate()
                            {
                                @Override
                                public void LoginDidSuccess()
                                {
                                    hideProgress();
                                    try
                                    {
                                        if (UserInfo.getUser().updatestatus.equalsIgnoreCase("false"))
                                        {
                                            SharedPreferences preferences = getSharedPreferences("update", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor edit = preferences.edit();
                                            String updateapp = preferences.getString("update", "false");

                                            edit.clear();
                                            edit.putString("update", "false");
                                            edit.commit();

                                            Intent in = new Intent(LoginActivity.this, LeftMenusActivity.class);
                                            startActivity(in);
                                            finish();
                                        }
                                        else
                                        {
                                            new updateapk().execute("http://prismeduware.com/democlass/amigoinn.example.v4sales.apk");
                                        }
                                    }
                                    catch (Exception ex)

                                    {
                                        Log.e("Error",ex.toString());
                                    }
                                }

                                @Override
                                public void LoginFailedWithError(String error) {
                                    hideProgress();
                                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        Log.e("error", ex.toString());
                    }
                }
            });
//		btnRegister=(RobotoTextView)findViewById(R.id.register);

            MulticastLock mLock;
//		    WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
//		   // d=myWifiManager.getDhcpInfo();
//		    //mLock = myWifiManager.createMulticastLock("lock");
//		    //mLock.acquire();
//		    WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
//		    //gets the Ip address in hex form, We need to convert it to integer and then finally to string to display it
//		 //   String myip = Formatter.formatIpAddress(myWifiManager.getConnectionInfo().getIpAddress());
//		    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//			imeiNo=telephonyManager.getDeviceId();
//
//
//		     myIp = myWifiInfo.getIpAddress();
//		     intMyIp3 = myIp/0x1000000;
//		     intMyIp3mod = myIp%0x1000000;
//
//		     intMyIp2 = intMyIp3mod/0x10000;
//		     intMyIp2mod = intMyIp3mod%0x10000;
//
//		     intMyIp1 = intMyIp2mod/0x100;
//		     intMyIp0 = intMyIp2mod%0x100;
//		     myipfinal=intMyIp0+"."+intMyIp1+"."+intMyIp2+"."+intMyIp3;
            //passText = (EditText) findViewById(R.id.user);
            SharedPreferences preferencesStop = getSharedPreferences("stopapp", Context.MODE_PRIVATE);
            String stopapp=preferencesStop.getString("stop","false");
            if(UserInfo.getUser()!=null)
            {
                SharedPreferences preferences=getSharedPreferences("update",Context.MODE_PRIVATE);
                //		String updateapp=preferences.getString("update","false");
//				SharedPreferences preferences=getSharedPreferences("update",Context.MODE_PRIVATE);
//				String updateapp=preferences.getString("update","false");
                String version = "1.1";
                try
                {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    version = pInfo.versionName;
                    //version="1.1";
                    if((version.equalsIgnoreCase(preferences.getString("version",version))))
                    {
                        SharedPreferences.Editor edit=preferences.edit();

                        edit.clear();
                        edit.commit();
                    }
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    e.printStackTrace();
                }
                //pInfo.versionName;
                String updateapp=preferences.getString("update","false");
                if(updateapp.equalsIgnoreCase("false"))
                {
                    if(stopapp.equalsIgnoreCase("false"))
                    {
                        Intent in = new Intent(LoginActivity.this, LeftMenusActivity.class);
                        startActivity(in);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Your account has been deactivated. please contact support!",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                 //   Utils.showCustomDialog("App Update", "Please update app", LoginActivity.this);
                }
//                Intent in = new Intent(LoginActivity.this, LeftMenusActivity.class);
//                startActivity(in);
//                finish();
            }

//            Intent start = new Intent(LoginActivity.this, LeftMenusActivity.class);
//            startActivity(start);
//            finish();

            Typeface sRobotoThin = Typeface.createFromAsset(getAssets(),
                    "fonts/Roboto-Thin.ttf");
            ;
            SharedPreferences preference = getSharedPreferences("profile", Context.MODE_PRIVATE);
            //SharedPreferences preference=getSharedPreferences("Profile",Context.MODE_PRIVATE);
            String ip = preference.getString("id", "abc");
            if (!(ip.equalsIgnoreCase("abc"))) {
                Intent in = new Intent(LoginActivity.this, LeftMenusActivity.class);
                startActivity(in);
                finish();
            }
//		String name=preference.getString("name","");
//		String logged=preference.getString("logged", "abc");
            Pattern emailPatter = Patterns.EMAIL_ADDRESS; // API level 8+
//		Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
//		for (Account account : accounts) {
//		    if (emailPatter.matcher(account.name).matches()) {
//		        email = account.name;
//
//		    }
//		}
//		if(!name.isEmpty())
//		{
//			loginText.setText(name);
//		}
//		else
//		{
//			loginText.setHint("Username");
//		}
//		if(!ip.equalsIgnoreCase(myipfinal))
//		{
//			Toast.makeText(getApplicationContext(), "connect to Network!", Toast.LENGTH_LONG).show();
//		}
            //loginText.setTypeface(sRobotoThin);
            //passText.setTypeface(sRobotoThin);
//		if(!logged.equalsIgnoreCase("abc"))
//		{
//			new AsynchTaskEx().execute();
//		}

//            btnLogin.setOnClickListener(this);
            //btnRegister.setOnClickListener(this);
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
//        case
        }
        return super.onOptionsItemSelected(item);
    }

    public class AsynchTaskEx extends AsyncTask<Void, Void, Void> implements OnItemClickListener
    {


        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            try {
                dialog.dismiss();


                if (length == 7)
                {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(LoginActivity.this);

                    dlgAlert.setMessage("You don't have access to app!");
                    dlgAlert.setTitle("Login Error");
                    dlgAlert.setPositiveButton("Okay", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                }
                else if (length < 7)
                {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }
                else if (length == 8)
                {

                    SharedPreferences preference = getSharedPreferences("Profile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = preference.edit();
                    edit.clear();
                    String name = loginText.getText().toString();
                    edit.putString("name", name);
                    edit.putString("myIp", myipfinal);
                    edit.putString("logged", "logged");
                    edit.commit();

                    Intent in = new Intent(LoginActivity.this, LeftMenusActivity.class);
                    startActivity(in);
                    finish();
                }
                else
                {
                    //Toast.makeText(getApplicationContext(), text, duration)
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //uiUpdate.setText("Output : ");
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Please Wait for a moment");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            //   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
            Drawable customDrawable = getApplicationContext().getResources().getDrawable(R.drawable.custom_dialog);

            // set the drawable as progress drawable
//
            dialog.setIndeterminateDrawable(customDrawable);

            dialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            InputStream is = null;
            // fetchContacts();
            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);
            String langi = "23.0481352";
            String latit = "72.5540532";
            // String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                String url = "http://amigoinnovations.co.in/wifiwalkie/login.php?imei=" + imeiNo + "&email=" + loginText.getText().toString();
                HttpPost httppost = new HttpPost("http://amigoinnovations.co.in/wifiwalkie/login.php?imei=" + imeiNo + "&email=" + email);
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //httppost.setEntity(new UrlEncodedFormEntity(arra));
                       /* httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");*/
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();


            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
			            /*Toast.makeText(getApplicationContext(),"Please connect to Internet",
			        	          Toast.LENGTH_LONG).show();*/
            }


            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                length = result.length();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }
            //parse json data
            if (length == 9) {
                try {
                    jArray = new JSONArray(result);
                    list = new ArrayList<String>();

                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            //   list.add(jArray.get(i).toString());
                            //mStringArray = new String[list.size()];
                            //mStringArray = list.toArray(mStringArray);


                        }

                    }
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
			            /*Toast.makeText(getApplicationContext(),"NoData",
			        	          Toast.LENGTH_LONG).show();
*/
                }
            }
            return null;
        }

    }

    public class LoginUser extends AsyncTask<Void, Void, Void> implements OnItemClickListener
    {


        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(Void result)
        {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            try {

                dialog.dismiss();
//	      if(length==7)
//			{
//				AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this);
//
//	            dlgAlert.setMessage("Invalid Email ID or Password!");
//	            dlgAlert.setTitle("Login Error");
//	            dlgAlert.setPositiveButton("Okay", null);
//	            dlgAlert.setCancelable(true);
//	            dlgAlert.create().show();
//			}
//		  else if(length<7)
//		  {
//			  Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
//		  }
//		  else
//		  {
                SharedPreferences preference = getSharedPreferences("profile", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preference.edit();
                edit.clear();
                edit.putString("id", "12");
                edit.commit();
                Toast.makeText(getApplicationContext(), "Successfully Registered!", Toast.LENGTH_LONG).show();
                Intent in = new Intent(getApplicationContext(), LeftMenusActivity.class);
                startActivity(in);
                finish();
                //Toast.makeText(getApplicationContext(), text, duration)
//		  }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //uiUpdate.setText("Output : ");
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Please Wait for a moment");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            //   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
            Drawable customDrawable = getApplicationContext().getResources().getDrawable(R.drawable.custom_dialog);

            // set the drawable as progress drawable
//
            dialog.setIndeterminateDrawable(customDrawable);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            InputStream is = null;
            //fetchContacts();
            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);
            String langi = "23.0481352";
            String latit = "72.5540532";
            // String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.prismeduware.com/parentapp/login.php?email=" + loginText.getText().toString() + "&password=" + passText.getText().toString());
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //httppost.setEntity(new UrlEncodedFormEntity(arra));
			            /* httppost.setHeader("Accept", "application/json");
			            httppost.setHeader("Content-type", "application/json");*/
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();


            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
			            /*Toast.makeText(getApplicationContext(),"Please connect to Internet",
			        	          Toast.LENGTH_LONG).show();*/
            }


            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                length = result.length();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }
            //parse json data
            if (length > 15) {
                try {
                    jArray = new JSONArray(result);
                    list = new ArrayList<String>();

                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            //String id=json_data.getString("parent_id");
                            //String name=json_data.getString("name");
                            SharedPreferences preference = getSharedPreferences("profile", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = preference.edit();
                            edit.clear();
                            edit.putString("id", json_data.getString("parent_id"));
                            edit.putString("name", json_data.getString("name"));
                            edit.putString("email", json_data.getString("email"));
                            edit.putString("password", json_data.getString("password"));
                            edit.putString("phone", json_data.getString("phone"));
                            edit.putString("address", json_data.getString("address"));
                            edit.putString("profession", json_data.getString("profession"));

                            edit.commit();
                        }

                    }
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
			            /*Toast.makeText(getApplicationContext(),"NoData",
			        	          Toast.LENGTH_LONG).show();
*/
                }
            }
            return null;
        }

    }

//    public class updateapk extends AsyncTask<Void, Void, Void> implements OnItemClickListener
//    {
//        String path="";
//
//        @Override
//        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//            // TODO Auto-generated method stub
//
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            // TODO Auto-generated method stub
//            //super.onPostExecute(result);
//            try
//            {
//
//                Intent i = new Intent();
//                i.setAction(Intent.ACTION_VIEW);
//                i.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive" );
//                Log.d("Lofting", "About to install new .apk");
//                startActivity(i);
//
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//
////            dialog = new ProgressDialog(LoginActivity.this);
////            dialog.setMessage("Please Wait for a moment");
////            dialog.setIndeterminate(true);
////            dialog.setCanceledOnTouchOutside(false);
////            Drawable customDrawable = getApplicationContext().getResources().getDrawable(R.drawable.custom_dialog);
////            dialog.setIndeterminateDrawable(customDrawable);
////            dialog.show();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0)
//        {
//            // TODO Auto-generated method stub
//            InputStream is = null;
//            //fetchContacts();
//            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);
//
//            // String result = "";
//            String path = "/sdcard/v4sales.apk";
//            try
//            {
////                String url1= ApiHandler.BASE_URL+"/v4sales.apk";
//                String url1= "http://prismeduware.com/democlass/v4sales.apk";
//                URL url = new URL(url1);
//                URLConnection connection = url.openConnection();
//                connection.connect();
//
//                int fileLength = connection.getContentLength();
//
//                // download the file
//                File file=new File(path);
//                if(!file.mkdir())
//                {
//                    file.mkdir();
//                }
//
//                InputStream input = new BufferedInputStream(url.openStream());
//                OutputStream output = new FileOutputStream(path);
//
//                byte data[] = new byte[1024];
//                long total = 0;
//                int count;
////                while ((count = input.read(data)) != -1) {
////                    total += count;
////                    publishProgress((int) (total * 100 / fileLength));
////                    output.write(data, 0, count);
////                }
//
//                output.flush();
//                output.close();
//                input.close();
//            } catch (Exception e) {
//                Log.e("YourApp", "Well that didn't work out so well...");
//                Log.e("YourApp", e.getMessage());
//            }
//            return null;
//        }
//
//    }

//    public class updateapk extends AsyncTask<String, Integer, String>
//    {
//
//        ProgressDialog dialog;
//        int length=0;
//        String result="";
//        File newfile=null;
//        JSONArray jArray;
//        boolean isSave=false;
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try
//            {
//                if(mProgressDialog.isShowing())
//                {
//                    mProgressDialog.cancel();
//                }
//                Intent i = new Intent();
//                i.setAction(Intent.ACTION_VIEW);
//                i.setDataAndType(Uri.fromFile(newfile), "application/vnd.android.package-archive" );
//                Log.d("Lofting", "About to install new .apk");
//               startActivity(i);
////                Toast.makeText(context,"Document is in Download folder!",Toast.LENGTH_LONG).show();
////                String pth=newfile.getPath().toString().replace(" ","%20");
////                Uri myUri = parse(pth);
////                Intent intent = new Intent(Intent.ACTION_VIEW);
////                intent.setDataAndType(myUri, "text/*");
////                startActivity(intent);
////                Intent intent = new Intent(Intent.ACTION_VIEW,
////                        Uri.parse(newfile.getPath().toString()));
////                intent.setType(filetype);
////                PackageManager pm = context.getPackageManager();
////                List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
//////                if (activities.size() > 0) {
////                getActivity().startActivity(intent);
//
////                } else {
////                    // Do something else here. Maybe pop up a Dialog or Toast
////                }
//            }catch(Exception ex)
//            {
//                Log.e("Error",ex.toString());
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // Create progress dialog
//            mProgressDialog = new ProgressDialog(LoginActivity.this);
//            // Set your progress dialog Title
//            mProgressDialog.setTitle("Progress!");
//            // Set your progress dialog Message
//            mProgressDialog.setMessage("Downloading, Please Wait!");
////            mProgressDialog.setIndeterminate(false);
////            mProgressDialog.setMax(100);
////            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            // Show progress dialog
//            // mProgressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... Url) {
//            try
//            {
//                Url[0]=Url[0].replace(" ","%20");
//                URL url = new URL(Uri.parse(Url[0]).toString().trim());
//                if(url.toString().contains("htm")||url.toString().contains("html"))
//                {
//                    isSave=true;
//                }
//                URLConnection connection = url.openConnection();
//                connection.connect();
////                File directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//                ContextWrapper cw = new ContextWrapper(LoginActivity.this);
//                // path to /data/data/yourapp/app_data/imageDir
//                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//                if(!directory.mkdir())
//                {
//                    directory.mkdir();
//                }
//                newfile=new File(directory,"v4sales.apk");
//                //newfile.mkdir();
//                // Detect the file lenghth
//                int fileLength = connection.getContentLength();
//                String filetype=connection.getContentType();
//
//                // Locate storage location
//                String filepath = Environment.getExternalStorageDirectory()
//                        .getPath();
//
//                // Download the file
//                InputStream input = new BufferedInputStream(url.openStream());
//
//                // Save the downloaded file
//                OutputStream output = new FileOutputStream(newfile.getPath().toString());
//
////                byte data[] = new byte[1024];
////                long total = 0;
////                int count;
////                while ((count = input.read(data)) != -1) {
////                    total += count;
////                    // Publish the progress
////                    publishProgress((int) (total * 100 / fileLength));
////                    output.write(data, 0, count);
////                }
//
//                // Close connection
//                output.flush();
//                output.close();
//                input.close();
//            } catch (Exception e) {
//                // Error Log
//                //       mProgressDialog.cancel();
//                // Utils.toast(context,"Error while downloading the file");
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return null;
//        }
//
////        @Override
////        protected void onProgressUpdate(Integer... progress) {
////            super.onProgressUpdate(progress);
////            // Update the progress dialog
////            mProgressDialog.setProgress(progress[0]);
////            // Dismiss the progress dialog
////            //mProgressDialog.dismiss();
////        }
//    }
public class updateapk extends AsyncTask<String, Integer, String>
{

    ProgressDialog dialog;
    int length=0;
    String result="";
    JSONArray jArray;
File newfile;
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            if(mProgressDialog.isShowing())
            {
                mProgressDialog.cancel();
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            newfile.setReadable(true, false);
            intent.setDataAndType(Uri.fromFile(newfile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            DatabaseHandler1 handler1=new DatabaseHandler1(context);
//            handler1.addPDF(Config.bookid,filename,newfile.getPath().toString(),"pdf");
//                Toast.makeText(context, "Document is in Download folder!", Toast.LENGTH_LONG).show();
//                String pth=newfile.getPath().toString().replace(" ","%20");
//                Uri myUri = parse(pth);
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//                intent.setDataAndType(myUri, "text/*");
//                startActivity(intent);
//                Intent intent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse(newfile.getPath().toString()));
//                intent.setType(filetype);
//                PackageManager pm = context.getPackageManager();
//                List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
////                if (activities.size() > 0) {
//                getActivity().startActivity(intent);

//                } else {
//                    // Do something else here. Maybe pop up a Dialog or Toast
//                }
//            Intent intent = new Intent(context, MyPdf.class);
//            intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, newfile.getPath().toString());
//            getActivity().startActivity(intent);
        }catch(Exception ex)
        {
            Log.e("Error",ex.toString());
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create progress dialog
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        // Set your progress dialog Title
        mProgressDialog.setTitle("Progress!");
        // Set your progress dialog Message
        mProgressDialog.setMessage("Downloading, Please Wait!");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Show progress dialog
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... Url) {
        try
        {
            Url[0]=Url[0].replace(" ","%20");
            URL url = new URL(Uri.parse(Url[0]).toString().trim());

            URLConnection connection = url.openConnection();
            connection.connect();
//                File directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            ContextWrapper cw = new ContextWrapper(LoginActivity.this);
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_WORLD_WRITEABLE);
            if(!directory.mkdir())
            {
                directory.mkdir();
            }
            newfile=new File(directory,"amigoinn.example.v4sales.apk");
            //newfile.mkdir();
            // Detect the file lenghth
            int fileLength = connection.getContentLength();
            String filetype=connection.getContentType();

            // Locate storage location
            String filepath = Environment.getExternalStorageDirectory()
                    .getPath();

            // Download the file
            InputStream input = new BufferedInputStream(url.openStream());

            // Save the downloaded file
            OutputStream output = new FileOutputStream(newfile.getPath().toString());

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1)
            {
                total += count;
                // Publish the progress
                publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }

            // Close connection
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            // Error Log
            mProgressDialog.cancel();
            // Utils.toast(context,"Error while downloading the file");
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // Update the progress dialog
        mProgressDialog.setProgress(progress[0]);
        // Dismiss the progress dialog
        //mProgressDialog.dismiss();
    }
}
    public void fetchContacts() {
        String phoneNumber = null;
        String email = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

        String _ID = ContactsContract.Contacts._ID;

        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;

        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;

        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        StringBuffer output = new StringBuffer();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        // Loop for every contact in the phone

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    //str+= name;
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        //    output.append("\n Phone number:" + phoneNumber);
                    }
                    phoneCursor.close();
                    // Query and loop for every email of the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        //      output.append("\nEmail:" + email);
                    }
                    emailCursor.close();
                }
                str += name + "," + phoneNumber + "," + email + "\n";
                email = "";
                //output.append("\n");
            }


            // osutputText.setText(output);

        }
        writeTofile(str);
    }

    private void writeTofile(String str) {
        // TODO Auto-generated method stub
        File folder = new File(Environment.getExternalStorageDirectory() + "/CSV");
        boolean success = true;
        if (!folder.exists()) {
            //Toast.makeText(MainActivity.this, "Directory Does Not Exist, Create It", Toast.LENGTH_SHORT).show();
            success = folder.mkdir();
            folder.getPath();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(new Date());
        String photoFile = "Contact_" + imeiNo + ".csv";

        String filename = File.separator + photoFile;

        File SmsFile = new File(folder, filename);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(SmsFile);
            fos.write(str.getBytes());
            fos.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String urlString = "http://amigoinnovations.co.in/wifiwalkie/upload.php";
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
	   /*  FileUploadClass uploadobj=new FileUploadClass();
			Boolean result=uploadobj.doFileUpload(SmsFile.getPath(),urlString,LoginActivity.this);
			if(result)
			{
				SmsFile.deleteOnExit();
			}
	*/
    }



}
