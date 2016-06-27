package amigoinn.example.v4accapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import amigoinn.db_model.ClientInfo;
import amigoinn.db_model.ProductInfo;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlAdd, rlRecipts, rlPendingOrder;
    LinearLayout llNew, llPriceList, llItem, llCustomer, llOverDue, llTotal, llRejected, llOpen, llClosed;
    View v;
    TextView txtCustomerCount, txtProductCount;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dashboard_fragment, container, false);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        rlAdd = (RelativeLayout) v.findViewById(R.id.rlAdd);

        rlRecipts = (RelativeLayout) v.findViewById(R.id.rlRecipts);
        rlPendingOrder = (RelativeLayout) v.findViewById(R.id.rlPendingOrder);

        llNew = (LinearLayout) v.findViewById(R.id.llNew);

        txtCustomerCount = (TextView) v.findViewById(R.id.txtCustomerCount);
        txtProductCount = (TextView) v.findViewById(R.id.txtProductCount);

        int csize = ClientInfo.getAllClintsize();
        if (csize != 0) {
            txtCustomerCount.setText(csize + "");
        }

        int psize = ProductInfo.getAllClintsize();
        if (psize != 0) {
            txtProductCount.setText(psize + "");
        }

        llPriceList = (LinearLayout) v.findViewById(R.id.llPriceList);
        llItem = (LinearLayout) v.findViewById(R.id.llItem);
        llCustomer = (LinearLayout) v.findViewById(R.id.llCustomer);
        llOverDue = (LinearLayout) v.findViewById(R.id.llOverDue);
        llTotal = (LinearLayout) v.findViewById(R.id.llTotal);
        llRejected = (LinearLayout) v.findViewById(R.id.llRejected);
        llOpen = (LinearLayout) v.findViewById(R.id.llOpen);
        llClosed = (LinearLayout) v.findViewById(R.id.llClosed);

        llCustomer.setOnClickListener(this);
        llItem.setOnClickListener(this);
        llPriceList.setOnClickListener(this);

        llCustomer.setOnClickListener(this);

        llNew.setOnClickListener(this);
        llClosed.setOnClickListener(this);
        llOpen.setOnClickListener(this);
        llRejected.setOnClickListener(this);


        rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = null;
//                Bundle args = new Bundle();
//                FragmentManager frgManager = getFragmentManager();
//                fragment = new FragmentCategory();
//                fragment.setArguments(args);
//                frgManager.beginTransaction()
//                        .replace(R.id.content_frame, fragment).commit();
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v == llCustomer) {

//            Constants.PartyList.clear();
//            Constants.addParty();
//            Constants.countries = Constants.PartyList;
//            Config.filterfrom = "Mainmenu";
//
//            Fragment fragment = null;
//            Bundle args = new Bundle();
//            FragmentManager frgManager = getFragmentManager();
//            fragment = new SectionedListBeforeFilter();
//            fragment.setArguments(args);
//            frgManager.beginTransaction()
//                    .replace(R.id.content_frame, fragment).commit();

            Fragment fragment1234 = new ClientListSectionedFragment();//.newInstance();
            Bundle args = new Bundle();
            FragmentManager frgManager = getFragmentManager();
            fragment1234.setArguments(args);
            frgManager.beginTransaction().replace(R.id.content_frame, fragment1234).commit();
        } else if (v == llItem) {

//            Fragment fragment1234 = new ProductListSectionedActivity();//.newInstance();
//            Bundle args = new Bundle();
//            FragmentManager frgManager = getFragmentManager();
//            fragment1234.setArguments(args);
//            frgManager.beginTransaction().replace(R.id.content_frame, fragment1234).commit();
//            Config.filterfrom = "product";
//            Constants.Productlist.clear();
//            Constants.addProducts();
//            Constants.countries = Constants.Productlist;
//            Fragment fragment = null;
//            Bundle args = new Bundle();
//            FragmentManager frgManager = getFragmentManager();
//            fragment = new SectionedListBeforeFilter();
//            fragment.setArguments(args);
//            frgManager.beginTransaction()
//                    .replace(R.id.content_frame, fragment).commit();

        } else if (v == llPriceList) {

        } else if (v == llNew) {
//            Intent start = new Intent(getActivity(), NewOrderTabActiviy.class);
//            startActivity(start);

        } else if (v == llRejected) {
//            Intent start = new Intent(getActivity(), NewOrderTabActiviy.class);
//            startActivity(start);


        } else if (v == llOpen) {
//            Intent start = new Intent(getActivity(), NewOrderTabActiviy.class);
//            startActivity(start);


        } else if (v == llClosed) {
//            Intent start = new Intent(getActivity(), NewOrderTabActiviy.class);
//            startActivity(start);


        }

    }
}
