package com.saneesh.psc_kerala.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Adapters.QuestionHomeAdapter;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.QuestionPaperHome;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class QuestionPaperHomeActivity extends AppCompatActivity {


    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    QuestionHomeAdapter questionHomeAdapter;
    RecyclerView recyclerView;
    private Toolbar toolBar;

    private SharedPreferences pref;
    private AdView adMobView;
    private InterstitialAd interstitialAd;

    private ArrayList<QuestionPaperHome> questionPaperArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper_home);

        setStatusBarGradiant(this);
        getViews();
        setActionBar();

        initControl();
        setDatas();

//        setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.INTERTITIAID));
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }

    public void setActionBar() {

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Question Papers");

    }

    private void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void initControl() {

        questionHomeAdapter = new QuestionHomeAdapter(QuestionPaperHomeActivity.this, new ArrayList<QuestionPaperHome>(), new QuestionHomeAdapter.QuestionsInterface() {
            @Override
            public void questionTapped(int adapterPosition)
            {

                startActivity(new Intent(QuestionPaperHomeActivity.this,QuestionPaperActivity.class)
                .putExtra("questionData",questionPaperArray.get(adapterPosition)));

            }
        });
        recyclerView.setAdapter(questionHomeAdapter);

    }

    public void setDatas()
    {
        layoutProgress.setVisibility(View.VISIBLE);
        avilayoutProgress.show();

        DataManager.getDatamanager().getQuestionPaperHomeDatas(new RetrofitCallBack<ArrayList<QuestionPaperHome>>() {
            @Override
            public void Success(final ArrayList<QuestionPaperHome> questionPaperHomesArray) {

                layoutProgress.setVisibility(View.GONE);
                avilayoutProgress.hide();

                questionPaperArray = questionPaperHomesArray;
                questionHomeAdapter.update(questionPaperHomesArray);

            }
            @Override
            public void Failure(String error) {
                layoutProgress.setVisibility(View.GONE);
                avilayoutProgress.hide();
            }
        });

    }



}


