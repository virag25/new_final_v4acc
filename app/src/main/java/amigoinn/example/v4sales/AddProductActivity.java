package amigoinn.example.v4sales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import amigoinn.db_model.GenLookInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.ProductInfo;
import amigoinn.modallist.GenLookup;
import amigoinn.modallist.ProductList;
import amigoinn.walkietalkie.DatabaseHandler1;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Virag kuvadia on 30-04-2016.
 */


public class AddProductActivity extends BaseActivity {

    //    private AudioFilesAdapter mAdapter;
    private NotifyingAsyncQueryHandler mQueryHandler;
    EditText inputSearch;
    //    List<String> countries;
    StickyListHeadersListView stickyList;
    TextView txtFilter;
    private Handler mHandler;
    public static SectionedListActivityForFilters listActivity;
    View v;
    ArrayList<ProductInfo> clint_info;
    ArrayList<GenLookInfo> gen_lookup = new ArrayList<>();


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

        }
    }

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
        txtDone.setVisibility(View.GONE);
        txtFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in;
                Config.filterfrom = "product";

                in = new Intent(getApplicationContext(), ProductFilterOrder.class);

                startActivityForResult(in, 123);

            }
        });

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.filterfrom.equalsIgnoreCase("Mainmenu")) {
                    Intent in = new Intent(getApplicationContext(), ClientsTabFragment.class);
                    startActivity(in);
                    finish();
//                    Fragment newFragment = new ClientsTabFragment();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                    transaction.commit();


                } else {
                    Intent in = new Intent(getApplicationContext(), AbsentList.class);
                    startActivity(in);
                }
            }
        });

        loadClients();

    }


//    public void getCategoris()
//    {
//        try
//        {
//            ArrayList<String> category=new ArrayList<>();
//            Database m_database=Database.createInstance(v.getContext(),"vact.db",1);
//            String querystring="SELECT distinct category FROM PRODUCT_INFO";
//            Cursor cursor = m_database.rawQuery(querystring, null);
//
//            if (cursor.moveToFirst())
//            {
//                do
//                {
//                    category.add(cursor.getString(0));
//                }
//                while (cursor.moveToNext());
//            }
//
//        } catch (Exception e) {
//            Log.e("Error",e.toString());
//        }
//    }

    public void loadClients() {
        showProgress();
        ProductList.Instance().DoProductCall(new ModelDelegates.ModelDelegate<ProductInfo>() {
            @Override
            public void ModelLoaded(ArrayList<ProductInfo> list) {
                hideProgress();
                Collections.sort(list, new Comparator<ProductInfo>() {
                    @Override
                    public int compare(ProductInfo s1, ProductInfo s2) {
                        return s1.ItemDesc.compareToIgnoreCase(s2.ItemDesc);
                    }
                });
//                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                clint_info = list;
                setbaseadapter();
//                DatabaseHandler1 handler1=new DatabaseHandler1(v.getContext());
//                ArrayList<String> categories=handler1.getCategoris();
////                ArrayList<String> itemgroup=handler1.getItemgroup();
//                loadGENLOOKUPS();
            }

            @Override
            public void ModelLoadFailedWithError(String error) {
                hideProgress();
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadGENLOOKUPS() {
        showProgress();
        GenLookup.Instance().DoProductCall(new ModelDelegates.ModelDelegate<GenLookInfo>() {
            @Override
            public void ModelLoaded(ArrayList<GenLookInfo> list) {
                hideProgress();
//                Collections.sort(list, new Comparator<GenLookInfo>() {
//                    @Override
//                    public int compare(GenLookInfo s1, GenLookInfo s2) {
//                        return s1.category.compareToIgnoreCase(s2.category);
//                    }
//                });
//                Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
                gen_lookup = list;
                //setbaseadapter();
//                DatabaseHandler1 handler1=new DatabaseHandler1(v.getContext());
//                ArrayList<String> categories=handler1.getCategoris();
//                ArrayList<String> itemgroup=handler1.getItemgroup();
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

        private List<ProductInfo> countries;
        private LayoutInflater inflater;
        private List<ProductInfo> filteredData = null;
        private ItemFilter mFilter = new ItemFilter();

        public MyAdapter(Context context, List<ProductInfo> countri) {
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

            holder.text.setText(filteredData.get(position).ItemDesc);
            final int pos = position;
            final ViewHolder holder1 = holder;
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductInfo c_info = filteredData.get(position);
                    Intent start = new Intent();
                    start.putExtra("product_id", c_info.StockNo);
                    setResult(RESULT_OK, start);
                    finish();
//                    Intent in = new Intent(getApplicationContext(), ClientsTabFragment.class);
//                    in.putExtra("client_code",c_info.client_code);
//                    startActivity(in);
//                    getApplicationContext().finish();
                }
            });

            return convertView;
        }

        @Override
        public View getHeaderView(final int position, View convertView, ViewGroup parent) {
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
            String headerText = "" + filteredData.get(position).ItemDesc.subSequence(0, 1).charAt(0);
            holder.text.setText(headerText);
            return convertView;
        }

        @Override
        public long getHeaderId(int position) {
            //return the first character of the country as ID because this is what headers are based upon
            return filteredData.get(position).ItemDesc.subSequence(0, 1).charAt(0);
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

                final List<ProductInfo> list = countries;

                int count = list.size();
                final ArrayList<ProductInfo> nlist = new ArrayList<ProductInfo>(count);

                ProductInfo filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.ItemDesc.toLowerCase().contains(filterString)) {
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
                filteredData = (ArrayList<ProductInfo>) results.values;
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
}
