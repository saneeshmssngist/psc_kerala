package com.saneesh.psc_kerala.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by saNeesH on 2018-04-17.
 */

public class PscApiClient {

    public static final String MASTEERURL = "http://192.168.43.59/psc_kerala/";
    public static final String MASTEERURL_LIVE = "http://www.comedyentertainer.esy.es/PSC_KERALA/";

    public static Retrofit retrofit = null;
    public static Retrofit getAPiClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(MASTEERURL_LIVE).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}


