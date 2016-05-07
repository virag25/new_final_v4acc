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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.v4sales.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReportCardFragment extends Fragment {


	public Context context;
	RobotoTextView tvDisplayDate;
	ListView lvGood, lvBad;
	List<String> good = new ArrayList<String>();
	List<String> bad = new ArrayList<String>();
	public static ReportCardFragment newInstance() {
		return new ReportCardFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_reportcard, container, false);
		context = rootView.getContext();
		lvGood = (ListView) rootView.findViewById(R.id.lstGood);
		lvBad = (ListView) rootView.findViewById(R.id.lstBad);
		new GetMarks().execute();
//		ArrayAdapter<String> ad = new ArrayAdapter<String>(context, R.layout.sammplelistitem, good);
//		ArrayAdapter<String> adBad = new ArrayAdapter<String>(context, R.layout.sammplelistitemred, good);
//		lvGood.setAdapter(ad);
//		lvBad.setAdapter(adBad);
		return rootView;
	}


//	public void  ParseJson(List<OverallPercentage> post)
//	{
//
//		//setContentView(chart);
//		int count=0;
//		for(OverallPercentage overall:post)
//		{
//
//			labels.add(overall.getName());
//			entries.add(new BarEntry(Float.parseFloat(overall.getPercentage()),count));
//			count++;
//		}
//		dataset = new BarDataSet(entries, "# of Calls");
//		chart.setDrawGridBackground(false);
//		BarData data = new BarData(labels, dataset);
//		data.setValueTextSize(10f);
//		chart.setData(data);
//		//chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//		//Float fl=Float.labels.size();
//		//chart.setVisibleXRange(labels.size(),labels.size());
//		chart.setDescription("% of marks in each subject");
//		chart.animateY(2000);
//
//		chart.setDescriptionTextSize(15f);
////        XAxis xa=chart.getXAxis();
////        xa.setTextSize(px);
//		YAxis yAxis = chart.getAxisLeft();
//		chart.getAxisRight().setAxisMaxValue(100f);
//		// chart.setRendererRightYAxis(null);
//		// yAxis.setTextSize(px);
//
//		yAxis.setAxisMaxValue(100f);
//		FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);
//
//		parent.addView(chart);
//
//

	public class GetMarks extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener {

		ProgressDialog dialog;
		int length;
		String result = "";

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			try {
				ArrayAdapter<String> ad = new ArrayAdapter<String>(context, R.layout.sammplelistitem, good);
		ArrayAdapter<String> adBad = new ArrayAdapter<String>(context, R.layout.sammplelistitemred, bad);
		lvGood.setAdapter(ad);
		lvBad.setAdapter(adBad);
				dialog.cancel();

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
			InputStream is = null;
			//fetchContacts();
			SharedPreferences preferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
			String id = preferences.getString("id", "");
			//List<NameValuePair> arra= new ArrayList<NameValuePair>(2);
			String langi = "23.0481352";
			String latit = "72.5540532";
			// String result = "";
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://www.prismeduware.com/parentapp/overallresult.php?parent_id=" + id);

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
//				GsonBuilder gsonBuilder = new GsonBuilder();
//				gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//				Gson gson = gsonBuilder.create();
//
//				posts = new ArrayList<OverallPercentage>();
//				posts = Arrays.asList(gson.fromJson(new InputStreamReader(is), OverallPercentage[].class));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				 result=sb.toString();
				length=result.length();
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());
			}
			//parse json data
			if (length > 15)
			{
				try {
					JSONObject mainObj = new JSONObject(result);
					JSONObject secondObjGood = new JSONObject();
					JSONObject secondObjBad = new JSONObject();

					if (mainObj != null) {
						secondObjGood=mainObj.getJSONObject("Good");
						secondObjBad=mainObj.getJSONObject("Bad");
						//JSONArray goodA1=secondObjGood.getJSONArray("good1");
//						JSONArray goodA2=secondObjGood.getJSONArray("good2");
//						JSONArray goodA3=secondObjGood.getJSONArray("good3");
//						JSONArray goodA4=secondObjGood.getJSONArray("good4");
//						JSONArray goodA5=secondObjGood.getJSONArray("good5");
					for(int i=0;i<5;i++)
					{
						JSONArray goodA1 = null;
						if(i==0)
						{

							 goodA1=secondObjGood.getJSONArray("good5");
						}
						if(i==1)
						{

							goodA1=secondObjGood.getJSONArray("good3");
						}
						if(i==2)
						{

							goodA1=secondObjGood.getJSONArray("good4");
						}
						if(i==3)
						{

							goodA1=secondObjGood.getJSONArray("good1");
						}
						if(i==4)
						{

							goodA1=secondObjGood.getJSONArray("good2");
						}
						for (int j=0;j<goodA1.length();j++)
						{
							if(!goodA1.getString(j).equalsIgnoreCase("0")) {
								good.add(goodA1.getString(j));
							}
						}

					}

						for(int i=0;i<5;i++)
						{
							JSONArray goodA1 = null;
							if(i==0)
							{
								goodA1=secondObjBad.getJSONArray("bad1");
							}
							if(i==1)
							{

								goodA1=secondObjBad.getJSONArray("bad2");
							}
							if(i==2)
							{

								goodA1=secondObjBad.getJSONArray("bad3");
							}
							if(i==3)
							{

								goodA1=secondObjBad.getJSONArray("bad4");
							}
							if(i==4)
							{

								goodA1=secondObjBad.getJSONArray("bad5");
							}
							for (int j=0;j<goodA1.length();j++)
							{
								if(!goodA1.getString(j).equalsIgnoreCase("0")) {
									bad.add(goodA1.getString(j));
								}
							}

						}

						//   list.add(jArray.get(i).toString());
							//mStringArray = new String[list.size()];
							//mStringArray = list.toArray(mStringArray);



					}
				}
				catch(Exception ex)
				{
					Log.e("Error",ex.toString());
				}
			}
			return null;
		}

	}
}

