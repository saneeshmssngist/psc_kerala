package com.saneesh.psc_kerala.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

public class BaseActivity extends AppCompatActivity {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    private Toolbar toolBar;
    private RelativeLayout layoutBack;
    private TextView txtHead;
    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void showProgress()
    {
        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        layoutProgress.setVisibility(View.VISIBLE);
        avilayoutProgress.show();
    }

    public void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    public void hideProgress()
    {
        avilayoutProgress.smoothToHide();
        layoutProgress.setVisibility(View.GONE);
    }

    public void setToolBar(String title)
    {
        toolBar = findViewById(R.id.toolBar);
        layoutBack = findViewById(R.id.layoutBack);
        txtHead = findViewById(R.id.txtHead);

        txtHead.setText(title);
    }

    public void setToolBarNoBack(String title)
    {
        toolBar = findViewById(R.id.toolBar);
        layoutBack = findViewById(R.id.layoutBack);
        txtHead = findViewById(R.id.txtHead);

        txtHead.setText(title);
        layoutBack.setVisibility(View.GONE);
    }

    public void onBackPressed(View view)
    {
        finish();
    }


}
