package amigoinn.example.v4sales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.v4sales.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import amigoinn.models.SubjectMonth;
import amigoinn.walkietalkie.Contact;
import amigoinn.walkietalkie.DatabaseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class NotificationFragment extends Fragment {

	public static WebView wvLoader;
	Context context;
	String url1;
	ProgressDialog dialog;
	ListView lvGood, lvBad;
	String selectedSubject;
	String monthName;
	MaterialSpinner spinnerSubject;
	List<String> good = new ArrayList<String>();
	List<String> date = new ArrayList<String>();
	int count=0;
	List<SubjectMonth> subjectList=new ArrayList<SubjectMonth>();
	DatabaseHandler handler;


	public static NotificationFragment newInstance() {
		return new NotificationFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.notificationlayout, container, false);
		context=rootView.getContext();
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
		spinnerSubject=(MaterialSpinner)rootView.findViewById(R.id.spinnerMonth);
		spinnerSubject.setBaseColor(context.getResources().getColor(R.color.white));
		monthName = month_date.format(cal.getTime());
		lvGood = (ListView) rootView.findViewById(R.id.list_view_absent);
		new GetSpinnerData1().execute();
		new GetMarks12().execute();

		return rootView;
	}
	private void ParseSubjectJson(final List<SubjectMonth> post)
	{
		try {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, post.get(0).getMonth());
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerSubject.setAdapter(adapter);

			spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
					String selectedValue = spinnerSubject.getSelectedItem().toString();
					if (!selectedValue.equalsIgnoreCase(spinnerSubject.getHint().toString())) {

						monthName = post.get(0).getMonth().get(i);
						new GetMarks12().execute();
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> adapterView) {

				}
			});


		}catch(Exception ex)
		{
			Log.e("Error", ex.toString());
		}
//		listViewMarks.setAdapter(adapter);

	}

	public class GetSpinnerData1 extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener {

		ProgressDialog dialog;
		int length;
		String result="";
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			try{
				dialog.cancel();
				if(subjectList.size()!=0) {

					ParseSubjectJson(subjectList);

				}
				else
				{

				}

			}
			catch(Exception e)
			{
				// Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//uiUpdate.setText("Output : ");
			dialog=new ProgressDialog(context);
			dialog.setMessage("Please Wait for a moment");
			dialog.setIndeterminate(true);
			dialog.setCanceledOnTouchOutside(false);
			//   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			//dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
			Drawable customDrawable= context.getResources().getDrawable(R.drawable.custom_dialog);

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
			SharedPreferences preferences=context.getSharedPreferences("profile",Context.MODE_PRIVATE);
			String id=preferences.getString("id","");
			//List<NameValuePair> arra= new ArrayList<NameValuePair>(2);
			String langi="23.0481352";
			String latit="72.5540532";
			// String result = "";
			try{
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://www.prismeduware.com/parentapp/subjectlist.php?parent_id="+id);

				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();


			}
			catch(Exception e){
				Log.e("log_tag", "Error in http connection " + e.toString());
			            /*Toast.makeText(getApplicationContext(),"Please connect to Internet",
			        	          Toast.LENGTH_LONG).show();*/
			}


			//convert response to string
			try{
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
				GsonBuilder gsonBuilder = new GsonBuilder();
				gsonBuilder.setDateFormat("M/d/yy hh:mm a");
				Gson gson = gsonBuilder.create();

//				posts = new ArrayList<MarksMainModel>();
				subjectList.clear();
				subjectList = Arrays.asList(gson.fromJson(new InputStreamReader(is), SubjectMonth.class));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				// result=sb.toString();
				//length=result.length();
			}catch(Exception e){
				Log.e("log_tag", "Error converting result "+e.toString());
			}
			//parse json data
			if(length>5)
			{

			}
			return null;
		}

	}
	public class GetMarks12 extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener {

		ProgressDialog dialog;
		int length;
		String result = "";
		List<Contact> contactsList=new ArrayList<Contact>();
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			try {
				dialog.cancel();
				good.clear();
				date.clear();
				for(int i=0;i<contactsList.size();i++)
				{
					good.add(contactsList.get(i).getMessage());
					date.add(contactsList.get(i).getDate());
				}
				CustomNotification ad = new CustomNotification(context,good,date);

//		ArrayAdapter<String> adBad = new ArrayAdapter<String>(context, R.layout.sammplelistitemred, bad);
				lvGood.setAdapter(ad);
//		lvBad.setAdapter(adBad);


			} catch (Exception e) {
				// Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//uiUpdate.setText("Output : ");
			dialog = new ProgressDialog(context);
			dialog.setMessage("Please Wait for a moment");
			dialog.setIndeterminate(true);
			dialog.setCanceledOnTouchOutside(false);
			//   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			//dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
			Drawable customDrawable = context.getResources().getDrawable(R.drawable.custom_dialog);

			// set the drawable as progress drawable
//
			dialog.setIndeterminateDrawable(customDrawable);
			dialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try {
				handler = new DatabaseHandler(context);
				contactsList = handler.getAllMessages(monthName);
			}catch(Exception ex)
			{
				Log.e("Error",ex.toString());
			}
			//fetchContacts();

			//List<NameValuePair> arra= new ArrayList<NameValuePair>(2);




//			// String result = "";
//			try {
//				HttpClient httpclient = new DefaultHttpClient();
//				HttpPost httppost = new HttpPost("http://www.prismeduware.com/parentapp/absent_full.php?parent_id=" + id+"&month="+monthName);
//
//				HttpResponse response = httpclient.execute(httppost);
//				HttpEntity entity = response.getEntity();
//				is = entity.getContent();
//
//
//			} catch (Exception e) {
//				Log.e("log_tag", "Error in http connection " + e.toString());
//			            /*Toast.makeText(getApplicationContext(),"Please connect to Internet",
//			        	          Toast.LENGTH_LONG).show();*/
//			}
//
//
//			//convert response to string
//			try {
//				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
////				GsonBuilder gsonBuilder = new GsonBuilder();
////				gsonBuilder.setDateFormat("M/d/yy hh:mm a");
////				Gson gson = gsonBuilder.create();
////
////				posts = new ArrayList<OverallPercentage>();
////				posts = Arrays.asList(gson.fromJson(new InputStreamReader(is), OverallPercentage[].class));
//				StringBuilder sb = new StringBuilder();
//				String line = null;
//				while ((line = reader.readLine()) != null) {
//					sb.append(line + "\n");
//				}
//				is.close();
//				result=sb.toString();
//				length=result.length();
//			} catch (Exception e) {
//				Log.e("log_tag", "Error converting result " + e.toString());
//			}
//			//parse json data
//			if (length > 5)
//			{
//				try {
//					good.clear();
//
//					JSONArray goodA1 = new JSONArray(result);
////						JSONArray goodA2=secondObjGood.getJSONArray("good2");
////						JSONArray goodA3=secondObjGood.getJSONArray("good3");
////						JSONArray goodA4=secondObjGood.getJSONArray("good4");
////						JSONArray goodA5=secondObjGood.getJSONArray("good5");
//					for (int i = 0; i < goodA1.length(); i++) {
//						JSONObject objData = goodA1.getJSONObject(i);
//						good.add(objData.getString("date"));
//
//					}
//				}
//				catch(Exception ex)
//				{
//					Log.e("Error",ex.toString());
//				}
			//}
			return null;
		}

	}
}
