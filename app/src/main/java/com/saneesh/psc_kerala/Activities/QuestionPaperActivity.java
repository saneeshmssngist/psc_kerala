package com.saneesh.psc_kerala.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Adapters.GeneralQuizAdapter;
import com.saneesh.psc_kerala.Adapters.QuestionPaperAdapter;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.GeneralModel;
import com.saneesh.psc_kerala.Model.QuestionTable1;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class QuestionPaperActivity extends AppCompatActivity {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    private RecyclerView recyclerViewQuestions;
    private int tokenNo = 1;

    private QuestionPaperAdapter generalQuizAdapter;
    private ImageView imgLeft,imgRight;
    private TextView txtViewTokenNo;
    private String paperNameNo,paperName = "";
    private Toolbar toolBar;

    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper);

        paperNameNo = getIntent().getStringExtra("paperNameNo");
        paperName = getIntent().getStringExtra("paperName");

        setStatusBarGradiant(this);
        getViews();
        setActionBar();
        initControl();
        setDatas();

      //  setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
//        MobileAds.initialize(this,getResources().getString(R.string.APPID));
//
//        adMobView = (AdView) findViewById(R.id.adMobView);
//        adMobView.loadAd(new AdRequest.Builder().build());

    }

    public void setActionBar() {

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Question Papers");

    }

    private void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        imgLeft = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        txtViewTokenNo = findViewById(R.id.txtViewNumber);

        recyclerViewQuestions = findViewById(R.id.recyclerViewQuestions);
        recyclerViewQuestions.setHasFixedSize(true);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initControl() {

        txtViewTokenNo.setText(String.valueOf(tokenNo));

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tokenNo > 1) {
                    tokenNo -= 1;
                    txtViewTokenNo.setText(String.valueOf(tokenNo));
                    setDatas();
                }
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tokenNo < 10) {
                    tokenNo += 1;
                    txtViewTokenNo.setText(String.valueOf(tokenNo));
                    setDatas();
                }
            }
        });


    }

    private void setDatas() {

        List<QuestionTable1> questionsModels = HomeActivity.INSTANCE.myDao().getQuestionPaper1((tokenNo-1)*10,paperNameNo);
        generalQuizAdapter = new QuestionPaperAdapter(this, questionsModels,tokenNo);
        recyclerViewQuestions.setAdapter(generalQuizAdapter);

    }

}
