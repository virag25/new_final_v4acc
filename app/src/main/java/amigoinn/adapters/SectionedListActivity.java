package amigoinn.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;


import com.example.v4sales.R;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * Shows a smart way of handling separators in {@link ListView}s. It also shows
 * some ways to boost your {@link ListView}s using techniques like 'section
 * caching', ViewHolder, CharArrayBuffer, etc.
 *
 * @author Cyril Mottier
 */
public class SectionedListActivity extends Activity
{

//    private AudioFilesAdapter mAdapter;
    private NotifyingAsyncQueryHandler mQueryHandler;
    EditText inputSearch;
    TextView txtDone;
   public static SectionedListActivity listActivity;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlistlayout);
        StickyListHeadersListView stickyList = (StickyListHeadersListView) findViewById(R.id.list);
        stickyList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        TextView txtDone = (TextView) findViewById(R.id.txtDone);
        List<String> countries=new ArrayList<>();
        countries.add("America");
        countries.add("Bangladesh");
        countries.add("china");
        countries.add("denmark");
        countries.add("delhi");
        countries.add("India");
        final MyAdapter adapter = new MyAdapter(this,countries);
        stickyList.setAdapter(adapter);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
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
        txtDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
//        listActivity=new SectionedListActivity();
//        mAdapter = new AudioFilesAdapter(this, null);
//        setListAdapter(mAdapter);

        // Starts querying the media provider. This is done asynchronously not
        // to possibly block the UI or even worse fire an ANR...
//        mQueryHandler = new NotifyingAsyncQueryHandler(getContentResolver(), this);
//        mQueryHandler.startQuery(Media.INTERNAL_CONTENT_URI, AudioFilesQuery.PROJECTION, AudioFilesQuery.SORT_ORDER);
    }
    public class MyAdapter extends BaseAdapter implements StickyListHeadersAdapter,Filterable
    {

        private List<String> countries;
        private LayoutInflater inflater;
        private List<String>filteredData = null;
        private ItemFilter mFilter = new ItemFilter();
        public MyAdapter(Context context,List<String> countri)
        {
            inflater = LayoutInflater.from(context);
            countries=countri;
            filteredData=countri;
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
        public Filter getFilter()
        {
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

                String filterableString ;

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
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                filteredData = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }

        }

        class HeaderViewHolder
        {
            TextView text;
        }

        class ViewHolder {
            TextView text;
        }

    }


//
//    @Override
//    protected void onDestroy() {
//        // Clear any strong reference to this Activity
//        mQueryHandler.clearQueryListener();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onQueryComplete(int token, Object cookie, Cursor cursor) {
//        if (cursor != null) {
//            startManagingCursor(cursor);
//        }
//        mAdapter.changeCursor(cursor);
//    }
//
//    private static class AudioFilesViewHolder
//    {
//        public TextView separator;
//        public TextView titleView;
//        public CharArrayBuffer titleBuffer = new CharArrayBuffer(128);
//        public TextView subtitleView;
//        public LinearLayout lnrtop;
//        public StringBuilder subtitleBuffer = new StringBuilder();
//    }
//
//    private static class AudioFilesAdapter extends CursorAdapter
//    {
//
//        /**
//         * State of ListView item that has never been determined.
//         */
//        private static final int STATE_UNKNOWN = 0;
//
//        /**
//         * State of a ListView item that is sectioned. A sectioned item must
//         * display the separator.
//         */
//        private static final int STATE_SECTIONED_CELL = 1;
//
//        /**
//         * State of a ListView item that is not sectioned and therefore does not
//         * display the separator.
//         */
//        private static final int STATE_REGULAR_CELL = 2;
//
//        private final CharArrayBuffer mBuffer = new CharArrayBuffer(128);
//        private int[] mCellStates;
//
//        public AudioFilesAdapter(Context context, Cursor cursor) {
//            super(context, cursor);
//            mCellStates = cursor == null ? null : new int[cursor.getCount()];
//        }
//
//        @Override
//        public void changeCursor(Cursor cursor) {
//            super.changeCursor(cursor);
//            mCellStates = cursor == null ? null : new int[cursor.getCount()];
//        }
//
//        @Override
//        public void bindView(View view, final Context context, Cursor cursor)
//        {
//
//            final AudioFilesViewHolder holder = (AudioFilesViewHolder) view.getTag();
//
//            /*
//             * Separator
//             */
//            boolean needSeparator = false;
//
//            final int position = cursor.getPosition();
//            cursor.copyStringToBuffer(AudioFilesQuery.TITLE, holder.titleBuffer);
//
//            switch (mCellStates[position])
//            {
//                case STATE_SECTIONED_CELL:
//                    needSeparator = true;
//                    break;
//
//                case STATE_REGULAR_CELL:
//                    needSeparator = false;
//                    break;
//
//                case STATE_UNKNOWN:
//                default:
//                    // A separator is needed if it's the first itemview of the
//                    // ListView or if the group of the current cell is different
//                    // from the previous itemview.
//                    if (position == 0) {
//                        needSeparator = true;
//                    } else {
//                        cursor.moveToPosition(position - 1);
//
//                        cursor.copyStringToBuffer(AudioFilesQuery.TITLE, mBuffer);
//                        if (mBuffer.sizeCopied > 0 && holder.titleBuffer.sizeCopied > 0 && mBuffer.data[0] != holder.titleBuffer.data[0]) {
//                            needSeparator = true;
//                        }
//
//                        cursor.moveToPosition(position);
//                    }
//
//                    // Cache the result
//                    mCellStates[position] = needSeparator ? STATE_SECTIONED_CELL : STATE_REGULAR_CELL;
//                    break;
//            }
//
//            if (needSeparator) {
//                holder.separator.setText(holder.titleBuffer.data, 0, 1);
//                holder.separator.setVisibility(View.VISIBLE);
//            } else {
//                holder.separator.setVisibility(View.GONE);
//            }
//
//            /*
//             * Title
//             */
//            holder.titleView.setText(holder.titleBuffer.data, 0, holder.titleBuffer.sizeCopied);
//
//            /*
//             * Subtitle
//             */
//            holder.subtitleBuffer.setLength(0);
//            final String album = cursor.getString(AudioFilesQuery.ALBUM);
//            if (!TextUtils.isEmpty(album)) {
//                holder.subtitleBuffer.append(album);
//                final String artist = cursor.getString(AudioFilesQuery.ARTIST);
//                if (!TextUtils.isEmpty(artist)) {
//                    holder.subtitleBuffer.append(" - ");
//                    holder.subtitleBuffer.append(artist);
//                }
//            }
//
//            if (TextUtils.isEmpty(holder.subtitleBuffer)) {
//                holder.subtitleView.setVisibility(View.GONE);
//            } else {
//                holder.subtitleView.setVisibility(View.VISIBLE);
//                holder.subtitleView.setText(holder.subtitleBuffer);
//            }
//
//            holder.lnrtop.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
////                    Cursor cursor = (Cursor) mAdapter.getItem(position);
////                    if(cursor.getCount()>0)
////                    {
////                        do
////                        {
//                            Constants.selectedclient=holder.titleView.getText().toString();
////                    Intent returnIntent = new Intent();
////                    returnIntent.putExtra("result", "6");
////
////                    listActivity.setResult(Activity.RESULT_OK,returnIntent);
//
//
//                            SectionedListActivity.listActivity.finish();
//
////                        }
////                        while (cursor.moveToFirst());
////                    }
//
//                }
//            });
//
//        }
//
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//
//            View v = LayoutInflater.from(context).inflate(R.layout.custom_search_list, parent, false);
//
//            // The following code allows us to keep a reference on the child
//            // views of the item. It prevents us from calling findViewById at
//            // each getView/bindView and boosts the rendering code.
//            AudioFilesViewHolder holder = new AudioFilesViewHolder();
//            holder.separator = (TextView) v.findViewById(R.id.separator);
//            holder.titleView = (TextView) v.findViewById(R.id.title);
//            holder.subtitleView = (TextView) v.findViewById(R.id.subtitle);
//            holder.lnrtop = (LinearLayout) v.findViewById(R.id.lnrtop);
//
//            v.setTag(holder);
//
//            return v;
//        }
//
//    }
//
//    /**
//     * Keep query data in one place
//     *
//     * @author Cyril Mottier
//     */
//    private interface AudioFilesQuery
//    {
//        String[] PROJECTION = {
//                Media._ID, Media.TITLE, Media.ALBUM, Media.ARTIST
//        };
//
//        int TITLE = 1;
//        int ALBUM = 2;
//        int ARTIST = 3;
//
//        String SORT_ORDER = Media.TITLE + " ASC";
//    }

}