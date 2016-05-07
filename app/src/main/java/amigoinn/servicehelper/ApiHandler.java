package amigoinn.servicehelper;


import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Rahil on 11-08-2015.
 */
public class ApiHandler {

//    public static final String BASE_URL = "http://www.prismeduware.com/democlass/parent/v1";
    public static final String BASE_URL1 = "http://www.v4account.net/v4webservice/";
    public static final String BASE_URL = "http://www.v4account.net/v4webservice/";
    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(60);
    private static StyletriebAppService apiService;
    private static StyletriebAppService apiService1;


    public static StyletriebAppService getApiService()
    {
        if (apiService == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setWriteTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL)
                    .setClient(new OkClient(okHttpClient))
                    .setConverter(new GsonConverter(new Gson()))
                    .build();

            apiService = restAdapter.create(StyletriebAppService.class);

            return apiService;
        } else {
            return apiService;
        }
    }

    public static StyletriebAppService getApiService1() {
        if (apiService1 == null)
        {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setWriteTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(BASE_URL1)
                    .setClient(new OkClient(okHttpClient))
                    .setConverter(new GsonConverter(new Gson()))
                    .build();

            apiService1 = restAdapter.create(StyletriebAppService.class);

            return apiService1;
        }else {
            return apiService1;
        }
    }
}
