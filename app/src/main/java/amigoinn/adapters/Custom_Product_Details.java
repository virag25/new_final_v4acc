package amigoinn.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.v4sales.R;

import java.util.ArrayList;

public class Custom_Product_Details extends BaseAdapter {
    Context context;
    ArrayList<String> name,Quantity,price;
//    String[] txthpright;
//    String[] txtsectional;
//    String[] imgsectional;

    private static LayoutInflater inflater=null;

    public Custom_Product_Details(Context context, ArrayList<String> name, ArrayList<String> extrra,ArrayList<String> Price) {
        // TODO Auto-generated constructor stub
        context=context;
        this.name=name;
        this.Quantity=extrra;
        this.price=Price;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Custom_Product_Details(Context context)
    {
        this.context=context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return name.size();
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
        TextView txtName,txtQuantity,txtPrice;
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
            rowView = inflater.inflate(R.layout.custom_product_details, null);
            holder.txtName = (TextView) rowView.findViewById(R.id.txtUsername);
            holder.txtPrice = (TextView) rowView.findViewById(R.id.txtprice);
            holder.txtQuantity = (TextView) rowView.findViewById(R.id.txtQuantity);
//            holder.tvPrice = (TextView) rowView.findViewById(R.id.txtprice);
//            holder.tvl.setText(name.get(position));

            holder.txtName.setText(name.get(position));
            holder.txtQuantity.setText(Quantity.get(position));
            holder.txtPrice.setText(price.get(position));
//            holder.tvPrice.setText(name.get(position));
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
