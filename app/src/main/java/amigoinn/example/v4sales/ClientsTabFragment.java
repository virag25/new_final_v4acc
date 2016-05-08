package amigoinn.example.v4sales;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;


import amigoinn.db_model.ClientInfo;

public class ClientsTabFragment extends FragmentActivity {

    private String Titles[];
    private int Numboftabs = 5;
    //List<Sections> list;
    private TextView top_title;
    View view;
    public static ViewPager viewPager;
    static PagerSlidingTabStrip tabs;
//    Context activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_tab_fragment);
        Titles = new String[5];
        Titles[0] = "Orders";
        Titles[1] = "Contacts";
        Titles[2] = "Analytics";
        Titles[3] = "Dispatch";//ladgers
        Titles[4] = "Sale";//outstandings
        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);
        top_title = (TextView) findViewById(R.id.txtCompany);
        String code = AccountApplication.getClient_code();
        ClientInfo code_info = ClientInfo.getClintInfoById(code);
        if (code != null) {
            top_title.setText(code_info.name);
        }
        viewPager.setAdapter(new ClientsPagerAdapter(
                getApplicationContext(), getSupportFragmentManager(), Titles, Numboftabs));
        int px = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, ClientsTabFragment.this.getResources().getDisplayMetrics()));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setAllCaps(true);

        tabs.setUnderlineHeight(3);
        int px1 = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, ClientsTabFragment.this.getResources().getDisplayMetrics()));
        tabs.setIndicatorHeight(px1);

        tabs.setIndicatorColor(ClientsTabFragment.this.getResources().getColor(R.color.main_color_300));
        tabs.setDividerColor(getResources().getColor(R.color.main_color_300));
        //tabs.setDividerColor("#478fcc");
        tabs.setShouldExpand(true);

        tabs.setTextSize(px);
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    //   @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.activity_clients_tab_fragment, container, false);
//        context = view.getContext();
//        Numboftabs = 5;
//
//        Titles = new String[5];
//        Titles[0] = "Business";
//        Titles[1] = "Contacts";
//        Titles[2] = "Analytics";
//        Titles[3] = "Ledgers";
//        Titles[4] = "Outstanding";
//        return v;
//    }


//    public void onStart() {
//        super.onStart();
////        activity = getActivity();
//        idMappings();
//        setListeners();
//
//        viewPager = (ViewPager) view.findViewById(R.id.tabanim_viewpager);
//        viewPager.setAdapter(new ClientsPagerAdapter(
//                getApplicationContext(), getSupportFragmentManager(), Titles, Numboftabs));
//        int px = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()));
//        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
//        tabs.setAllCaps(true);
//
//        tabs.setUnderlineHeight(3);
//        int px1 = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()));
//        tabs.setIndicatorHeight(px1);
//
//        tabs.setIndicatorColor(context.getResources().getColor(R.color.main_color_300));
//        tabs.setDividerColor(getResources().getColor(R.color.main_color_300));
//        //tabs.setDividerColor("#478fcc");
//        tabs.setShouldExpand(true);
//
//        tabs.setTextSize(px);
//        tabs.setViewPager(viewPager);
//        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//    }

    private void idMappings() {


    }

    private void setListeners() {
    }

    public class ClientsPagerAdapter extends FragmentStatePagerAdapter {

        CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
        int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
        private Context context;


        public ClientsPagerAdapter(Context context, FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);
            this.context = context;
            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;
        }

        @Override
        public Fragment getItem(int position) {
            SharedPreferences preference = context.getSharedPreferences("url", Context.MODE_PRIVATE);
            SharedPreferences.Editor edotor = preference.edit();


            switch (position) {// if the position is 0 we are returning the First tab

                case 0:
                    try {
                        ClientBussinessFragments business_fragments = new ClientBussinessFragments();
                        return business_fragments;
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                    }
                    break;
                case 1:

                    try {
                        ClientContactFragment contact = new ClientContactFragment();
                        return contact;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:

                    try {

                        FragmentReportChart analytics = new FragmentReportChart();
                        return analytics;
//                        ClientAnalyticsFragment analytics = new ClientAnalyticsFragment();
//                        return analytics;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        ClientLedgersFragment ladgers = new ClientLedgersFragment();
                        return ladgers;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    try {
                        ClientsOutStandingsFragment outstandings = new ClientsOutStandingsFragment();
                        return outstandings;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;


            }

            return null;

        }

        @Override
        public int getCount() {
            return NumbOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[position];
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent start = new Intent(getApplicationContext(), LeftMenusActivity.class);
        start.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(start);
    }
}
