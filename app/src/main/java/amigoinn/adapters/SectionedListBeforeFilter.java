package amigoinn.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import amigoinn.db_model.ClientInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.example.v4sales.ClientsTabFragment;
import amigoinn.example.v4sales.Config;
import amigoinn.example.v4sales.ProductFilter;
import amigoinn.example.v4sales.R;
import amigoinn.modallist.ClientList;
import amigoinn.walkietalkie.Constants;
import retrofit.client.Client;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * Shows a smart way of handling separators in {@link ListView}s. It also shows
 * some ways to boost your {@link ListView}s using techniques like 'section
 * caching', ViewHolder, CharArrayBuffer, etc.
 *
 * @author Cyril Mottier
 */
public class SectionedListBeforeFilter extends Fragment
{

    //    private AudioFilesAdapter mAdapter;
    private NotifyingAsyncQueryHandler mQueryHandler;
    EditText inputSearch;
    List<String> countries;
    StickyListHeadersListView stickyList;
    TextView txtFilter;
    private Handler mHandler;
    public static SectionedListActivityForFilters listActivity;
    View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        v = inflater.inflate(R.layout.searchlistlayoutbefore, container, false);

        stickyList = (StickyListHeadersListView) v.findViewById(R.id.list);
        txtFilter = (TextView) v.findViewById(R.id.txtFilter);
        stickyList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        countries = new ArrayList<>();
        countries = Constants.countries;
//        Constants.initString2(countries.size());
        loadClients();
//        final MyAdapter adapter = new MyAdapter(this,countries);
//        stickyList.setAdapter(adapter);


        inputSearch = (EditText) v.findViewById(R.id.inputSearch);
        setbaseadapter();
        TextView txtDone = (TextView) v.findViewById(R.id.txtDone);
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in;
                if (Config.filterfrom.equalsIgnoreCase("product"))
                {
                    in = new Intent(getActivity(), ProductFilter.class);
                }
                else if (Config.filterfrom.equalsIgnoreCase("Mainmenu"))
                {
                    in = new Intent(getActivity(), amigoinn.example.v4sales.Filter.class);
                }
                else
                {
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


        return v;
    }


    public void loadClients() {
        ClientList.Instance().DoClint(new ModelDelegates.ModelDelegate<ClientInfo>() {
            @Override
            public void ModelLoaded(ArrayList<ClientInfo> list) {

            }

            @Override
            public void ModelLoadFailedWithError(String error) {

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

        private List<String> countries;
        private LayoutInflater inflater;
        private List<String> filteredData = null;
        private ItemFilter mFilter = new ItemFilter();

        public MyAdapter(Context context, List<String> countri) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.sammplelistitem, parent, false);
                holder.text = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(filteredData.get(position));
            final int pos = position;
            final ViewHolder holder1 = holder;
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constants.arrTemp2[pos].equalsIgnoreCase("b")) {
                        // array.put(position, false);
                        Constants.arrTemp2[pos] = "a";
                        holder1.text.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        holder1.text.setBackgroundColor(Color.parseColor("#00000000"));
                    } else {
//                    array.put(position, true);
                        // listner.BrandListClicked();
                        Constants.arrTemp2[pos] = "b";
                        holder1.text.setBackgroundColor(Color.parseColor("#EAEAEA"));
                        holder1.text.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.true1, 0);
                    }
//                if (array.get(position)) {
//                    array.put(position, false);
//                    tv_catprice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//
//                    tv_catprice.setBackgroundColor(Color.parseColor("#00000000"));
//                } else {
//                    array.put(position, true);
//                    //listner.shopListClicked();
//                    tv_catprice.setBackgroundColor(Color.parseColor("#EAEAEA"));
//                    tv_catprice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.com_facebook_tooltip_black_bottomnub, 0);
//                }


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
            String headerText = "" + filteredData.get(position).subSequence(0, 1).charAt(0);
            holder.text.setText(headerText);
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            //return the first character of the country as ID because this is what headers are based upon
            return filteredData.get(position).subSequence(0, 1).charAt(0);
        }

        @Override
        public Filter getFilter() {
            return mFilter;
        }

        private class ItemFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final List<String> list = countries;

                int count = list.size();
                final ArrayList<String> nlist = new ArrayList<String>(count);

                String filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.toLowerCase().contains(filterString)) {
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
                filteredData = (ArrayList<String>) results.values;
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
        final MyAdapter adapter = new MyAdapter(getActivity(), countries);
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