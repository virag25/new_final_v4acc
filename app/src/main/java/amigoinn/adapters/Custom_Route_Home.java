package amigoinn.adapters;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by joy on 1/21/2016.
 */

import android.view.View.OnClickListener;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import amigoinn.db_model.RouteInfo;
import amigoinn.example.v4sales.GoogleMapActivity;
import amigoinn.example.v4sales.R;
import amigoinn.example.v4sales.Utils;
import amigoinn.models.Datum;
import amigoinn.models.Datum1;
import amigoinn.models.MyPojoRoute;
import amigoinn.models.MyPojoRouteDetails;
import amigoinn.servicehelper.ApiHandler;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Custom_Route_Home extends BaseAdapter{
    ArrayList<Integer> lista;
    Random r;
    Context context;
    List<Datum> routes;
    List<Datum1> routesdetails;
    private static LayoutInflater inflater=null;


    public Custom_Route_Home(Context mainActivity,List<Datum> routelist)
    {
        // TODO Auto-generated constructor stub
        context = mainActivity;
        routes=routelist;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return routes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }





    public class Holder
    {
        RelativeLayout rltvLeft,rltvRight;
        ImageView imgroutemiddot,imgrouteleftdot,imgrouterightdot;
        TextView txtroutelineleft,txtroutelineright,txtrouteshaperight,txtrouteshapeleft;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
        TextView tv7;
        ImageView img1;
        ImageView img2;
        ImageView img3;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        try {
            rowView = inflater.inflate(R.layout.custom_route_home, null);
            holder.imgroutemiddot= (ImageView) rowView.findViewById(R.id.imgroutemiddot);
            holder.imgrouteleftdot= (ImageView) rowView.findViewById(R.id.imgrouteleftdot);
            holder.imgrouterightdot= (ImageView) rowView.findViewById(R.id.imgrouterightdot);
            holder.txtroutelineleft= (TextView) rowView.findViewById(R.id.txtroutelineleft);
            holder.txtroutelineright= (TextView) rowView.findViewById(R.id.txtroutelineright);
            holder.txtrouteshapeleft= (TextView) rowView.findViewById(R.id.txtshapeleft);
            holder.txtrouteshaperight= (TextView) rowView.findViewById(R.id.txtshaperight);
            holder.txtrouteshapeleft.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    try
                    {
                        callRouteInfo(String.valueOf(routes.get(position).getVRootID()), String.valueOf(position));
//                        createdialog(position);

//                        Intent in = new Intent(context, GoogleMapActivity.class);
//                        context.startActivity(in);
                    }catch (Exception ex)
                    {
                        Log.e("Error",ex.toString());
                    }
                  //  createdialog(position);
                    //createdialog();
                }
            });
            holder.txtrouteshaperight.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v) {
//                    createdialog(position);
                    callRouteInfo(String.valueOf(routes.get(position).getVRootID()),String.valueOf(position));
                }
            });

            if(position%2==0)
            {
              //  int widthline=lista.get(r.nextInt(lista.size()));
                holder.txtrouteshapeleft.setVisibility(View.GONE);
                holder.txtroutelineleft.setVisibility(View.GONE);
                holder.imgrouteleftdot.setVisibility(View.GONE);
                holder.txtroutelineright.setVisibility(View.VISIBLE);
                holder.txtrouteshaperight.setVisibility(View.VISIBLE);
                holder.imgrouterightdot.setVisibility(View.VISIBLE);
                holder.txtrouteshaperight.setText(routes.get(position).getVRootname());
        //        holder.txttl1right.setWidth(widthline);
//                RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(widthline,
//                        3);
//
//                holder.rltvRight.setLayoutParams(layout_description);

            }
            else
            {
               // int widthline=lista.get(r.nextInt(lista.size()));
                holder.txtrouteshapeleft.setVisibility(View.VISIBLE);
                holder.txtroutelineleft.setVisibility(View.VISIBLE);
                holder.imgrouteleftdot.setVisibility(View.VISIBLE);
                holder.txtroutelineright.setVisibility(View.GONE);
                holder.txtrouteshaperight.setVisibility(View.GONE);
                holder.imgrouterightdot.setVisibility(View.GONE);
                holder.txtrouteshapeleft.setText(routes.get(position).getVRootname());
         //       holder.txttl1left.setWidth(widthline);
//                RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(widthline,
//                        3);
//
//                holder.rltvLeft.setLayoutParams(layout_description);
            }
//            holder.tv1 = (TextView) rowView.findViewById(R.id.txttl1);
//            holder.tv2 = (TextView) rowView.findViewById(R.id.txttl2);
//            holder.tv3 = (TextView) rowView.findViewById(R.id.txttl3);
//            holder.tv4 = (TextView) rowView.findViewById(R.id.txttl4);
//            holder.tv5 = (TextView) rowView.findViewById(R.id.txttl5);
//            holder.tv6 = (TextView) rowView.findViewById(R.id.txttl6);
//            holder.tv7 = (TextView) rowView.findViewById(R.id.txttl7);
//            holder.img1 = (ImageView) rowView.findViewById(R.id.imgtlbg);
//            holder.img2 = (ImageView) rowView.findViewById(R.id.imgtlloc);
//            holder.img3 = (ImageView) rowView.findViewById(R.id.imgtlround);
//            holder.tv1.setText(txttl1[position]);
//            holder.tv2.setText(txttl2[position]);
//            holder.tv3.setText(txttl3[position]);
//            holder.tv4.setText(txttl4[position]);
//            holder.tv5.setText(txttl5[position]);
//            holder.tv6.setText(txttl6[position]);
//            holder.tv7.setText(txttl7[position]);
//            holder.img1.setImageResource(Integer.parseInt(String.valueOf((imgtlbg[position]))));
//            holder.img2.setImageResource(Integer.parseInt(String.valueOf(imgtlloc[position])));
//            holder.img3.setImageResource(Integer.parseInt(String.valueOf(imgtlround[position])));
            return rowView;
        }catch (Exception ex)
        {
            Log.e("Error", ex.toString());
            return null;
        }


    }
    private void callRouteInfo(String rootid, final String position)
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
        routesdetails=new ArrayList<Datum1>();
        ApiHandler.getApiService().getRootDetails(rootid, new Callback<MyPojoRouteDetails>() {
            @Override
            public void success(MyPojoRouteDetails res, Response response) {

                Utils.dismissDialog();

//                loadGENLOOKUPS();

                try
                {
                    routesdetails=res.getData();
                    createdialog(Integer.parseInt(position));

                }
                catch (Exception ex)
                {
                    Log.e("Error",ex.toString());
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
    public void createdialog(int position)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.customdialog);
        dialog.setTitle(routes.get(position).getVRootname());
        ListView listparties=(ListView)dialog.findViewById(R.id.listParties);
        Custom_Route route=new Custom_Route(context,routesdetails);
        listparties.setAdapter(route);

      // set the custom dialog components - text, image and button
//        TextView text = (TextView) dialog.findViewById(R.id.text);
//        text.setText("Message goes here!");
//        ImageView image = (ImageView) dialog.findViewById(R.id.image);
//        image.setImageResource(R.drawable.ic_launcher);
//
//        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//        // if button is clicked, close the custom dialog
//        dialogButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                String uri = "geo:0,0?q=Ramjimandir road, ranip, ahmedabad";
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
//                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//                context.startActivity(intent);
//                dialog.dismiss();
//            }
//        });

        dialog.show();
    }




}
