package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.R;

public class MockHomeActivity extends AppCompatActivity {


    private ImageButton btnQLeft, btnQRight, btnCLeft, btnCRight;
    private TextView txtCategory, txtQuestionNo;
    private LinearLayout layoutButton;

    private String[] stringsCategories;
    private int questionCount = 10, categoryCount = 0;
    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_home);

        Base.setStatusBarGradiant(this);

        getViews();
        setDatas();
        setUpAdmob();

    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void getViews() {

        stringsCategories = getResources().getStringArray(R.array.categories);

        txtCategory = findViewById(R.id.txtCategory);
        txtQuestionNo = findViewById(R.id.txtQuestionNo);
        layoutButton = findViewById(R.id.layoutButton);

        layoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MockHomeActivity.this, MockActivity.class);

                if (categoryCount >= 7) {
                    intent.putExtra("category", String.valueOf(categoryCount + 2));

                } else {
                    intent.putExtra("category", String.valueOf(categoryCount + 1));
                }
                intent.putExtra("questions", txtQuestionNo.getText().toString());

                startActivity(intent);
            }
        });

    }

    public void onCategoryLeftClick(View view) {
        if (categoryCount > 0) {
            categoryCount -= 1;
            setDatas();
        }
    }

    public void onCategoryRightClick(View view) {
        if (categoryCount < 9) {
            categoryCount += 1;
            setDatas();
        }
    }

    public void onQuestionsLeftClick(View view) {
        if (questionCount > 10) {
            questionCount -= 10;
            setDatas();
        }
    }

    public void onQuestionsRightClick(View view) {
        if (questionCount < 30) {
            questionCount += 10;
            setDatas();
        }
    }

    private void setDatas() {

        txtCategory.setText(stringsCategories[categoryCount]);
        txtQuestionNo.setText(String.valueOf(questionCount));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}
