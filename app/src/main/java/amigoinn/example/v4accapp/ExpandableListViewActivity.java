package amigoinn.example.v4accapp;

import java.util.ArrayList;
import java.util.List;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;


import amigoinn.models.GroupItems;


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
