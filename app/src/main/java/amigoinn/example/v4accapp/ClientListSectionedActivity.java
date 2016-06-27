package amigoinn.example.v4accapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import amigoinn.adapters.NotifyingAsyncQueryHandler;
import amigoinn.adapters.SectionedListActivityForFilters;
import amigoinn.db_model.ClientInfo;
import amigoinn.db_model.LoginInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.modallist.ClientList;
import amigoinn.walkietalkie.Constants;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Virag kuvadia on 30-04-2016.
 */


public class ClientListSectionedActivity extends BaseActivity {

    //    private AudioFilesAdapter mAdapter;
    private NotifyingAsyncQueryHandler mQueryHandler;
    EditText inputSearch;
    //    List<String> countries;
    StickyListHeadersListView stickyList;
    TextView txtFilter;
    private Handler mHandler;
    public static SectionedListActivityForFilters listActivity;
    View v;
    ArrayList<ClientInfo> clint_info;
    TextView txtuser, txtZone, txtState,
            txtCity, txtAddress;
    TextView txtCall, txtMap;
    ClientInfo cinfo;
    Button btncall;
    ImageView btnlocation;
    LocationManager lm;
    Location l;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    String Longitude, Latitude;
    GPSTracker gpsTracker;
    UniversalDelegate m_delegate;

    public interface UniversalDelegate {
        public void CallDidSuccess(String lat, String lang);

        public void CallFailedWithError(String error);
    }


//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        v = inflater.inflate(R.layout.searchlistlayoutbefore, container, false);
//
//        stickyList = (StickyListHeadersListView) v.findViewById(R.id.list);
//        txtFilter = (TextView) v.findViewById(R.id.txtFilter);
//        stickyList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
////        countries = new ArrayList<>();
////        countries = Constants.countries;
////        Constants.initString2(countries.size());
//
////        final MyAdapter adapter = new MyAdapter(this,countries);
////        stickyList.setAdapter(adapter);
//
//
//        inputSearch = (EditText) v.findViewById(R.id.inputSearch);
//
//        TextView txtDone = (TextView) v.findViewById(R.id.txtDone);
//        txtFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in;
//                Config.filterfrom = "party";
//                if (Config.filterfrom.equalsIgnoreCase("product")) {
//                    in = new Intent(getApplicationContext(), ProductFilter.class);
//                } else if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
//                    in = new Intent(getApplicationContext(), amigoinn.example.v4sales.Filter.class);
//                } else {
//                    in = new Intent(getApplicationContext(), amigoinn.example.v4sales.Filter.class);
//                }
//                try {
//                    startActivityForResult(in, 111);
//                } catch (Exception ex) {
//
//                }
//            }
//        });
//        txtDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
//                    Intent in = new Intent(getApplicationContext(), amigoinn.example.v4sales.ClientsTabFragment.class);
//                    startActivity(in);
//                    finish();
////                    Fragment newFragment = new ClientsTabFragment();
////                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
////
////                    transaction.commit();
//
//
//                } else {
//                    Intent in = new Intent(getApplicationContext(), amigoinn.example.v4sales.AbsentList.class);
//                    startActivity(in);
//                }
//            }
//        });
//
//        loadClients();
//        return v;
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlistlayoutbefore);


        stickyList = (StickyListHeadersListView) findViewById(R.id.list);
        txtFilter = (TextView) findViewById(R.id.txtFilter);
        stickyList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        countries = new ArrayList<>();
//        countries = Constants.countries;
//        Constants.initString2(countries.size());

//        final MyAdapter adapter = new MyAdapter(this,countries);
//        stickyList.setAdapter(adapter);


        inputSearch = (EditText) findViewById(R.id.inputSearch);

        TextView txtDone = (TextView) findViewById(R.id.txtDone);
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in;
                Config.filterfrom = "party";
                if (Config.filterfrom.equalsIgnoreCase("product")) {
                    in = new Intent(getApplicationContext(), ProductFilter.class);
                } else if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
                    in = new Intent(getApplicationContext(), amigoinn.example.v4accapp.Filter.class);
                } else {
                    in = new Intent(getApplicationContext(), amigoinn.example.v4accapp.Filter.class);
                }
                try {
                    startActivityForResult(in, 111);
                } catch (Exception ex) {

                }
            }
        });
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
                    Intent in = new Intent(getApplicationContext(), amigoinn.example.v4accapp.ClientsTabFragment.class);
                    startActivity(in);
                    finish();
//                    Fragment newFragment = new ClientsTabFragment();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                    transaction.commit();


                } else {
                    Intent in = new Intent(getApplicationContext(), amigoinn.example.v4accapp.AbsentList.class);
                    startActivity(in);
                }
            }
        });

        loadClients();

    }

    public void loadClients() {
        showProgress();
        ClientList.Instance().DoClint(new ModelDelegates.ModelDelegate<ClientInfo>() {
            @Override
            public void ModelLoaded(ArrayList<ClientInfo> list) {
                hideProgress();
                Collections.sort(list, new Comparator<ClientInfo>() {
                    @Override
                    public int compare(ClientInfo s1, ClientInfo s2) {
                        return s1.name.compareToIgnoreCase(s2.name);
                    }
                });
//                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                clint_info = list;
                setbaseadapter();
            }

            @Override
            public void ModelLoadFailedWithError(String error) {
                hideProgress();
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.searchlistlayoutbefore);
//        stickyList = (StickyListHeadersListView) findViewById(R.id.list);
//        txtFilter = (TextView) findViewById(R.id.txtFilter);
//        stickyList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        countries = new ArrayList<>();
//        countries = Constants.countries;
//        Constants.initString2(countries.size());
////        final MyAdapter adapter = new MyAdapter(this,countries);
////        stickyList.setAdapter(adapter);
//
//
//        inputSearch = (EditText) findViewById(R.id.inputSearch);
//        setbaseadapter();
//        TextView txtDone = (TextView) findViewById(R.id.txtDone);
//        txtFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in;
//                if (Config.filterfrom.equalsIgnoreCase("product")) {
//                    in = new Intent(SectionedListBeforeFilter.this, ProductFilter.class);
//                } else if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
//                    in = new Intent(SectionedListBeforeFilter.this, amigoinn.example.v4sales.Filter.class);
//                } else {
//                    in = new Intent(SectionedListBeforeFilter.this, amigoinn.example.v4sales.Filter.class);
//                }
//                try {
//                    startActivity(in);
//                } catch (Exception ex) {
//
//                }
//            }
//        });
//        txtDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
//                    Intent   in =new Intent(SectionedListBeforeFilter.this, amigoinn.example.v4sales.ClientsTabFragment.class);
//                    startActivity(in);
//                    finish();
////                    Fragment newFragment = new ClientsTabFragment();
////                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
////
////                    transaction.commit();
//
//
//                } else {
//                    finish();
//                }
//            }
//        });
//
//
//
//
////        listActivity=new SectionedListActivity();
////        mAdapter = new AudioFilesAdapter(this, null);
////        setListAdapter(mAdapter);
//
//    // Starts querying the media provider. This is done asynchronously not
//    // to possibly block the UI or even worse fire an ANR...
////        mQueryHandler = new NotifyingAsyncQueryHandler(getContentResolver(), this);
////        mQueryHandler.startQuery(Media.INTERNAL_CONTENT_URI, AudioFilesQuery.PROJECTION, AudioFilesQuery.SORT_ORDER);
//}

    public class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter, Filterable {

        private List<ClientInfo> countries;
        private LayoutInflater inflater;
        private List<ClientInfo> filteredData = null;
        private ItemFilter mFilter = new ItemFilter();

        public MyAdapter(Context context, List<ClientInfo> countri) {
            inflater = LayoutInflater.from(context);
            countries = countri;
            filteredData = countri;
//            countries = context.getResources().getStringArray(R.array.countries);
        }

        @Override
        public int getCount() {
            return filteredData.size();
        }

        @Override
        public Object getItem(int position) {
            return filteredData.get(0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.sammplelistitem, parent, false);
                holder.text = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(filteredData.get(position).name);
            final int pos = position;
            final ViewHolder holder1 = holder;
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClientInfo c_info = filteredData.get(position);
//                    Intent in = new Intent(getApplicationContext(), amigoinn.example.v4sales.ClientsTabFragment.class);
//                    in.putExtra("client_code", c_info.client_code);
//                    AccountApplication.setClient_code(c_info.client_code);
//                    startActivity(in);
//                    finish();
                    Constants.client_id = c_info.client_code;
                    QuestionDialog("1");
                    //     showAlertDialog();
                }
            });

            return convertView;
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            HeaderViewHolder holder;
            if (convertView == null) {
                holder = new HeaderViewHolder();
                convertView = inflater.inflate(R.layout.sammplelistitemabsent, parent, false);
                holder.text = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            } else {
                holder = (HeaderViewHolder) convertView.getTag();
            }
            //set header text as first char in name
            String headerText = "" + filteredData.get(position).name.subSequence(0, 1).charAt(0);
            holder.text.setText(headerText);
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            //return the first character of the country as ID because this is what headers are based upon
            return filteredData.get(position).name.subSequence(0, 1).charAt(0);
        }

        @Override
        public android.widget.Filter getFilter() {
            return mFilter;
        }

        private class ItemFilter extends android.widget.Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final List<ClientInfo> list = countries;

                int count = list.size();
                final ArrayList<ClientInfo> nlist = new ArrayList<ClientInfo>(count);

                ClientInfo filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.name.toLowerCase().contains(filterString)) {
                        nlist.add(filterableString);
                    }
                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<ClientInfo>) results.values;
                notifyDataSetChanged();
            }

        }

        class HeaderViewHolder {
            TextView text;
        }

        class ViewHolder {
            TextView text;
        }

    }

    public void setbaseadapter() {
        final MyAdapter adapter = new MyAdapter(getApplicationContext(), clint_info);
        stickyList.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }


        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 111) {
                QuestionDialog("1");
//                showAlertDialog();
//                Intent in = new Intent(getApplicationContext(), amigoinn.example.v4sales.ClientsTabFragment.class);
//                String str = AccountApplication.getClient_code();
//                ClientInfo ci = ClientInfo.getClintInfoByName(str);
//                if (ci != null) {
//                    in.putExtra("client_code", ci.client_code);
//                    AccountApplication.setClient_code(ci.client_code);
//                }
//                startActivity(in);
            }
        }

    }

    public void QuestionDialog(String chapId) {
        try {
            final Dialog storyDialog = new Dialog(ClientListSectionedActivity.this);
            storyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            storyDialog.setContentView(R.layout.client_contact_fragment);
            storyDialog.getWindow().setLayout(ActionBar.LayoutParams.FILL_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT);
            storyDialog.setCancelable(true);
            storyDialog.show();
            String c_code = Constants.client_id;
            txtuser = (TextView) storyDialog.findViewById(R.id.txtuser);
            txtZone = (TextView) storyDialog.findViewById(R.id.txtZone);
            txtState = (TextView) storyDialog.findViewById(R.id.txtState);
            txtCity = (TextView) storyDialog.findViewById(R.id.txtCity);
            txtAddress = (TextView) storyDialog.findViewById(R.id.txtAddress);

            btncall = (Button) storyDialog.findViewById(R.id.btncall);
            btncall.setVisibility(View.GONE);
            btnlocation = (ImageView) storyDialog.findViewById(R.id.btnlocation);

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

            btnlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Intent start = new Intent(ClientListSectionedActivity.this, GoogleMapActivity.class);
//                startActivity(start);
//                showAlertDialog();
                    try {
                        storyDialog.cancel();
                        DoCall();

                    } catch (Exception ex) {
                    }
                }
            });


            txtCall = (TextView) findViewById(R.id.txtCall);
            txtMap = (TextView) findViewById(R.id.txtMap);

//        txtCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String numberr = cinfo.mobile_number;
//                if (numberr != null) {
//                    Uri number = Uri.parse("tel:" + numberr);
//                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
//                    startActivity(callIntent);
//                }
//            }
//        });
//
//        txtMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent start = new Intent(ClientListSectionedActivity.this, GoogleMapActivity.class);
//                startActivity(start);
//            }
//        });

            if (c_code != null) {
                cinfo = ClientInfo.getClintInfoById(c_code);
                if (cinfo != null) {
                    txtuser.setText(cinfo.name);
                    txtZone.setText(cinfo.Zone);
                    txtState.setText(cinfo.client_state);
                    txtCity.setText(cinfo.City);
                    txtAddress.setText(cinfo.client_addr_one + "," + cinfo.client_addr_two + "," + cinfo.client_addr_three + "," + cinfo.client_addr_four);
                }
            }


//		DatabaseHandler1 handler1=new DatabaseHandler1(context);
        } catch (Exception ex) {

        }
    }

    private void showAlertDialog() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ClientListSectionedActivity.this);

        // set title
        alertDialogBuilder.setTitle("V4 Account");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to send your location to server?")
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

    public void DoCall() {
        showProgress();
        getLocation(new UniversalDelegate() {

            @Override
            public void CallFailedWithError(String error) {
                hideProgress();
//                Toast.makeText(getApplicationContext(), error,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void CallDidSuccess(String lat, String lang) {
                LoginInfo.Instance().DoUpdatelocations(new ModelDelegates.LoginDelegate() {
                    @Override
                    public void LoginDidSuccess() {
                        hideProgress();
                        Toast.makeText(ClientListSectionedActivity.this, "Location sent successfully", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void LoginFailedWithError(String error) {
                        hideProgress();
                        Toast.makeText(ClientListSectionedActivity.this, "Error in sending location", Toast.LENGTH_LONG).show();
                    }
                }, lang, lat, false, "", "");
//				Toast.makeText(getApplicationContext(),
//						" lat=" + lat + "lang " + lang, Toast.LENGTH_LONG)
//						.show();
            }
        });

    }

    public void getLocation(UniversalDelegate delegate) {
        m_delegate = delegate;

        gpsTracker = new GPSTracker(getApplicationContext());
        Location location = null;
        try {
            lm = (LocationManager) getApplicationContext().getSystemService(
                    LOCATION_SERVICE);
            isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled) {
                Latitude = String.valueOf(gpsTracker.latitude);
                Longitude = String.valueOf(gpsTracker.longitude);
                m_delegate.CallDidSuccess(Latitude, Longitude);
            } else {
                showSettingsAlert(this);
                gpsTracker.showSettingsAlert(getApplicationContext());
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

}
