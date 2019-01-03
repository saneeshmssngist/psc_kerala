package com.saneesh.psc_kerala.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

import java.util.ArrayList;
import java.util.List;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtViewQuestion, txtViewOption1, txtViewOption2, txtViewOption3, txtViewOption4;

    private int questionNumber = -1;
    private ArrayList<QuestionsModel> questionDatasArray;
    private QuizTable questionData;
    private LinearLayout layoutOption1, layoutOption2, layoutOption3, layoutOption4;
    private LinearLayout layoutButton;

    private TextView txtViewCoins, txtViewQuestionNumber;

    private Handler handler1, handler2, handlerTimer;

    private int myCoins, count;
    private MediaPlayer mediaCoinDrop, mediaCountDown, mediaError;

    private CircleProgress circleProgress;
    private TextView textViewTime, txtViewTimeout;
    private int timerCount;

    private String[] quizItems;
    private int quizItemsCount = 0;
    private List<String> dupIds;

    private ImageView imgViewVolumeOn, imgViewVolumeOff;
    private LinearLayout layoutButtonLeft;
    private SharedPreferences pref;

    private AdView adMobView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_quiz);

        handler1 = new Handler();
        handler2 = new Handler();
        handlerTimer = new Handler();

        Session.getSession(this);
        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

        setStatusBarGradiant(this);
        getViews();
        initControl();
        setData();

        setVolume();
        setUpAdmob();
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

    public void getViews() {

        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtViewOption1 = (TextView) findViewById(R.id.txtViewOption1);
        txtViewOption2 = (TextView) findViewById(R.id.txtViewOption2);
        txtViewOption3 = (TextView) findViewById(R.id.txtViewOption3);
        txtViewOption4 = (TextView) findViewById(R.id.txtViewOption4);

        layoutOption1 = (LinearLayout) findViewById(R.id.layoutOption1);
        layoutOption2 = (LinearLayout) findViewById(R.id.layoutOption2);
        layoutOption3 = (LinearLayout) findViewById(R.id.layoutOption3);
        layoutOption4 = (LinearLayout) findViewById(R.id.layoutOption4);

        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);
        dupIds = new ArrayList<>();

        txtViewCoins = (TextView) findViewById(R.id.txtViewCoins);
        txtViewQuestionNumber = (TextView) findViewById(R.id.txtViewQuestionNumber);

        circleProgress = (CircleProgress) findViewById(R.id.circleProgress);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        txtViewTimeout = (TextView) findViewById(R.id.txtViewTimeout);

        imgViewVolumeOn = (ImageView) findViewById(R.id.imgViewVolumeOn);
        imgViewVolumeOff = (ImageView) findViewById(R.id.imgViewVolumeOff);
        layoutButtonLeft = (LinearLayout) findViewById(R.id.layoutButtonLeft);

    }

    private void setVolume() {

        if (pref.getBoolean("SoundSystem", true)) {

            imgViewVolumeOn.setVisibility(View.VISIBLE);
            imgViewVolumeOff.setVisibility(View.GONE);

            mediaCoinDrop.setVolume(1, 1);
            mediaCountDown.setVolume(1, 1);
            mediaError.setVolume(1, 1);

        } else {
            imgViewVolumeOn.setVisibility(View.GONE);
            imgViewVolumeOff.setVisibility(View.VISIBLE);

            mediaCoinDrop.setVolume(0, 0);
            mediaCountDown.setVolume(0, 0);
            mediaError.setVolume(0, 0);
        }

    }

    public void initControl() {

        mediaCoinDrop = MediaPlayer.create(this, R.raw.coin_drop);
        mediaCountDown = MediaPlayer.create(this, R.raw.sound2);
        mediaError = MediaPlayer.create(this, R.raw.sound1);

        txtViewOption1.setOnClickListener(this);
        txtViewOption2.setOnClickListener(this);
        txtViewOption3.setOnClickListener(this);
        txtViewOption4.setOnClickListener(this);

        layoutButton.setOnClickListener(this);
        layoutButtonLeft.setOnClickListener(this);

    }

    public void setData() {

        txtViewCoins.setText(Session.getSharedData("myCoins"));
        setQuestionDatas();

    }

    private void setInterstitialAd() {

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                setData();

                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {

            setData();
        }

    }

    public void setQuestionDatas() {

        //increase question number .
        questionNumber++;

        questionData = HomeActivity.INSTANCE.myDao().getQuizQuestion();
        //check duplicate data
        checkDupdatas(questionData.getId());

        //set Question number.
        txtViewQuestionNumber.setText("Q. " + String.valueOf(questionNumber + 1));

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

        timerCount = 30;
        mediaCountDown.start();
        mediaCountDown.setLooping(true);
        circleProgress.setFinishedColor(Color.parseColor("#3084ea"));
        handlerTimer.post(runnableTimer);

    }

    private void checkDupdatas(int id) {

        for (int i = 0; i < dupIds.size(); i++) {
            if (dupIds.get(i).equals(String.valueOf(id)))
                setQuestionDatas();
        }
    }


    Runnable runnableTimer = new Runnable() {
        @Override
        public void run() {

            timerCount--;
            textViewTime.setText(String.valueOf(timerCount));

            int countSet = Integer.valueOf((int) (timerCount * 3.3333));
            circleProgress.setProgress(countSet);

            if (timerCount == 5) {
//                mediaCountDown.set
                circleProgress.setFinishedColor(Color.parseColor("#d91a1d"));
            }

            if (timerCount == 0) {
                handlerTimer.removeCallbacks(runnableTimer);
                timerComplete();
                return;
            }

            handlerTimer.postDelayed(runnableTimer, 1000);
        }
    };

    private void timerComplete() {

        mediaCountDown.pause();
        mediaError.start();
        txtViewTimeout.setVisibility(View.VISIBLE);

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


    public void resetAllDatas() {

        enableButtons();
        layoutButton.setVisibility(View.GONE);
        txtViewTimeout.setVisibility(View.GONE);

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

                if ((questionNumber + 1) % 10 == 0) {
                    setInterstitialAd();
                } else {
                setData();
                }
                break;
            case R.id.layoutButtonLeft:

                SharedPreferences.Editor edt = pref.edit();

                if (pref.getBoolean("SoundSystem", true)) {
                    edt.putBoolean("SoundSystem", false);
                    edt.commit();
                    setVolume();
                } else {
                    edt.putBoolean("SoundSystem", true);
                    edt.commit();
                    setVolume();
                }

                break;
        }

    }

    private void checkAnswer(int answerNum) {

        handlerTimer.removeCallbacks(runnableTimer);
        mediaCountDown.pause();

        //setting the coins ..
        setCoinsCount(answerNum);
        setLOttie();

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

    public void setCoinsCount(int answerNum) {
        myCoins = Integer.parseInt(Session.getSharedData("myCoins"));
        count = 0;

        if (questionData.getAnswer().equals(String.valueOf(answerNum))) {

            mediaCoinDrop.start();

            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    count += 1;

                    if (count > 10) {
                        mediaCoinDrop.pause();
                        Session.setSharedData("myCoins", String.valueOf(myCoins));
                        return;
                    }

                    myCoins += 1;
                    txtViewCoins.setText(String.valueOf(myCoins));
                    handler1.postDelayed(this, 100);

                }
            }, 100);
        } else {
            mediaError.start();

            if (myCoins > 2) {

                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        count += 1;

                        if (count > 3) {
                            Session.setSharedData("myCoins", String.valueOf(myCoins));
                            return;
                        }

                        myCoins -= 1;
                        txtViewCoins.setText(String.valueOf(myCoins));
                        handler2.postDelayed(this, 100);

                    }
                }, 100);

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        handlerTimer.removeCallbacks(runnableTimer);

        mediaCoinDrop.stop();
        mediaCountDown.stop();
        mediaError.stop();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mediaCoinDrop.setVolume(1, 1);
        mediaCountDown.setVolume(1, 1);
        mediaError.setVolume(1, 1);

    }

    @Override
    protected void onStop() {
        super.onStop();

        mediaCoinDrop.setVolume(0, 0);
        mediaCountDown.setVolume(0, 0);
        mediaError.setVolume(0, 0);
    }

    public void setLOttie() {
//        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
//        animationView.setAnimation(String.valueOf(R.raw.sky));
//
//        animationView.playAnimation();

    }


}
