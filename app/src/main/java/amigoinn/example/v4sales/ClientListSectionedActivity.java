package amigoinn.example.v4sales;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filterable;
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
import amigoinn.db_model.ModelDelegates;
import amigoinn.modallist.ClientList;
import amigoinn.walkietalkie.Constants;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Virag kuvadia on 30-04-2016.
 */


public class ClientListSectionedActivity extends BaseFragment {

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.searchlistlayoutbefore, container, false);

        stickyList = (StickyListHeadersListView) v.findViewById(R.id.list);
        txtFilter = (TextView) v.findViewById(R.id.txtFilter);
        stickyList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        countries = new ArrayList<>();
//        countries = Constants.countries;
//        Constants.initString2(countries.size());

//        final MyAdapter adapter = new MyAdapter(this,countries);
//        stickyList.setAdapter(adapter);


        inputSearch = (EditText) v.findViewById(R.id.inputSearch);

        TextView txtDone = (TextView) v.findViewById(R.id.txtDone);
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in;
                Config.filterfrom = "party";
                if (Config.filterfrom.equalsIgnoreCase("product")) {
                    in = new Intent(getActivity(), ProductFilter.class);
                } else if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
                    in = new Intent(getActivity(), amigoinn.example.v4sales.Filter.class);
                } else {
                    in = new Intent(getActivity(), amigoinn.example.v4sales.Filter.class);
                }
                try {
                    startActivity(in);
                } catch (Exception ex) {

                }
            }
        });
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
                    Intent in = new Intent(getActivity(), amigoinn.example.v4sales.ClientsTabFragment.class);
                    startActivity(in);
                    getActivity().finish();
//                    Fragment newFragment = new ClientsTabFragment();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                    transaction.commit();


                } else {
                    Intent in = new Intent(getActivity(), amigoinn.example.v4sales.AbsentList.class);
                    startActivity(in);
                }
            }
        });

        loadClients();
        return v;
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
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
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
                    Intent in = new Intent(getActivity(), amigoinn.example.v4sales.ClientsTabFragment.class);
                    in.putExtra("client_code", c_info.client_code);
                    AccountApplication.setClient_code(c_info.client_code);
                    startActivity(in);
                    getActivity().finish();
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
        final MyAdapter adapter = new MyAdapter(getActivity(), clint_info);
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
}
