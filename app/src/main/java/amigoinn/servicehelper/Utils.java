package amigoinn.servicehelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

import amigoinn.example.v4sales.R;


/**
 * Created by Manthan on 10/10/2015.
 */

public class Utils
{

    public static final String CURRICULUM = "curriculam";
    private static ProgressDialog progressDialog;
   public static String twitter_id="b565edc882e84a85b4eddaafaa2b22b3";
    public static String user_id="2263767616";
    public static String subject_id="";
    public static String subject_name="";
    public static String chapter_id="";
    public static String chapter_name="";
    public static String video_id="";
    public static String Total_Marks="";
    public static String Exam_Time="";


//    CLIENT SECRET	adbd82813604415cb52168bd0a1412db
//    WEBSITE URL	http://www.amigoinnovations.co.in
//    REDIRECT URI	http://www.amigoinnovations.co.in
//    SUPPORT EMAIL	teamamigoinnovations@gmail.com


    public static boolean checkNetwork(Context context) {
        boolean wifiAvailable = false;
        boolean mobileAvailable = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    wifiAvailable = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    mobileAvailable = true;
        }
        return wifiAvailable || mobileAvailable;
    }
    public static void log(String message) {
        Log.i(CURRICULUM, message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static int dpToPx(Context context,int dp)
    {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, r.getDisplayMetrics());
        return (int)px;
    }

    public static void showDialog(Context context) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void showDialog1(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Downloading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
    public static void ShowCustomProgress(Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait for a moment");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
            //   dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            //dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.dialog));
            Drawable customDrawable= context.getResources().getDrawable(R.drawable.custom_dialog);

            // set the drawable as progress drawable

        progressDialog.setIndeterminateDrawable(customDrawable);
        progressDialog.show();

    }
//    public static void showCustomDialog(String title, String str, Activity activity) {
//        // custom dialog
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
//// ...Irrelevant code for customizing the buttons and title
//        LayoutInflater inflater = activity.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.custom_simple_dialog_message_layout, null);
//        dialogBuilder.setView(dialogView);
//
//        TextView txt_message_dialog = (TextView) dialogView.findViewById(R.id.txt_message_dialog);
//        txt_message_dialog.setText(str);
//
//        TextView txt_title_dialog = (TextView) dialogView.findViewById(R.id.txt_title_dialog);
//        txt_title_dialog.setText(title);
//
//        Button btn_ok = (Button) dialogView.findViewById(R.id.btn_ok);
//
//
//        final AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//    }
}
