package com.example.sda.retrofitapp.network;

import com.example.sda.retrofitapp.BuildConfig;
import com.example.sda.retrofitapp.utlis.PrefsManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sda on 23.05.17.
 */

public class ApiClient {

    private static final String BASE_URL = "http://cbm.aype.pl/CBM_API/api/";

    private ApiService service;

    public ApiClient(PrefsManager prefsManager) {
        createRetrofit(prefsManager);
    }

    public ApiService getService() {
        // Get service instance to invoke its methods
        return service;
    }

    private void createRetrofit(final PrefsManager prefsManager) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        // Add logging interceptor to see communication between app and server
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        // Add header field to all requests
        Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + prefsManager.getToken())
                        .build();
                return chain.proceed(request);
            }
        };

        // Add interceptors to OkHttpClient
        clientBuilder.addInterceptor(loggingInterceptor);
        clientBuilder.addInterceptor(tokenInterceptor);
        // Set timeouts
        clientBuilder.connectTimeout(1, TimeUnit.MINUTES);
        clientBuilder.writeTimeout(1, TimeUnit.MINUTES);
        clientBuilder.readTimeout(1, TimeUnit.MINUTES);

        // Create retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build();

        // Create service(interface) instance
        service = retrofit.create(ApiService.class);
    }
}
