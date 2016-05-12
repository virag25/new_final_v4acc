package amigoinn.example.v4sales;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import amigoinn.adapters.CameraActivity;


import com.google.android.gms.gcm.GoogleCloudMessaging;

import amigoinn.adapters.SectionedListBeforeFilter;
import amigoinn.db_model.UserInfo;
import amigoinn.walkietalkie.Constants;
import amigoinn.walkietalkie.images.ImageUtil;
import amigoinn.walkietealkie.drawable.DrawerAdapter;
import amigoinn.walkietealkie.drawable.DrawerItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LeftMenusActivity extends ActionBarActivity {

    public static final String LEFT_MENU_OPTION = "com.csform.android.uiapptemplate.LeftMenusActivity";
    public static final String LEFT_MENU_TRAVEL = "Travel Left Menu";
    public static final String LEFT_MENU_SOCIAL = "Social Left Menu";
    public static final String LEFT_MENU_MEDIA = "Media Left Menu";
    public static final String LEFT_MENU_SHOPPING = "Shopping Left Menu";
    public static final String LEFT_MENU_NEWS = "News Left Menu";
    String regId = "abc";
    public static final String LEFT_MENU_OPTION_1 = "Universal Left Menu";
    public static final String LEFT_MENU_OPTION_2 = "Universal 2 Left Menu";
    public static final String REG_ID = "regId";
    private ListView mDrawerList;
    private List<DrawerItem> mDrawerItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    String imeiNo = "";
    private Handler mHandler;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    GoogleCloudMessaging gcm;

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public interface UniversalDelegate {
        public void CallDidSuccess(String lat, String lang);

        public void CallFailedWithError(String error);
    }

    public String getMailId() {
        String strGmail = null;
        try {
            Account[] accounts = AccountManager.get(this).getAccounts();
            Log.e("PIKLOG", "Size: " + accounts.length);
            for (Account account : accounts) {

                String possibleEmail = account.name;
                String type = account.type;

                if (type.equals("com.google")) {

                    strGmail = possibleEmail;
                    Log.e("PIKLOG", "Emails: " + strGmail);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strGmail;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("V4Account");
        //GcmMessageHandler.count=0;
        //registerGCM();

//		setAlaram(10);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_view);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        prepareNavigationDrawerItems();
        setAdapter();
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mHandler = new Handler();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        if (savedInstanceState == null) {
            try {
                //mDrawerLayout.openDrawer(mDrawerList);
                selectItem(1);
            } catch (Exception ex) {
                Log.e("Error", ex.toString());
            }
        }
        String from = getIntent().getStringExtra("for");
        try {
            if (from.equalsIgnoreCase("order")) {
                selectItem(3);
            }
        } catch (Exception ex) {

        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imeiNo = telephonyManager.getDeviceId();
        try {
            registerGCM();
        } catch (Exception ex) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
//	private void registerInBackground() {
//		new AsyncTask<Void, Void, Void>() {
//			@Override
//			protected Void doInBackground(Void... params) {
//				String msg = "";
//				try {
//					if (gcm == null) {
//						gcm = GoogleCloudMessaging.getInstance(LeftMenusActivity.this);
//					}
//					regId = gcm.register(Config.SENDER_ID);
//					Log.d("RegisterActivity", "registerInBackground - regId: "
//							+ regId);
//					msg = "Device registered, registration ID=" + regId;
//
//				} catch (IOException ex) {
//					msg = "Error :" + ex.getMessage();
//					Log.d("RegisterActivity", "Error: " + msg);
//				}
//				Log.d("RegisterActivity", "AsyncTask completed: " + msg);
//				return null;
//			}
//
//			@Override
//			protected void onPostExecute(String msg) {
//				Toast.makeText(getApplicationContext(),
//						"Registered with GCM Server." + msg, Toast.LENGTH_LONG)
//						.show();
//				saveRegisterId(LeftMenusActivity.this, regId);
//			}
//		}.execute(null, null, null);
//	}


    private void saveRegisterId(Context context, String regId) {
        final SharedPreferences prefs = getSharedPreferences(
                LeftMenusActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        Log.i("Error", "Saving regId on app version ");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.commit();
    }

    private void setAdapter() {
        String option = LEFT_MENU_OPTION_1;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(LEFT_MENU_OPTION)) {
            option = extras.getString(LEFT_MENU_OPTION, LEFT_MENU_OPTION_1);
        }

        boolean isFirstType = true;

        View headerView = null;
        if (option.equals(LEFT_MENU_OPTION_1)) {
            headerView = prepareHeaderView(R.layout.header_navigation_drawer_1,
                    "http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg",
                    "Marketing Feedback", "V4 Accounts");
        } else if (option.equals(LEFT_MENU_OPTION_2)) {
            headerView = prepareHeaderView(R.layout.header_navigation_drawer_2,
                    "http://pengaja.com/uiapptemplate/newphotos/profileimages/0.jpg",
                    "Marketing Feedback", "V4 Accounts");
            isFirstType = false;
        }

        BaseAdapter adapter = new DrawerAdapter(this, mDrawerItems, isFirstType);

        mDrawerList.addHeaderView(headerView);// Add header before adapter (for
        // pre-KitKat)
        mDrawerList.setAdapter(adapter);
    }

    private View prepareHeaderView(int layoutRes, String url, String email, String Name) {
        View headerView = getLayoutInflater().inflate(layoutRes, mDrawerList,
                false);
        try {
            ImageView iv = (ImageView) headerView.findViewById(R.id.image);
            TextView tv = (TextView) headerView.findViewById(R.id.txtUpdatephoto);
            TextView tvName = (TextView) headerView.findViewById(R.id.name);
//            ImageUtil.displayRoundImage(iv, url, null);
            tv.setText("v4acc@gmail.com");

//            tvName.setText(Name);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(LeftMenusActivity.this, CameraActivity.class);
                    startActivity(in);
                }
            });
        } catch (Exception ex) {

        }
        return headerView;
    }

    private void prepareNavigationDrawerItems() {
        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_ReportBug,
                R.string.drawer_title_home,
                DrawerItem.DRAWER_ITEM_TAG_LINKED_IN));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_task,
                R.string.drawer_title_tasks, DrawerItem.DRAWER_ITEM_TAG_BLOG));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_order,
                R.string.drawer_title_order,
                DrawerItem.DRAWER_ITEM_TAG_GIT_HUB));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_order,
                R.string.drawer_title_sales,
                DrawerItem.DRAWER_ITEM_TAG_GIT_HUB));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_route,
                R.string.drawer_title_route,
                DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_product,
                R.string.drawer_title_PRODUCTS,
                DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_client,
                R.string.drawer_title_CLIENTS,
                DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));
//		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_ReportBug,
//				R.string.drawer_title_PROFILE,
//				DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));

//		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_Share,
//				R.string.drawer_title_MESSAGE,
//				DrawerItem.DRAWER_ITEM_TAG_GIT_HUB));
        ////////FOR STOCK TAKE
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_report, R.string.drawer_title_REPORTS, DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));
        ////////FOR STOKE TAKE

        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_stocktake,
                R.string.drawer_title_RankFinal,
                DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));
        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_task, R.string.drawer_title_message, DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));

        mDrawerItems.add(new DrawerItem(R.string.drawer_icon_order, R.string.drawer_title_target, DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));


//		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_Share,
//				R.string.drawer_title_LOGOUT,
//				DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));
//		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_Share,
//				R.string.drawer_title_PRICELIST,
//				DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));
//		mDrawerItems.add(new DrawerItem(R.string.drawer_icon_about,
//				R.string.drawer_title_RETURN,
//				DrawerItem.DRAWER_ITEM_TAG_INSTAGRAM));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences preference = getSharedPreferences("url", Context.MODE_PRIVATE);
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.Announcements:
//				SharedPreferences.Editor editor=preference.edit();
//				editor.clear();
//				editor.putString("url", "http://www.amigoinnovations.co.in/aspiremobile/index.php?student/noticeboard");//?teacher/dashboard");
//				editor.commit();
                Fragment fragment = new NotificationFragment();//.newInstance();
                commitFragment(fragment);
                setTitle("Notifications");
                break;
            case R.id.Messages:
                Fragment fragment1 = new FragmentMessages();//.newInstance();
                commitFragment(fragment1);
                setTitle("Message");
                break;
            case R.id.Profile:
                try {
                    Fragment fragment12345 = new ProfileActivity();//.newInstance();
                    commitFragment(fragment12345);
                    setTitle("Profile");
                    break;

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                break;
            case R.id.About:
                try {
                    Fragment fragment12345 = new CheckAndRadioBoxesFragment();//.newInstance();
                    commitFragment(fragment12345);
                    setTitle("About");
                    break;

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                break;

            case R.id.LogOut:
                try {
                    UserInfo.DeleteUser();
                    SharedPreferences preference1 = getSharedPreferences("profile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edi = preference1.edit();
                    edi.clear();
                    edi.commit();
                    Intent in = new Intent(LeftMenusActivity.this, LoginActivity.class);
                    startActivity(in);
                    finish();
                } catch (Exception ex) {

                }
                break;

            case R.id.Support:
                try {
//					Intent intent = new Intent(Intent.ACTION_MAIN);
//					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					intent.setComponent(ComponentName.unflattenFromString("com.teamviewer.quicksupport.market"));
//					intent.addCategory(Intent.CATEGORY_LAUNCHER);
//					startActivity(intent)
                    openApp(LeftMenusActivity.this, "com.teamviewer.quicksupport.market");
                } catch (Exception ex) {
                    Log.e("teamviewer", ex.toString());
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                return false;
                //throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position/* , mDrawerItems.get(position - 1).getTag() */);

        }
    }

    public void commitFragment(Fragment fragment) {
        // Using Handler class to avoid lagging while
        // committing fragment in same time as closing
        // navigation drawer
        mHandler.post(new CommitFragmentRunnable(fragment));
    }

    private class CommitFragmentRunnable implements Runnable {

        private Fragment fragment;

        public CommitFragmentRunnable(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void run() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

    private void selectItem(int position/* , int drawerTag */) {
        // minus 1 because we have header that has 0 position
        if (position < 1) { // because we have header, we skip clicking on it
            return;
        }
        SharedPreferences preference = getSharedPreferences("url", Context.MODE_PRIVATE);
        switch (position) {
            case 1:
//			SharedPreferences.Editor editor=preference.edit();
//			editor.clear();
//			editor.putString("url", "http://www.amigoinnovations.co.in/aspiremobile/index.php?student/dashboard");//?teacher/dashboard");
//			editor.commit();
                try {
                    Fragment fragment = new DashboardFragment();//.newInstance();
                    commitFragment(fragment);
                    setTitle(mDrawerItems.get(position - 1).getTitle());
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                break;
            case 2:
                try {
                    Fragment fragment1234f = new FragmentCategoryItem();//.newInstance();
                    commitFragment(fragment1234f);
//				setTitle("Product Information");
//				Fragment fragment1234f = new AbsentList();//.newInstance();
//				commitFragment(fragment1234f);
//				setTitle("Product Information");
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                break;
            case 3:
                try {
                    Fragment fragment12 = new FragmentCategoryItemRank();//.newInstance();
                    commitFragment(fragment12);
//			setTitle("Homework Diary");
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
//			SharedPreferences.Editor editor2=preference.edit();
//			editor2.clear();
//			editor2.putString("url", "http://www.amigoinnovations.co.in/aspiremobile/index.php?student/study_material");
//			editor2.commit();
//			Fragment fragment2 = new CheckAndRadioBoxesFragment();//.newInstance();
//			commitFragment(fragment2);
//			setTitle(mDrawerItems.get(position - 1).getTitle());
                break;
            case 4:
//			SharedPreferences.Editor editor3=preference.edit();
//			editor3.clear();
//			editor3.putString("url", "http://www.amigoinnovations.co.in/aspiremobile/index.php?student/marks");
//			editor3.commit();
                Fragment fragment3 = new Fragmentsales();//.newInstance();
                commitFragment(fragment3);
                setTitle(mDrawerItems.get(position - 1).getTitle());
                break;
            case 5:
//			SharedPreferences.Editor editor3=preference.edit();
//			editor3.clear();
//			editor3.putString("url", "http://www.amigoinnovations.co.in/aspiremobile/index.php?student/marks");
//			editor3.commit();
                Fragment fragment31 = new FragmentCategoryItemAttendance();//.newInstance();
                commitFragment(fragment31);
                setTitle(mDrawerItems.get(position - 1).getTitle());
                break;
            case 6:
                try {
//				212.131.115.219/navkarmobileservice/
//				Config.filterfrom="product";
//				Constants.Productlist.clear();
//				Constants.addProducts();
//				Constants.countries=Constants.Productlist;
//				Fragment fragment1234 = new SectionedListBeforeFilter();//.newInstance();
//				commitFragment(fragment1234);

                    Fragment fragment1234 = new ProductListSectionedActivity();//.newInstance();
                    commitFragment(fragment1234);
//				setTitle("Result");

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                break;

            case 7:
                try {
//				Fragment fragment123 = new ReportCardFragment();//.newInstance();
//				commitFragment(fragment123);
                    try {
//						Constants.PartyList.clear();
//						Constants.addParty();
//						Constants.countries=Constants.PartyList;
//						Config.filterfrom="Mainmenu";
                        Fragment fragment1234 = new ClientListSectionedActivity();//.newInstance();
                        commitFragment(fragment1234);
//						Intent in = new Intent(LeftMenusActivity.this, SectionedListBeforeFilter.class);
//						startActivity(in);
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                    }
//				setTitle("Report Card");

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                break;
//			case 7:
//
//				try{
//				Fragment fragment12345 = new ProfileActivity();//.newInstance();
//				commitFragment(fragment12345);
//				setTitle("Profile");
//				break;
//
//				}catch(Exception ex)
//				{
//					Log.e("Error",ex.toString());
//				}

            case 8:

                try {
                    Intent ina = new Intent(this, AndroidDatabaseManager.class);
                    startActivity(ina);
//					Fragment fragment12345 = new FragmentReportChart();//.newInstance();
//					commitFragment(fragment12345);
//					setTitle("Report");
                    break;

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }

            case 9:

                try {
                    //FOR STOCK TAKE
                    Fragment fragment12345 = new FragmentCategoryItemRank();//FragmentRankDuplicate();//.newInstance();
                    commitFragment(fragment12345);
                    setTitle("Stock Take");

//                    Intent in = new Intent(LeftMenusActivity.this, CameraActivity.class);
//                    startActivity(in);

                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                break;
            case 10:
                try {
                    Intent in = new Intent(LeftMenusActivity.this, CameraActivity.class);
                    startActivity(in);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 11:
                try {
                    Fragment fragment12345 = new TargetActivity();//.newInstance();
                    commitFragment(fragment12345);
                    setTitle("Target");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

//			case 10:
//
//				try{
//					SharedPreferences preference1=getSharedPreferences("profile", Context.MODE_PRIVATE);
//					SharedPreferences.Editor edi=preference1.edit();
//					edi.clear();
//					edi.commit();
//					Intent in=new Intent(LeftMenusActivity.this,LoginActivity.class);
//					startActivity(in);
//					finish();
////					Fragment fragment12345 = new ProfileActivity();//.newInstance();
////					commitFragment(fragment12345);
////					setTitle("Profile");
//					break;
//
//				}catch(Exception ex)
//				{
//					Log.e("Error",ex.toString());
//				}
        }
//		String drawerTitle = getString(mDrawerItems.get(position - 1)
//				.getTitle());
//		Toast.makeText(this,
//				"You selected " + drawerTitle + " at position: " + position,
//				Toast.LENGTH_SHORT).show();

        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerItems.get(position - 1).getTitle());
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }
//	BroadcastReceiver recever = new BroadcastReceiver()
//	{
//
//		@Override
//		public void onReceive(Context context, Intent intent)
//		{
//			String Message = intent.getStringExtra("price");
//		}
//	};

    public void registerGCM() {
        // TODO Auto-generated method stub
//		registerReceiver(recever, new IntentFilter(Config.DISPLAY_MESSAGE_ACTION));
        try {
            gcm = GoogleCloudMessaging.getInstance(this);
//			SharedPreferences regIdPref=getSharedPreferences("regId", Context.MODE_PRIVATE);
//			String registrationId = regIdPref.getString(REG_ID, "");
//			if (gcm == null)
//			{
//				gcm = GoogleCloudMessaging.getInstance(LeftMenusActivity.this);
//			}

//			StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
//			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
//					.permitAll()
//					.build());

//			regId = gcm.register(Config.SENDER_ID);
            try {
                task.execute();
            } catch (Exception ex) {
                Log.e("Error", ex.toString());
            }

        } catch (Exception e) {
            //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            Log.e("error", e.toString());
        }

        //	SharedPreferences regIdPref=getSharedPreferences("regId", Context.MODE_PRIVATE);
//		if(!regId.equals(regIdPref.getString(REG_ID,"abcd")))
//		{
//		/* SharedPreferences.Editor edit=regIdPref.edit();
//			 edit.clear();
//			 edit.putString("regId",regId);
//			 edit.commit();
//			*/
//			try
//			{
//				new updateRegId().execute();
//			}
//			catch(Exception ex)
//			{
//
//
//			}
//		}

    }

    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
        @Override
        protected void onPostExecute(Void result) {

            // TODO Auto-generated method stub
            SharedPreferences regIdPref = getSharedPreferences("regId", Context.MODE_PRIVATE);
            String registrationId = regIdPref.getString(REG_ID, "");
            if (!regId.equalsIgnoreCase(registrationId)) {
                //	task.execute(null, null, null);
                Log.i("Error", "Registration not found.");
                try {
                    new updateRegId().execute();
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }

//			SharedPreferences regIdPref=getSharedPreferences("regId", Context.MODE_PRIVATE);
//			if(finalresult.length()==5)
//			{
//				SharedPreferences.Editor edit=regIdPref.edit();
//				edit.clear();
//				edit.putString(REG_ID, regId);
//				edit.commit();
//				//Create a new PendingIntent and add it to he AlarmManager
//			}
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging.getInstance(LeftMenusActivity.this);
                }
                regId = gcm.register(Config.SENDER_ID);
//					Log.d("RegisterActivity", "registerInBackground - regId: "
//							+ regId);
//					msg = "Device registered, registration ID=" + regId;


//					GCMRegistrar.register(OrderHistory.this, Config.SENDER_ID);
//
//				if(GCMRegistrar.isRegistered(OrderHistory.this))
//				{
//
//					Log.i("GCM Registration","  registerd  ");
//				}
//				else
//				{
//					Log.i("GCM Registration"," not registerd  ");
//					GCMRegistrar.setRegisteredOnServer(OrderHistory.this, true);
//				}

            } catch (Exception e) {
                Log.e("error ", " " + e);

                e.printStackTrace();
            }
            return null;


        }
    };

    public class updateRegId extends AsyncTask<Void, Void, Void> {
        String finalresult;
        String result;

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            try {
                SharedPreferences regIdPref = getSharedPreferences("regId", Context.MODE_PRIVATE);
                if (finalresult.length() == 5) {
                    SharedPreferences.Editor edit = regIdPref.edit();
                    edit.clear();
                    edit.putString(REG_ID, regId);
                    edit.commit();
                    //Create a new PendingIntent and add it to he AlarmManager
                }
            } catch (Exception ex) {

            }
        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            InputStream is = null;
            //SharedPreferences regIdPref=getSharedPreferences("regId", Context.MODE_PRIVATE);
            //String reg_id=regIdPref.getString("regId",regId);
            int length = 0;
            try {

                SharedPreferences preferences = getApplicationContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
                String id = preferences.getString("id", "");
                String url = ("http://www.v4account.net/v4webservice/insertimei.php?imei=" + imeiNo + "&regid=" + regId + "&email=" + getMailId());

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();


            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection " + e.toString());

            }


            //convert response to string

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "");
                }
                is.close();
                result = sb.toString();
                finalresult = result;
                length = result.length();
            } catch (Exception e) {


            }
            //parse json data
            if (length > 20) {
                try {
                    JSONArray jArray = new JSONArray(result);
                    ArrayList<String> list = new ArrayList<String>();

                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json_data = jArray.getJSONObject(i);


                            //contact,address1,address2,zipcode,city,description,leaddate

                        }

                    }
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                    //  Toast.makeText(getApplicationContext(),"NoData",
                    //          Toast.LENGTH_LONG).show();
                }
            }


            return null;
        }

    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if ((keyCode == KeyEvent.KEYCODE_BACK) && CheckAndRadioBoxesFragment.wvLoader.canGoBack()) {
////			CheckAndRadioBoxesFragment.wvLoader.goBack();
//			CheckAndRadioBoxesFragment.myOnKeyDown(keyCode);
//			return true;
//		}

    //		return super.onKeyDown(keyCode, event);
//	}
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setAlaram(int time) {
        // Intent intent = new Intent("com.mfoodx.app.ServiceForLoadData");
        pendingIntent = PendingIntent.getService(getApplicationContext(), 0,
                new Intent(getApplicationContext(), LocationService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager = (AlarmManager) getApplicationContext().getSystemService(
                Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar
                        .getInstance().getTimeInMillis() + (time * 1000), time * 1000,
                pendingIntent);

        // AlarmManager alarmManager = (AlarmManager) getApplicationContext()
        // .getSystemService(Context.ALARM_SERVICE);
        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar
        // .getInstance().getTimeInMillis() + (time * (3600) * 1000),
        // time * (3600) * 1000, pendingIntent);
    }

}
