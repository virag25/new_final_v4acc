package amigoinn.adapters;

import android.widget.BaseAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.v4sales.R;

import java.util.ArrayList;

public class Custom_Home_tasks extends BaseAdapter {
    Context context;
    ArrayList<String> name,extra;
    String[] txthpright;
//    String[] txtsectional;
//    String[] imgsectional;

    private static LayoutInflater inflater=null;

    public Custom_Home_tasks(Context context,ArrayList<String> name,ArrayList<String> extrra) {
        // TODO Auto-generated constructor stub
        context=context;
        this.name=name;
        extra=extrra;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return this.name.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tvl;
        TextView tvr;
        TextView tvsectional;
        ImageView imgsectional;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        try {
            rowView = inflater.inflate(R.layout.custom_tasks, null);
            holder.tvl = (TextView) rowView.findViewById(R.id.txthpleft);
            holder.tvr = (TextView) rowView.findViewById(R.id.txthpright);

            holder.tvl.setText(name.get(position));

            holder.tvr.setText(extra.get(position));
            holder.tvr.setVisibility(View.GONE);

            return rowView;

//            if(position%2==0)
//            {
//
////                holder.tvl.setVisibility(View.GONE);
////                holder.tvr.setVisibility(View.GONE);
//                holder.imgsectional.setVisibility(View.VISIBLE);
//                holder.tvsectional.setVisibility(View.VISIBLE);
//
//
//
//            }
//            else
//            {
//
//                holder.tvl.setVisibility(View.VISIBLE);
//                holder.tvr.setVisibility(View.VISIBLE);
//                holder.tvsectional.setVisibility(View.GONE);
//                holder.imgsectional.setVisibility(View.GONE);
//
//            }

        }catch (Exception ex)
        {
            Log.e("Error",ex.toString());
            return null;
        }
   }
}
