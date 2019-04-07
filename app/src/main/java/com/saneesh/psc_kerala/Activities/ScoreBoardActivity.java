package com.saneesh.psc_kerala.Activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

public class ScoreBoardActivity extends BaseActivity {


    private TextView txtCorrect, txtWrong, txtSkipped, txtAccuracy, txtName;
    private ImageView imgViewProfile, imgViewBack;
    private TextView txtViewTitle, txtCoins;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_score_board);

        txtCorrect = (TextView) findViewById(R.id.txtCorrect);
        txtWrong = (TextView) findViewById(R.id.txtWrong);
        txtSkipped = (TextView) findViewById(R.id.txtSkipped);
        txtAccuracy = (TextView) findViewById(R.id.txtAccuracy);
        imgViewProfile = (ImageView) findViewById(R.id.imgViewProfile);
        imgViewBack = (ImageView) findViewById(R.id.imgViewBack);
        txtName = (TextView) findViewById(R.id.txtName);
        txtViewTitle = (TextView) findViewById(R.id.txtViewTitle);
        txtCoins = (TextView) findViewById(R.id.txtCoins);

        txtCorrect.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtWrong.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));
        txtSkipped.setTypeface(Typeface.createFromAsset(getAssets(), "montserrat-bold.ttf"));

        txtCorrect.setText(String.valueOf(Session.getCorrectAns()));
        txtWrong.setText(String.valueOf(Session.getWrongAns()));
        txtSkipped.setText(String.valueOf(Session.getSkippedAns()));
        txtName.setText(Session.getUserName());

        txtCoins.setText(Session.getSharedData("myCoins") + " Coins");

        float accuracy = 0;

        if (Session.getCorrectAns() == 0)
            accuracy = 0;
        else
            accuracy = Float.valueOf(Session.getCorrectAns()) / Float.valueOf(Session.getTotQuestions()) * 100;

        txtAccuracy.setText(Math.round(accuracy * 100) / 100 + "%");


//        new Random().nextInt(4) + 1


        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        txtViewTitle.setTypeface(Typeface.createFromAsset(getAssets(), "raleway-thin.ttf"));

        ObjectAnimator animation = ObjectAnimator.ofFloat(txtViewTitle, "translationY", -100f);
        animation.setDuration(10000);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                txtViewTitle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animation.reverse();
        animation.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
