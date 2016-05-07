package amigoinn.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.v4sales.R;

import amigoinn.example.v4sales.Config;
import amigoinn.example.v4sales.Filter;

import amigoinn.example.v4sales.FragmentCategoryItemRank;
import amigoinn.walkietalkie.Constants;

import java.util.ArrayList;

public class Custom_Home_Orders extends BaseAdapter {
    Context context;
    ArrayList<String> name,extra,product,price;
    String[] txthpright;
//    String[] txtsectional;
//    String[] imgsectional;
ArrayAdapter<String> adapter;
    private static LayoutInflater inflater=null;

    public Custom_Home_Orders(Context context, ArrayList<String> name, ArrayList<String> extrra, ArrayList<String> product, ArrayList<String> price) {
        // TODO Auto-generated constructor stub
        context=context;
        this.name=name;
        extra=extrra;
        this.product=product;
        this.price=price;
//        Constants.searchlist.add("amigo");
//        Constants.searchlist.add("himanshu");
//        Constants.searchlist.add("maulik");
//        Constants.searchlist.add("mohit");
//        Constants.searchlist.add("vinit");
//        Constants.searchlist.add("ahmedabad");
//        Constants.searchlist.add("bombay");
//        Constants.searchlist.add("delhi");
        this.context=context;
        adapter=new ArrayAdapter<String>(context,R.layout.sammplelistitemabsent, R.id.tv,Constants.searchlist);
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

    public void remove(int position)
    {
        //   message.add(date.get(position));
            name.remove(position);

        notifyDataSetChanged();
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
        AutoCompleteTextView edtcode;
        EditText edtQuantity,edtDate;
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
            rowView = inflater.inflate(R.layout.custom_order_home, null);
            holder.edtcode = (AutoCompleteTextView) rowView.findViewById(R.id.edtcode);
            holder.edtDate = (EditText) rowView.findViewById(R.id.edtdate);
            holder.edtQuantity = (EditText) rowView.findViewById(R.id.edtQty);
            holder.tvPrice = (TextView) rowView.findViewById(R.id.txtprice);
            holder.imgsectional = (ImageView) rowView.findViewById(R.id.imgBrowse);
//            holder.imgsectional.setVisibility(View.GONE);
//            holder.tvl.setText(name.get(position));

            holder.edtDate.setText(product.get(position));
            holder.edtQuantity.setText(extra.get(position));
            holder.edtcode.setText(name.get(position));
            holder.tvPrice.setText(price.get(position));

            holder.edtDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Config.SELECTEDPOSITION = position;
                    FragmentCategoryItemRank itemRank = new FragmentCategoryItemRank();
                    itemRank.filledittext();

                }
            });
            holder.tvPrice.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {
                    Config.SELECTEDPOSITION=position;
//                    FragmentCategoryItemRank itemRank=new FragmentCategoryItemRank();
//                    itemRank.filledittext();

                }
            });
            holder.edtQuantity.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {
                    Config.SELECTEDPOSITION=position;
                    FragmentCategoryItemRank itemRank=new FragmentCategoryItemRank();
                    itemRank.filledittext();

                }
            });
            holder.edtcode.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {
                    Config.SELECTEDPOSITION=position;
                    FragmentCategoryItemRank itemRank=new FragmentCategoryItemRank();
                    itemRank.filledittext();

                }
            });
            holder.imgsectional.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        Intent in = new Intent(context, Filter.class);
                        context.startActivity(in);
                    }catch (Exception ex)
                    {
                        Log.e("Error",ex.toString());
                    }
                }
            });
            try
            {

                //holder.edtcode.setThreshold(1);//will start working from first character
//                actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//                actv.setTextColor(Color.RED);
                holder.edtcode.setAdapter(adapter);
            }
            catch (Exception ex)
            {
                Log.e("Error",ex.toString());
            }
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
