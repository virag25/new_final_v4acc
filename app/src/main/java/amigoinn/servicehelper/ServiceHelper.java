package amigoinn.servicehelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import amigoinn.common.CommonUtils;

public class ServiceHelper {

    // private static String TAG = "QClub.ServiceHelper";
    private static String TAG = "";
    public static final String COMMON_ERROR = "Could not connect to server, please try again later";
    public static final String NETWORK_ERROR = "Please check your connection";

    public static final String LOGIN = "login.php";
    public static final String CLIENT = "clientlist.php";
    public static final String CLIENT_ORDERS = "clientorders.php";
    public static final String CLIENT_ORDERS_Details = "orderdetails.php";
    public static final String Order_DISPATCH = "clientdispatch.php";

    public static final String ADD_ORDER = "insertorder.php";

    public static final String Order_DISPATCH_DETAILS = "dispatchdetails.php";

    public static final String Product_list = "productlist.php";
    public static final String route_list = "productlist.php";
    public static final String gen_list = "getgenlookup.php";
    public static final String class12combo_list = "class12combo.php";
    public static final String UPDATE_PASSWORD = "updatepassword.php";
    public static final String INSERT_LOCATIONS = "insertlocation.php";
    public static final String CLIENT_SALE = "clientsales.php";

    String my_quesryString;

    private static boolean IS_DEBUG = true;

//    http://www.v4account.net/v4webservice/login.php

    public enum RequestMethod {
        GET, POST
    }

    public interface ServiceHelperDelegate {
        /**
         * Calls when got the response from the API
         *
         * @param res Service Response Obejct
         */
        public void CallFinish(ServiceResponse res);

        /**
         * Service call fail with error message
         *
         * @param ErrorMessage Error Message
         */
        public void CallFailure(String ErrorMessage);
    }

    // String m_methodName = null;
    private ServiceHelperDelegate m_delegate = null;

    public static String URL = "http://www.v4account.net/v4webservice/";

    // http://www.mfoodx.com/mfoodxapi/api/GetAllCompany?api_key=e2c552b7e6f200825e94c78923129484dc9cac1b

    // http://192.168.1.114/mfoodx/mfoodxapi/api/
    // http://scteches.com/mfoodx/mfoodxapi/api/

    // For AWS URL

    // http://54.183.77.227/mfoodxapi/api/

    // for test

    // http://test.mfoodx.com/mfoodxapi/api/

    // public static final String URL
    // ="http://192.168.1.114/marqet/marqetapi/api/";
    private ArrayList<String> m_params = new ArrayList<String>();
    private HashMap<String, String> m_hashParams = new HashMap<String, String>();
    private static int REQUEST_TIMEOUT = 90000;
    public RequestMethod RequestMethodType = RequestMethod.GET;
    String m_methodName = null;
    int REQUEST_TAG = 0;

    public ServiceHelper(String method) {
        m_methodName = method;
    }

    /**
     * When using more than one call from one class and same delegate is used.
     * So the call response is identify by TAG
     */
    public ServiceHelper(String method, int tag) {
        m_methodName = method;
        REQUEST_TAG = tag;
        RequestMethodType = RequestMethod.GET;
    }

    public ServiceHelper(String method, RequestMethod requestMethod) {
        m_methodName = method;
        RequestMethodType = requestMethod;
    }

    public ServiceHelper(String method, RequestMethod requestMethod, String data) {
        m_methodName = method;
        RequestMethodType = requestMethod;
        my_quesryString = data;
    }

    public void setTAG(String tag) {
        TAG = tag;
    }

    // public void addParam(String key, Object value) {
    // m_hashParams.put(key, String.valueOf(value));
    // }

    public void addParam(String key, Object value) {
        m_params.add(key + "=" + value);
    }

    // public void addParam(String key, int value) {
    // m_params.add(key + "=" + String.valueOf(value));
    // }

    public void setParams(ArrayList<String> params) {
        m_params = new ArrayList<String>(params);
    }

    public String getFinalUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(URL);
        sb.append(m_methodName.toString());
        // ArrayList<String> m_params = new ArrayList<String>();
        // for (String key : m_hashParams.keySet()) {
        // m_params.add(key + "=" + m_hashParams.get(key));
        // }
//        sb.append("?api_key=b49f380d5106a222c5c51751d159fd87ae8f1fd1");

        if (RequestMethodType == RequestMethod.GET) {
            if (m_params.size() > 0) {
                String queryString = CommonUtils.Instance().join(m_params, "&");
                sb.append("&");
                sb.append(queryString);
            }
        }
        return sb.toString();
    }

    // private ServiceHelperDelegate m_delegate = null;

    public void call(ServiceHelperDelegate delegate) {
        m_delegate = delegate;
        // if (NetworkConnectivity.isConnected()) {
        CallServiceAsync calling = new CallServiceAsync(true);
        calling.execute();

    }

    private String call() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = getNewHttpClient();
        HttpRequestBase request = null;
        if (RequestMethodType == RequestMethod.GET) {
            request = new HttpGet(getFinalUrl());
            request.setHeader("Accept", "*/*");
            request.setHeader("Content-Type", "text/plain; charset=utf-8");
        } else {
            request = new HttpPost(getFinalUrl());
            StringEntity se;
            // request.setHeader("Accept", "application/json");
            if (m_methodName.equalsIgnoreCase(ADD_ORDER)) {
                request.setHeader("Content-Type",
                        "application/json");
                if (my_quesryString != null) {
                    CommonUtils.LogInfo("REGISTER---->>" + my_quesryString);
                    try {
                        se = new StringEntity(my_quesryString);
                        ((HttpPost) request).setEntity(se);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }

            } else {
                request.setHeader("Content-Type",
                        "application/x-www-form-urlencoded");
                if (m_params.size() > 0) {
                    // JSONObject json = JsonCreator.getJsonObject(m_params);
                    String queryString = CommonUtils.Instance().join(m_params, "&");
                    CommonUtils.LogInfo("REGISTER---->>" + queryString);

                    try {
                        se = new StringEntity(queryString);
                        ((HttpPost) request).setEntity(se);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
        try {
            request.getParams().setParameter("http.socket.timeout",
                    REQUEST_TIMEOUT);
            CommonUtils.LogInfo("**URL : " + getFinalUrl());
            request.setHeader("Cache-Control", "no-cache");

            HttpResponse response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                builder.append(statusCode);
                CommonUtils.LogInfo("Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            builder.append("{\"result\":{\"code\":1011,\"error\":\"Unknow errro occuerd, please try again later.\"}}");
        }
        return builder.toString();
    }

    public void callUrl(String url, ServiceHelperDelegate delegate) {
        m_delegate = delegate;
        CallServiceAsync calling = new CallServiceAsync(url);
        calling.execute();
    }

    private DefaultHttpClient getHttpClient() {
        final HttpParams httpParams = new BasicHttpParams();

        // SchemeRegistry schemeRegistry = new SchemeRegistry();
        // schemeRegistry.register(new Scheme("http", SSLSocketFactory
        // .getSocketFactory(), 80));
        //
        // SingleClientConnManager mgr = new SingleClientConnManager(httpParams,
        // schemeRegistry);
        // HttpProtocolParams.setUseExpectContinue(httpParams, false);
        // HttpConnectionParams.setConnectionTimeout(httpParams,
        // REQUEST_TIMEOUT);
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                REQUEST_TIMEOUT);
        DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
        httpclient.setHttpRequestRetryHandler(new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= 2) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    return true;
                } else if (exception instanceof ClientProtocolException) {
                    return true;
                }
                return false;
            }
        });
        return httpclient;
    }

    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    private String call(String url) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = getNewHttpClient();
        HttpRequestBase request = null;

        request = new HttpPost(url);
        try {
            request.setHeader("Cache-Control", "no-cache");

            HttpResponse response = client.execute(request);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                builder.append(statusCode);
                CommonUtils.LogInfo("Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    class CallServiceAsync extends AsyncTask<Void, Void, ServiceResponse> {

        boolean m_withFullHeader = false;
        String m_url = null;

        public CallServiceAsync(boolean withFullHeader) {
            m_withFullHeader = withFullHeader;
        }

        public CallServiceAsync(String url) {
            m_url = url;
        }

        @Override
        protected ServiceResponse doInBackground(Void... params) {
            String strResponse = "";
            if (m_url == null) {
                strResponse = call();
            } else {
                strResponse = call(m_url);
            }
            if (strResponse.equalsIgnoreCase("500")) {
                return null;
            }
            ServiceResponse response = new ServiceResponse();
//            JSONObject data;
            try {
//                data = new JSONObject(strResponse);
//                if (data != null) {
                if (m_methodName.equalsIgnoreCase(ServiceHelper.LOGIN)) {
//                        response.isSuccess = data.getBoolean("success");
//                        response.Message = data.getString("message");
//                        response.RawResponse = strResponse;
                    response.RawResponse = strResponse;
                } else {
                    //response.isSuccess = data.getBoolean("success");
                    //response.Message = data.getString("message");
                    response.RawResponse = strResponse;
                }

//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            CommonUtils.LogInfo("RAW RESPONSE :: " + strResponse);
            return response;
        }

        @Override
        protected void onPostExecute(ServiceResponse result)
        {
            if (result != null)
            {
                if (m_delegate != null)
                {
                    m_delegate.CallFinish(result);
                }
            }
            else
            {
                if (m_delegate != null)
                {
                    m_delegate.CallFailure(ServiceHelper.COMMON_ERROR);
                }
            }
            super.onPostExecute(result);
        }

    }
}

// ........................................................................................//

// String url = getFinalUrl();
// int m = (RequestMethodType == RequestMethod.GET) ? Method.GET
// : Method.POST;
// final int tag = REQUEST_TAG;
//
// addLog("URL : " + url);
//
//
// StringRequest request = new StringRequest(m, url,
// new Response.Listener<String>() {
//
// @Override
// public void onResponse(String response) {
// Utils.LogInfo(response.toString());
//
// ServiceResponse res = new ServiceResponse();
// JSONObject data;
// try {
// data = new JSONObject(response);
// if (data != null) {
// res.isSuccess = data.getBoolean("Success");
// res.Message = data.getString("Message");
// res.Tag = tag;
// res.RawResponse = data.getString("Data");
// addLog("RAW_RESPONSE : " + res.RawResponse);
// }
// } catch (JSONException e) {
// e.printStackTrace();
// }
// if (delegate != null) {
// delegate.CallFinish(res);
// }
// }
// }, new Response.ErrorListener() {
//
// @Override
// public void onErrorResponse(VolleyError paramVolleyError) {
//
// if (paramVolleyError != null
// && paramVolleyError.getMessage() != null) {
// addLog(paramVolleyError.getMessage());
// if (delegate != null) {
// delegate.CallFailure(paramVolleyError
// .getMessage());
// }
// } else {
// addLog("Server Error");
// if (delegate != null) {
// delegate.CallFailure("Service Error");
// }
// }
//
// }
// }) {
//
// @Override
// public Map<String, String> getHeaders() throws AuthFailureError {
// HashMap<String, String> hrds = new HashMap<String, String>();
// hrds.put("Content-Type",
// "application/x-www-form-urlencoded");
// // if (headers.size() > 0) {
// // hrds.putAll(headers);
// // }
// return hrds;
// }
//
// @Override
// protected Map<String, String> getParams()
// throws AuthFailureError {
// HashMap<String, String> prms = new HashMap<String, String>();
// if (m_hashParams.size() > 0) {
// for (String key : m_hashParams.keySet()) {
// prms.put(key, String.valueOf(m_hashParams.get(key)));
// }
// }
// Utils.LogInfo("Params ==== " + prms.toString());
// return prms;
// }
//
// @Override
// public byte[] getBody() throws AuthFailureError {
//
// return super.getBody();
// }
//
// @Override
// public Request<?> setCacheEntry(Entry entry) {
//
// return super.setCacheEntry(entry);
// }
//
// @Override
// protected VolleyError parseNetworkError(VolleyError volleyError) {
//
// return super.parseNetworkError(volleyError);
// }
// };
// request.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIMEOUT, 1,
// DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// QClubApplication.getRequestQueue().add(request);
//
// // } else {
// // Toast.makeText(QClubApplication.getContext(),
// // "Please check your internet connection", Toast.LENGTH_LONG)
// // .show();
// //
// // }
// }
//
// private void addLog(String log) {
// if (IS_DEBUG) {
// Log.d(TAG, log);
// }
// }

