package amigoinn.example.v4sales;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;


public class BaseFragment extends android.support.v4.app.Fragment
{
    ProgressDialog m_pd = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        m_pd = new ProgressDialog(getActivity());
        m_pd.setMessage(Html.fromHtml("Please wait..."));
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

}
