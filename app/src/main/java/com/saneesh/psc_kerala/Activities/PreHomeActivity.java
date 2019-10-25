package com.saneesh.psc_kerala.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.NetworkChangeCallBack;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;


public class PreHomeActivity extends BaseActivity {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    private EditText edtTxtName;
    private Button btnEnter;
    private LinearLayout layoutSkip;
    private boolean doubleBackToExitPressedOnce = false;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pre_home);

        Base.setStatusBarGradiant(this);

        getViews();
        initControls();

    }

    private void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        edtTxtName = findViewById(R.id.edtTxtName);
        btnEnter = findViewById(R.id.btnEnter);
        layoutSkip = findViewById(R.id.layoutSkip);

    }

    private void initControls() {

        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetworkConnection networkConnection = new NetworkConnection();
                if (!networkConnection.isConnected(PreHomeActivity.this)) {
                    networkConnection.buildDialog(findViewById(android.R.id.content));

                } else {
                    if (!TextUtils.isEmpty(edtTxtName.getText().toString())) {

                        layoutProgress.setVisibility(View.VISIBLE);
                        avilayoutProgress.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                SharedPreferences.Editor edt = pref.edit();
                                edt.putBoolean("login_executed", true);
                                edt.commit();

                                Session.setUserName(edtTxtName.getText().toString());
                                Intent intent = new Intent(PreHomeActivity.this, HomeActivity.class);
                                startActivity(intent);

                            }
                        }, 1000);

                    } else {
                        Toast.makeText(PreHomeActivity.this, "Please enter name or Skip", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        layoutSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NetworkConnection networkConnection = new NetworkConnection();
                if (!networkConnection.isConnected(PreHomeActivity.this)) {
                    networkConnection.buildDialog(findViewById(android.R.id.content));

                } else {
                    SharedPreferences.Editor edt = pref.edit();
                    edt.putBoolean("login_executed", true);
                    edt.commit();

                    Intent intent = new Intent(PreHomeActivity.this, HomeActivity.class);
                    startActivity(intent);

                }
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
