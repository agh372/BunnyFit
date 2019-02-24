package com.teamfitness.fitapp.webservice;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebserviceUtils {

    //url
    public static final String BASE_URL = "https://fitapp-232623.appspot.com";

    //endpoints
    public static final String ENDPOINT_SIGN_UP = "/v1/user/register";

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
