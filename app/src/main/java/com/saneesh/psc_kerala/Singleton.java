package com.saneesh.psc_kerala;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by saNeesH on 17/10/26.
 */

public class Singleton {


    private static Singleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    public Singleton(Context ctx)
    {
        context = ctx;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if (requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Singleton getInstance(Context context)
    {
        if(singleton == null)
        {
            singleton = new Singleton(context);
        }
        return singleton;
    }

    public<T> void addToRequestque(Request<T> request)
    {
        requestQueue.add(request);
    }


}




