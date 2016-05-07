package amigoinn.example.v4sales;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.v4sales.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import amigoinn.walkietalkie.Constants;


public class ClientActivity extends Fragment implements OnClickListener {
    FloatLabeledEditText loginText, passText, newpass, confText;
    RobotoTextView btnLogin, btnRegister;
    //	int myIp;
//	int intMyIp3;
//	int intMyIp3mod;
//	String myipfinal;
//	int intMyIp2;
//	int intMyIp2mod;
    Context context;
    View view;
    int length = 0;
    String result = "";
    String user = "", pass = "";
    ProgressDialog dialog;
    ArrayList<String> list;
    JSONObject json_data;
    Custom_Home_Orders ordersadapter;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> extras = new ArrayList<String>();
    ArrayList<String> Product = new ArrayList<String>();
    ArrayList<String> Price = new ArrayList<String>();

    JSONArray jArray;
    String str = "";
    String email;
    ListView lv1;
    SharedPreferences preferences;

    @Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.client_activity);
//		lv1 = (ListView)findViewById(R.id.lstOrders);
//		names.add("13");
//		extras.add("111");
//		Product.add("bicycle");
//		Price.add("1000");
//		ordersadapter=new Custom_Home_Orders(ClientActivity.this,names,extras,Product,Price);
//		lv1.setAdapter(ordersadapter);
////        view = inflater.inflate(R.layout.client_activity, container, false);
////        context = view.getContext();
////
//////	loginText = (FloatLabeledEditText)view.findViewById(R.id.user);
//////	passText = (FloatLabeledEditText)view.findViewById(R.id.etPassword);
//////	newpass = (FloatLabeledEditText)view.findViewById(R.id.etnewPassword);
//////	confText = (FloatLabeledEditText)view.findViewById(R.id.etnewPasswordConfirm);
//////
//////	btnLogin=(RobotoTextView)view.findViewById(R.id.login);
//////	btnLogin.setOnClickListener(this);
////        // new AsynchTaskEx().execute();
////        return  view;
//	}


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.client_activity, container, false);
        lv1 = (ListView) v.findViewById(R.id.lstOrders);
        names.add("13");
        extras.add("111");
        Product.add("bicycle");
        Price.add("1000");
        ordersadapter = new Custom_Home_Orders(getActivity(), names, extras, Product, Price);
        lv1.setAdapter(ordersadapter);
        return v;
    }

//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//						 Bundle savedInstanceState)
//{
//
//	// Inflate the layout for this fragment
//	view = inflater.inflate(R.layout.client_activity, container, false);
//	context = view.getContext();
//
////	loginText = (FloatLabeledEditText)view.findViewById(R.id.user);
////	passText = (FloatLabeledEditText)view.findViewById(R.id.etPassword);
////	newpass = (FloatLabeledEditText)view.findViewById(R.id.etnewPassword);
////	confText = (FloatLabeledEditText)view.findViewById(R.id.etnewPasswordConfirm);
////
////	btnLogin=(RobotoTextView)view.findViewById(R.id.login);
////	btnLogin.setOnClickListener(this);
//   // new AsynchTaskEx().execute();
//	return  view;
//}


    @Override
    public void onClick(View v) {

    }

    public class Custom_Home_Orders extends BaseAdapter {
        Context context;
        ArrayList<String> name, extra, product, price;
        String[] txthpright;
        //    String[] txtsectional;
//    String[] imgsectional;
        ArrayAdapter<String> adapter;
        private LayoutInflater inflater = null;

        public Custom_Home_Orders(Context context, ArrayList<String> name, ArrayList<String> extrra, ArrayList<String> product, ArrayList<String> price) {
            // TODO Auto-generated constructor stub
            context = context;
            this.name = name;
            extra = extrra;
            this.product = product;
            this.price = price;
//        Constants.searchlist.add("amigo");
//        Constants.searchlist.add("himanshu");
//        Constants.searchlist.add("maulik");
//        Constants.searchlist.add("mohit");
//        Constants.searchlist.add("vinit");
//        Constants.searchlist.add("ahmedabad");
//        Constants.searchlist.add("bombay");
//        Constants.searchlist.add("delhi");
            this.context = context;
//			adapter=new ArrayAdapter<String>(context,R.layout.sammplelistitemabsent, R.id.tv, Constants.searchlist);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return this.name.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public void remove(int position) {
            //   message.add(date.get(position));
            name.remove(position);
            Product.remove(position);
            extra.remove(position);
            Price.remove(position);

            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder {
            TextView tvl;
            TextView tvPrice;
            AutoCompleteTextView edtcode;
            EditText edtQuantity, edtDate;
            TextView tvsectional;
            ImageView imgsectional;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder = new Holder();
            View rowView;
            try {
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
                holder.edtcode.setEnabled(false);
                holder.edtQuantity.setEnabled(false);
                holder.edtDate.setEnabled(false);
                holder.edtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            Config.SELECTEDPOSITION = position;
//							edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
//							edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
//							edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
//							edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
                        }
                    }
                });
                holder.tvPrice.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Config.SELECTEDPOSITION = position;
//						edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
//						edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
//						edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
//						edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
//                    FragmentCategoryItemRank itemRank=new FragmentCategoryItemRank();
//                    itemRank.filledittext();

                    }
                });
                holder.edtQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            Config.SELECTEDPOSITION = position;
//							edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
//							edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
//							edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
//							edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
                        }
                    }
                });
                holder.edtcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            Config.SELECTEDPOSITION = position;
//							edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
//							edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
//							edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
//							edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
                        }
                    }
                });

                holder.imgsectional.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent in = new Intent(context, Filter.class);
                            context.startActivity(in);
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                        }
                    }
                });
                try {

                    //holder.edtcode.setThreshold(1);//will start working from first character
//                actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
//                actv.setTextColor(Color.RED);
                    //	holder.edtcode.setAdapter(adapter);
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
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

            } catch (Exception ex) {
                Log.e("Error", ex.toString());
                return null;
            }
        }
    }
}
