package amigoinn.example.v4sales;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import amigoinn.adapters.Custom_Home_Orders;


import com.example.v4sales.R;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import amigoinn.adapters.SectionedListActivityForFilters;
import amigoinn.adapters.SectionedListBeforeFilter;
import amigoinn.db_model.CartInfo;
import amigoinn.db_model.ClientInfo;
import amigoinn.db_model.ProductInfo;
import amigoinn.db_model.UserInfo;
import amigoinn.models.OverallPercentage;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;
import amigoinn.walkietalkie.Constants;
import amigoinn.walkietalkie.DatabaseHandler1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Manthan on 28/09/2015.
 */
public class FragmentCategoryItemRank extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    View view;
    public List<String> list = new ArrayList<String>();
    ListView lv1;
    public static final String DATEPICKER_TAG = "datepicker";
    String monthName;
    int totalsum;
    Context context;
    DatabaseHandler1 handler1;
    ArrayList<BarEntry> entries = new ArrayList<>();
    int selectedPosition = 0;
    BarDataSet dataset;
    List<OverallPercentage> posts;
    ArrayList<String> labels = new ArrayList<String>();
    Custom_Home_Orders ordersadapter;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> extras = new ArrayList<String>();
    ArrayList<String> Product = new ArrayList<String>();
    ArrayList<String> Price = new ArrayList<String>();
//        AutoCompleteTextView edtCode,edtProduct;
//    EditText edtQuantity,edtProduct1,edtPrice;
    TextView edtOrderDate, edtOrderdueDate, edtclient;
    //        txtSubmit,txtSave,txtCancel,txtTotal;
    ImageView imgSearch;
    TextView txtSubmit;

    ImageView imgClientAdd, add_product;
    ArrayList<CartInfo> cart = new ArrayList<>();
    String cid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_order_layout, container, false);
        context = view.getContext();
        final Calendar calendar = Calendar.getInstance();
        //	Calendar cal=Calendar.getInstance();
        imgSearch = (ImageView) view.findViewById(R.id.imgBrowse);


        imgClientAdd = (ImageView) view.findViewById(R.id.imgClientAdd);
        add_product = (ImageView) view.findViewById(R.id.add_product);

        edtclient = (EditText) view.findViewById(R.id.edtclient);
        add_product = (ImageView) view.findViewById(R.id.add_product);
        txtSubmit = (TextView) view.findViewById(R.id.txtSubmit);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                makeJson();
                sendData();
            }
        });

        imgClientAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getActivity(), AddClientActivity.class);
                startActivityForResult(start, 112);
//                Fragment fragment = null;
//                Bundle args = new Bundle();
//                FragmentManager frgManager = getFragmentManager();
//                fragment = new ClientListSectionedActivity();
//                fragment.setArguments(args);
//                frgManager.beginTransaction()
//                        .replace(R.id.content_frame, fragment).commit();
            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getActivity(), AddProductItemActivity.class);
                startActivityForResult(start, 111);
//                Fragment fragment = null;
//                Bundle args = new Bundle();
//                FragmentManager frgManager = getFragmentManager();
//                fragment = new ProductListSectionedActivity();
//                fragment.setArguments(args);
//                frgManager.beginTransaction()
//                        .replace(R.id.content_frame, fragment).commit();
            }
        });

        imgSearch = (ImageView) view.findViewById(R.id.imgBrowse);
        handler1 = new DatabaseHandler1(context);
//        imgFilter=(ImageView)view.findViewById(R.id.imgBrowse1);
//        imgFilter=(ImageView)view.findViewById(R.id.imgBrowse1);
        SimpleDateFormat month_date = new SimpleDateFormat("yyyy-MM-dd");
        monthName = month_date.format(calendar.getTime());
        names.add("13");
        extras.add("111");
        Product.add("bicycle");
        Price.add("1000");
        lv1 = (ListView) view.findViewById(R.id.lvorder);
        Constants.PartyList.clear();
        Constants.addParty();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.sammplelistitemabsent, R.id.tv, Constants.PartyList);
        edtOrderDate = (TextView) view.findViewById(R.id.edtorderdate);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        edtOrderDate.setText(formattedDate);
        edtOrderDate.setEnabled(false);
        edtOrderdueDate = (TextView) view.findViewById(R.id.edtOrderdue);
        Constants.Productlist.clear();
        Constants.addProducts();
        String cid;

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        edtOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setVibrate(false);
                datePickerDialog.setYearRange(2014, 2028);
                datePickerDialog.setCloseOnSingleTapDay(false);
                datePickerDialog.show(getFragmentManager(), DATEPICKER_TAG);
            }
        });
//        lv1.setAdapter(new Custom_Order(this, txttl1, imgtlbg, imgtlround, imgtlloc, txttl2, txttl3, txttl5, txttl6, txttl7, txttl4));

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, SectionedListBeforeFilter.class);
                Constants.countries = Constants.PartyList;
                Config.filterfrom = "party";
                context.startActivity(in);
            }
        });

        DatabaseHandler1 handler = new DatabaseHandler1(context);
//        ArrayList<String> mastergrouplist = handler.getMastergroup();
//        ArrayList<String> reportinggrouplist = handler.getReportingGroupcode();
        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        //Toast.makeText(context, "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();

        String newMonth = null;
        if ((month + 1) < 10) {
            newMonth = "0" + String.valueOf((month + 1));
        } else {
            newMonth = String.valueOf((month + 1));
        }
        //int montho=Integer.parseInt(newMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        monthName = year + "/" + newMonth + "/" + day;
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
//        edtProduct1.setText(Product.get(Config.SELECTEDPOSITION).toString());
//        edtPrice.setText(Price.get(Config.SELECTEDPOSITION).toString());
//        edtQuantity.setText(extras.get(Config.SELECTEDPOSITION).toString());
//        edtCode.setText(names.get(Config.SELECTEDPOSITION).toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        getActivity();

        if (requestCode == 6 && resultCode == Activity.RESULT_OK) {
//            edtProduct.setText(Constants.selectedclient);

            //some code
        }

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == 111)
            {
                if (data != null)
                {
                    String pid = data.getStringExtra("product_id");
                    int qty = data.getIntExtra("qty", 0);
                    ProductInfo pinfo = ProductInfo.getProductInfoById(pid);
                    CartInfo cinfo = new CartInfo();
                    if (pinfo != null)
                    {
                        cinfo.product = pinfo.product;
                        cinfo.qty = qty;
                        double price = Double.parseDouble(pinfo.Retail_Price);
                        double total = qty * price;
                        cinfo.total = String.valueOf(total);
                        cinfo.StockNo = pid;
                        cinfo.rete = pinfo.Retail_Price;
                        cart.add(cinfo);

                    }
                    ordersadapter = new Custom_Home_Orders(getActivity(), cart);
                    lv1.setAdapter(ordersadapter);


                }


            } else if (requestCode == 112) {
                if (data != null) {
                    cid = data.getStringExtra("client_id");
                    ClientInfo c_info = ClientInfo.getClintInfoById(cid);
                    if (c_info != null) {
                        edtclient.setText(c_info.name);
                    }

                }
            }
        }
    }


    //////////////////////

    public class Custom_Home_Orders extends BaseAdapter {
        public Activity context;
        LayoutInflater inflater;

        ArrayList<CartInfo> cart_list;

        public Custom_Home_Orders(Activity context, ArrayList<CartInfo> list)

        {
            super();
            this.context = context;
            cart_list = list;
            this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return cart_list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        class Holder {
            TextView name;
            TextView txtPrice;
            EditText txtQty;
            TextView edtcode, edtdate , txtRate;

        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub

            Holder hv;

            if (arg1 == null) {
                hv = new Holder();
                arg1 = inflater.inflate(R.layout.custom_order_home, null);
                //hv.edtcode = (TextView) arg1.findViewById(R.id.edtcode);
                hv.edtdate = (TextView) arg1.findViewById(R.id.edtdate);
                hv.txtQty = (EditText) arg1.findViewById(R.id.txtQty);
                hv.txtPrice = (TextView) arg1.findViewById(R.id.txtprice);
                hv.txtRate = (TextView) arg1.findViewById(R.id.txtRate);
                arg1.setTag(hv);
            } else {
                hv = (Holder) arg1.getTag();
            }

            CartInfo info = cart_list.get(arg0);
            if (info != null) {
//                hv.edtcode.setText(info.StockNo);
                hv.edtdate.setText(info.StockNo+"\n"+info.product);
                hv.txtRate.setText(info.rete);
                hv.txtQty.setText(String.valueOf(info.qty));
                hv.txtPrice.setText(info.total);
            }

            return arg1;
        }

    }

    public String makeJson() {
        String str1 = edtOrderDate.getText().toString();
        String str2 = edtOrderdueDate.getText().toString();
        String data = "{\"userid\":\"%s\",\"client_code\":\"%s\",\"order_date\":\"%s\",\"due_date\":\"%s\",\"devicecode\":\"%s\",\"products\":\"%s\"}";
        String strr = GetJsonForPackage();
        data = String.format(data, UserInfo.getUser().login_id, cid, str1, str2, "0001", strr);
        return data;
    }

    public String GetJsonForPackage() {
        String mainjson = "[%s]";
        String inner = "{\"product_code\":%s,\"product_qty\":%s,\"product_price\":%s}";
        ArrayList<String> m_iners = new ArrayList<String>();
        for (CartInfo cartPackInfo : cart) {
            inner = String.format(inner, cartPackInfo.StockNo,
                    String.valueOf(cartPackInfo.qty), String.valueOf(cartPackInfo.total));
            m_iners.add(inner);
        }
        mainjson = String.format(mainjson, join(m_iners, ","));
        return mainjson;

    }

    public String join(List<? extends CharSequence> s, String delimiter) {
        int capacity = 0;
        int delimLength = delimiter.length();
        Iterator<? extends CharSequence> iter = s.iterator();
        if (iter.hasNext()) {
            capacity += iter.next().length() + delimLength;
        }

        StringBuilder buffer = new StringBuilder(capacity);
        iter = s.iterator();
        if (iter.hasNext()) {
            buffer.append(iter.next());
            while (iter.hasNext()) {
                buffer.append(delimiter);
                buffer.append(iter.next());
            }
        }
        return buffer.toString();
    }

    public void sendData()
    {
        showProgress();
        String data = makeJson();
        if (data != null && data.length() > 0)
        {
            ServiceHelper helper = new ServiceHelper(ServiceHelper.ADD_ORDER, ServiceHelper.RequestMethod.POST, data);
            helper.call(new ServiceHelper.ServiceHelperDelegate()
            {
                @Override
                public void CallFinish(ServiceResponse res)
                {
                    hideProgress();
                    if (res.RawResponse != null)
                    {
                        Toast.makeText(context,"Order placed successfully!",Toast.LENGTH_LONG).show();
//                        Intent start = new Intent(getActivity(), LeftMenusActivity.class);
//                        startActivity(start);
//                        getActivity().finish();
                    }
                    else
                    {

                    }

                }

                @Override
                public void CallFailure(String ErrorMessage) {
                    hideProgress();
                    if (ErrorMessage != null) {
                        Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG).show();
                    }

                }
            });
        } else {
            Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG).show();
        }


    }


}



