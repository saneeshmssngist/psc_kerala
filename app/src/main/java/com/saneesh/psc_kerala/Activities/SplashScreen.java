package com.saneesh.psc_kerala.Activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

public class SplashScreen extends AppCompatActivity {

    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgView = findViewById(R.id.imgView);

        Base.setStatusBarGradiant(this);

        DataManager.getDatamanager().init(this);
        Session.getSession(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = null;

                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                if (pref.getBoolean("login_executed", false)) {

                    if (getIntent().hasExtra("notificationType")) {

                        switch (getIntent().getStringExtra("notificationType")) {
                            case "1":
                                intent = new Intent(SplashScreen.this, QuestionPaperHomeActivity.class);
                                break;
                            case "2":
                                intent = new Intent(SplashScreen.this, TrollsHomeActivity.class);
                                break;
                            case "3":
                                intent = new Intent(SplashScreen.this, DailyQuizHomeActivity.class);
                                break;

                        }
                    } else {
                        intent = new Intent(SplashScreen.this, HomeActivity.class);
                    }

                } else {
                    intent = new Intent(SplashScreen.this, PreHomeActivity.class);
                }

//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Pair pairs = new Pair<View, String>(imgView, "homeQuizzr");
                ActivityOptions activityOptions = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                    activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this, pairs);

                }
                startActivity(intent, activityOptions.toBundle());
                finish();
            }
        }, 3000);

    }
}
