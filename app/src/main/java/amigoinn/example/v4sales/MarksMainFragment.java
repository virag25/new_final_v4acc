package amigoinn.example.v4sales;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.v4sales.R;

import amigoinn.models.MarksMainModel;
import amigoinn.models.SubjectMonth;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class MarksMainFragment extends Fragment {

	public	Context context;
	RobotoTextView tvDisplayDate;
	ListView listViewMarks;
	boolean isMonth=false,isSubject=false;
	String selectedMonth="",selectedSubject="";
	MaterialSpinner spinnerSubject,spinnerMonths;
	List<MarksMainModel> posts=new ArrayList<MarksMainModel>();
	List<SubjectMonth> subjectList=new ArrayList<SubjectMonth>();
	public static MarksMainFragment newInstance() {
		return new MarksMainFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{

		try {
		View rootView = inflater.inflate(R.layout.activity_marks_main, container, false);
		context=rootView.getContext();


//			spinnerMonths.setAdapter(adapter);





			return rootView;

		}
		catch(Exception ex)
		{
			Log.e("Error",ex.toString());
		}
		return  null;
	}


}
