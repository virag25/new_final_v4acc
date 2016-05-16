package amigoinn.example.v4sales;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import amigoinn.db_model.ClientDispatchInfo;
import amigoinn.db_model.ClientDispatchInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.modallist.ClientDispatchList;
import amigoinn.modallist.ClientOrderList;

public class ClientLedgersFragment extends BaseFragment {
    ListView listView2;
    ListViewCustomAdapter adateer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.client_ledgers_fragment, container, false);
        listView2 = (ListView) v.findViewById(R.id.listView2);
//        loadData();
        v.setFocusableInTouchMode(true);
        v.requestFocus();

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Intent start = new Intent(getActivity(), LeftMenusActivity.class);
                        start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(start);
                        return true;
                    }
                }
                return false;
            }
        });
        return v;
    }

    public void loadData() {
        showProgress();
        ClientDispatchList.Instance().DoClientDispatch(new ModelDelegates.ModelDelegate<ClientDispatchInfo>() {
            @Override
            public void ModelLoaded(ArrayList<ClientDispatchInfo> list) {
                hideProgress();
                if (list != null && list.size() > 0) {
                    adateer = new ListViewCustomAdapter(getActivity(), list);
                    listView2.setAdapter(adateer);
                } else {
                    Toast.makeText(getActivity(), "There are no any data found", Toast.LENGTH_LONG).show();
                    ;
                }

            }

            @Override
            public void ModelLoadFailedWithError(String error) {
                hideProgress();
                Toast.makeText(getActivity(), "There are no any data found", Toast.LENGTH_LONG).show();
                ;
            }
        });
    }


    public class ListViewCustomAdapter extends BaseAdapter {
        public Activity context;
        LayoutInflater inflater;
        ArrayList<ClientDispatchInfo> m_list;

        public ListViewCustomAdapter(Activity context, ArrayList<ClientDispatchInfo> list)

        {
            super();
            this.context = context;
            this.inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            m_list = list;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return m_list.size();
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
            TextView textview1, textview2, textview3;
        }

        @Override
        public View getView(final int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub

            Holder hv;

            if (arg1 == null) {
                hv = new Holder();
                arg1 = inflater.inflate(R.layout.new_dashboard_item, null);
                hv.rlMain = (LinearLayout) arg1.findViewById(R.id.rlMain);
                hv.textview1 = (TextView) arg1.findViewById(R.id.textView1);
                hv.textview2 = (TextView) arg1.findViewById(R.id.textView2);
                hv.textview3 = (TextView) arg1.findViewById(R.id.textView3);
                arg1.setTag(hv);
            } else {
                hv = (Holder) arg1.getTag();
            }

            ClientDispatchInfo c_info = m_list.get(arg0);
            if (c_info != null) {
                String str = "TrnCtrlNo " + c_info.TrnCtrlNo;
                hv.textview1.setText(str);
                String str2 = "NetDocValue " + c_info.NetDocValue;
                hv.textview2.setText(str2);
                String str3 = "VAUid " + c_info.VAUid;
                hv.textview3.setText(str3);
            }

            hv.rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent start = new Intent(getActivity(), ClientOrderDispatchActiviy.class);
                    ClientDispatchInfo c_info = m_list.get(arg0);
                    start.putExtra("Order no.: ", c_info.TrnCtrlNo);
                    start.putExtra("Net Amount: ", c_info.DocNoPrefix);
                    start.putExtra("Date", c_info.DocNoPrefix);
                    startActivity(start);
                }
            });

            return arg1;
        }

    }

}
