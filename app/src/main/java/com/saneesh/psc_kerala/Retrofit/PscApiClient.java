package com.saneesh.psc_kerala.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by saNeesH on 2018-04-17.
 */

public class PscApiClient {

    public static final String MASTEERURL = "http://192.168.43.59/psc_kerala/";
    public static final String MASTEERURL_LIVE = "https://astalavistasan.000webhostapp.com/PSC_KERALA/";
    public static final String FIREBASE_URL = "https://firebasestorage.googleapis.com/v0/b/quizrr-dd785.appspot.com/o/";

    public static Retrofit retrofit = null;
    public static Retrofit getAPiClient(String base)
    {
        if(base.contentEquals("server"))
            return new Retrofit.Builder().baseUrl(MASTEERURL_LIVE).
                    addConverterFactory(GsonConverterFactory.create()).build();

        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(FIREBASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}


