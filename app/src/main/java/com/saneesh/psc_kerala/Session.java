package com.saneesh.psc_kerala;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

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

    public static void setCorrectAns(int value) {
        if (value == 0)
            editor.putInt("correct", 0).apply();
        else
            editor.putInt("correct", getCorrectAns() + value).apply();
    }

    public static int getCorrectAns() {
        return sharedPreferences.getInt("correct", 0);
    }

    public static void setSkippedAns(int value) {
        if (value == 0)
            editor.putInt("skipped", 0).apply();
        else
            editor.putInt("skipped", getSkippedAns() + value).apply();
    }

    public static int getSkippedAns() {
        return sharedPreferences.getInt("skipped", 0);
    }

    public static void setWrongAns(int value) {
        if (value == 0)
            editor.putInt("wrong", 0).apply();
        else
            editor.putInt("wrong", getWrongAns() + value).apply();
    }

    public static int getWrongAns() {
        return sharedPreferences.getInt("wrong", 0);
    }

    public static void setTotQuestions(int value) {
        editor.putInt("total", value).apply();
    }

    public static int getTotQuestions() {
        return sharedPreferences.getInt("total", 0);
    }

    public static void setUserName(String value) {
        editor.putString("name", value).apply();
    }

    public static String getUserName() {
        return sharedPreferences.getString("name", "");
    }

    public static void setChallengeName(String value) {
        editor.putString("cname", value).apply();
    }

    public static String getChallengeName() {
        return sharedPreferences.getString("cname", "");
    }

    public static void setGender(String value) {
        editor.putString("gender", value).apply();
    }

    public static String getGender() {
        return sharedPreferences.getString("gender", "");
    }

    public static void setDailyQuizData(String dailyQuizData) {
        editor.putString("daily_quiz", dailyQuizData).apply();
    }

    public static String getDailyQuizData() {
        return sharedPreferences.getString("daily_quiz","[]");
    }


}
