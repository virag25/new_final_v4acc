package amigoinn.example.v4sales;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class BaseActivity extends Activity {
    ProgressDialog m_pd = null;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_pd = new ProgressDialog(BaseActivity.this);
        m_pd.setMessage(Html.fromHtml("Please wait..."));
        m_pd.setCancelable(false);


        settings = PreferenceManager.getDefaultSharedPreferences(this);
        editor = settings.edit();
    }

    public void showProgress() {
        if (m_pd != null) {
            m_pd.show();
        }
    }

    public void hideProgress() {
        if (m_pd != null) {
            m_pd.dismiss();
        }
    }

    public void setMyTitle(String str) {
        setTitle(Html.fromHtml("<font color='#FF771C'>" + str
                + "</font>"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}