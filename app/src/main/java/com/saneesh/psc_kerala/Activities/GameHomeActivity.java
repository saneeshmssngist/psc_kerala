package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.QuestionTable1;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class GameHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    private TextView txtViewKerala,txtViewWorld,txtViewIndia,txtViewHistory,txtViewSports,
            txtViewGeography,txtViewMaths,txtViewMovies,txtViewScience,txtViewLiterature;

    private ArrayList<String> quizItems;
    private LinearLayout layoutButton;

    private ArrayList<QuestionsModel> questionDatasArray;
    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_home);

        Session.getSession(this);
        Base.setStatusBarGradiant(this);

        getViews();
        initControl();

        setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this,getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().addTestDevice("C0256988724EBA3D6A98B53747EE5900").build());

    }

    private void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        quizItems = new ArrayList<String>();
        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);

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

    @Override
    public void onClick(View v) {

        int id = v.getId();

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
            startActivity(new Intent(this,GameActivity.class));
        }
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


}
