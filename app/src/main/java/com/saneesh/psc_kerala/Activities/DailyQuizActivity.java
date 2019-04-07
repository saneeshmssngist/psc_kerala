package com.saneesh.psc_kerala.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

import java.util.ArrayList;

public class DailyQuizActivity extends BaseActivity implements View.OnClickListener {


    private TextView txtViewQuestion, txtViewOption1, txtViewOption2, txtViewOption3, txtViewOption4;
    private TextView txtViewQuestionNumber, txtTotalQns;
    private ArrayList<QuestionsModel> questionsdatsArray;
    private LinearLayout layoutButton;
    private QuestionsModel questionData;
    private int questionNumber = -1;
    private int countCorrectAnswer = 0,countWrongAnswer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);

        initViews();
        initControls();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setDatas(getIntent().getStringExtra("quizUrl"));

    }

    private void initViews()
    {
        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewOption1 = (TextView) findViewById(R.id.txtViewOption1);
        txtViewOption2 = (TextView) findViewById(R.id.txtViewOption2);
        txtViewOption3 = (TextView) findViewById(R.id.txtViewOption3);
        txtViewOption4 = (TextView) findViewById(R.id.txtViewOption4);

        txtViewQuestionNumber = (TextView) findViewById(R.id.txtViewQuestionNumber);
        txtTotalQns = (TextView) findViewById(R.id.txtTotalQns);

        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);


    }

    private void initControls() {

        txtViewOption1.setOnClickListener(this);
        txtViewOption2.setOnClickListener(this);
        txtViewOption3.setOnClickListener(this);
        txtViewOption4.setOnClickListener(this);

        layoutButton.setOnClickListener(this);

    }

    private void setDatas(String quizUrl) {

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(DailyQuizActivity.this)) {
            startActivity(new Intent(DailyQuizActivity.this, NetworkStateActivity.class));

        } else {
            DataManager.getDatamanager().fetchDailyQuizDatas(quizUrl, new RetrofitCallBack<ArrayList<QuestionsModel>>() {
                @Override
                public void Success(ArrayList<QuestionsModel> questionsModelsArray) {

                    txtTotalQns.setText(String.valueOf(questionsModelsArray.size()));
                    questionsdatsArray = questionsModelsArray;
                    setQuizDatas();
                }

                @Override
                public void Failure(String error) {

                }
            });

        }
    }

    private void setQuizDatas() {

        questionNumber++;
        if(questionNumber < 10) {

            txtViewQuestionNumber.setText("Q. "+String.valueOf(questionNumber+1)+" /"+questionsdatsArray.size());
            questionData = questionsdatsArray.get(questionNumber);

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
        else
        {
            showProgressCongrats();
        }

    }

    public void resetAllDatas() {

        enableButtons();
        layoutButton.setVisibility(View.GONE);

        txtViewOption1.setBackgroundResource(R.drawable.game_quiz_options);
        txtViewOption2.setBackgroundResource(R.drawable.game_quiz_options);
        txtViewOption3.setBackgroundResource(R.drawable.game_quiz_options);
        txtViewOption4.setBackgroundResource(R.drawable.game_quiz_options);

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

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.txtViewOption1:
                txtViewOption1.setBackgroundResource(R.drawable.game_quiz_click);
                checkAnswer(1);
                break;
            case R.id.txtViewOption2:
                txtViewOption2.setBackgroundResource(R.drawable.game_quiz_click);
                checkAnswer(2);
                break;
            case R.id.txtViewOption3:
                txtViewOption3.setBackgroundResource(R.drawable.game_quiz_click);
                checkAnswer(3);
                break;
            case R.id.txtViewOption4:
                txtViewOption4.setBackgroundResource(R.drawable.game_quiz_click);
                checkAnswer(4);
                break;
            case R.id.layoutButton:
                setQuizDatas();
                break;
        }

    }

    private void checkAnswer(final int answerNum) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                //for display wrong answer .
                if (!questionData.getAnswer().equals(String.valueOf(answerNum))) {
                    switch (answerNum) {
                        case 1:
                            txtViewOption1.setBackgroundResource(R.drawable.game_quiz_wrong);
                            break;
                        case 2:
                            txtViewOption2.setBackgroundResource(R.drawable.game_quiz_wrong);
                            break;
                        case 3:
                            txtViewOption3.setBackgroundResource(R.drawable.game_quiz_wrong);
                            break;
                        case 4:
                            txtViewOption4.setBackgroundResource(R.drawable.game_quiz_wrong);
                            break;
                    }

                }

                //for display correct answer.
                switch (Integer.valueOf(questionData.getAnswer())) {
                    case 1:
                        txtViewOption1.setBackgroundResource(R.drawable.game_quiz_correct);
                        break;
                    case 2:
                        txtViewOption2.setBackgroundResource(R.drawable.game_quiz_correct);
                        break;
                    case 3:
                        txtViewOption3.setBackgroundResource(R.drawable.game_quiz_correct);
                        break;
                    case 4:
                        txtViewOption4.setBackgroundResource(R.drawable.game_quiz_correct);
                        break;
                }

                if(questionData.getAnswer().equals(String.valueOf(answerNum)))
                    countCorrectAnswer += 1;
                else
                    countWrongAnswer += 1;


                layoutButton.setVisibility(View.VISIBLE);

            }
        }, 1000);

        diableButtons();

    }

    public void showProgressCongrats() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View view = LayoutInflater.from(this).inflate(R.layout.daily_quiz_congrates, null);
        builder.setView(view);

        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);

        CardView cardContinue = (CardView) view.findViewById(R.id.cardContinue);
        TextView txtCorrect = (TextView) view.findViewById(R.id.txtCorrect);
        TextView txtWrong = (TextView) view.findViewById(R.id.txtWrong);

        txtCorrect.setText(String.valueOf(countCorrectAnswer));
        txtWrong.setText(String.valueOf(countWrongAnswer));

        cardContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                setInterstitialAd();
            }
        });

    }

    private void setInterstitialAd() {

//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                super.onAdClosed();
//                setData();
//
//                interstitialAd.loadAd(new AdRequest.Builder().build());
//            }
//        });
//
//        if (interstitialAd.isLoaded()) {
//            interstitialAd.show();
//        } else {
//
            finish();
//        }

    }





}
