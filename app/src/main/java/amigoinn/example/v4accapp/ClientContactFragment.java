package amigoinn.example.v4accapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

import amigoinn.adapters.CameraActivity;
import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.ClientInfo;
import amigoinn.db_model.LoginInfo;
import amigoinn.db_model.MarketTypeInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.SalesmenInfo;
import amigoinn.modallist.SalesList;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

public class ClientContactFragment extends BaseFragment {
    TextView txtuser, txtZone, txtState,
            txtCity, txtAddress;
    TextView txtCall, txtMap;
    ClientInfo cinfo;
    ImageView btnlocation;
    Button btncall;
    Spinner spnData, spnType;
    ArrayList<String> bnd_list = new ArrayList<>();
    ArrayList<String> bnd_list_type = new ArrayList<>();
    String selected_id;
    String selected_type_id;
    EditText edtSpin;

    LocationManager lm;
    Location l;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    String Longitude, Latitude;
    GPSTracker gpsTracker;
    UniversalDelegate m_delegate;

    TextView txtTempo, txtContact;
    String client_lat = "", client_lang = "";
    LinearLayout llClintAddr;
    TextView txtClientLocation;
    ImageView btnFeed;
    WebView webView;

    String map = "<html> <head> <style type=\"text/css\"> div#map { position: relative; } div#crosshair { position: absolute; top: 192px; height: 19px; width: 19px; left: 50%; margin-left: -8px; display: block; background: url(crosshair.gif); background-position: center center; background-repeat: no-repeat; } </style> <script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=false\"></script> <script type=\"text/javascript\"> var map; var geocoder; var centerChangedLast; var reverseGeocodedLast; var currentReverseGeocodeResponse; function initialize() { var latlng = new google.maps.LatLng(-33.827128,151.200459); var myOptions = { zoom: 20, center: latlng, mapTypeId: google.maps.MapTypeId.ROADMAP }; map = new google.maps.Map(document.getElementById(\"map_canvas\"), myOptions); geocoder = new google.maps.Geocoder(); var marker = new google.maps.Marker({ position: latlng, map: map, title: \"Hello World!\" }); } </script> </head> <body onLoad=\"initialize()\"> <center> <div id=\"map\" style=\"width:100%; height:100%;\"> <div id=\"map_canvas\" style=\"width:100%; height:100%\"></div> <div id=\"crosshair\"></div> </div></center> </body></html>";

    public interface UniversalDelegate {
        public void CallDidSuccess(String lat, String lang);

        public void CallFailedWithError(String error);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.client_contact_fragment, container, false);
        String c_code = AccountApplication.getClient_code();
        txtuser = (TextView) v.findViewById(R.id.txtuser);
        txtZone = (TextView) v.findViewById(R.id.txtZone);
        txtState = (TextView) v.findViewById(R.id.txtState);
        txtTempo = (TextView) v.findViewById(R.id.txtTempo);
        txtCity = (TextView) v.findViewById(R.id.txtCity);
        txtContact = (TextView) v.findViewById(R.id.txtContact);
        edtSpin = (EditText) v.findViewById(R.id.edtSpn);
        txtAddress = (TextView) v.findViewById(R.id.txtAddress);
        spnData = (Spinner) v.findViewById(R.id.spnData);
        spnType = (Spinner) v.findViewById(R.id.spnType);
        txtClientLocation = (TextView) v.findViewById(R.id.txtClientLocation);
        llClintAddr = (LinearLayout) v.findViewById(R.id.llClintAddr);
        btnlocation = (ImageView) v.findViewById(R.id.btnlocation);
        btnFeed = (ImageView) v.findViewById(R.id.btnFeed);
        webView = (WebView) v.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.loadData(map, "text/Html", "UTF-8");


        btnFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), CameraActivity.class);
                startActivity(in);
            }
        });


        if ((client_lat.length() > 0 && client_lang.length() > 0)) {
            llClintAddr.setVisibility(View.VISIBLE);
            txtClientLocation.setText(client_lat + "," + client_lang);
            btnlocation.setBackgroundResource(R.drawable.loc);
        } else {
            llClintAddr.setVisibility(View.GONE);
            btnlocation.setBackgroundResource(R.drawable.greenmarker);
            loadLatLang();
        }


        ArrayList<SalesmenInfo> lst = SalesmenInfo.getAllProduct();
        if (lst != null && lst.size() > 0) {
            selected_id = lst.get(0).Code;
            bnd_list = new ArrayList<>();
            for (int i = 0; i < lst.size(); i++) {
                SalesmenInfo sale = lst.get(i);
                bnd_list.add(sale.Nm);
            }

            ArrayList<MarketTypeInfo> lsttype = MarketTypeInfo.getAllProduct();
            if (lsttype != null && lsttype.size() > 0) {
                selected_type_id = lsttype.get(0).Code;
                bnd_list_type = new ArrayList<>();
                for (int i = 0; i < lsttype.size(); i++) {
                    MarketTypeInfo sale = lsttype.get(i);
                    bnd_list_type.add(sale.Descr);
                }
            }

//            edtSpin.setText(bnd_list.get(0));
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, bnd_list);
            spnData.setAdapter(adapter);

// add elements to al, including duplicates
            HashSet hs = new HashSet();
            hs.addAll(bnd_list_type);
            bnd_list_type.clear();
            bnd_list_type.addAll(hs);

            ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, bnd_list_type);
            spnType.setAdapter(adapter1);
        } else {
            SalesList.Instance().DoSalsApi(new ModelDelegates.ModelDelegate<SalesmenInfo>() {
                @Override
                public void ModelLoaded(ArrayList<SalesmenInfo> list) {
                    ArrayList<SalesmenInfo> lst = SalesmenInfo.getAllProduct();
                    if (lst != null && lst.size() > 0) {
                        bnd_list = new ArrayList<String>();
                        selected_id = lst.get(0).Code;
                        for (int i = 0; i < lst.size(); i++) {
                            SalesmenInfo sale = lst.get(i);
                            bnd_list.add(sale.Nm);
                        }
//                        edtSpin.setText(bnd_list.get(0));
                    }


                    ArrayList<MarketTypeInfo> lsttype = MarketTypeInfo.getAllProduct();
                    bnd_list_type = new ArrayList<String>();
                    if (lsttype != null && lsttype.size() > 0) {
                        selected_type_id = lsttype.get(0).Code;
                        for (int i = 0; i < lsttype.size(); i++) {
                            MarketTypeInfo sale = lsttype.get(i);
                            bnd_list_type.add(sale.Descr);
                        }
                    }
                    ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, bnd_list);
                    spnData.setAdapter(adapter);

                    HashSet hs = new HashSet();
                    hs.addAll(bnd_list_type);
                    bnd_list_type.clear();
                    bnd_list_type.addAll(hs);
                    ArrayAdapter adapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, bnd_list_type);
                    spnType.setAdapter(adapter1);
                }

                @Override
                public void ModelLoadFailedWithError(String error) {

                }
            });
        }


        btncall = (Button) v.findViewById(R.id.btncall);


        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberr = cinfo.mobile_number;
                if (numberr != null) {
                    Uri number = Uri.parse("tel:" + numberr);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                }

            }
        });

        edtSpin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    spnData.performClick();

                }
                return true;
            }
        });

        spnData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = bnd_list.get(position);
//                edtSpin.setText(str);
                SalesmenInfo si = SalesmenInfo.getSalesmenInfoByItemName(str);
                if (si != null) {
                    selected_id = si.Code;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = bnd_list_type.get(position);
//                edtSpin.setText(str);
                MarketTypeInfo si = MarketTypeInfo.getMarketTypeInfoByItemName(str);
                if (si != null) {
                    selected_type_id = si.Code;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DoCall();
                showAlertDialog();

            }
        });


        txtCall = (TextView) v.findViewById(R.id.txtCall);
        txtMap = (TextView) v.findViewById(R.id.txtMap);

        txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberr = cinfo.mobile_number;
                if (numberr != null) {
                    Uri number = Uri.parse("tel:" + numberr);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                }
            }
        });

        txtMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(getActivity(), GoogleMapActivity.class);
                startActivity(start);
            }
        });

        if (c_code != null) {
            cinfo = ClientInfo.getClintInfoById(c_code);
            if (cinfo != null) {
                txtuser.setText(cinfo.name + ", " + "Code: " + c_code);
                txtZone.setText(cinfo.Zone);
                txtContact.setText(cinfo.mobile_number);
                txtState.setText(cinfo.client_state);
                txtCity.setText(cinfo.City);
                txtAddress.setText(cinfo.client_addr_one + "," + cinfo.client_addr_two + "," + cinfo.client_addr_three + "," + cinfo.client_addr_four);

                String salese = cinfo.salesman;
                if (salese != null) {
                    SalesmenInfo si = SalesmenInfo.getSalesmenInfoById(salese);
                    if (si != null) {
                        String mi_txt = null;
                        MarketTypeInfo mi = MarketTypeInfo.getMarketTypeInfoById(cinfo.ctype);
                        if (mi != null) {
                            mi_txt = mi.Descr;
                        }
                        txtTempo.setText("Selected Saleseman:" + si.Nm + "/nMarket Type: " + mi_txt + "/Lat&Lang: " + cinfo.lat + "," + cinfo.lang);
                    }

                }
            }
        }


        return v;
    }


    public void DoCall() {
        showProgress();
        getLocation(new UniversalDelegate() {

            @Override
            public void CallFailedWithError(String error) {
                hideProgress();
//                Toast.makeText(getActivity(), error,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void CallDidSuccess(String lat, String lang) {
                LoginInfo.Instance().DoUpdatelocations(new ModelDelegates.LoginDelegate() {
                    @Override
                    public void LoginDidSuccess() {
                        hideProgress();
                        Toast.makeText(getActivity(), "Customer Mapped successfully", Toast.LENGTH_LONG).show();
//                        Intent start = new Intent(getActivity(), GoogleMapActivity.class);
//                        startActivity(start);

                    }

                    @Override
                    public void LoginFailedWithError(String error) {
                        hideProgress();
                        Toast.makeText(getActivity(), "Error in sending location", Toast.LENGTH_LONG).show();
//                        Intent start = new Intent(getActivity(), GoogleMapActivity.class);
//                        startActivity(start);
                    }
                }, lang, lat, false, selected_id, selected_type_id);
//				Toast.makeText(getActivity(),
//						" lat=" + lat + "lang " + lang, Toast.LENGTH_LONG)
//						.show();
            }
        });

    }

    private void showAlertDialog() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set title
        alertDialogBuilder.setTitle("V4 Account");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to update location for customer?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DoCall();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void getLocation(UniversalDelegate delegate) {
        m_delegate = delegate;

        gpsTracker = new GPSTracker(getActivity());
        Location location = null;
        try {
            lm = (LocationManager) getActivity().getSystemService(
                    getActivity().LOCATION_SERVICE);
            isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled) {
                Latitude = String.valueOf(gpsTracker.latitude);
                Longitude = String.valueOf(gpsTracker.longitude);
                m_delegate.CallDidSuccess(Latitude, Longitude);
            } else {
                showSettingsAlert(getActivity());
                gpsTracker.showSettingsAlert(getActivity());
                m_delegate.CallFailedWithError("try again");
            }

        } catch (Exception e) {
            m_delegate.CallFailedWithError("try again");
            e.printStackTrace();
        }
    }


    public void showSettingsAlert(final Context mContext) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("V4 Account");

        // Setting Dialog Message
        if (isGPSEnabled) {
//			alertDialog.setMessage("Would you like to Stop GPS Service.");
        } else {
            alertDialog.setMessage("Start your location service");
        }

        // On Pressing Setting button
        alertDialog.setPositiveButton("Setting",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);// ACTION_LOCATION_SOURCE_SETTINGS
                        mContext.startActivity(intent);
                    }
                });

        // On pressing cancel button
        alertDialog.setNegativeButton("Cancle",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public void loadLatLang() {
        if (NetworkConnectivity.isConnected()) {
            String c_code = AccountApplication.getClient_code();
            ServiceHelper helper = new ServiceHelper(ServiceHelper.FETCH_LOCATION, ServiceHelper.RequestMethod.GET);
            helper.addParam("clientid", c_code);
            helper.call(new ServiceHelper.ServiceHelperDelegate() {
                @Override
                public void CallFinish(ServiceResponse res) {
                    if (res.RawResponse != null) {
                        try {
                            JSONObject jobj = new JSONObject(res.RawResponse);
                            if (jobj != null) {
                                JSONArray jdata = jobj.optJSONArray("data");
                                if (jdata != null) {
                                    for (int i = 0; i < jdata.length(); i++) {
                                        JSONObject job = jdata.optJSONObject(i);
                                        client_lat = job.optString("vlat");
                                        client_lang = job.optString("vlong");
                                        if ((client_lat.length() > 0 && client_lang.length() > 0)) {
                                            llClintAddr.setVisibility(View.VISIBLE);
                                            txtClientLocation.setText(client_lat + "," + client_lang);
                                            if (map.contains("-33.827128")) {
                                                map = map.replace("-33.827128", client_lat);
                                                map = map.replace("151.200459", client_lang);
                                                webView.loadData(map, "text/Html", "UTF-8");

                                            }
                                        } else {
                                            llClintAddr.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void CallFailure(String ErrorMessage) {

                }
            });

        } else {

        }
    }


}
