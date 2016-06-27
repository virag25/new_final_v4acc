package amigoinn.example.v4accapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import amigoinn.db_model.ClientOrderSaleDetailInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.ProductInfo;
import amigoinn.modallist.ClientSaleList;

public class ClientOrderSaleDetail extends BaseActivity {
    ListView lvorder;
    ListViewCustomAdapter adateer;
    TextView txtClient;
    String TrnCtrlNo, DocNoPrefix, DocNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_order_sale_detail);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            TrnCtrlNo = b.getString("TrnCtrlNo");
            DocNoPrefix = b.getString("DocNoPrefix");
            DocNo = b.getString("DocNo");
        }
        lvorder = (ListView) findViewById(R.id.lvorder);
        txtClient = (TextView) findViewById(R.id.txtClient);
        loadData();
    }

    public void loadData() {
        showProgress();
        ClientSaleList.Instance().DoSaleDetail(new ModelDelegates.ModelDelegate<ClientOrderSaleDetailInfo>() {
            @Override
            public void ModelLoaded(ArrayList<ClientOrderSaleDetailInfo> list) {
                hideProgress();
                if (list != null && list.size() > 0) {
                    adateer = new ListViewCustomAdapter(ClientOrderSaleDetail.this, list);
                    lvorder.setAdapter(adateer);
                } else {
                    Toast.makeText(getApplicationContext(), "There are no any data found", Toast.LENGTH_LONG).show();
                    ;
                }

            }

            @Override
            public void ModelLoadFailedWithError(String error) {
                hideProgress();
                Toast.makeText(getApplicationContext(), "There are no any data found", Toast.LENGTH_LONG).show();
                ;
            }
        }, TrnCtrlNo, DocNoPrefix, DocNo);
    }

    public class ListViewCustomAdapter extends BaseAdapter {
        public Activity context;
        LayoutInflater inflater;
        ArrayList<ClientOrderSaleDetailInfo> m_list;

        public ListViewCustomAdapter(Activity context, ArrayList<ClientOrderSaleDetailInfo> list)

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
            TextView name;
            TextView txtPrice;
            EditText txtQty;
            TextView edtcode, edtdate, txtRate;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            // TODO Auto-generated method stub

            Holder hv;

            if (arg1 == null) {
                hv = new Holder();
                arg1 = inflater.inflate(R.layout.custom_order_home, null);
//                hv.edtcode = (TextView) arg1.findViewById(R.id.edtcode);
                hv.edtdate = (TextView) arg1.findViewById(R.id.edtdate);
                hv.txtQty = (EditText) arg1.findViewById(R.id.txtQty);
                hv.txtPrice = (TextView) arg1.findViewById(R.id.txtprice);
                hv.txtRate = (TextView) arg1.findViewById(R.id.txtRate);
                arg1.setTag(hv);
            } else {
                hv = (Holder) arg1.getTag();
            }


            ClientOrderSaleDetailInfo info = m_list.get(arg0);

            if (info != null) {
//                hv.edtcode.setText(info.StockNo);
                ProductInfo pinfo = ProductInfo.getProductInfoById(info.StockNo);
                hv.edtdate.setText(pinfo.product);
                hv.txtRate.setText(info.StkUpdtRate);
                hv.txtQty.setText(String.valueOf(info.DocQty));
                hv.txtQty.setEnabled(false);
                hv.txtPrice.setText(info.StkUpdtValueOut);
            }


            return arg1;
        }

    }
}
