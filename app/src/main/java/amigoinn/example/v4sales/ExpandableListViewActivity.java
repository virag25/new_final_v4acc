package amigoinn.example.v4sales;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.example.v4sales.R;

import amigoinn.models.GroupItems;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ExpandableListViewActivity extends Activity {

	private AnimatedExpandableListView listView;
	List<GroupItems> dataList;
	ArrayList<String> name=new ArrayList<String>();
	ArrayList<String> comment=new ArrayList<String>();
	ArrayList<String> mark=new ArrayList<String>();
	String sub_id;

	ArrayList<String> date=new ArrayList<String>();
	ArrayList<String> month=new ArrayList<String>();
//	ArrayList<String> mark_obtained=new ArrayList<String>();
//	public String ;
//	public String   ;
//	public String ;
//	public String   ;
//	public String  student_id;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandable_list_view);
		//getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.buttonshape));

		// Populate our list with groups and it's children

	}


}
