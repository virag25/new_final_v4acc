package amigoinn.example.v4sales;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.v4sales.R;

import java.util.ArrayList;

import amigoinn.db_model.ClientOrderInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.modallist.ClientOrderList;

public class ClientsOutStandingsFragment extends BaseFragment {

    ListView listView2;
    ListViewCustomAdapter adateer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clients_out_standings_fragment, container, false);
        listView2 = (ListView) v.findViewById(R.id.listView2);
        loadData();
        return v;
    }


    public void loadData() {
        showProgress();
        ClientOrderList.Instance().DoSale(new ModelDelegates.ModelDelegate<ClientOrderInfo>() {
            @Override
            public void ModelLoaded(ArrayList<ClientOrderInfo> list) {
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
        ArrayList<ClientOrderInfo> m_list;

        public ListViewCustomAdapter(Activity context, ArrayList<ClientOrderInfo> list)

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
        public View getView(int arg0, View arg1, ViewGroup arg2) {
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

            ClientOrderInfo c_info = m_list.get(arg0);
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

                }
            });

            return arg1;
        }

    }


}
