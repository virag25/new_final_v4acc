package amigoinn.example.v4sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.v4sales.R;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import amigoinn.adapters.SectionedListBeforeFilter;
import amigoinn.models.OverallPercentage;
import amigoinn.walkietalkie.Constants;

/**
 * Created by Manthan on 28/09/2015.
 */
public class FragmentRankDuplicate extends Fragment implements DatePickerDialog.OnDateSetListener
{
    View view;
    public List<String> list = new ArrayList<String>();
    ListView lv1;
    public static final String DATEPICKER_TAG = "datepicker";
    String monthName;
    int totalsum;
    Context context;
    ArrayList<BarEntry> entries = new ArrayList<>();
    int selectedPosition=0;
    BarDataSet dataset;
    List<OverallPercentage> posts;
    ArrayList<String> labels = new ArrayList<String>();
    Custom_Home_Orders ordersadapter;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> extras = new ArrayList<String>();
    ArrayList<String> Product = new ArrayList<String>();
    ArrayList<String> Price = new ArrayList<String>();
    AutoCompleteTextView edtCode,edtProduct;
    EditText edtQuantity,edtProduct1,edtPrice;
    TextView edtOrderDate,edtOrderdueDate,txtSubmit,txtSave,txtCancel,txtTotal;
    ImageView imgSearch,imgFilter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rank_duplicate, container, false);
        context=view.getContext();
        final Calendar calendar = Calendar.getInstance();
        //	Calendar cal=Calendar.getInstance();
            imgSearch=(ImageView)view.findViewById(R.id.imgBrowse);
        imgFilter=(ImageView)view.findViewById(R.id.imgBrowse1);
        imgFilter=(ImageView)view.findViewById(R.id.imgBrowse1);
        imgFilter=(ImageView)view.findViewById(R.id.imgBrowse1);
        SimpleDateFormat month_date = new SimpleDateFormat("yyyy-MM-dd");
        //spinnerSubject=(MaterialSpinner)rootView.findViewById(R.id.spinnerMonth);
        //tvHomework=(TextView)rootView.findViewById(R.id.textTitle);
        //spinnerSubject.setBaseColor(context.getResources().getColor(R.color.white));
        monthName = month_date.format(calendar.getTime());
        names.add("13");
//        names.add("122");
//        names.add("11");
//        names.add("123");
//        names.add("121");
//        names.add("111");

        extras.add("111");
        Product.add("bicycle");
        Price.add("1000");
//        extras.add("100");
//        extras.add("12");
//        extras.add("1234");
//        extras.add("11");
//        extras.add("11");
        lv1 = (ListView)view.findViewById(R.id.lvorder);
        edtProduct = (AutoCompleteTextView)view.findViewById(R.id.edtclient);
        Constants.PartyList.clear();
        Constants.addParty();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.sammplelistitemabsent,R.id.tv,Constants.PartyList);
        edtProduct.setAdapter(adapter);
        edtOrderDate = (TextView)view.findViewById(R.id.edtorderdate);
        edtOrderdueDate = (TextView)view.findViewById(R.id.edtOrderdue);

        edtProduct1 = (EditText)view.findViewById(R.id.edtProduct);
        edtPrice = (EditText)view.findViewById(R.id.edtPrice);
        edtQuantity = (EditText)view.findViewById(R.id.edtQuantity);
        edtCode = (AutoCompleteTextView)view.findViewById(R.id.edtCode1);
        txtSubmit = (TextView)view.findViewById(R.id.txtSubmit);
        txtTotal = (TextView)view.findViewById(R.id.txtTotal);
        txtSave = (TextView)view.findViewById(R.id.txtSave);
        txtCancel = (TextView)view.findViewById(R.id.txtCancel);

        Constants.Productlist.clear();
        Constants.addProducts();
        ArrayAdapter<String> adapterCode=new ArrayAdapter<String>(context,R.layout.sammplelistitemabsent,R.id.tv,Constants.Productlist);
        edtCode.setAdapter(adapter);
//        edtProduct.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Intent in = new Intent(context, SectionedListActivity.class);
//                getActivity().startActivityForResult(in, 6);
//            }
//
//
//        });
        ordersadapter=new Custom_Home_Orders(context,names,extras,Product,Price);
        lv1.setAdapter(ordersadapter);
//        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                Config.SELECTEDPOSITION=position;
//                edtProduct1.setText(Product.get(position).toString());
//                edtPrice.setText(Price.get(position).toString());
//                edtQuantity.setText(extras.get(position).toString());
//                edtCode.setText(names.get(position).toString());
////                        .add(.toString());
////                .add(.getText().toString());
////                .add(.getText().toString());
////                .add(.getText().toString());
//
//            }
//        });
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        edtOrderDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(2014, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
            }
        });
//        lv1.setAdapter(new Custom_Order(this, txttl1, imgtlbg, imgtlround, imgtlloc, txttl2, txttl3, txttl5, txttl6, txttl7, txttl4));

        imgSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent in = new Intent(context, SectionedListBeforeFilter.class);
                Constants.countries=Constants.PartyList;
                Config.filterfrom="party";
                context.startActivity(in);
            }
        });
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, SectionedListBeforeFilter.class);
                Constants.countries=Constants.Productlist;
                Config.filterfrom="product";
                context.startActivity(in);
            }
        });
        txtSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Product.add(edtProduct1.getText().toString());
                Price.add(""+(Integer.parseInt(edtQuantity.getText().toString())*40));
                totalsum=Integer.parseInt(edtQuantity.getText().toString())*40;
                txtTotal.setText("Total " + totalsum + 1000);
                extras.add(edtQuantity.getText().toString());
                names.add(edtCode.getText().toString());
                ordersadapter.notifyDataSetChanged();
                edtProduct1.setText("");//.toString();
                edtPrice.setText("");//.toString();.getText().toString();
                edtQuantity.setText("");//.toString();.getText().toString();
                edtCode.setText("");//.toString();.getText().toString();
            }
        });
        txtSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Product.add(edtProduct1.getText().toString());
                totalsum=Integer.parseInt(edtQuantity.getText().toString())*40;
                txtTotal.setText("Total "+totalsum+1000);
                Price.add(""+(Integer.parseInt(edtQuantity.getText().toString())*40));
                extras.add(edtQuantity.getText().toString());
                names.add(edtCode.getText().toString());
                ordersadapter.notifyDataSetChanged();
                edtProduct1.setText("");//.toString();
                edtPrice.setText("");//.toString();.getText().toString();
                edtQuantity.setText("");//.toString();.getText().toString();
                edtCode.setText("");//.toString();.getText().toString();
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                edtProduct1.setText("");//.toString();
                edtPrice.setText("");//.toString();.getText().toString();
                edtQuantity.setText("");//.toString();.getText().toString();
                edtCode.setText("");//.toString();.get
                ordersadapter.remove(Config.SELECTEDPOSITION);

            }
        });
        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day)
    {
        //Toast.makeText(context, "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();

        String newMonth = null;
        if((month+1)<10)
        {
            newMonth="0"+String.valueOf((month+1));
        }
        else
        {
            newMonth=String.valueOf((month+1));
        }
        //int montho=Integer.parseInt(newMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        monthName=year+"/"+newMonth+"/"+day;
        edtOrderDate.setText(monthName);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(monthName)); // Now use today date.
        c.add(Calendar.DATE, 7); // Adding 5 days
        String output = sdf.format(c.getTime());
//        System.out.println(output);
        edtOrderdueDate.setText(output);
//        tvDisplayDate.setText(year+"-"+newMonth+"-"+day);
//        new GetMarks12().execute();

    }

    public void filledittext()
    {
        edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
        edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
        edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
        edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if(requestCode == 6 && resultCode == Activity.RESULT_OK)
        {
            edtProduct.setText(Constants.selectedclient);

            //some code
        }
    }

    public class Custom_Home_Orders extends BaseAdapter
    {
        Context context;
        ArrayList<String> name,extra,product,price;
        String[] txthpright;
        //    String[] txtsectional;
//    String[] imgsectional;
        ArrayAdapter<String> adapter;
        private  LayoutInflater inflater=null;

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

                holder.edtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus)
                        {
                            Config.SELECTEDPOSITION = position;
                            edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
                            edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
                            edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
                            edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
                        }
                    }
                });
                holder.tvPrice.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                        Config.SELECTEDPOSITION=position;
                        edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
                        edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
                        edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
                        edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
//                    FragmentCategoryItemRank itemRank=new FragmentCategoryItemRank();
//                    itemRank.filledittext();

                    }
                });
                holder.edtQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            Config.SELECTEDPOSITION = position;
                            edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
                            edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
                            edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
                            edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
                        }
                    }
                });
                holder.edtcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if(hasFocus)
                        {
                            Config.SELECTEDPOSITION=position;
                            edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
                            edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
                            edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
                            edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
                        }
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
                            Log.e("Error", ex.toString());
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

}
