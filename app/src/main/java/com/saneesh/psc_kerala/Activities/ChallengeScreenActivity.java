package com.saneesh.psc_kerala.Activities;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.RandomGen;
import com.saneesh.psc_kerala.Session;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChallengeScreenActivity extends BaseActivity implements View.OnClickListener {


    private CircularProgressBar progressBar;
    private CircleImageView imgViewPlayer, imgViewOpponent;
    private TextView txtViewOpponentName, txtVwOpponentPoint, txtViewPlayerPoint, txtViewName;
    private CardView btnOption1, btnOption2, btnOption3, btnOption4;
    private TextView txtViewOption1, txtViewOption2, txtViewOption3, txtViewOption4, txtCount, txtViewQuestion, txtQuestionNo;
    private TextView txtHint1, txtHint2, txtHint3, txtHint4;
    private int progressStatus = 0;
    private Handler hadler = new Handler();
    private CountDownTimer counterTimer;

    private BroadcastReceiver broadcastReceiver;
    private QuizTable currentQuestion = new QuizTable();
    private boolean enableUserOptionClicked = false, enableOpponentOptionRececived = false;
    private String opponentAnswer = "";
    private int questionNumber = 0;
    static boolean activityActive = false;
    private boolean opponentDeadState = false;

    private int userScore = 0, opponentScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_screen);

        initViews();
        initControls();

        setQuestion();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void setQuestion() {

        currentQuestion = HomeActivity.INSTANCE.myDao().getQuizQuestion();

        resetViews();

        //setting robot opponent
        setOpponent();

        questionNumber++;
        txtQuestionNo.setText(String.valueOf(questionNumber));

        if (currentQuestion != null) {
            txtViewOption1.setText(currentQuestion.getOption1());
            txtViewOption2.setText(currentQuestion.getOption2());
            txtViewOption3.setText(currentQuestion.getOption3());
            txtViewOption4.setText(currentQuestion.getOption4());
            txtViewQuestion.setText(currentQuestion.getQuestion());

        }

    }

    private void setOpponent() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                opponentAnswer = RandomGen.getAnswer();
                enableOpponentOptionRececived = true;

                if (enableUserOptionClicked) {
                    setPlayerAnswer(opponentAnswer, currentQuestion.getAnswer(), "opponent");
                    setRightAnswer();
                }
            }
        }, RandomGen.getGuessTime());

    }


    private void resetViews() {

        enableOpponentOptionRececived = false;
        enableUserOptionClicked = false;

        startTimer(15);

        btnOption1.setCardBackgroundColor(getResources().getColor(R.color.white));
        btnOption2.setCardBackgroundColor(getResources().getColor(R.color.white));
        btnOption3.setCardBackgroundColor(getResources().getColor(R.color.white));
        btnOption4.setCardBackgroundColor(getResources().getColor(R.color.white));
        txtHint1.setText("");
        txtHint2.setText("");
        txtHint3.setText("");
        txtHint4.setText("");

        btnOption1.setEnabled(true);
        btnOption2.setEnabled(true);
        btnOption3.setEnabled(true);
        btnOption4.setEnabled(true);
    }

    private void initViews() {

        progressBar = (CircularProgressBar) findViewById(R.id.progressBar);

        imgViewPlayer = (CircleImageView) findViewById(R.id.imgViewPlayer);
        imgViewOpponent = (CircleImageView) findViewById(R.id.imgViewOpponent);
        txtQuestionNo = (TextView) findViewById(R.id.txtQuestionNo);

        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewPlayerPoint = (TextView) findViewById(R.id.txtViewPlayerPoint);
        txtVwOpponentPoint = (TextView) findViewById(R.id.txtVwOpponentPoint);
        txtViewOpponentName = (TextView) findViewById(R.id.txtViewOpponentName);

        btnOption1 = (CardView) findViewById(R.id.btnOption1);
        btnOption2 = (CardView) findViewById(R.id.btnOption2);
        btnOption3 = (CardView) findViewById(R.id.btnOption3);
        btnOption4 = (CardView) findViewById(R.id.btnOption4);

        txtViewOption1 = (TextView) findViewById(R.id.txtViewOption1);
        txtViewOption2 = (TextView) findViewById(R.id.txtViewOption2);
        txtViewOption3 = (TextView) findViewById(R.id.txtViewOption3);
        txtViewOption4 = (TextView) findViewById(R.id.txtViewOption4);
        txtViewQuestion = (TextView) findViewById(R.id.txtViewQuestion);
        txtHint1 = (TextView) findViewById(R.id.txtHint1);
        txtHint2 = (TextView) findViewById(R.id.txtHint2);
        txtHint3 = (TextView) findViewById(R.id.txtHint3);
        txtHint4 = (TextView) findViewById(R.id.txtHint4);

        txtCount = (TextView) findViewById(R.id.txtCount);

    }

    private void initControls() {

        txtViewName.setText(Session.getChallengeName());

        txtViewOpponentName.setText(getIntent().getStringExtra("name"));
        switch (getIntent().getStringExtra("gender")) {
            case "1":
                imgViewOpponent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.male));
                break;
            case "2":
                imgViewOpponent.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.female));
                break;
        }

        switch (Session.getGender()) {
            case "male":
                imgViewPlayer.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.male));
                break;
            case "female":
                imgViewPlayer.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.female));
                break;
        }


        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        btnOption3.setOnClickListener(this);
        btnOption4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnOption1:
                btnOption1.setCardBackgroundColor(getResources().getColor(R.color.primary_dark));
                txtHint1.setText(getString(R.string.you));
                optionClicked("1");
                break;
            case R.id.btnOption2:
                btnOption2.setCardBackgroundColor(getResources().getColor(R.color.primary_dark));
                txtHint2.setText(getString(R.string.you));
                optionClicked("2");
                break;
            case R.id.btnOption3:
                btnOption3.setCardBackgroundColor(getResources().getColor(R.color.primary_dark));
                txtHint3.setText(getString(R.string.you));
                optionClicked("3");
                break;
            case R.id.btnOption4:
                btnOption4.setCardBackgroundColor(getResources().getColor(R.color.primary_dark));
                txtHint4.setText(getString(R.string.you));
                optionClicked("4");
                break;

        }

    }

    private void optionClicked(String option) {

        enableUserOptionClicked = true;
        //for avoid double click
        disableAllButtons();

        //method can be user for setting both user and opponent answer .
        setPlayerAnswer(option, currentQuestion.getAnswer(), "you");


//        //delay give 2 sec for right answer .
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }, 2000);

        //set answer of opponent
        if (enableOpponentOptionRececived) {

            //delay give 2 sec for opponent answer .
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    setPlayerAnswer(opponentAnswer, currentQuestion.getAnswer(), "opponent");
                    setRightAnswer();
                }
            }, 2000);
        }


    }

    private void disableAllButtons() {

        btnOption1.setEnabled(false);
        btnOption2.setEnabled(false);
        btnOption3.setEnabled(false);
        btnOption4.setEnabled(false);

    }


    public void startTimer(final int length) {

        counterTimer = new CountDownTimer(length * 1000, 500) {
            public void onTick(long leftTimeInMilliseconds) {

                long seconds = leftTimeInMilliseconds / 1000;
//                Log.d(TAG_CHECK, "onTick: "+String.valueOf(seconds*3.33));

                progressBar.setProgress((int) (seconds * 6.66));

                txtCount.setText(String.format("%02d", seconds % 60));

            }

            public void onFinish() {
                progressBar.setProgress(0);
                txtCount.setText("00");

                //.........check opponent answered or not ....
                if (enableOpponentOptionRececived)
                    setPlayerAnswer(opponentAnswer, currentQuestion.getAnswer(), "opponent");
                else
                    Toast.makeText(ChallengeScreenActivity.this, "Opponent not answered ...", Toast.LENGTH_SHORT).show();

                setRightAnswer();
                disableAllButtons();
                Toast.makeText(ChallengeScreenActivity.this, "Time Out", Toast.LENGTH_SHORT).show();

            }
        }.start();
    }

    public void stopTimer() {

        if (counterTimer != null) {
            counterTimer.cancel();
//            Toast.makeText(this, "Timer stopped", Toast.LENGTH_SHORT).show();
        }
    }


    //can user for both user and opponent
    //c
    private void setPlayerAnswer(String cllickedOption, String rightOption, String userType) {

        //if user clicked wrong answer .
        //userType = you / opponent.
        if (!rightOption.equals(String.valueOf(cllickedOption))) {

            switch (Integer.valueOf(cllickedOption)) {
                case 1:
                    btnOption1.setCardBackgroundColor(getResources().getColor(R.color.challenge_red));
                    txtHint1.setText(userType);
                    break;
                case 2:
                    btnOption2.setCardBackgroundColor(getResources().getColor(R.color.challenge_red));
                    txtHint2.setText(userType);
                    break;
                case 3:
                    btnOption3.setCardBackgroundColor(getResources().getColor(R.color.challenge_red));
                    txtHint3.setText(userType);
                    break;
                case 4:
                    btnOption4.setCardBackgroundColor(getResources().getColor(R.color.challenge_red));
                    txtHint4.setText(userType);
                    break;
            }
        } else {

            if (userType.contentEquals("opponent")) {
                opponentScore++;
                txtVwOpponentPoint.setText(String.valueOf(opponentScore));
            } else {
                userScore++;
                txtViewPlayerPoint.setText(String.valueOf(userScore));
            }

            switch (Integer.valueOf(cllickedOption)) {
                case 1:
                    btnOption1.setCardBackgroundColor(getResources().getColor(R.color.challenge_green));
                    txtHint1.setText(userType);
                    break;
                case 2:
                    btnOption2.setCardBackgroundColor(getResources().getColor(R.color.challenge_green));
                    txtHint2.setText(userType);
                    break;
                case 3:
                    btnOption3.setCardBackgroundColor(getResources().getColor(R.color.challenge_green));
                    txtHint3.setText(userType);
                    break;
                case 4:
                    btnOption4.setCardBackgroundColor(getResources().getColor(R.color.challenge_green));
                    txtHint4.setText(userType);
                    break;
            }
        }

        if (userType.contentEquals("opponent")) {
            stopTimer();
        }

    }


    //if both users clicked wrong answer ...
    public void setRightAnswer() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                switch (Integer.valueOf(currentQuestion.getAnswer())) {
                    case 1:
                        btnOption1.setCardBackgroundColor(getResources().getColor(R.color.challenge_green));
                        txtHint1.setText("Answer");
                        break;
                    case 2:
                        btnOption2.setCardBackgroundColor(getResources().getColor(R.color.challenge_green));
                        txtHint2.setText("Answer");
                        break;
                    case 3:
                        btnOption3.setCardBackgroundColor(getResources().getColor(R.color.challenge_green));
                        txtHint3.setText("Answer");
                        break;
                    case 4:
                        btnOption4.setCardBackgroundColor(getResources().getColor(R.color.challenge_green));
                        txtHint4.setText("Answer");
                        break;
                }


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (questionNumber == 10)
                            finish();
                        else
                            setQuestion();
                    }
                }, 3000);

            }
        }, 2000);


    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
