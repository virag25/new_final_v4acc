package amigoinn.example.v4accapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.astuetz.PagerSlidingTabStrip;


/**
 * Created by Manthan on 27/09/2015.
 */
public class FragmentCategory extends Fragment
{

//    private String Titles[]= {"New", "Accepted","Completed","New", "Accepted","Completed"};
//    private int Numboftabs = 6
    private String Titles[];
    private int Numboftabs;
    //List<Sections> list;
    private TextView top_title;
    View view;
    public static ViewPager viewPager;
    public static int pos;
    static PagerSlidingTabStrip tabs;
    Context context;
    Context activity;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_category, container, false);
//         list = Constants.sectionList;
        context=view.getContext();
        Numboftabs =3;

             Titles = new String[3];
              Titles[0]="Task";
        Titles[1]="Route";
        Titles[2]="Order";
//        Titles[2]="Rank";

        return view;
    }

    public void onStart()
    {
        super.onStart();
        activity = getActivity();
        idMappings();
        setListeners();

         viewPager= (ViewPager) view.findViewById(R.id.tabanim_viewpager);
        viewPager.setAdapter(new CategoryPagerAdapter(
                activity, getChildFragmentManager(), Titles, Numboftabs));
        int px = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()));
         tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setAllCaps(true);

        tabs.setUnderlineHeight(3);
        int px1 = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()));
        tabs.setIndicatorHeight(px1);

        tabs.setIndicatorColor(context.getResources().getColor(R.color.main_color_300));
        tabs.setDividerColor(getResources().getColor(R.color.main_color_300));
        //tabs.setDividerColor("#478fcc");
        tabs.setShouldExpand(true);

        tabs.setTextSize(px);
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
//                SharedPreferences preference=context.getSharedPreferences("url1",Context.MODE_PRIVATE);
//                SharedPreferences.Editor edotor=preference.edit();
//               // FragmentCategoryItem menu_completed = new FragmentCategoryItem();
//
//                switch (position) {// if the position is 0 we are returning the First tab
//
//                    case 0:
//
//                        edotor.clear();
//                        edotor.putString("url","0");
//                        edotor.commit();
//                        //return menu_completed;
//                        break;
//
//
//
//                    // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
//                    case 1:
//
//
//                        edotor.clear();
//                        edotor.putString("url","1");
//                        edotor.commit();
//                        //return menu_completed;
//                        break;
//                    case 2:
//
//
//                        edotor.clear();
//                        edotor.putString("url","2");
//                        edotor.commit();
//                        //return menu_completed;
//                        break;
//
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        // Give the TabLayout the ViewPager
//        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabanim_tabs);
//        tabLayout.setupWithViewPager(viewPager);
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//
//                /*switch (tab.getPosition()){
//
//                    case 0:
//
//                    case 1:
//
//                    case 2:
//
//                    default:
//                }
//*/
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    private void idMappings()
    {


    }
    private void setListeners() {
    }

}

