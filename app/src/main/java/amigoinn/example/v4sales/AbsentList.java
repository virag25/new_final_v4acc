package amigoinn.example.v4sales;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import amigoinn.adapters.Custom_Product_Details;

import com.example.v4sales.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import amigoinn.adapters.SectionedListBeforeFilter;
import amigoinn.db_model.ClientInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.ProductDetailInfo;
import amigoinn.db_model.ProductInfo;
import amigoinn.db_model.RouteInfo;
import amigoinn.modallist.ProdetailList;
import amigoinn.models.SubjectMonth;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import amigoinn.walkietalkie.Constants;
import fr.ganfra.materialspinner.MaterialSpinner;

public class AbsentList extends BaseActivity {
    //	public Context AbsentList.this;
    RobotoTextView tvDisplayDate;
    ListView lvGood, lvBad;
    String selectedSubject;
    String monthName;
    MaterialSpinner spinnerSubject;
    List<String> good = new ArrayList<String>();
    List<SubjectMonth> subjectList = new ArrayList<SubjectMonth>();
    TextView txtFilter;
    TextView txtTitle, txtType, txtCompany;
    String pro_id;
    ProductInfo pro_info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productinformation);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            pro_id = b.getString("pro_id");
        }

        pro_info = ProductInfo.getProductInfoById(pro_id);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
//			spinnerSubject=(MaterialSpinner)rootView.findViewById(R.id.spinnerMonth);
//			spinnerSubject.setBaseColor(AbsentList.this.getResources().getColor(R.color.white));
//			monthName = month_date.format(cal.getTime());
        lvGood = (ListView) findViewById(R.id.list_view_absent);
        ArrayList<String> names = new ArrayList<>();
        names.add("Shoprite Benguela");
        names.add("SPAR CABINDA");
        names.add("JFC Comercial Sumbe");
        names.add("Martal Cardoso");
        ArrayList<String> Quantity = new ArrayList<>();
        Quantity.add("1");
        Quantity.add("1");
        Quantity.add("2");
        Quantity.add("4");
        ArrayList<String> Price = new ArrayList<>();
        Price.add("250");
        Price.add("250");
        Price.add("500");
        Price.add("1000");

        showProgress();
        ArrayList<ProductDetailInfo> pro_list = null;
        ProdetailList.Instance().DoProductDetails(new ModelDelegates.ModelDelegate<ProductDetailInfo>() {
            @Override
            public void ModelLoaded(ArrayList<ProductDetailInfo> list) {
                hideProgress();
                if (list != null && list.size() > 0) {
                    ListViewCustomAdapter adapter = new ListViewCustomAdapter(AbsentList.this, list);
                    lvGood.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void ModelLoadFailedWithError(String error) {
                hideProgress();
                Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
            }
        }, pro_id);


//        Custom_Product_Details product_details = new Custom_Product_Details(AbsentList.this, names, Quantity, Price);
//        lvGood.setAdapter(product_details);
        txtFilter = (TextView) findViewById(R.id.txtFilter);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtCompany = (TextView) findViewById(R.id.txtCompany);
        txtType = (TextView) findViewById(R.id.txttype);


        txtTitle.setText(pro_info.ItemDesc);
        txtType.setText(pro_info.model);
        txtCompany.setText(pro_info.brand);


        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(AbsentList.this, SectionedListBeforeFilter.class);
                Config.filterfrom = "product";
                Constants.Productlist.clear();
                Constants.addProducts();
                Constants.countries = Constants.Productlist;
                AbsentList.this.startActivity(in);

            }
        });
    }

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//							 Bundle savedInstanceState)
//	{
//		try {
//			View rootView = inflater.inflate(R.layout.productinformation, container, false);
//			AbsentList.this=rootView.getContext();
//			Calendar cal=Calendar.getInstance();
//			SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
////			spinnerSubject=(MaterialSpinner)rootView.findViewById(R.id.spinnerMonth);
////			spinnerSubject.setBaseColor(AbsentList.this.getResources().getColor(R.color.white));
////			monthName = month_date.format(cal.getTime());
//			lvGood = (ListView) rootView.findViewById(R.id.list_view_absent);
//			ArrayList<String>  names=new ArrayList<>();
//			names.add("Shoprite Benguela");
//			names.add("SPAR CABINDA");
//			names.add("JFC Comercial Sumbe");
//			names.add("Martal Cardoso");
//			ArrayList<String>  Quantity=new ArrayList<>();
//			Quantity.add("1");
//			Quantity.add("1");
//			Quantity.add("2");
//			Quantity.add("4");
//			ArrayList<String>  Price=new ArrayList<>();
//			Price.add("250");
//			Price.add("250");
//			Price.add("500");
//			Price.add("1000");
//			Custom_Product_Details product_details=new Custom_Product_Details(AbsentList.this,names,Quantity,Price);
//			lvGood.setAdapter(product_details);
//			txtFilter=(TextView)rootView.findViewById(R.id.txtFilter);
//			txtFilter.setOnClickListener(new View.OnClickListener()
//			{
//				@Override
//				public void onClick(View v)
//				{
//					Intent in=new Intent(AbsentList.this, SectionedListBeforeFilter.class);
//					Config.filterfrom="product";
//					Constants.Productlist.clear();
//					Constants.addProducts();
//					Constants.countries=Constants.Productlist;
//					AbsentList.this.startActivity(in);
//
//				}
//			});
////			new GetSpinnerData1().execute();
////			new GetMarks12().execute();
//
//
//
//			return rootView;
//
//		}
//		catch(Exception ex)
//		{
//			Log.e("Error",ex.toString());
//		}
//		return  null;
//	}

    private void ParseSubjectJson(final List<SubjectMonth> post) {
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AbsentList.this, android.R.layout.simple_spinner_item, post.get(0).getMonth());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSubject.setAdapter(adapter);

            spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedValue = spinnerSubject.getSelectedItem().toString();
                    if (!selectedValue.equalsIgnoreCase(spinnerSubject.getHint().toString())) {

                        monthName = post.get(0).getMonth().get(i);
                        new GetMarks12().execute();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
//		listViewMarks.setAdapter(adapter);

    }

    public class GetSpinnerData1 extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener {

        ProgressDialog dialog;
        int length;
        String result = "";

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            try {
                dialog.cancel();
                if (subjectList.size() != 0) {

                    ParseSubjectJson(subjectList);

                } else {

                }

            } catch (Exception e) {
                // Toast.makeText(AbsentList.this, "Please check your internet connection!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //uiUpdate.setText("Output : ");
            dialog = new ProgressDialog(AbsentList.this);
            dialog.setMessage("Please Wait for a moment");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            //   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
            Drawable customDrawable = AbsentList.this.getResources().getDrawable(R.drawable.custom_dialog);

            // set the drawable as progress drawable
//
            dialog.setIndeterminateDrawable(customDrawable);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            InputStream is = null;
            //fetchContacts();
            SharedPreferences preferences = AbsentList.this.getSharedPreferences("profile", Context.MODE_PRIVATE);
            String id = preferences.getString("id", "");
            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);
            String langi = "23.0481352";
            String latit = "72.5540532";
            // String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.prismeduware.com/parentapp/subjectlist.php?parent_id=" + id);

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();


            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
                        /*Toast.makeText(getApplicationContext(),"Please connect to Internet",
                                  Toast.LENGTH_LONG).show();*/
            }


            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

//				posts = new ArrayList<MarksMainModel>();
                subjectList.clear();
                subjectList = Arrays.asList(gson.fromJson(new InputStreamReader(is), SubjectMonth.class));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                // result=sb.toString();
                //length=result.length();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }
            //parse json data
            if (length > 5) {

            }
            return null;
        }

    }

    public class GetMarks12 extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener {

        ProgressDialog dialog;
        int length;
        String result = "";

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            try {
                ArrayAdapter<String> ad = new ArrayAdapter<String>(AbsentList.this, R.layout.sammplelistitemabsent, good);
//		ArrayAdapter<String> adBad = new ArrayAdapter<String>(AbsentList.this, R.layout.sammplelistitemred, bad);
                lvGood.setAdapter(ad);
//		lvBad.setAdapter(adBad);
                dialog.cancel();

            } catch (Exception e) {
                // Toast.makeText(AbsentList.this, "Please check your internet connection!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //uiUpdate.setText("Output : ");
            dialog = new ProgressDialog(AbsentList.this);
            dialog.setMessage("Please Wait for a moment");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            //   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
            Drawable customDrawable = AbsentList.this.getResources().getDrawable(R.drawable.custom_dialog);

            // set the drawable as progress drawable
//
            dialog.setIndeterminateDrawable(customDrawable);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            InputStream is = null;
            //fetchContacts();
            SharedPreferences preferences = AbsentList.this.getSharedPreferences("profile", Context.MODE_PRIVATE);
            String id = preferences.getString("id", "");
            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);
            String langi = "23.0481352";
            String latit = "72.5540532";
            // String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.prismeduware.com/parentapp/absent_full.php?parent_id=" + id + "&month=" + monthName);

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();


            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());
                        /*Toast.makeText(getApplicationContext(),"Please connect to Internet",
                                  Toast.LENGTH_LONG).show();*/
            }


            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//				GsonBuilder gsonBuilder = new GsonBuilder();
//				gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//				Gson gson = gsonBuilder.create();
//
//				posts = new ArrayList<OverallPercentage>();
//				posts = Arrays.asList(gson.fromJson(new InputStreamReader(is), OverallPercentage[].class));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
                length = result.length();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }
            //parse json data
            if (length > 5) {
                try {
                    good.clear();

                    JSONArray goodA1 = new JSONArray(result);
//						JSONArray goodA2=secondObjGood.getJSONArray("good2");
//						JSONArray goodA3=secondObjGood.getJSONArray("good3");
//						JSONArray goodA4=secondObjGood.getJSONArray("good4");
//						JSONArray goodA5=secondObjGood.getJSONArray("good5");
                    for (int i = 0; i < goodA1.length(); i++) {
                        JSONObject objData = goodA1.getJSONObject(i);
                        good.add(objData.getString("date"));

                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }
            return null;
        }

    }


    public class ListViewCustomAdapter extends BaseAdapter {
        public Activity context;
        LayoutInflater inflater;
        ArrayList<ProductDetailInfo> arr;

        public ListViewCustomAdapter(Activity context, ArrayList<ProductDetailInfo> arr_list)

        {
            super();
            this.context = context;
            this.inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            arr = arr_list;


        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr.size();
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

        public class Holder {
            LinearLayout rlMain;
            TextView txtName, txtQuantity, txtPrice;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub

            Holder hv;

            if (arg1 == null) {
                hv = new Holder();
                arg1 = inflater.inflate(R.layout.custom_product_details, null);
                hv.txtName = (TextView) arg1.findViewById(R.id.txtUsername);
                hv.txtPrice = (TextView) arg1.findViewById(R.id.txtprice);
                hv.txtQuantity = (TextView) arg1.findViewById(R.id.txtQuantity);
                arg1.setTag(hv);
            } else {
                hv = (Holder) arg1.getTag();
            }

            ProductDetailInfo pro = arr.get(arg0);
            if (pro != null) {
                if (pro.PartyId != null) {
                    ClientInfo cinfo = ClientInfo.getClintInfoById(pro.PartyId);
                    if (cinfo != null) {
                        hv.txtName.setText(cinfo.name);

                        String qty = pro.product_quantity;
                        if (qty != null) {
                            int qtty = Integer.parseInt(qty);
                            ProductInfo proinfo = ProductInfo.getProductInfoById(pro_id);
                            if (proinfo != null) {
                                double price_product = Integer.parseInt(proinfo.Retail_Price);
                                double price = qtty * price_product;
                                hv.txtPrice.setText(price + "");
                                hv.txtQuantity.setText(qty);
                            }
                        }

                    }
                }
            }


            return arg1;
        }

    }
}

