package com.saneesh.psc_kerala;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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

    public void buildDialog(View view) {

        Snackbar snackbar = Snackbar.make(view, "Internet disconnected.", 1000);
//                .setAction("GO ONLINE", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        changeCallBack.onGoOnlineTapped();
//                    }
//                });

        snackbar.setActionTextColor(Color.parseColor("#FFD40700"));

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#FF129417"));
        snackbar.show();


    }


}