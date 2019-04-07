package com.saneesh.psc_kerala;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saneesh.psc_kerala.Interfaces.NetworkChangeCallBack;


public class NetworkConnection {


    private Button button;
    private Context context;

//To check whether that internet connection is available or not.

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public void buildDialog(View view, final NetworkChangeCallBack changeCallBack) {

        Snackbar snackbar = Snackbar.make(view, "Internet disconnected.", 60000)
                .setAction("GO ONLINE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeCallBack.onGoOnlineTapped();
                    }
                });

        snackbar.setActionTextColor(Color.parseColor("#FFD40700"));

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#FF129417"));
        snackbar.show();


    }


}