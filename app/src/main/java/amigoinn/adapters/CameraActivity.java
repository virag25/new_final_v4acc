package amigoinn.adapters;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;


import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import amigoinn.common.NetworkConnectivity;
import amigoinn.db_model.LoginInfo;
import amigoinn.db_model.MarketTypeInfo;
import amigoinn.db_model.ModelDelegates;
import amigoinn.db_model.SalesmenInfo;
import amigoinn.db_model.UserInfo;
import amigoinn.example.v4sales.BaseActivity;
import amigoinn.example.v4sales.GPSTracker;
import amigoinn.example.v4sales.R;
import amigoinn.example.v4sales.RobotoTextView;
import amigoinn.modallist.SalesList;
import amigoinn.servicehelper.MultipartUtility;
import amigoinn.servicehelper.ServiceHelper;
import amigoinn.servicehelper.ServiceResponse;

/**
 * Created by maulik on 1/28/2016.
 */
public class CameraActivity extends BaseActivity implements View.OnClickListener {

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public RobotoTextView txtSubmit;
    EditText edtTitle, edtDescription;
    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Camera";

    private Uri fileUri; // file url to store image/video

    private ImageView imgPreview;
    private VideoView videoPreview;
    private Button btnCapturePicture, btnRecordVideo;
    String path;
    Spinner spnType;
    String selected_type_id;
    ArrayList<String> bnd_list_type = new ArrayList<>();
    String lat, lang, desccc;

    LocationManager lm;
    Location l;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    String Longitude, Latitude;
    GPSTracker gpsTracker;
    UniversalDelegate m_delegate;
    File mediaFile;

    public interface UniversalDelegate {
        public void CallDidSuccess(String lat, String lang);

        public void CallFailedWithError(String error);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_feedback_fragment);

        imgPreview = (ImageView) findViewById(R.id.imgImage);
        edtTitle = (EditText) findViewById(R.id.title);
        edtDescription = (EditText) findViewById(R.id.Description);
        txtSubmit = (RobotoTextView) findViewById(R.id.login);
        spnType = (Spinner) findViewById(R.id.spnType);

        txtSubmit.setOnClickListener(this);


        ArrayList<MarketTypeInfo> lsttype = MarketTypeInfo.getAllProduct();
        if (lsttype != null && lsttype.size() > 0) {
            selected_type_id = lsttype.get(0).Code;
            bnd_list_type = new ArrayList<>();
            for (int i = 0; i < lsttype.size(); i++) {
                MarketTypeInfo sale = lsttype.get(i);
                bnd_list_type.add(sale.Descr);
            }
        } else {
            {
                showProgress();
                SalesList.Instance().DoSalsApi(new ModelDelegates.ModelDelegate<SalesmenInfo>() {
                    @Override
                    public void ModelLoaded(ArrayList<SalesmenInfo> list) {
                        hideProgress();
                        ArrayList<MarketTypeInfo> lsttype = MarketTypeInfo.getAllProduct();
                        bnd_list_type = new ArrayList<String>();
                        if (lsttype != null && lsttype.size() > 0) {
                            selected_type_id = lsttype.get(0).Code;
                            for (int i = 0; i < lsttype.size(); i++) {
                                MarketTypeInfo sale = lsttype.get(i);
                                bnd_list_type.add(sale.Descr);
                            }
                        }

                        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, bnd_list_type);
                        spnType.setAdapter(adapter1);
                    }

                    @Override
                    public void ModelLoadFailedWithError(String error) {
                        hideProgress();
                    }
                });
            }
        }


//        videoPreview = (VideoView) findViewById(R.id.videoPreview);
//        btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);
//        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);

        /**
         * Capture image button click event
         * */
        // capture picture
        try {
            imgPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    captureImage();
                }
            });
//            txtSubmit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    edtDescription.setText("");
//                    edtTitle.setText("");
//
//                }
//            });

        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }


        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = bnd_list_type.get(position);
//                edtSpin.setText(str);
                MarketTypeInfo si = MarketTypeInfo.getMarketTypeInfoByItemName(str);
                if (si != null) {
                    selected_type_id = si.Code;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * Record video button click event
         */

    }

    public Uri getOutputMediaFileUri(int type) {

        return Uri.fromFile(getOutputMediaFile(type));
    }

    public File getOutputMediaFile(int type) {

        // External sdcard location

        ContextWrapper c = new ContextWrapper(this);
        File directory = c.getFilesDir();

        File mediaStorageDir = new File(directory,
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdir();
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
//                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imgPreview.setImageBitmap(imageBitmap);
//                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void previewCapturedImage() {
        try {
            // hide video preview
            videoPreview.setVisibility(View.GONE);

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public void SubmitData(String lata, String lang, String desc) {
        if (NetworkConnectivity.isConnected()) {
            loadDataToServer async = new loadDataToServer(String.valueOf(mediaFile), desc, lata, lang);
            async.execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please check your connection.", Toast.LENGTH_LONG).show();
            hideProgress();
        }

    }

    @Override
    public void onClick(View v) {
        if (v == txtSubmit) {
            String des = edtDescription.getText().toString();
            desccc = des;
            boolean flag = true;
            String msg = "";

            if (des != null && des.length() == 0) {
                flag = false;
                msg = "Please add description";
            } else if (mediaFile != null && mediaFile.length() == 0) {
//                flag = false;
//                msg = "Please add image";
            }

            if (flag) {
                showProgress();
                getLocation(new UniversalDelegate() {
                    @Override
                    public void CallDidSuccess(String lat, String lang) {
                        SubmitData(lat, lang, desccc);
                    }

                    @Override
                    public void CallFailedWithError(String error) {
                        SubmitData("", "", desccc);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }


        }

    }


    public void getLocation(UniversalDelegate delegate) {
        m_delegate = delegate;

        gpsTracker = new GPSTracker(getApplicationContext());
        Location location = null;
        try {
            lm = (LocationManager) getApplicationContext().getSystemService(
                    getApplicationContext().LOCATION_SERVICE);
            isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = lm
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled) {
                Latitude = String.valueOf(gpsTracker.latitude);
                Longitude = String.valueOf(gpsTracker.longitude);
                m_delegate.CallDidSuccess(Latitude, Longitude);
            } else {
//                showSettingsAlert(getActivity());
//                gpsTracker.showSettingsAlert(getActivity());
//                m_delegate.CallFailedWithError("try again");
            }

        } catch (Exception e) {
            m_delegate.CallFailedWithError("try again");
            e.printStackTrace();
        }
    }


    public class loadDataToServer extends AsyncTask<Void, Void, Void> {
        ServiceResponse res;
        ModelDelegates.UniversalDelegate delegate;
        int error;
        String filepath;
        List<String> response;
        int quot_order_id;
        String descc, latt, langg;

        public loadDataToServer(String path, String des, String lat, String lang) {
            filepath = path;
            descc = des;
            latt = lat;
            langg = lang;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String charset = "UTF-8";
            // String requestURL =
            // ServiceHelper.URL+ServiceHelper.ADD_QUOTE+"?api_key=e2c552b7e6f200825e94c78923129484dc9cac1b";
            ServiceHelper helper = new ServiceHelper(ServiceHelper.Feedback,
                    ServiceHelper.RequestMethod.POST);
            String requestURL = helper.getFinalUrl();

            try {
                MultipartUtility multipart = new MultipartUtility(requestURL,
                        charset);
                multipart.addHeaderField("User-Agent", "CodeJava");
                multipart.addHeaderField("Test-Header", "Header-Value");
                UserInfo u_info = UserInfo.getUser();
                String uid = u_info.user_code;
                String user_id = String.valueOf(uid);

                multipart.addFormField("userid", user_id);
                multipart.addFormField("type", selected_type_id);
                multipart.addFormField("Description", descc);
                multipart.addFormField("Longitude", latt);
                multipart.addFormField("Latitude", langg);
                if (filepath != null) {
                    File sourceFile = new File(filepath);
                    multipart.addFilePart("uploaded_file", sourceFile);
                    Log.i("FilePath==========>", filepath);
                    // MFoodxApplication.setQuotOrder_id(quot_order_id);
                    // MFoodxApplication.setQuotSending(quot_order_id);
                }
                response = multipart.finish();
                Log.i("mFood", response.toString());
                if (response != null) {
                    res = new ServiceResponse();
//                    JSONArray jobj = new JSONArray(response.toString());
//                    if (jobj != null) {
//
//                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            hideProgress();
            // Utils.StopGps(SendQuotationDialog.this);
            if (response != null) {
                if (res.isSuccess) {

                } else if (error == 21) {

                } else {
                }
            } else {
            }

        }

    }


}