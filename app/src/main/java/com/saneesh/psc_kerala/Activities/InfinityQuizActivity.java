package com.saneesh.psc_kerala.Activities;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.fabric.sdk.android.Fabric;

import static android.media.AudioManager.RINGER_MODE_SILENT;
import static android.media.AudioManager.RINGER_MODE_VIBRATE;

public class InfinityQuizActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtViewQuestion, txtViewOption1, txtViewOption2, txtViewOption3, txtViewOption4;

    private int questionNumber = 0;
    private QuizTable questionData;
    private LinearLayout layoutOption1, layoutOption2, layoutOption3, layoutOption4;
    private LinearLayout layoutButton;

    private TextView txtViewCoins, txtViewQuestionNumber;

    private Handler handler1, handler2, handlerTimer;

    private int myCoins, count;
//    private MediaPlayer mediaCoinDrop, mediaCountDown, mediaError;

    private MediaPlayer nextSlide, buttonClick, wrongAnswer, correctAnswer, menubutton, optionclick;
    private TextView txtViewTimeout;
    private int timerCount;

    private String[] quizItems;
    private int quizItemsCount = 0;
    private List<String> dupIds;

    private ImageView imgSound;
    private LinearLayout layoutButtonLeft;
    private SharedPreferences pref;
    private RelativeLayout layoutQuizrr;
    private Dialog dialog;
    private TextView txtNum1, txtNum2, txtNum3, txtNum4;
    private ImageView imgViewOptions;
    private RelativeLayout fiftyOption, layoutTop;

    private AdView adMobView;
    private InterstitialAd interstitialAd;
    private boolean revealEnabled = false;
    private boolean clickEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_infinity_quiz);

        handler1 = new Handler();
        handler2 = new Handler();
        handlerTimer = new Handler();
        
        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);


//        setStatusBarGradiant(this);
        getViews();
        initControl();
        diableButtons();
        setSoundSystem();

        setInitialDatas();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setData();
            }
        }, 3000);

//        setVolume();
      //  setUpAdmob();
    }

    private void setInitialDatas() {

        final Animation zoom = AnimationUtils.loadAnimation(InfinityQuizActivity.this, R.anim.zoom_in);
        final Animation slideDown = AnimationUtils.loadAnimation(InfinityQuizActivity.this, R.anim.slide_down);
        final Animation slideUp = AnimationUtils.loadAnimation(InfinityQuizActivity.this, R.anim.slide_up);
        layoutOption1.startAnimation(zoom);
        layoutOption2.startAnimation(zoom);
        layoutOption3.startAnimation(zoom);
        layoutOption4.startAnimation(zoom);
        layoutTop.startAnimation(slideDown);
        imgViewOptions.startAnimation(slideUp);

    }

    private void setSoundSystem() {

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getRingerMode() == RINGER_MODE_SILENT || audioManager.getRingerMode() == RINGER_MODE_VIBRATE) {
            SharedPreferences.Editor edt = pref.edit();
            edt.putBoolean("SoundSystem", false);
            edt.commit();
            setVolume();
        } else {
            SharedPreferences.Editor edt = pref.edit();
            edt.putBoolean("SoundSystem", true);
            edt.commit();
            setVolume();
        }

    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

//        adMobView = (AdView) findViewById(R.id.adMobView);
//        adMobView.loadAd(new AdRequest.Builder().build());

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

        layoutTop = (RelativeLayout) findViewById(R.id.layoutTop);

        txtNum1 = (TextView) findViewById(R.id.txtNum1);
        txtNum2 = (TextView) findViewById(R.id.txtNum2);
        txtNum3 = (TextView) findViewById(R.id.txtNum3);
        txtNum4 = (TextView) findViewById(R.id.txtNum4);
        imgViewOptions = (ImageView) findViewById(R.id.imgView);

        txtViewQuestion.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtNum1.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtNum2.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtNum3.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtNum4.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtViewOption1.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtViewOption2.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtViewOption3.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtViewOption4.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));

        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);
        dupIds = new ArrayList<>();

        txtViewCoins = (TextView) findViewById(R.id.txtViewCoins);
        txtViewQuestionNumber = (TextView) findViewById(R.id.txtViewQuestionNumber);
        layoutQuizrr = (RelativeLayout) findViewById(R.id.activity_sample);

//        circleProgress = (CircleProgress) findViewById(R.id.circleProgress);
//        textViewTime = (TextView) findViewById(R.id.textViewTime);
        txtViewTimeout = (TextView) findViewById(R.id.txtViewTimeout);

        layoutButtonLeft = (LinearLayout) findViewById(R.id.layoutButtonLeft);

    }

    private void setVolume() {

        if (pref.getBoolean("SoundSystem", true)) {

            nextSlide.setVolume(1, 1);
            buttonClick.setVolume(1, 1);
            wrongAnswer.setVolume(1, 1);
            correctAnswer.setVolume(1, 1);
            menubutton.setVolume(1, 1);
            optionclick.setVolume(1, 1);
//            mediaCountDown.setVolume(1, 1);
//            mediaError.setVolume(1, 1);

        } else {

            nextSlide.setVolume(0, 0);
            buttonClick.setVolume(0, 0);
            wrongAnswer.setVolume(0, 0);
            correctAnswer.setVolume(0, 0);
            menubutton.setVolume(0, 0);
            optionclick.setVolume(0, 0);
//            mediaCountDown.setVolume(0, 0);
//            mediaError.setVolume(0, 0);
        }

    }

    public void initControl() {

        nextSlide = MediaPlayer.create(this, R.raw.next_slide);
        buttonClick = MediaPlayer.create(this, R.raw.button_click);
        wrongAnswer = MediaPlayer.create(this, R.raw.wrongsound);
        correctAnswer = MediaPlayer.create(this, R.raw.correctsound);
        menubutton = MediaPlayer.create(this, R.raw.menubutton);
        optionclick = MediaPlayer.create(this, R.raw.optionclick);
//        mediaCountDown = MediaPlayer.create(this, R.raw.sound2);
//        mediaError = MediaPlayer.create(this, R.raw.sound1);

        txtViewOption1.setOnClickListener(this);
        txtViewOption2.setOnClickListener(this);
        txtViewOption3.setOnClickListener(this);
        txtViewOption4.setOnClickListener(this);

        layoutButton.setOnClickListener(this);
        layoutButtonLeft.setOnClickListener(this);
        imgViewOptions.setOnClickListener(this);

        questionNumber = Session.getTotQuestions();

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

        Session.setTotQuestions(questionNumber);

        questionData =  HomeActivity.INSTANCE.myDao().getQuizQuestion();
        //check duplicate data
        checkDupdatas(questionData.getId());

        //set Question number.
        txtViewQuestionNumber.setText("#" + String.valueOf(questionNumber));

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
//        mediaCountDown.start();
//        mediaCountDown.setLooping(true);
        // circleProgress.setFinishedColor(Color.parseColor("#3084ea"));
//        handlerTimer.post(runnableTimer);

    }

    private void setBackground() {

        switch (new Random().nextInt(4) + 1) {
            case 1:
                layoutQuizrr.setBackgroundResource(R.drawable.quizrr_bg_1);
                break;
            case 2:
                layoutQuizrr.setBackgroundResource(R.drawable.quizrr_bg_2);
                break;
            case 3:
                layoutQuizrr.setBackgroundResource(R.drawable.quizrr_bg_3);
                break;
            case 4:
                layoutQuizrr.setBackgroundResource(R.drawable.quizrr_bg_4);
                break;
        }

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
//            textViewTime.setText(String.valueOf(timerCount));

            int countSet = Integer.valueOf((int) (timerCount * 3.3333));
            // circleProgress.setProgress(countSet);

            if (timerCount == 5) {
//                mediaCountDown.set
                //     circleProgress.setFinishedColor(Color.parseColor("#d91a1d"));
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

//        mediaCountDown.pause();
//        mediaError.start();
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

        layoutOption1.setVisibility(View.VISIBLE);
        layoutOption2.setVisibility(View.VISIBLE);
        layoutOption3.setVisibility(View.VISIBLE);
        layoutOption4.setVisibility(View.VISIBLE);

        layoutOption1.setBackgroundResource(R.drawable.game_quiz_optionss);
        layoutOption2.setBackgroundResource(R.drawable.game_quiz_optionss);
        layoutOption3.setBackgroundResource(R.drawable.game_quiz_optionss);
        layoutOption4.setBackgroundResource(R.drawable.game_quiz_optionss);

        txtNum1.setBackgroundResource(R.drawable.game_quiz_num);
        txtNum2.setBackgroundResource(R.drawable.game_quiz_num);
        txtNum3.setBackgroundResource(R.drawable.game_quiz_num);
        txtNum4.setBackgroundResource(R.drawable.game_quiz_num);

    }


    long TIME = 1 * 1000;

    @Override
    public void onClick(final View v) {

        if (clickEnabled)
            return;

        clickEnabled = true;

        int id = v.getId();

        switch (id) {
            case R.id.txtViewOption1:
                buttonClick.start();
                layoutOption1.setBackgroundResource(R.drawable.game_quiz_selected);
                txtNum1.setBackgroundResource(R.drawable.game_quiz_num_selected);
                checkAnswer(1);
                break;
            case R.id.txtViewOption2:
                buttonClick.start();
                layoutOption2.setBackgroundResource(R.drawable.game_quiz_selected);
                txtNum2.setBackgroundResource(R.drawable.game_quiz_num_selected);
                checkAnswer(2);
                break;
            case R.id.txtViewOption3:
                buttonClick.start();
                layoutOption3.setBackgroundResource(R.drawable.game_quiz_selected);
                txtNum3.setBackgroundResource(R.drawable.game_quiz_num_selected);
                checkAnswer(3);
                break;
            case R.id.txtViewOption4:
                buttonClick.start();
                layoutOption4.setBackgroundResource(R.drawable.game_quiz_selected);
                txtNum4.setBackgroundResource(R.drawable.game_quiz_num_selected);
                checkAnswer(4);
                break;
            case R.id.imgView:
                menubutton.start();
                revealShowHome(0);
                break;
            case R.id.layoutButton:

//                if ((questionNumber + 1) % 10 == 0) {
//                    setInterstitialAd();
//                } else {
                //reveal animation .

//                }
                break;
//            case R.id.layoutButtonLeft:
//
//                SharedPreferences.Editor edt = pref.edit();
//
//                if (pref.getBoolean("SoundSystem", true)) {
//                    edt.putBoolean("SoundSystem", false);
//                    edt.commit();
//                    setVolume();
//                } else {
//                    edt.putBoolean("SoundSystem", true);
//                    edt.commit();
//                    setVolume();
//                }
//
//                break;
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                clickEnabled = false;
            }
        }, TIME);


    }

    private void checkAnswer(final int answerNum) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                handlerTimer.removeCallbacks(runnableTimer);
//        mediaCountDown.pause();

                //setting the coins ..
                setCoinsCount(answerNum);
//        setLOttie();

                //for display wrong answer .
                if (!questionData.getAnswer().equals(String.valueOf(answerNum))) {
                    wrongAnswer.start();
                    Session.setWrongAns(1);
                    switch (answerNum) {
                        case 1:
                            layoutOption1.setBackgroundResource(R.drawable.game_quiz_wrongii);
                            txtNum1.setBackgroundResource(R.drawable.game_quiz_num_wrong);
                            break;
                        case 2:
                            layoutOption2.setBackgroundResource(R.drawable.game_quiz_wrongii);
                            txtNum2.setBackgroundResource(R.drawable.game_quiz_num_wrong);
                            break;
                        case 3:
                            layoutOption3.setBackgroundResource(R.drawable.game_quiz_wrongii);
                            txtNum3.setBackgroundResource(R.drawable.game_quiz_num_wrong);
                            break;
                        case 4:
                            layoutOption4.setBackgroundResource(R.drawable.game_quiz_wrongii);
                            txtNum4.setBackgroundResource(R.drawable.game_quiz_num_wrong);
                            break;
                    }

                }

                //for display wrong answer .
                if (questionData.getAnswer().equals(String.valueOf(answerNum))) {
                    correctAnswer.start();
                    Session.setCorrectAns(1);
                }

                setCorrectAnswer();

            }
        }, 1000);

    }


    public void setCorrectAnswer() {
        //for display correct answer.
        switch (Integer.valueOf(questionData.getAnswer())) {
            case 1:
                layoutOption1.setBackgroundResource(R.drawable.game_quiz_correctii);
                txtNum1.setBackgroundResource(R.drawable.game_quiz_num_right);
                break;
            case 2:
                layoutOption2.setBackgroundResource(R.drawable.game_quiz_correctii);
                txtNum2.setBackgroundResource(R.drawable.game_quiz_num_right);
                break;
            case 3:
                layoutOption3.setBackgroundResource(R.drawable.game_quiz_correctii);
                txtNum3.setBackgroundResource(R.drawable.game_quiz_num_right);
                break;
            case 4:
                layoutOption4.setBackgroundResource(R.drawable.game_quiz_correctii);
                txtNum4.setBackgroundResource(R.drawable.game_quiz_num_right);
                break;
        }


//        layoutButton.setVisibility(View.VISIBLE);
        diableButtons();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                revealShowHome(1);
                nextSlide.start();

            }
        }, 1500);
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


            Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
            txtViewCoins.startAnimation(zoomin);

//            mediaCoinDrop.start();

            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    count += 1;

                    if (count > 10) {
//                        mediaCoinDrop.pause();
                        Session.setSharedData("myCoins", String.valueOf(myCoins));
                        Animation zoomout = AnimationUtils.loadAnimation(InfinityQuizActivity.this, R.anim.zoomout);
                        txtViewCoins.startAnimation(zoomout);
                        return;
                    }

                    myCoins += 1;
                    txtViewCoins.setText(String.valueOf(myCoins));
                    handler1.postDelayed(this, 70);

                }
            }, 100);
        } else {
//            mediaError.start();

            if (myCoins >= 5) {

                Animation zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
                txtViewCoins.startAnimation(zoomin);

                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        count += 1;
                        if (count > 5) {
                            Session.setSharedData("myCoins", String.valueOf(myCoins));
                            Animation zoomout = AnimationUtils.loadAnimation(InfinityQuizActivity.this, R.anim.zoomout);
                            txtViewCoins.startAnimation(zoomout);
                            return;
                        }

                        myCoins -= 1;
                        txtViewCoins.setText(String.valueOf(myCoins));
                        handler2.postDelayed(this, 70);

                    }
                }, 100);

            }
        }
    }

    @Override
    public void onBackPressed() {
        handlerTimer.removeCallbacks(runnableTimer);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        mediaCoinDrop.setVolume(1, 1);
//        mediaCountDown.setVolume(1, 1);
//        mediaError.setVolume(1, 1);

    }

    @Override
    protected void onStop() {
        super.onStop();

//        mediaCoinDrop.setVolume(0, 0);
//        mediaCountDown.setVolume(0, 0);
//        mediaError.setVolume(0, 0);
    }

    public void setLOttie() {
//        LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.animation_view);
//        animationView.setAnimation(String.valueOf(R.raw.sky));
//
//        animationView.playAnimation();

    }


    private View dView;

    private void revealShowHome(final int id) {

        if (dialog != null)
            dialog.dismiss();

        if (id == 1) {
            dView = LayoutInflater.from(InfinityQuizActivity.this).inflate(R.layout.game_reveal_anim, null);
        } else {
            dView = LayoutInflater.from(InfinityQuizActivity.this).inflate(R.layout.game_options_layout, null);
        }
        dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    revealShow(dView, id);
                }
            });

            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == KeyEvent.KEYCODE_BACK) {

                        revealShow(dView, id);
                        return true;
                    }

                    return false;
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialog.show();
    }

    private View viewss;

    private void revealShow(final View dialogView, final int id) {

        if (revealEnabled)
            return;

        revealEnabled = true;

        int w, h;

        if (id == 1) {
            viewss = dialogView.findViewById(R.id.reveallayout);
            w = 0;
            h = viewss.getHeight();
        } else {
            viewss = dialogView.findViewById(R.id.layoutMain);
            imgSound = dialogView.findViewById(R.id.imgSound);
            w = viewss.getWidth() / 2;
            h = viewss.getHeight();

            if (pref.getBoolean("SoundSystem", true))
                imgSound.setImageResource(R.drawable.ic_volume_up);
            else
                imgSound.setImageResource(R.drawable.ic_volume_off);

        }


        int endRadius = (int) Math.hypot(viewss.getWidth(), h);

        Animator revealAnimator = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            revealAnimator = ViewAnimationUtils.createCircularReveal(viewss, w, h, 0, endRadius);
        }

        viewss.setVisibility(View.VISIBLE);
        if (id == 1)
            revealAnimator.setDuration(1200);
        else
            revealAnimator.setDuration(500);

        revealAnimator.start();

        if (id == 1)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    setBackground();

                }
            }, 500);

        revealAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                revealEnabled = false;

                if (id == 1) {
                    if (dialog != null)
                        dialog.dismiss();

                    if (questionNumber % 10 == 0)
                        showProgressCongrats();
                    else
                        setData();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    public void fiftyOptionClick(View view) {

        optionclick.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int answer = Integer.valueOf(questionData.getAnswer());

                int first = answer - 1;
                int second = answer + 1;

                if (first == 0)
                    hideOption(3);
                else
                    hideOption(first);

                if (second == 5)
                    hideOption(2);
                else
                    hideOption(second);
            }
        }, 1000);


    }

    private void hideOption(int first) {

        if (dialog != null)
            dialog.dismiss();

        switch (first) {
            case 1:
                layoutOption1.setVisibility(View.INVISIBLE);
                break;
            case 2:
                layoutOption2.setVisibility(View.INVISIBLE);
                break;
            case 3:
                layoutOption3.setVisibility(View.INVISIBLE);
                break;
            case 4:
                layoutOption4.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void viewOptionClick(View view) {

        optionclick.start();
        diableButtons();
        Session.setSkippedAns(1);
        if (dialog != null)
            dialog.dismiss();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setCorrectAnswer();
            }
        }, 1000);
    }


    public void soundOptionClick(View view) {
        if (dialog != null)
            dialog.dismiss();

        SharedPreferences.Editor edt = pref.edit();

        if (pref.getBoolean("SoundSystem", true)) {
            edt.putBoolean("SoundSystem", false);
            edt.commit();
            setVolume();
            imgSound.setImageResource(R.drawable.ic_volume_off);
        } else {

            optionclick.start();
            edt.putBoolean("SoundSystem", true);
            edt.commit();
            setVolume();
            imgSound.setImageResource(R.drawable.ic_volume_up);
        }

    }

    public void shareOptionClick(View view) {

        optionclick.start();
        if (dialog != null)
            dialog.dismiss();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Infinity Quiz");

        String message = "Let me recommend you this application for psc preparation"
                + "https://play.google.com/store/apps/details?id=com.astalavista.saneesh.quizrrgame";

        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Choose one"));

    }

    public void optionsLayoutClick(View view) {

        menubutton.start();
        if (dialog != null)
            dialog.dismiss();
    }

    public void reloadOptionClick(View view) {
        if (dialog != null)
            dialog.dismiss();

        optionclick.start();
        Session.setSkippedAns(1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setData();
            }
        }, 1000);
    }

    public void profileOptionClick(View view) {
        handlerTimer.removeCallbacks(runnableTimer);
        startActivity(new Intent(this, ScoreBoardActivity.class));
    }

    public void showProgressCongrats() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View view = LayoutInflater.from(this).inflate(R.layout.congrates_screen, null);
        builder.setView(view);

        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);

        CardView cardContinue = (CardView) view.findViewById(R.id.cardContinue);
        TextView txtCorrect = (TextView) view.findViewById(R.id.txtCorrect);
        TextView txtWrong = (TextView) view.findViewById(R.id.txtWrong);
        TextView txtSkipped = (TextView) view.findViewById(R.id.txtSkipped);
        TextView txtHead = (TextView) view.findViewById(R.id.txtHead);
        TextView txtSubHead = (TextView) view.findViewById(R.id.txtSubHead);


        txtCorrect.setText(String.valueOf(Session.getCorrectAns()));
        txtWrong.setText(String.valueOf(Session.getWrongAns()));
        txtSkipped.setText(String.valueOf(Session.getSkippedAns()));
        txtHead.setText("Congrats " + Session.getUserName() + " !!");
        txtSubHead.setText("You have finished " + questionNumber + " questions");


        cardContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                setInterstitialAd();
            }
        });


    }


}
