package amigoinn.example.v4sales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v4sales.R;

import amigoinn.adapters.Custom_Route_Home;
import amigoinn.db_model.GenLookInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.RouteInfo;
import amigoinn.db_model.UserInfo;
import amigoinn.modallist.GenLookup;
import amigoinn.modallist.RouteList;
import amigoinn.models.Datum;
import amigoinn.models.MyPojoRoute;
import amigoinn.models.MyPojotaskDetails;
import amigoinn.servicehelper.ApiHandler;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manthan on 28/09/2015.
 */
public class FragmentCategoryItemAttendance extends BaseFragment
{
    View view;
    ListView lvroute;
    Context context;
    ArrayList<String> labels = new ArrayList<String>();
//    public static String[] leftline = {"", "",""};
    List<Datum> routeinfo =new ArrayList<>();
//    public static String[] rightline = {"","",""};
//    public static String[] leftbox = {"Places", "Places","Places"};
//    public static String[] rightbox = {"55","78","13"};
//    public static int [] middot = {R.drawable.tl1middot, R.drawable.tl1middot,R.drawable.tl1middot};
//    public static int[] leftdot = {R.drawable.tl1dot,R.drawable.tl1dot,R.drawable.tl1dot};
//    public static int[] rightdot = {R.drawable.tl1dot,R.drawable.tl1dot,R.drawable.tl1dot};
    TextView txtSubmit;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category_item_attendence, container, false);
        context=view.getContext();
        lvroute = (ListView) view.findViewById(R.id.lvroute);
        txtSubmit = (TextView) view.findViewById(R.id.txtSubmit);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Intent in = new Intent(context, GoogleMapActivity.class);
                    context.startActivity(in);
                }catch (Exception ex)
                {
                    Log.e("Error", ex.toString());
                }
            }
        });

//        ArrayList<String> routes=new ArrayList<>();
//        for (int j=1;j<=10;j++)
//        {
//            routes.add("Route "+j);
//        }
//        lvroute.setAdapter(new Custom_Route_Home(view.getContext(),routes));
callRouteInfo(UserInfo.getUser().login_id,UserInfo.getUser().login_id);
        return view;
    }
//    public void loadGENLOOKUPS()
//    {
//        //   showProgress();
//        RouteList.Instance().DoClintOrder(new ModelDelegates.ModelDelegate<RouteInfo>()
//        {
//            @Override
//            public void ModelLoaded(ArrayList<RouteInfo> list) {
//                //         hideProgress();
////                Collections.sort(list, new Comparator<GenLookInfo>() {
////                    @Override
////                    public int compare(GenLookInfo s1, GenLookInfo s2) {
////                        return s1.category.compareToIgnoreCase(s2.category);
////                    }
////                });
////                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
//                routeinfo = list;
//     //           loadClass12Combo();
//                //setbaseadapter();
////                DatabaseHandler1 handler1=new DatabaseHandler1(v.getContext());
////                ArrayList<String> categories=handler1.getCategoris();
////                ArrayList<String> itemgroup=handler1.getItemgroup();
//                lvroute.setAdapter(new Custom_Route_Home(view.getContext(),routeinfo));
//            }
//
//            @Override
//            public void ModelLoadFailedWithError(String error)
//            {
//                //hideProgress();
//                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
//            }
//        });
//    }
private void callRouteInfo(String userid, final String usernAME)
{
//
//        if (!Utils.checkNetwork(getApplicationContext()))
//        {
//            //  Utils.toast(context,"No Internet Connection Available!");
//            Utils.showCustomDialog("Warning!", "No Internet Connection Available!",this);
//            return;
//        }
//    tasksList.clear();
//    subtasks.clear();
    Utils.ShowCustomProgress(context);
    ApiHandler.getApiService().getRootInfo(userid, new Callback<MyPojoRoute>()
    {
        @Override
        public void success(MyPojoRoute res, Response response)
        {

            Utils.dismissDialog();

//                loadGENLOOKUPS();
            try
            {
                routeinfo=res.getData();
//                for (int i = 0; i < res.getData().size(); i++)
//                {
////                    routeinfo.add(res.getData())
////                        MyPojotaskDetails task=res[i];
////                    subtasks.add(res[i]);
////                    tasksList.add(res[i].getVsubtaskname());
////                        tasksList.add(todaystasks.get(i).getVtaskdetail());
//
//                }
                lvroute.setAdapter(new Custom_Route_Home(view.getContext(),routeinfo));

            }
            catch (Exception ex)
            {

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



}
