package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

import java.util.ArrayList;

public class MockHomeActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageButton btnQLeft, btnQRight, btnCLeft, btnCRight;
    private TextView txtQuestionNo;
    private LinearLayout layoutButton;
    private TextView txtViewKerala,txtViewWorld,txtViewIndia,txtViewHistory,txtViewSports,
            txtViewGeography,txtViewMaths,txtViewMovies,txtViewScience,txtViewLiterature;

    private ArrayList<String> quizItems;
    private String[] stringsCategories;
    private int questionCount = 10, categoryCount = 0;
    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_home);

        Base.setStatusBarGradiant(this);

        getViews();
        initControl();
        setDatas();
    //    setUpAdmob();

    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void getViews() {

        stringsCategories = getResources().getStringArray(R.array.categories);

        txtViewKerala = (TextView) findViewById(R.id.txtViewKerala);
        txtViewWorld = (TextView) findViewById(R.id.txtViewWorld);
        txtViewIndia = (TextView) findViewById(R.id.txtViewIndia);
        txtViewHistory = (TextView) findViewById(R.id.txtViewHistory);
        txtViewSports = (TextView) findViewById(R.id.txtViewSports);

        txtViewGeography = (TextView) findViewById(R.id.txtViewGeography);
        txtViewMaths = (TextView) findViewById(R.id.txtViewMaths);
        txtViewMovies = (TextView) findViewById(R.id.txtViewMovies);
        txtViewScience = (TextView) findViewById(R.id.txtViewScience);
        txtViewLiterature = (TextView) findViewById(R.id.txtViewLiterature);

        txtQuestionNo = findViewById(R.id.txtQuestionNo);
        layoutButton = findViewById(R.id.layoutButton);
        quizItems = new ArrayList<String>();


    }

    private void initControl() {

        txtViewKerala.setTag(0);
        txtViewWorld.setTag(0);
        txtViewIndia.setTag(0);
        txtViewHistory.setTag(0);
        txtViewSports.setTag(0);

        txtViewGeography.setTag(0);
        txtViewMaths.setTag(0);
        txtViewMovies.setTag(0);
        txtViewScience.setTag(0);
        txtViewLiterature.setTag(0);


        txtViewKerala.setOnClickListener(this);
        txtViewWorld.setOnClickListener(this);
        txtViewIndia.setOnClickListener(this);
        txtViewHistory.setOnClickListener(this);
        txtViewSports.setOnClickListener(this);

        txtViewGeography.setOnClickListener(this);
        txtViewMaths.setOnClickListener(this);
        txtViewMovies.setOnClickListener(this);
        txtViewScience.setOnClickListener(this);
        txtViewLiterature.setOnClickListener(this);
        layoutButton.setOnClickListener(this);

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

        txtQuestionNo.setText(String.valueOf(questionCount));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

    private void setData(TextView txtView ,String type ) {

        if(txtView.getTag().equals(0)) {
            txtView.setBackgroundResource(R.drawable.game_quiz_item_click);
            txtView.setTag(1);
            quizItems.add(type);
        }
        else
        {
            txtView.setBackgroundResource(R.drawable.game_quiz_items);
            txtView.setTag(0);
            quizItems.remove(type);
        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id)
        {
            case R.id.txtViewKerala :
                setData(txtViewKerala,"10");
                break;
            case R.id.txtViewWorld :
                setData(txtViewWorld,"7");
                break;
            case R.id.txtViewIndia :
                setData(txtViewIndia,"3");
                break;
            case R.id.txtViewHistory :
                setData(txtViewHistory,"2");
                break;
            case R.id.txtViewSports :
                setData(txtViewSports,"6");
                break;
            case R.id.txtViewGeography :
                setData(txtViewGeography,"1");
                break;
            case R.id.txtViewMaths :
                setData(txtViewMaths,"11");
                break;
            case R.id.txtViewMovies :
                setData(txtViewMovies,"4");
                break;
            case R.id.txtViewScience :
                setData(txtViewScience,"5");
                break;
            case R.id.txtViewLiterature :
                setData(txtViewLiterature,"9");
                break;
            case R.id.layoutButton :
                startQuiz();
                break;
        }

    }

    private void startQuiz() {

        if(quizItems.size() == 0)
        {
            Toast.makeText(this,"Select favorites ..",Toast.LENGTH_SHORT).show();
        }
        else {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < quizItems.size(); i++) {
                sb.append(quizItems.get(i)).append(",");
            }

            Session.setSharedData("quizItems",sb.toString());
            startActivity(new Intent(this,MockActivity.class).putExtra("questions", txtQuestionNo.getText().toString()));

        }
    }

}
