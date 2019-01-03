package com.saneesh.psc_kerala.Activities;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.RoomDatabaseRoom;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    private Toolbar toolbar;
    private RelativeLayout layoutGame, layoutRead, layoutQPapers, layoutMockTest, layoutTopicLearn, layotShareApp;
    private TextView txtView1;

    private Context context;
    public static RoomDatabaseRoom INSTANCE;

    private SharedPreferences pref;
    private ArrayList<QuestionsModel> questionModelDatasArray;
    private ArrayList<QuizTable> questionTableDatasArray;
    private boolean doubleBackToExitPressedOnce = false;

    private AdView adMobView;
    private String questionDatas = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;
        Base.setStatusBarGradiant(this);
        Fabric.with(this, new Crashlytics());

       // setUpAdmob();
        //message topic ...
        FirebaseMessaging.getInstance().subscribeToTopic("fcm_message");
        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(getApplicationContext(), RoomDatabaseRoom.class, "quizerrDb").allowMainThreadQueries().build();
        }

        getViews();
        initControl();
        setAllDatas();

    }

    private void setUpAdmob() {

        //admob sync..
//        MobileAds.initialize(this, getResources().getString(R.string.APPID));
//
//        adMobView = (AdView) findViewById(R.id.adMobView);
//        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void setAllDatas() {

        if (pref.getBoolean("quiz_executed", false)) {
            return;

        } else {
            setDatas();
        }
    }

    public void setDatas() {

        layoutProgress.setVisibility(View.VISIBLE);
        avilayoutProgress.show();

        DataManager.getDatamanager().getDatas(new RetrofitCallBack<ArrayList<QuestionsModel>>() {
            @Override
            public void Success(ArrayList<QuestionsModel> questionsModels) {

                questionModelDatasArray = questionsModels;
                setQuestionDatas();
                //  Toast.makeText(SAmple.this,status,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void Failure(String error) {

                avilayoutProgress.smoothToHide();
                layoutProgress.setVisibility(View.GONE);

                Toast.makeText(HomeActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setQuestionDatas() {


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {


                questionDatas = new Gson().toJson(questionModelDatasArray);

                questionTableDatasArray = new Gson().fromJson(questionDatas, new TypeToken<List<QuizTable>>() {
                }.getType());


                for (int i = 0; i < questionTableDatasArray.size(); i++) {
//                    QuizTable table = new QuizTable();
//                    table.setQuestion(questionDatasArray.get(i).getQuestion());
//                    table.setOption1(questionDatasArray.get(i).getOption1());
//                    table.setOption2(questionDatasArray.get(i).getOption2());
//                    table.setOption3(questionDatasArray.get(i).getOption3());
//                    table.setOption4(questionDatasArray.get(i).getOption4());
//                    table.setAnswer(questionDatasArray.get(i).getAnswer());
//                    table.setFlag(questionDatasArray.get(i).getStatus());

                    HomeActivity.INSTANCE.myDao().addQuizQuestions(questionTableDatasArray.get(i));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                avilayoutProgress.smoothToHide();
                layoutProgress.setVisibility(View.GONE);

                SharedPreferences.Editor edt = pref.edit();
                edt.putBoolean("quiz_executed", true);
                edt.commit();
            }

        }.execute();


    }

    private void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        layoutGame = (RelativeLayout) findViewById(R.id.layoutGame);
        layoutRead = (RelativeLayout) findViewById(R.id.layoutRead);
        layoutQPapers = (RelativeLayout) findViewById(R.id.layoutQPapers);

        layoutMockTest = (RelativeLayout) findViewById(R.id.layoutMockTest);
        layoutTopicLearn = (RelativeLayout) findViewById(R.id.layoutTopicLearn);
        layotShareApp = (RelativeLayout) findViewById(R.id.layotShareApp);

        txtView1 = (TextView) findViewById(R.id.txtView1);
    }

    private void initControl() {

        layoutGame.setOnClickListener(this);
        layoutRead.setOnClickListener(this);
        layoutMockTest.setOnClickListener(this);
        layoutTopicLearn.setOnClickListener(this);
        layoutQPapers.setOnClickListener(this);
        layotShareApp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("quiz_executed", false)) {

            int id = v.getId();

            switch (id) {
                case R.id.layoutGame:
                    startActivity(new Intent(this, GameActivity.class));
                    break;
                case R.id.layoutRead:
                    startActivity(new Intent(this, GeneralHomeActivity.class));
                    break;
                case R.id.layoutMockTest:
                    startActivity(new Intent(this, MockHomeActivity.class));
                    break;
                case R.id.layoutTopicLearn:
                    startActivity(new Intent(this, TopicHomeActivity.class));
                    break;
                case R.id.layoutQPapers:
                    startActivity(new Intent(this, QuestionPaperHomeActivity.class));
                    break;
                case R.id.layotShareApp:
                    shareAPp();
                    break;

            }
        }

    }

    private void shareAPp() {


        if (pref.getString("shareLink", "false").equals("false")) {

            layoutProgress.setVisibility(View.VISIBLE);
            avilayoutProgress.show();

            DataManager.getDatamanager().getShareLink(new RetrofitCallBack<String>() {
                @Override
                public void Success(String response) {

                    avilayoutProgress.smoothToHide();
                    layoutProgress.setVisibility(View.GONE);

                    SharedPreferences.Editor edt = pref.edit();
                    edt.putString("shareLink", response);
                    edt.commit();

                    shareApp(response);
                }

                @Override
                public void Failure(String error) {

                    Toast.makeText(HomeActivity.this, "Network connection needed !!", Toast.LENGTH_SHORT).show();

                    avilayoutProgress.smoothToHide();
                    layoutProgress.setVisibility(View.GONE);
                }
            });

        } else {
            shareApp(pref.getString("shareLink", "false"));
        }
    }

    private void shareApp(String response) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quizrr");

        String message = "Let me recommend you this application \n\n";
        message += response;

        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Choose one"));
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }


}
