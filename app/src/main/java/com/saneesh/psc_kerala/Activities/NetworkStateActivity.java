package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;


public class NetworkStateActivity extends AppCompatActivity {


    private TextView txtRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_state);

        txtRetry = (TextView) findViewById(R.id.txtRetry);

        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetworkConnection networkConnection = new NetworkConnection();
                if(networkConnection.isConnected(NetworkStateActivity.this))
                {
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this,HomeActivity.class));
    }
}
