package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class MockActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    private TextView txtViewQuestion, txtViewOption1, txtViewOption2, txtViewOption3, txtViewOption4;

    private int questionNumber = -1;
    private List<QuizTable> questionDatasArray;
    private ArrayList<String> answersArray;
    private QuizTable questionData;
    private LinearLayout layoutOption1, layoutOption2, layoutOption3, layoutOption4;
    private LinearLayout layoutButton;

    private int myCoins, count;

    private String[] quizItems;

    private String categoryType, questionsCount;
    private TextView txtViewQuestionNumber, txtTotalQns;

    private AdView adMobView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        categoryType = getIntent().getStringExtra("category");
        questionsCount = getIntent().getStringExtra("questions");

        Session.getSession(this);

        setStatusBarGradiant(this);
        getViews();
        initControl();

        setData();
        setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this,getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.INTERTITIAID));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        interstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                Intent intent = new Intent(MockActivity.this, MockReviewActivity.class);
                intent.putExtra("mockDatas", (Serializable) questionDatasArray);
                intent.putExtra("answerDatas", answersArray);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                interstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });


    }

    public void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewOption1 = (TextView) findViewById(R.id.txtViewOption1);
        txtViewOption2 = (TextView) findViewById(R.id.txtViewOption2);
        txtViewOption3 = (TextView) findViewById(R.id.txtViewOption3);
        txtViewOption4 = (TextView) findViewById(R.id.txtViewOption4);

        layoutOption1 = (LinearLayout) findViewById(R.id.layoutOption1);
        layoutOption2 = (LinearLayout) findViewById(R.id.layoutOption2);
        layoutOption3 = (LinearLayout) findViewById(R.id.layoutOption3);
        layoutOption4 = (LinearLayout) findViewById(R.id.layoutOption4);
        txtViewQuestionNumber = (TextView) findViewById(R.id.txtViewQuestionNumber);
        txtTotalQns = (TextView) findViewById(R.id.txtTotalQns);

        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);

        answersArray = new ArrayList<>();

    }

    public void initControl() {

        txtTotalQns.setText(questionsCount);

        txtViewOption1.setOnClickListener(this);
        txtViewOption2.setOnClickListener(this);
        txtViewOption3.setOnClickListener(this);
        txtViewOption4.setOnClickListener(this);

        layoutButton.setOnClickListener(this);

    }


    private void setData() {

        questionDatasArray = HomeActivity.INSTANCE.myDao().getMockData(categoryType, questionsCount);

        if(questionDatasArray.size() != 0) {
            setQuestionDatas();
        }
    }

    public void setQuestionDatas() {

        //increase question number .
        questionNumber++;

        if (questionsCount.equals(String.valueOf(questionNumber))) {
            Toast.makeText(MockActivity.this, "Test Ended", Toast.LENGTH_SHORT).show();

            setupReview();
            return;
        }

        questionData = questionDatasArray.get(questionNumber);

        //set Question number.
        txtViewQuestionNumber.setText("Q. " + String.valueOf(questionNumber + 1) + " /");

        //clearing all datas .
        resetAllDatas();

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(500);

        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(500);

        txtViewQuestion.startAnimation(out);
        txtViewOption1.startAnimation(out);
        txtViewOption2.startAnimation(out);
        txtViewOption3.startAnimation(out);
        txtViewOption4.startAnimation(out);
        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtViewQuestion.setText(questionData.getQuestion());
                txtViewQuestion.startAnimation(in);

                txtViewOption1.setText(questionData.getOption1());
                txtViewOption1.startAnimation(in);

                txtViewOption2.setText(questionData.getOption2());
                txtViewOption2.startAnimation(in);

                txtViewOption3.setText(questionData.getOption3());
                txtViewOption3.startAnimation(in);

                txtViewOption4.setText(questionData.getOption4());
                txtViewOption4.startAnimation(in);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        txtViewOption1.setText(questionData.getOption1());
        txtViewOption2.setText(questionData.getOption2());
        txtViewOption3.setText(questionData.getOption3());
        txtViewOption4.setText(questionData.getOption4());

    }

    public void resetAllDatas() {

        enableButtons();
        layoutButton.setVisibility(View.GONE);

        layoutOption1.setBackgroundResource(R.drawable.game_quiz_options);
        layoutOption2.setBackgroundResource(R.drawable.game_quiz_options);
        layoutOption3.setBackgroundResource(R.drawable.game_quiz_options);
        layoutOption4.setBackgroundResource(R.drawable.game_quiz_options);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.txtViewOption1:
                checkAnswer(1);
                break;
            case R.id.txtViewOption2:
                checkAnswer(2);
                break;
            case R.id.txtViewOption3:
                checkAnswer(3);
                break;
            case R.id.txtViewOption4:
                checkAnswer(4);
                break;
            case R.id.layoutButton:
                setQuestionDatas();
                break;
        }

    }

    private void checkAnswer(int answerNum) {

        setLOttie();
        //user entry datas..
        answersArray.add(String.valueOf(answerNum));

        //for display wrong answer .
        if (!questionData.getAnswer().equals(String.valueOf(answerNum))) {
            switch (answerNum) {
                case 1:
                    layoutOption1.setBackgroundResource(R.drawable.game_quiz_wrong);
                    break;
                case 2:
                    layoutOption2.setBackgroundResource(R.drawable.game_quiz_wrong);
                    break;
                case 3:
                    layoutOption3.setBackgroundResource(R.drawable.game_quiz_wrong);
                    break;
                case 4:
                    layoutOption4.setBackgroundResource(R.drawable.game_quiz_wrong);
                    break;
            }

        }

        //for display correct answer.
        switch (Integer.valueOf(questionData.getAnswer())) {
            case 1:
                layoutOption1.setBackgroundResource(R.drawable.game_quiz_correct);
                break;
            case 2:
                layoutOption2.setBackgroundResource(R.drawable.game_quiz_correct);
                break;
            case 3:
                layoutOption3.setBackgroundResource(R.drawable.game_quiz_correct);
                break;
            case 4:
                layoutOption4.setBackgroundResource(R.drawable.game_quiz_correct);
                break;
        }


        layoutButton.setVisibility(View.VISIBLE);
        diableButtons();

    }

    public void diableButtons() {
        txtViewOption1.setEnabled(false);
        txtViewOption2.setEnabled(false);
        txtViewOption3.setEnabled(false);
        txtViewOption4.setEnabled(false);
    }

    public void enableButtons() {
        txtViewOption1.setEnabled(true);
        txtViewOption2.setEnabled(true);
        txtViewOption3.setEnabled(true);
        txtViewOption4.setEnabled(true);
    }

    public void setLOttie() {
//        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
//        animationView.setAnimation(String.valueOf(R.raw.sky));
//
//        animationView.playAnimation();

    }


    private void setupReview() {

        if(interstitialAd.isLoaded())
        {
            interstitialAd.show();

        }
        else {

            Intent intent = new Intent(this, MockReviewActivity.class);
            intent.putExtra("mockDatas", (Serializable) questionDatasArray);
            intent.putExtra("answerDatas", answersArray);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }


}
