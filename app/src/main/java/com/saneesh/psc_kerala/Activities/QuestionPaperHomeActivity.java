package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Adapters.QuestionHomeAdapter;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.QuestionPaperHome;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class QuestionPaperHomeActivity extends BaseActivity {


    QuestionHomeAdapter questionHomeAdapter;
    RecyclerView recyclerView;

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
        setToolBar("Question Papers");

        initControl();
        setUpAdmobs();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setDatas();
    }

    private void setUpAdmobs() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.INTERTITIAID));
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }


    private void getViews() {

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void initControl() {

        questionHomeAdapter = new QuestionHomeAdapter(QuestionPaperHomeActivity.this, new ArrayList<QuestionPaperHome>(), new QuestionHomeAdapter.QuestionsInterface() {
            @Override
            public void questionTapped(final int adapterPosition) {

                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        startActivity(new Intent(QuestionPaperHomeActivity.this, QuestionPaperActivity.class)
                                .putExtra("questionData", questionPaperArray.get(adapterPosition)));

                        interstitialAd.loadAd(new AdRequest.Builder().build());
                    }
                });

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {

                    startActivity(new Intent(QuestionPaperHomeActivity.this, QuestionPaperActivity.class)
                            .putExtra("questionData", questionPaperArray.get(adapterPosition)));
                }

            }
        });
        recyclerView.setAdapter(questionHomeAdapter);

    }

    public void setDatas() {

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(QuestionPaperHomeActivity.this)) {
            startActivity(new Intent(QuestionPaperHomeActivity.this, NetworkStateActivity.class));

        } else {

            showProgress();
            DataManager.getDatamanager().getQuestionPaperHomeDatas(new RetrofitCallBack<ArrayList<QuestionPaperHome>>() {
                @Override
                public void Success(final ArrayList<QuestionPaperHome> questionPaperHomesArray) {

                    hideProgress();

                    questionPaperArray = questionPaperHomesArray;
                    questionHomeAdapter.update(questionPaperHomesArray);

                }

                @Override
                public void Failure(String error) {
                    hideProgress();
                }
            });
        }

    }


}


