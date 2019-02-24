package com.teamfitness.fitapp.webservice;


import android.content.Context;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebserviceClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(final Context context) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(logging);

        OkHttpClient client = builder.build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(WebserviceUtils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
