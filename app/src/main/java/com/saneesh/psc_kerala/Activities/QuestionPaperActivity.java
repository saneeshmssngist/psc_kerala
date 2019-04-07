package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.saneesh.psc_kerala.Adapters.QuestionPaperAdapter;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.QuestionPaperHome;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class QuestionPaperActivity extends BaseActivity {

    private RecyclerView recyclerViewQuestions;
    private int tokenNo = 1;

    private QuestionPaperAdapter generalQuizAdapter;
    private ImageView imgLeft, imgRight;
    private TextView txtViewTokenNo;

    private AdView adMobView;
    private QuestionPaperHome questionPaperHome;
    private ArrayList<QuestionsModel> questionPaperDatasArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper);

        questionPaperHome = (QuestionPaperHome) getIntent().getSerializableExtra("questionData");

        setStatusBarGradiant(this);
        getViews();
        initControl();

        //  setUpAdmob();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDatas();
    }

    private void setUpAdmob() {

        //admob sync..
//        MobileAds.initialize(this,getResources().getString(R.string.APPID));
//
//        adMobView = (AdView) findViewById(R.id.adMobView);
//        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void getViews() {

        imgLeft = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        txtViewTokenNo = findViewById(R.id.txtViewNumber);

        recyclerViewQuestions = findViewById(R.id.recyclerViewQuestions);
        recyclerViewQuestions.setHasFixedSize(true);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));

        generalQuizAdapter = new QuestionPaperAdapter(this, new ArrayList<QuestionsModel>(), tokenNo);
        recyclerViewQuestions.setAdapter(generalQuizAdapter);

    }

    private void initControl() {

        txtViewTokenNo.setText(String.valueOf(tokenNo));

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tokenNo > 1) {
                    tokenNo -= 1;
                    txtViewTokenNo.setText(String.valueOf(tokenNo));
                    setQuestionDatas();
                }
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tokenNo < 10) {
                    tokenNo += 1;
                    txtViewTokenNo.setText(String.valueOf(tokenNo));
                    setQuestionDatas();
                }
            }
        });


    }

    private void setDatas() {

        setToolBar(questionPaperHome.getName());

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(QuestionPaperActivity.this)) {
            startActivity(new Intent(QuestionPaperActivity.this, NetworkStateActivity.class));

        } else {
            DataManager.getDatamanager().getQuestionPaper(questionPaperHome.getUrl(), new RetrofitCallBack<QuestionPaperHome>() {
                @Override
                public void Success(QuestionPaperHome questionPaperData) {
                    questionPaperDatasArray = questionPaperData.getQuestionsModelsArry();
                    setQuestionDatas();
                }

                @Override
                public void Failure(String error) {

                }
            });
        }

    }

    public void setQuestionDatas() {
        ArrayList<QuestionsModel> questionsModelsArray = new ArrayList<>();
        for (int i = ((tokenNo - 1) * 10); i < (tokenNo * 10) && i < questionPaperDatasArray.size(); i++) {
            questionsModelsArray.add(questionPaperDatasArray.get(i));
        }


        generalQuizAdapter = new QuestionPaperAdapter(QuestionPaperActivity.this, questionsModelsArray, tokenNo);
        recyclerViewQuestions.setAdapter(generalQuizAdapter);
    }

}
