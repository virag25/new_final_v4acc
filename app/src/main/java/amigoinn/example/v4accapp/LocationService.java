package amigoinn.example.v4accapp;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.Toast;

import amigoinn.db_model.LoginInfo;
import amigoinn.db_model.ModelDelegates;


public class LocationService extends Service {
    LocationManager lm;
    Location l;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    String Longitude, Latitude;
    GPSTracker gpsTracker;
    LeftMenusActivity.UniversalDelegate m_delegate;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();

        getLocation(new LeftMenusActivity.UniversalDelegate() {

            @Override
            public void CallFailedWithError(String error) {
//                Toast.makeText(getApplicationContext(), error,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void CallDidSuccess(final String lat, final String lang) {
                LoginInfo.Instance().DoUpdatelocations(new ModelDelegates.LoginDelegate() {
                    @Override
                    public void LoginDidSuccess() {
//                        Toast.makeText(getApplicationContext(), lat + "," + lang,
//                                Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void LoginFailedWithError(String error) {
//                        Toast.makeText(getApplicationContext(), error,
//                                Toast.LENGTH_LONG).show();
                    }
                }, lang, lat, true, "", "");
//				Toast.makeText(getApplicationContext(),
//						" lat=" + lat + "lang " + lang, Toast.LENGTH_LONG)
//						.show();
            }
        });

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        stopSelf();
        super.onDestroy();
    }

    public void getLocation(LeftMenusActivity.UniversalDelegate delegate) {
        m_delegate = delegate;

        gpsTracker = new GPSTracker(getApplicationContext());
        Location location = null;
        try {
            lm = (LocationManager) getApplicationContext().getSystemService(
                    LOCATION_SERVICE);
            isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled) {
                Latitude = String.valueOf(gpsTracker.latitude);
                Longitude = String.valueOf(gpsTracker.longitude);
                m_delegate.CallDidSuccess(Latitude, Longitude);
            } else {
                gpsTracker.showSettingsAlert(getApplicationContext());
                m_delegate.CallFailedWithError("try again");

            }

        } catch (Exception e) {
            m_delegate.CallFailedWithError("try again");
            e.printStackTrace();
        }
    }
}
