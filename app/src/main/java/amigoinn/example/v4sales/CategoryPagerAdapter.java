package amigoinn.example.v4sales;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


public class CategoryPagerAdapter extends FragmentStatePagerAdapter
{

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    private Context context;


    public CategoryPagerAdapter(Context context, FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb)
    {
        super(fm);
        this.context = context;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position)
    {
        SharedPreferences preference=context.getSharedPreferences("url", Context.MODE_PRIVATE);
        SharedPreferences.Editor edotor=preference.edit();


        switch (position) {// if the position is 0 we are returning the First tab

            case 0:
    try {
        FragmentCategoryItem menu_completed = new FragmentCategoryItem();
        return menu_completed;
    }catch(Exception ex)
    {
        Log.e("Error",ex.toString());
    }
                break;

//                edotor.clear();
//                edotor.putString("url","0");
//                edotor.commit();
//                //return menu_completed;
//                break;



            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
            case 1:

                FragmentCategoryItemAttendance menu_completedA = new FragmentCategoryItemAttendance();
                return menu_completedA;

                //return menu_completed;
//                break;
            case 2:


                FragmentCategoryItemRank menu_completedR = new FragmentCategoryItemRank();
                return menu_completedR;
//                //return menu_completed;


        }

        return  null;

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

