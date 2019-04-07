package com.saneesh.psc_kerala;

import android.content.res.Resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by saNeesH on 2018-06-30.
 */

public class Utils {


    public static String getTimeDifference(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date fromdate = dateFormat.parse(date);


            long different = fromdate.getTime() - System.currentTimeMillis();

            if(different < 0)
                return "";
            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            return elapsedHours+"-"+elapsedMinutes+"-"+elapsedSeconds;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

}
