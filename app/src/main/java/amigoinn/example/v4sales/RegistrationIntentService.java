package amigoinn.example.v4sales;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Maulik Patel on 10/15/2015.
 */
public class RegistrationIntentService extends IntentService {

    // abbreviated tag name
    private static final String TAG = "RegIntentService";
    String regId;
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Make a call to Instance API
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        InstanceID instanceID = InstanceID.getInstance(this);
        String senderId = "1075540226763";//getResources().getString(R.string.gcm_defaultSenderId);
        try {
            // request token that will be used by the server to send push notifications
            String token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.d(TAG, "GCM Registration Token: " + token);
            SharedPreferences regIdPref=getSharedPreferences("regId", Context.MODE_PRIVATE);
            regId=token;
            if(!regId.equals(regIdPref.getString("regId","abcd")))
            {
		/* SharedPreferences.Editor edit=regIdPref.edit();
			 edit.clear();
			 edit.putString("regId",regId);
			 edit.commit();
			*/
                try
                {

                    new updateRegId().execute();
                }
                catch(Exception ex)
                {

                    sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();

                }
            }

            // pass along this data
           // sendRegistrationToServer(token);
        } catch (IOException e) {
            e.printStackTrace();
            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();

        }
    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
    public class updateRegId extends AsyncTask<Void, Void, Void>
    {
        String finalresult;
        String result;
        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            SharedPreferences regIdPref=getSharedPreferences("regId", Context.MODE_PRIVATE);

            if(finalresult.length()==5)
            {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegistrationIntentService.this);
                sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();

                SharedPreferences.Editor edit=regIdPref.edit();
                edit.clear();
                edit.putString("regId",regId);
                edit.commit();
                //Create a new PendingIntent and add it to he AlarmManager
            }
        }
        @Override
        protected void onPreExecute() {


        }
        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            InputStream is = null;
            SharedPreferences preferences=getSharedPreferences("profile",Context.MODE_PRIVATE);
           // String id=preferences.getString("id", "");
            String parent_id=preferences.getString("id","");
            int length=0;
            try{
                SharedPreferences preference=getSharedPreferences("Id",Context.MODE_PRIVATE);
                String userId = preference.getString("userid","7");
                String url=("http://kauverygroup.com/mobile/getregid.php?reg_id="+regId+"&parent_id="+parent_id);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();


            }catch(Exception e){
                Log.e("log_tag", "Error in http connection "+e.toString());

            }


            //convert response to string

            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result=sb.toString();
                finalresult=result;
                length=result.length();
            }catch(Exception e){


            }
            //parse json data
            if(length>20)
            {
                try{
                    JSONArray jArray = new JSONArray(result);
                    ArrayList<String> list = new ArrayList<String>();

                    if (jArray != null) {
                        for (int i=0;i<jArray.length();i++){
                            JSONObject json_data = jArray.getJSONObject(i);


                            //contact,address1,address2,zipcode,city,description,leaddate

                        }

                    }
                }catch(JSONException e){
                    Log.e("log_tag", "Error parsing data "+e.toString());
                    //  Toast.makeText(getApplicationContext(),"NoData",
                    //          Toast.LENGTH_LONG).show();
                }
            }



            return null;
        }

    }
}