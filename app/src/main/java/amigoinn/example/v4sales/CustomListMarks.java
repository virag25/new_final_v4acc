package amigoinn.example.v4sales;

import java.util.List;

import amigoinn.models.MarksMainModel;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.v4sales.R;

public class CustomListMarks extends BaseAdapter{

	String[]   userId,Status,Name;
	String[] activen,deactiven,activem,deactivem;
	//ListOfLeads leads;
//	ArrayList<String> userIdList,nameList,statusList,user;
List<MarksMainModel> myMarks;
	 Typeface tf;
	 
	int length;
	public Context context;
	LayoutInflater inflater;
	
	

	public CustomListMarks(Context context, List<MarksMainModel> myMarks) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.myMarks=myMarks;
		this.inflater=(LayoutInflater)this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);

	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myMarks.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public static class ChildHolder {
		RobotoTextView title;
		RobotoTextView date;
		RobotoTextView marks;
		RobotoTextView rank;
		RobotoTextView subject;


		//TextView hint;
	}


	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ChildHolder holder;
if(arg1==null)
{
		holder = new ChildHolder();
		arg1 = inflater.inflate(R.layout.main_marks_custom, arg2,
				false);

		holder.title = (RobotoTextView) arg1
				.findViewById(R.id.textTitle);
		holder.subject= (RobotoTextView) arg1
				.findViewById(R.id.textSubject);

		holder.date = (RobotoTextView) arg1
				.findViewById(R.id.textDate);
		holder.marks= (RobotoTextView) arg1
				.findViewById(R.id.textMarks);

		holder.rank = (RobotoTextView) arg1
				.findViewById(R.id.textRank);

				/*holder.hint = (TextView) convertView
						.findViewById(R.id.textHint);*/
		arg1.setTag(holder);
	} else {
		holder = (ChildHolder) arg1.getTag();
	}

	holder.title.setText(myMarks.get(arg0).getComment());
	holder.rank.setText(myMarks.get(arg0).getRank());
	holder.subject.setText(myMarks.get(arg0).getSubject_name());
	holder.marks.setText(myMarks.get(arg0).getMark_obtained()+"/"+myMarks.get(arg0).getMark_total());
	holder.date.setText(myMarks.get(arg0).getDate());
				
			return arg1;
//		}
		
		
		//return convertView;

	}

}

