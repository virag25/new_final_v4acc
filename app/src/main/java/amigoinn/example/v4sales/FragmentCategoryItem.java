package amigoinn.example.v4sales;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;
import com.squareup.okhttp.internal.Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import amigoinn.adapters.Custom_Home_tasks;
import amigoinn.db_model.ClassCombInfo;
import amigoinn.db_model.GenLookInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.ProductInfo;
import amigoinn.db_model.UserInfo;
import amigoinn.modallist.Combo12;
import amigoinn.modallist.GenLookup;
import amigoinn.models.MyPojotaskDetails;
import amigoinn.models.MyPojotaskList;
import amigoinn.models.OverallPercentage;
import amigoinn.models.Pending;
import amigoinn.models.TaskDetails;
import amigoinn.models.Today;
import amigoinn.servicehelper.ApiHandler;
import amigoinn.walkietalkie.DatabaseHandler1;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Manthan on 28/09/2015.
 */
public class FragmentCategoryItem extends Fragment
{
    View view;
   public List<String> list = new ArrayList<String>();

    Context context;
    BarChart chart;
    int count=0;
    String selectedstatus="",taskid="";
    DatabaseHandler1 handler1;
    CategoryGridAdpter adpter;
    TextView tv_pricesort,tv_filter;
    ArrayList<GenLookInfo> gen_lookup =new ArrayList<>();
    ArrayList<ClassCombInfo> class12combolist =new ArrayList<>();
    ArrayList<BarEntry> entries = new ArrayList<>();
    BarDataSet dataset;
    List<OverallPercentage> posts;
    public static ArrayList<String> names = new ArrayList<String>();
    public static ArrayList<String> namespending = new ArrayList<String>();
    public static ArrayList<String> tasksList = new ArrayList<String>();
    List<Today> todaystasks=new ArrayList<>();
    List<MyPojotaskDetails> subtasks=new ArrayList<>();
    List<Pending> pendingstasks=new ArrayList<>();
    ListView listTasks,listPending;
    Custom_Home_tasks tasks,pending_tasks;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category_item, container, false);
        context=view.getContext();
//        names.add("Write blog post");
//        names.add("Schedul meeting with Ryan o.");
//        names.add("Make tutorial");
//        names.add("Write blog post");
//        names.add("Schedul meeting with Ryan o.");
//        names.add("Make tutorial");
//
//        namespending.add("Write blog post");
//        namespending.add("Schedul meeting with Ryan o.");
//        namespending.add("Make tutorial");
//        namespending.add("Write blog post");
//        namespending.add("Schedul meeting with Ryan o.");
//        namespending.add("Make tutorial");
//        extras.add("View");
//        extras.add("View");
//        extras.add("View");
//        extras.add("View");
//        extras.add("View");
//        extras.add("View");

//        tasksList.add("Write blog post");
//        tasksList.add("Schedul meeting with Ryan o.");
//        tasksList.add("Make tutorial");
//        tasksList.add("Write blog post");
//        namespending.add("Schedul meeting with Ryan o.");
//        namespending.add("Make tutorial");
        listTasks=(ListView)view.findViewById(R.id.lvhomepagetasks);
//        tasks=new Custom_Home_tasks(context,names,extras);
//        listTasks.setAdapter(tasks);

        listPending=(ListView)view.findViewById(R.id.lvhomepagetasks1);
        handler1=new DatabaseHandler1(context);
//        pending_tasks=new Custom_Home_tasks(context,names,extras);
//        listTasks.setAdapter(pending_tasks);
        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                QuestionDialog("1");
//                Intent ina=new Intent(context,AndroidDatabaseManager.class);
//                startActivity(ina);
                callSubTaskAPI(String.valueOf(todaystasks.get(position).getVtasksid()),todaystasks.get(position).getVtaskname());
            }
        });

        listPending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callSubTaskAPI(String.valueOf(pendingstasks.get(position).getVtasksid()),pendingstasks.get(position).getVtaskname());
//                QuestionDialog("1");
//                Intent ina=new Intent(context,AndroidDatabaseManager.class);
//                startActivity(ina);
            }
        });
        try
        {
            //createListview1();
            callAPI(UserInfo.getUser().user_code);
        }
        catch (Exception ex)
        {

        }
//
           try
           {

//               new GetMarks().execute();
           }
           catch(Exception ex)
           {
               Log.e("Error",ex.toString());
           }


try
{
//    ArrayList<ProductInfo> products = handler1.getproductforItemgroups("Home Care");
//    ArrayList<ProductInfo> producta = handler1.getproductforReportingGroupcode("Automobile");
//    ArrayList<ProductInfo> productI = handler1.getproductforMasterGroup("Electronics");
}
catch(Exception ex)
{

}
        return view;
    }

    public void loadGENLOOKUPS()
    {
     //   showProgress();
        GenLookup.Instance().DoProductCall(new ModelDelegates.ModelDelegate<GenLookInfo>()
        {
            @Override
            public void ModelLoaded(ArrayList<GenLookInfo> list)
            {
       //         hideProgress();
//                Collections.sort(list, new Comparator<GenLookInfo>() {
//                    @Override
//                    public int compare(GenLookInfo s1, GenLookInfo s2) {
//                        return s1.category.compareToIgnoreCase(s2.category);
//                    }
//                });
//                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                gen_lookup = list;
                loadClass12Combo();
                //setbaseadapter();
//                DatabaseHandler1 handler1=new DatabaseHandler1(v.getContext());
//                ArrayList<String> categories=handler1.getCategoris();
//                ArrayList<String> itemgroup=handler1.getItemgroup();
            }

            @Override
            public void ModelLoadFailedWithError(String error)
            {
                //hideProgress();
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadClass12Combo()
    {
        //   showProgress();
        Combo12.Instance().DoProductCall(new ModelDelegates.ModelDelegate<ClassCombInfo>()
        {
            @Override
            public void ModelLoaded(ArrayList<ClassCombInfo> list)
            {
                //         hideProgress();
//                Collections.sort(list, new Comparator<GenLookInfo>() {
//                    @Override
//                    public int compare(GenLookInfo s1, GenLookInfo s2) {
//                        return s1.category.compareToIgnoreCase(s2.category);
//                    }
//                });
//                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                class12combolist = list;
                //setbaseadapter();
//                DatabaseHandler1 handler1=new DatabaseHandler1(v.getContext());
//                ArrayList<String> categories=handler1.getCategoris();
//                ArrayList<String> itemgroup=handler1.getItemgroup();
            }

            @Override
            public void ModelLoadFailedWithError(String error)
            {
                //hideProgress();
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createListview1()
    {
        try
        {
            final MyBaseAdapter adapter = new MyBaseAdapter();
            listTasks.setAdapter(adapter);
            final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                    new SwipeToDismissTouchListener<>(
                            new ListViewAdapter(listTasks),
                            new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(ListViewAdapter view, int position)
                                {
//											String name=mock.get(0).getSubjectName().get(position);
//											String id=mock.get(0).getId().get(position);
                                    //String name=listStudents.get(position).getName();
                                    //message.add(date.get(position));
                                    adapter.remove(position);
                                    names.remove(position);

                                    TaskDetails tasks=new TaskDetails();
                                    tasks.setTask_Id(String.valueOf(todaystasks.get(position).getVtasksid()));
                                    tasks.setTask_AsigneerId(todaystasks.get(position).getAsname().toString());
                                    tasks.setTask_AsigneeId(UserInfo.getUser().login_id);
                                    tasks.setTask_Status("C");
                                    tasks.setTask_UploadStatus("N");
                                    handler1.addTaskStatus(tasks);
                                    try
                                    {
                                        selectedstatus="Completed";
                                        taskid=String.valueOf(todaystasks.get(position).getVtasksid());
                                        new updatetaskstatus().execute();
                                    }
                                    catch (Exception ex)
                                    {

                                    }

                                    //listStudents.remove(position);
//											Intent in=new Intent(view.getContext(),AutomaticPhotoActivity.class);
//											SharedPreferences preferences=getSharedPreferences("sectionid", Context.MODE_PRIVATE);
//
//											String sectionid=preferences.getString("sectionid", "");
//											in.putExtra("roll", id);
//											in.putExtra("name", name);
//											in.putExtra("id",sectionid);
//
//											startActivity(in);

                                }
                            });
            listTasks.setOnTouchListener(touchListener);
            listTasks.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
            listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (touchListener.existPendingDismisses()) {
                        touchListener.undoPendingDismiss();
                    } else
                    {
//                        Intent ina=new Intent(context,AndroidDatabaseManager.class);
//                        startActivity(ina);
                        callSubTaskAPI(String.valueOf(todaystasks.get(position).getVtasksid()),todaystasks.get(position).getVtaskname());
//                        QuestionDialog(names.get(position));
                        // Toast.makeText(ListViewActivity.this, "Position " + position, LENGTH_SHORT).show();
                    }
                }
            });

            final MyBaseAdapter1 adapter1 = new MyBaseAdapter1();
            listPending.setAdapter(adapter1);
            final SwipeToDismissTouchListener<ListViewAdapter> touchListener1 =
                    new SwipeToDismissTouchListener<>(
                            new ListViewAdapter(listPending),
                            new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                                @Override
                                public boolean canDismiss(int position) {
                                    return true;
                                }

                                @Override
                                public void onDismiss(ListViewAdapter view, int position) {
//											String name=mock.get(0).getSubjectName().get(position);
//											String id=mock.get(0).getId().get(position);
                                    //String name=listStudents.get(position).getName();
                                    //message.add(date.get(position));
                                    adapter1.remove(position);
                                    namespending.remove(position);
                                    TaskDetails tasks=new TaskDetails();
                                    tasks.setTask_Id(pendingstasks.get(position).getVtasksid().toString());
                                    tasks.setTask_AsigneerId(pendingstasks.get(position).getAsname().toString());
                                    tasks.setTask_AsigneeId(UserInfo.getUser().login_id);
                                    tasks.setTask_Status("C");
                                    tasks.setTask_UploadStatus("N");
                                    handler1.addTaskStatus(tasks);

                                    selectedstatus="Completed";
                                    taskid=String.valueOf(pendingstasks.get(position).getVtasksid());
                                    new updatetaskstatus().execute();
                                    //listStudents.remove(position);
//											Intent in=new Intent(view.getContext(),AutomaticPhotoActivity.class);
//											SharedPreferences preferences=getSharedPreferences("sectionid", Context.MODE_PRIVATE);
//
//											String sectionid=preferences.getString("sectionid", "");
//											in.putExtra("roll", id);
//											in.putExtra("name", name);
//											in.putExtra("id",sectionid);
//
//											startActivity(in);

                                }
                            });
            listPending.setOnTouchListener(touchListener1);
            listPending.setOnScrollListener((AbsListView.OnScrollListener) touchListener1.makeScrollListener());
            listPending.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (touchListener1.existPendingDismisses()) {
                        touchListener1.undoPendingDismiss();
                    } else
                    {
                        callSubTaskAPI(String.valueOf(pendingstasks.get(position).getVtasksid()),pendingstasks.get(position).getVtaskname());
//                        QuestionDialog(namespending.get(position));
                        // Toast.makeText(ListViewActivity.this, "Position " + position, LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception ex)
        {

        }
    }

    private void callAPI(String userid)
    {
//
//        if (!Utils.checkNetwork(getApplicationContext()))
//        {
//            //  Utils.toast(context,"No Internet Connection Available!");
//            Utils.showCustomDialog("Warning!", "No Internet Connection Available!",this);
//            return;
//        }
        names.clear();
        tasksList.clear();
        pendingstasks.clear();
        namespending.clear();
        todaystasks.clear();
        Utils.ShowCustomProgress(context);
        ApiHandler.getApiService().getTasksList(userid, new Callback<MyPojotaskList>()
        {
            @Override
            public void success(MyPojotaskList res, Response response)
            {
                Utils.dismissDialog();

                loadGENLOOKUPS();
                try
                {
                    pendingstasks=res.getPending();
                    todaystasks=res.getToday();
                    for(int i=0;i<todaystasks.size();i++)
                    {
                        names.add(todaystasks.get(i).getVtaskname());
                        tasksList.add(todaystasks.get(i).getVtaskdetail());

                    }
                    for(int i=0;i<pendingstasks.size();i++)
                    {
                        namespending.add(pendingstasks.get(i).getVtaskname());
                        tasksList.add(pendingstasks.get(i).getVtaskdetail());

                    }
                    createListview1();
//                    Utils.dismissDialog();
//                    data.add(res);
//                    imageLoader.displayImage(data.get(0).getLink() + data.get(0).getData().get(0).getImage(), imgView, getProductImageDisplayOption(getApplicationContext()));
//                    rbtView.setText("Check In : " + data.get(0).getData().get(0).getIntime() + "\n" + "Check Out :  " + data.get(0).getData().get(0).getOuttime());
                } catch (Exception ex) {

                }
            }

            @Override
            public void failure(RetrofitError error)
            {
                //Utils.dismissDialog();
                Utils.dismissDialog();
                error.printStackTrace();
                //error.getMessage();
                //Utils.toast(ActivityIndividualItem.this, "Something Went Wrong");
            }
        });

    }

    public class updatetaskstatus extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener {

ProgressDialog dialog;
        int length=0;
        String result,resultP;
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            try {
                dialog.dismiss();


                if (resultP.equalsIgnoreCase("failed"))
                {

                    Toast.makeText(context, "Error in updating status!", Toast.LENGTH_LONG).show();
                } else if (length < 7)
                {
                    Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }
                else if (resultP.equalsIgnoreCase("success"))
                {
                    Toast.makeText(context, "Status  updated successfully!", Toast.LENGTH_LONG).show();

                } else
                {
                    Toast.makeText(context, "Error in updating status!", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), text, duration)
                }

            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //uiUpdate.setText("Output : ");
            dialog = new ProgressDialog(context);
            dialog.setMessage("Please Wait for a moment");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            //   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
            Drawable customDrawable = context.getResources().getDrawable(R.drawable.custom_dialog);

            // set the drawable as progress drawable
//
            dialog.setIndeterminateDrawable(customDrawable);

            dialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            InputStream is = null;
            // fetchContacts();
            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);

            // String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                String url = "http://www.v4account.net/v4webservice/updatetask.php?status="+selectedstatus+"&taskid="+taskid;
                HttpPost httppost = new HttpPost(url);
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //httppost.setEntity(new UrlEncodedFormEntity(arra));
                       /* httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");*/
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
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "");
                }
                is.close();
                resultP = sb.toString();
                length = resultP.length();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }
            //parse json data
//            if (length == 9) {
//                try {
//                    jArray = new JSONArray(result);
//                    list = new ArrayList<String>();
//
//                    if (jArray != null) {
//                        for (int i = 0; i < jArray.length(); i++) {
//                            json_data = jArray.getJSONObject(i);
//                            //   list.add(jArray.get(i).toString());
//                            //mStringArray = new String[list.size()];
//                            //mStringArray = list.toArray(mStringArray);
//
//
//                        }
//
//                    }
//                } catch (JSONException e) {
//                    Log.e("log_tag", "Error parsing data " + e.toString());
//			            /*Toast.makeText(getApplicationContext(),"NoData",
//			        	          Toast.LENGTH_LONG).show();
//*/
//                }
//            }
            return null;
        }

    }
    public class updatesubtaskstatus extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener {

        ProgressDialog dialog;
        int length=0;
        String result,resultP;
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            try {
                dialog.dismiss();


                if (resultP.equalsIgnoreCase("failed"))
                {

                    Toast.makeText(context, "Error in updating status!", Toast.LENGTH_LONG).show();
                } else if (length < 7)
                {
                    Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }
                else if (resultP.equalsIgnoreCase("success"))
                {
                    Toast.makeText(context, "Status  updated successfully!", Toast.LENGTH_LONG).show();

                } else
                {
                    Toast.makeText(context, "Error in updating status!", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), text, duration)
                }

            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //uiUpdate.setText("Output : ");
            dialog = new ProgressDialog(context);
            dialog.setMessage("Please Wait for a moment");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            //   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
            Drawable customDrawable = context.getResources().getDrawable(R.drawable.custom_dialog);

            // set the drawable as progress drawable
//
            dialog.setIndeterminateDrawable(customDrawable);

            dialog.show();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            InputStream is = null;
            // fetchContacts();
            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);

            // String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                String url = "http://www.v4account.net/v4webservice/updatesubtask.php?status="+selectedstatus+"&subtaskid="+taskid;
                HttpPost httppost = new HttpPost(url);
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //httppost.setEntity(new UrlEncodedFormEntity(arra));
                       /* httppost.setHeader("Accept", "application/json");
                        httppost.setHeader("Content-type", "application/json");*/
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
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "");
                }
                is.close();
                resultP = sb.toString();
                length = resultP.length();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }
            //parse json data
//            if (length == 9) {
//                try {
//                    jArray = new JSONArray(result);
//                    list = new ArrayList<String>();
//
//                    if (jArray != null) {
//                        for (int i = 0; i < jArray.length(); i++) {
//                            json_data = jArray.getJSONObject(i);
//                            //   list.add(jArray.get(i).toString());
//                            //mStringArray = new String[list.size()];
//                            //mStringArray = list.toArray(mStringArray);
//
//
//                        }
//
//                    }
//                } catch (JSONException e) {
//                    Log.e("log_tag", "Error parsing data " + e.toString());
//			            /*Toast.makeText(getApplicationContext(),"NoData",
//			        	          Toast.LENGTH_LONG).show();
//*/
//                }
//            }
            return null;
        }

    }

    private void callSubTaskAPI(String userid, final String usernAME)
    {
//
//        if (!Utils.checkNetwork(getApplicationContext()))
//        {
//            //  Utils.toast(context,"No Internet Connection Available!");
//            Utils.showCustomDialog("Warning!", "No Internet Connection Available!",this);
//            return;
//        }
        tasksList.clear();
        subtasks.clear();
        Utils.ShowCustomProgress(context);
        ApiHandler.getApiService().getTasksDetails(userid, new Callback<MyPojotaskDetails[]>() {
            @Override
            public void success(MyPojotaskDetails[] res, Response response)
            {
                Utils.dismissDialog();

//                loadGENLOOKUPS();
                try
                {

                    for (int i = 0; i < res.length; i++)
                    {
//                        MyPojotaskDetails task=res[i];
                        subtasks.add(res[i]);
                        tasksList.add(res[i].getVsubtaskname());
//                        tasksList.add(todaystasks.get(i).getVtaskdetail());

                    }

                    QuestionDialog(usernAME);
//                    createListview1();
//                    Utils.dismissDialog();
//                    data.add(res);
//                    imageLoader.displayImage(data.get(0).getLink() + data.get(0).getData().get(0).getImage(), imgView, getProductImageDisplayOption(getApplicationContext()));
//                    rbtView.setText("Check In : " + data.get(0).getData().get(0).getIntime() + "\n" + "Check Out :  " + data.get(0).getData().get(0).getOuttime());
                } catch (Exception ex) {

                }
            }

            @Override
            public void failure(RetrofitError error) {
                //Utils.dismissDialog();
                Utils.dismissDialog();
                error.printStackTrace();
                //error.getMessage();
                //Utils.toast(ActivityIndividualItem.this, "Something Went Wrong");
            }
        });

    }

    public int dpToPx(int dp)
    {
       DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);

    }
    public void  ParseJson(List<OverallPercentage> post)
    {

        //setContentView(chart);
        int count=0;
        for(OverallPercentage overall:post)
        {

           // labels.add(overall.getName());
            entries.add(new BarEntry(Float.parseFloat(overall.getPercentage()),count));
            count++;
        }
        dataset = new BarDataSet(entries, "# of Calls");
        chart.setDrawGridBackground(false);
        BarData data = new BarData(names, dataset);
        data.setValueTextSize(10f);
        chart.zoom(1.1f,1,1,1);
        chart.setData(data);
        //chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //Float fl=Float.labels.size();
        //chart.setVisibleXRange(labels.size(),labels.size());
        chart.setDescription("% of marks in each subject");
        chart.animateY(2000);
        //chart.zoom(1.4,1.4,100f,100f);
        chart.setVisibleXRangeMaximum(5);
        chart.setDescriptionTextSize(15f);
//        XAxis xa=chart.getXAxis();
//        xa.setTextSize(px);
        YAxis yAxis = chart.getAxisLeft();
        chart.setAlwaysDrawnWithCacheEnabled(true);
        chart.getAxisRight().setAxisMaxValue(100f);
        // chart.setRendererRightYAxis(null);
        // yAxis.setTextSize(px);

        yAxis.setAxisMaxValue(100f);
        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);

        parent.addView(chart);


    }
    public class GetMarks extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemClickListener {

        ProgressDialog dialog;
        int length;
        String result="";
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            //super.onPostExecute(result);
            try
            {
                dialog.cancel();
                if(posts.size()!= 0)
                {

                    ParseJson(posts);

                }
                else
                {

                }

            }
            catch(Exception e)
            {
               // Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //uiUpdate.setText("Output : ");
            dialog=new ProgressDialog(context);
            dialog.setMessage("Please Wait for a moment");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            //   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
            Drawable customDrawable= context.getResources().getDrawable(R.drawable.custom_dialog);

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
            SharedPreferences preferences=context.getSharedPreferences("profile",Context.MODE_PRIVATE);
            String id=preferences.getString("id","");
            //List<NameValuePair> arra= new ArrayList<NameValuePair>(2);

            // String result = "";
            try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.prismeduware.com/parentapp/marksall.php?parent_id="+id);

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();


            }
            catch(Exception e){
                Log.e("log_tag", "Error in http connection "+e.toString());
			            /*Toast.makeText(getApplicationContext(),"Please connect to Internet",
			        	          Toast.LENGTH_LONG).show();*/
            }


            //convert response to string
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson gson = gsonBuilder.create();

                posts = new ArrayList<OverallPercentage>();
                posts = Arrays.asList(gson.fromJson(new InputStreamReader(is), OverallPercentage[].class));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result=sb.toString();
               length=result.length();
            }catch(Exception e){
                Log.e("log_tag", "Error converting result "+e.toString());
            }
            //parse json data
//            if(length>15)
//            {
//                try{
////                    jArray = new JSONArray(result);
////                    list = new ArrayList<String>();
////
////                    if (jArray != null) {
////                        for (int i=0;i<jArray.length();i++){
////                            json_data = jArray.getJSONObject(i);
////                            //String id=json_data.getString("parent_id");
////                            //String name=json_data.getString("name");
////                            SharedPreferences preference=getSharedPreferences("profile", Context.MODE_PRIVATE);
////                            SharedPreferences.Editor edit=preference.edit();
////                            edit.clear();
////                            edit.putString("id",json_data.getString("parent_id"));
////                            edit.putString("name",json_data.getString("name"));
////                            edit.putString("email",json_data.getString("email"));
////                            edit.putString("password",json_data.getString("password"));
////                            edit.putString("phone",json_data.getString("phone"));
////                            edit.putString("address",json_data.getString("address"));
////                            edit.putString("profession",json_data.getString("profession"));
////
////                            edit.commit();
////                        }
//
//                    }
//                }catch(JSONException e){
//                    Log.e("log_tag", "Error parsing data "+e.toString());
//			            /*Toast.makeText(getApplicationContext(),"NoData",
//			        	          Toast.LENGTH_LONG).show();
//*/					    }
            //}
            return null;
        }

    }

    static class MyBaseAdapter extends BaseAdapter
    {

        private static final int SIZE = 100;

        //private final List<String> mDataSet = new ArrayList<>();
        private List<String> mDataSet = new ArrayList<>();
        MyBaseAdapter()
        {
            mDataSet=names;
//            for (int i = 0; i < SIZE; i++)
//                mDataSet.add(i, "This is row number " + i);
        }

        @Override
        public int getCount()
        {
            return mDataSet.size();
        }

        @Override
        public String getItem(int position) {
            return mDataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void remove(int position)
        {
         //   message.add(date.get(position));
//            names.remove(position);

            notifyDataSetChanged();
        }

        static class ViewHolder
        {
            TextView dataTextView,rollnotextView,txt_delete;
            ViewHolder(View view)
            {
                dataTextView = ((TextView) view.findViewById(R.id.txt_data1));
                rollnotextView = ((TextView) view.findViewById(R.id.txt_roll));
                txt_delete = ((TextView) view.findViewById(R.id.txt_delete));
                view.setTag(this);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ViewHolder viewHolder = convertView == null
                    ? new ViewHolder(convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listviewlayout, parent, false))
                    : (ViewHolder) convertView.getTag();

            viewHolder.dataTextView.setText(mDataSet.get(position));
            viewHolder.rollnotextView.setText("Answer");
            viewHolder.rollnotextView.setVisibility(View.GONE);
            viewHolder.rollnotextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   QuestionDialog("1");
                }
            });
            viewHolder.txt_delete.setText("swipe to remove task "+mDataSet.get(position)+" ");
            return convertView;
        }
    }

    static class MyBaseAdapter1 extends BaseAdapter
    {

        private static final int SIZE = 100;

        //private final List<String> mDataSet = new ArrayList<>();
        private List<String> mDataSet = new ArrayList<>();
        MyBaseAdapter1()
        {
            mDataSet=namespending;
//            for (int i = 0; i < SIZE; i++)
//                mDataSet.add(i, "This is row number " + i);
        }

        @Override
        public int getCount()
        {
            return mDataSet.size();
        }

        @Override
        public String getItem(int position) {
            return mDataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void remove(int position)
        {
            //   message.add(date.get(position));
//            namespending.remove(position);

            notifyDataSetChanged();
        }

        static class ViewHolder
        {
            TextView dataTextView,rollnotextView,txt_delete;
            ViewHolder(View view)
            {
                dataTextView = ((TextView) view.findViewById(R.id.txt_data1));
                rollnotextView = ((TextView) view.findViewById(R.id.txt_roll));
                txt_delete = ((TextView) view.findViewById(R.id.txt_delete));
                view.setTag(this);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ViewHolder viewHolder = convertView == null
                    ? new ViewHolder(convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listviewlayout, parent, false))
                    : (ViewHolder) convertView.getTag();

            viewHolder.dataTextView.setText(mDataSet.get(position));
            viewHolder.rollnotextView.setText("Answer");
            viewHolder.rollnotextView.setVisibility(View.GONE);
            viewHolder.rollnotextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   QuestionDialog("1");
                }
            });
            viewHolder.txt_delete.setText("swipe to remove task "+mDataSet.get(position)+" ");
            return convertView;
        }
    }

    static class MyBaseAdapterDialog extends BaseAdapter
    {

        private static final int SIZE = 100;

        //private final List<String> mDataSet = new ArrayList<>();
        private List<String> mDataSet = new ArrayList<>();
        MyBaseAdapterDialog()
        {
            mDataSet=tasksList;
//            for (int i = 0; i < SIZE; i++)
//                mDataSet.add(i, "This is row number " + i);
        }

        @Override
        public int getCount()
        {
            return mDataSet.size();
        }

        @Override
        public String getItem(int position) {
            return mDataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void remove(int position)
        {
            //   message.add(date.get(position));
//            namespending.remove(position);

            notifyDataSetChanged();
        }

        static class ViewHolder
        {
            TextView dataTextView,rollnotextView,txt_delete;
            ViewHolder(View view)
            {
                dataTextView = ((TextView) view.findViewById(R.id.txt_data1));
                rollnotextView = ((TextView) view.findViewById(R.id.txt_roll));
                txt_delete = ((TextView) view.findViewById(R.id.txt_delete));
                view.setTag(this);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            ViewHolder viewHolder = convertView == null
                    ? new ViewHolder(convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listviewlayout, parent, false))
                    : (ViewHolder) convertView.getTag();

            viewHolder.dataTextView.setText(mDataSet.get(position));
            viewHolder.rollnotextView.setText("Answer");
            viewHolder.rollnotextView.setVisibility(View.GONE);
            viewHolder.rollnotextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   QuestionDialog("1");
                }
            });
            viewHolder.txt_delete.setText("swipe to remove task "+mDataSet.get(position)+" ");
            return convertView;
        }
    }

    public  void QuestionDialog(String chapId)
    {
        final Dialog storyDialog = new Dialog(context);
        storyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        storyDialog.setContentView(R.layout.questionoutput);
        storyDialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        storyDialog.setCancelable(true);
        storyDialog.show();
//		DatabaseHandler1 handler1=new DatabaseHandler1(context);
        TextView txtTitle=(TextView)storyDialog.findViewById(R.id.txtTitle1);
        List<String> message1 = new ArrayList<String>();
        message1.add(0, "Task description comes here!\n You can view your task here! ");
        ListView lstSubtask = (ListView) storyDialog.findViewById(R.id.listTask);
        final MyBaseAdapterDialog adapter = new MyBaseAdapterDialog();
        lstSubtask.setAdapter(adapter);
        txtTitle.setText(chapId);
        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(lstSubtask),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
//											String name=mock.get(0).getSubjectName().get(position);
//											String id=mock.get(0).getId().get(position);
                                //String name=listStudents.get(position).getName();
                                //message.add(date.get(position));
                                adapter.remove(position);
                                tasksList.remove(position);
                                selectedstatus="Completed";
                                taskid=String.valueOf(pendingstasks.get(position).getVtasksid());
                                new updatesubtaskstatus().execute();
                                //listStudents.remove(position);
//											Intent in=new Intent(view.getContext(),AutomaticPhotoActivity.class);
//											SharedPreferences preferences=getSharedPreferences("sectionid", Context.MODE_PRIVATE);
//
//											String sectionid=preferences.getString("sectionid", "");
//											in.putExtra("roll", id);
//											in.putExtra("name", name);
//											in.putExtra("id",sectionid);
//
//											startActivity(in);

                            }
                        });
        lstSubtask.setOnTouchListener(touchListener);
        lstSubtask.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        lstSubtask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)

            {
                if (touchListener.existPendingDismisses())
                {
                    touchListener.undoPendingDismiss();
                }
                else
                {
                    String detaildata="Details : "+subtasks.get(position).getVsubtaskdetails()+"\nStatus : "+subtasks.get(position).getVsubtaskstatus()+"\nAssigned By : "+subtasks.get(position).getAssigenername();
                    TaskDetailsDialog(subtasks.get(position).getVsubtaskname(),detaildata);
                    // Toast.makeText(ListViewActivity.this, "Position " + position, LENGTH_SHORT).show();
                }
            }
        });


//        TextView txtTile1 = (TextView) storyDialog.findViewById(R.id.txtDescription);
//        txtTile1.setText("Task description comes here!\n You can view your task here! ");
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.sammplelistitem,R.id.tv,message1);
//
//        listMessage.setAdapter(adapter);
//        listMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//				lessonname=chaptername.get(position);
//				CommonUtils.chapter_name=lessonname;
//				CommonUtils.chapter_id=chapterId.get(position);
//				CommonUtils.Total_Marks=TotalMarks.get(position);
//				CommonUtils.Exam_Time=examtime.get(position);
//				DatabaseHandler1 handler=new DatabaseHandler1(Lessons.this);
//				int count=handler.getMessageCount(CommonUtils.chapter_id);
//				Intent in=new Intent(CreatedExams.this,ExamsActivity.class);
//				in.putExtra("lesson",CommonUtils.chapter_id);
//				in.putExtra("lessonName",CommonUtils.chapter_name);
//				switch (position)
//				{
//
//					case 0:
//						Styletrieb.examtype="Normal";
//						startActivity(in);
//						storyDialog.dismiss();
//						//   finish();
//						break;
//					case 1:
//						Styletrieb.examtype="New";
//						startActivity(in);
//						storyDialog.dismiss();
//						// finish();
//						break;
//					case 2:
//						Styletrieb.examtype="Unattempted";
//						startActivity(in);
//						storyDialog.dismiss();
//						//finish();
//						break;
//					case 4:
//						Styletrieb.examtype="Right";
//						startActivity(in);
//						storyDialog.dismiss();
//						//finish();
//						break;
//					case 3:
//						Styletrieb.examtype="Wrong";
//						startActivity(in);
//						storyDialog.dismiss();
//						//finish();
//						break;
//				}

//            }
//        );
    }
    public  void TaskDetailsDialog(String chapId,String details)
    {
        final Dialog storyDialog = new Dialog(context);
        storyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        storyDialog.setContentView(R.layout.tasksdetails);
        storyDialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        storyDialog.setCancelable(true);
        storyDialog.show();
//		DatabaseHandler1 handler1=new DatabaseHandler1(context);
        TextView txtTitle=(TextView)storyDialog.findViewById(R.id.txtTitle1);
        TextView txtDescription=(TextView)storyDialog.findViewById(R.id.txtDescription);
        txtDescription.setText(details);
//        List<String> message1 = new ArrayList<String>();
//        message1.add(0, "Task description comes here!\n You can view your task here! ");
//        ListView lstSubtask = (ListView) storyDialog.findViewById(R.id.listTask);
//        final MyBaseAdapterDialog adapter = new MyBaseAdapterDialog();
//        lstSubtask.setAdapter(adapter);
        txtTitle.setText(chapId);
//        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
//                new SwipeToDismissTouchListener<>(
//                        new ListViewAdapter(lstSubtask),
//                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
//                            @Override
//                            public boolean canDismiss(int position) {
//                                return true;
//                            }
//
//                            @Override
//                            public void onDismiss(ListViewAdapter view, int position) {
////											String name=mock.get(0).getSubjectName().get(position);
////											String id=mock.get(0).getId().get(position);
//                                //String name=listStudents.get(position).getName();
//                                //message.add(date.get(position));
//                                adapter.remove(position);
//                                tasksList.remove(position);
//                                //listStudents.remove(position);
////											Intent in=new Intent(view.getContext(),AutomaticPhotoActivity.class);
////											SharedPreferences preferences=getSharedPreferences("sectionid", Context.MODE_PRIVATE);
////
////											String sectionid=preferences.getString("sectionid", "");
////											in.putExtra("roll", id);
////											in.putExtra("name", name);
////											in.putExtra("id",sectionid);
////
////											startActivity(in);
//
//                            }
//                        });
//        lstSubtask.setOnTouchListener(touchListener);
//        lstSubtask.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
//        lstSubtask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//
//            {
//                if (touchListener.existPendingDismisses()) {
//                    touchListener.undoPendingDismiss();
//                } else {
//                    //  QuestionDialog(names.get(position));
//                    // Toast.makeText(ListViewActivity.this, "Position " + position, LENGTH_SHORT).show();
//                }
//            }
//        });


//        TextView txtTile1 = (TextView) storyDialog.findViewById(R.id.txtDescription);
//        txtTile1.setText("Task description comes here!\n You can view your task here! ");
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.sammplelistitem,R.id.tv,message1);
//
//        listMessage.setAdapter(adapter);
//        listMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//				lessonname=chaptername.get(position);
//				CommonUtils.chapter_name=lessonname;
//				CommonUtils.chapter_id=chapterId.get(position);
//				CommonUtils.Total_Marks=TotalMarks.get(position);
//				CommonUtils.Exam_Time=examtime.get(position);
//				DatabaseHandler1 handler=new DatabaseHandler1(Lessons.this);
//				int count=handler.getMessageCount(CommonUtils.chapter_id);
//				Intent in=new Intent(CreatedExams.this,ExamsActivity.class);
//				in.putExtra("lesson",CommonUtils.chapter_id);
//				in.putExtra("lessonName",CommonUtils.chapter_name);
//				switch (position)
//				{
//
//					case 0:
//						Styletrieb.examtype="Normal";
//						startActivity(in);
//						storyDialog.dismiss();
//						//   finish();
//						break;
//					case 1:
//						Styletrieb.examtype="New";
//						startActivity(in);
//						storyDialog.dismiss();
//						// finish();
//						break;
//					case 2:
//						Styletrieb.examtype="Unattempted";
//						startActivity(in);
//						storyDialog.dismiss();
//						//finish();
//						break;
//					case 4:
//						Styletrieb.examtype="Right";
//						startActivity(in);
//						storyDialog.dismiss();
//						//finish();
//						break;
//					case 3:
//						Styletrieb.examtype="Wrong";
//						startActivity(in);
//						storyDialog.dismiss();
//						//finish();
//						break;
//				}

//            }
//        );
    }

}
