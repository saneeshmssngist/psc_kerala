package com.saneesh.psc_kerala;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-06-03.
 */

public class Session {

    private static final String PREFERENCE = "PREFERENCE";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void getSession(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static void setSharedData(String key, String data) {
        editor.putString(key, data).apply();
    }

    public static String getSharedData(String key) {
        return sharedPreferences.getString(key, "0");
    }

    public static void setSharedBoolean(String key, boolean data) {
        editor.putBoolean(key, data).apply();
    }

    public static boolean getSharedBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

}
