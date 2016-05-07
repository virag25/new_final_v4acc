package amigoinn.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v4sales.R;

import java.util.ArrayList;
import java.util.List;

import amigoinn.example.v4sales.ClientActivity;
import amigoinn.example.v4sales.GoogleMapActivity;
import amigoinn.models.Datum1;
import amigoinn.walkietalkie.Constants;

public class Custom_Route extends BaseAdapter
{
    Context context;
    ArrayList<String> name,extra;
    String[] txthpright;
    List<Datum1> routedetails;
//    String[] txtsectional;
//    String[] imgsectional;

    private static LayoutInflater inflater=null;

    public Custom_Route(Context context, ArrayList<String> name, ArrayList<String> extrra)
    {
        // TODO Auto-generated constructor stub
        context=context;
        this.name=name;
        extra=extrra;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Custom_Route(Context context,List<Datum1> routedetails)
    {
        this.context=context;
        this.routedetails=routedetails;
//        Constants.PartyList.clear();
//        Constants.addParty();
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return routedetails.size();
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
        TextView tvPrice;
        TextView txtName,txtQuantity,txtAddress,txtMap;
        TextView tvsectional;
        ImageView imgsectional;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        try
        {
            rowView = inflater.inflate(R.layout.custom_party_details, null);
            holder.txtName = (TextView) rowView.findViewById(R.id.txtUsername);
            holder.txtAddress = (TextView) rowView.findViewById(R.id.txtAddress);
            holder.txtMap = (TextView) rowView.findViewById(R.id.txtmap);
//            holder.tvPrice = (TextView) rowView.findViewById(R.id.txtprice);
//            holder.tvl.setText(name.get(position));

//            holder.edtDate.setText(extra.get(position));
//            holder.edtDate.setText(extra.get(position));
//            holder.edtDate.setText(extra.get(position));
            holder.txtName.setText(routedetails.get(position).getClientname());
            holder.txtAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        Intent in = new Intent(context, ClientActivity.class);
                        context.startActivity(in);
                    }catch (Exception ex)
                    {
                        Log.e("Error",ex.toString());
                    }
                }
            });
            holder.txtMap.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        Intent in = new Intent(context, GoogleMapActivity.class);
                        context.startActivity(in);
                    }catch (Exception ex)
                    {
                        Log.e("Error",ex.toString());
                    }
                }
            });
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
