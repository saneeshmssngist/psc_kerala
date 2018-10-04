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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;


public class PreHomeActivity extends Activity {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    private EditText edtTxtName,edtTxtPhone;
    private Button btnEnter;
    private LinearLayout layoutSkip;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_home);

        getViews();
        initControls();

    }

    private void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtPhone = findViewById(R.id.edtTxtPhone);
        btnEnter = findViewById(R.id.btnEnter);
        layoutSkip = findViewById(R.id.layoutSkip);

    }

    private void initControls() {

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(edtTxtName.getText().toString()) && !TextUtils.isEmpty(edtTxtPhone.getText().toString()))
                {

                    layoutProgress.setVisibility(View.VISIBLE);
                    avilayoutProgress.show();

                    DataManager.getDatamanager().loginUser(getParams(), new RetrofitCallBack<String>() {
                        @Override
                        public void Success(String status) {

                            avilayoutProgress.smoothToHide();
                            layoutProgress.setVisibility(View.GONE);

                            SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edt = pref.edit();
                            edt.putBoolean("activity_executed", true);
                            edt.commit();

                            Intent intent = new Intent(PreHomeActivity.this,HomeActivity.class);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            startActivity(intent);
                        }

                        @Override
                        public void Failure(String error) {

                            avilayoutProgress.smoothToHide();
                            layoutProgress.setVisibility(View.GONE);

                            Toast.makeText(PreHomeActivity.this,"Something error happened !!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(PreHomeActivity.this,"Please enter fields or Skip",Toast.LENGTH_SHORT).show();
                }

            }
        });

        layoutSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutProgress.setVisibility(View.VISIBLE);
                avilayoutProgress.show();

                DataManager.getDatamanager().loginUser(getSkipParams(), new RetrofitCallBack<String>() {
                    @Override
                    public void Success(String status) {

                        avilayoutProgress.smoothToHide();
                        layoutProgress.setVisibility(View.GONE);

                        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edt = pref.edit();
                        edt.putBoolean("activity_executed", true);
                        edt.commit();

                        Intent intent = new Intent(PreHomeActivity.this,HomeActivity.class);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                    }

                    @Override
                    public void Failure(String error) {
                        avilayoutProgress.smoothToHide();
                        layoutProgress.setVisibility(View.GONE);

                        Toast.makeText(PreHomeActivity.this,"Network connection needed !!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private HashMap<String,String> getSkipParams()
    {
        HashMap<String,String> hashMap = new HashMap<>();

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        hashMap.put("login_type","nodata");
        hashMap.put("unique_id",androidId);

        return hashMap;
    }

    private HashMap<String,String> getParams() {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user_name",edtTxtName.getText().toString());
        hashMap.put("phone_number",edtTxtPhone.getText().toString());

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        hashMap.put("unique_id",androidId);
        hashMap.put("login_type","withdata");

        return hashMap;
    }

    @Override
    public void onBackPressed() {

        if(doubleBackToExitPressedOnce) {
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
        },2000);

    }
}
