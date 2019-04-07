package com.saneesh.psc_kerala.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener {


    private RelativeLayout layoutMockTest, layoutRead, layoutQPapers, layoutInfinityQuiz, layoutTopicLearn,
            layotShareApp, layoutDailyQuiz, layottroll;
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

        FirebaseMessaging.getInstance().subscribeToTopic("quizrr_fcm_message");

//        Fabric.with(this, new Crashlytics());

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


        showProgress();
        DataManager.getDatamanager().getDatas(new RetrofitCallBack<ArrayList<QuestionsModel>>() {
            @Override
            public void Success(ArrayList<QuestionsModel> questionsModels) {

                SharedPreferences.Editor edt = pref.edit();
                edt.putBoolean("quiz_executed", true);
                edt.commit();

                hideProgress();
                questionModelDatasArray = questionsModels;
                setQuestionDatas();
                //  Toast.makeText(SAmple.this,status,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void Failure(String error) {

                hideProgress();
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
                    Log.d("QUIZZ", "doInBackground: " + i);

                    HomeActivity.INSTANCE.myDao().addQuizQuestions(questionTableDatasArray.get(i));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }

        }.execute();


    }

    private void getViews() {

//        setToolBarNoBack("");

        layoutMockTest = (RelativeLayout) findViewById(R.id.layoutMockTest);
        layoutRead = (RelativeLayout) findViewById(R.id.layoutRead);
        layoutQPapers = (RelativeLayout) findViewById(R.id.layoutQPapers);

        layoutInfinityQuiz = (RelativeLayout) findViewById(R.id.layoutInfinityQuiz);
        layoutTopicLearn = (RelativeLayout) findViewById(R.id.layoutTopicLearn);
        layotShareApp = (RelativeLayout) findViewById(R.id.layotShareApp);
        layoutDailyQuiz = (RelativeLayout) findViewById(R.id.layoutDailyQuiz);
        layottroll = (RelativeLayout) findViewById(R.id.layottroll);

        txtView1 = (TextView) findViewById(R.id.txtView1);
    }

    private void initControl() {

        layoutMockTest.setOnClickListener(this);
        layoutRead.setOnClickListener(this);
        layoutInfinityQuiz.setOnClickListener(this);
        layoutTopicLearn.setOnClickListener(this);
        layoutQPapers.setOnClickListener(this);
        layoutDailyQuiz.setOnClickListener(this);
        layottroll.setOnClickListener(this);
        layotShareApp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);

        int id = v.getId();

        switch (id) {
            case R.id.layoutMockTest:
                if (pref.getBoolean("quiz_executed", false))
                    startActivity(new Intent(this, MockHomeActivity.class));
                break;
            case R.id.layoutInfinityQuiz:
                if (pref.getBoolean("quiz_executed", false))
                    startActivity(new Intent(this, InfinityQuizActivity.class));
                break;
            case R.id.layoutRead:
                startActivity(new Intent(this, GeneralHomeActivity.class));
                break;

            case R.id.layoutTopicLearn:
                startActivity(new Intent(this, TopicHomeActivity.class));
                break;
            case R.id.layoutQPapers:
                startActivity(new Intent(this, QuestionPaperHomeActivity.class));
                break;
            case R.id.layoutDailyQuiz:
                startActivity(new Intent(this, DailyQuizHomeActivity.class));
                break;
            case R.id.layottroll:
                startActivity(new Intent(this, TrollsHomeActivity.class));
                break;
            case R.id.layotShareApp:
                shareApp();
                break;

        }

    }


    private void shareApp() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quizrr");

        String message = "P.S.C.  പഠനം ഈസിയാക്കു ......\n" +
                "( FREE Android Application )\n";
        message += "https://play.google.com/store/apps/details?id="+getApplication().getPackageName();

        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Choose one"));
    }

    @Override
    public void onBackPressed() {

//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View view = LayoutInflater.from(this).inflate(R.layout.exit_layout_screen, null);

        builder.setView(view);
        LinearLayout imgWrong = (LinearLayout) view.findViewById(R.id.imgWrong);
        LinearLayout imgRight = (LinearLayout) view.findViewById(R.id.imgRight);

        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        imgWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


}
