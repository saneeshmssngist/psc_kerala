package com.saneesh.psc_kerala.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


    }


    public void showProgress()
    {

    }

    public void hideProgress()
    {

    }

}
