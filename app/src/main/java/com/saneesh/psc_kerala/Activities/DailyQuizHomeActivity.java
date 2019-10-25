package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saneesh.psc_kerala.Adapters.DailyQuizHomeAdapter;
import com.saneesh.psc_kerala.Model.DailyQuiz;
import com.saneesh.psc_kerala.Model.DailyQuizData;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;
import com.saneesh.psc_kerala.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class DailyQuizHomeActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private DailyQuizHomeAdapter dailyQuizHomeAdapter;
    private TextView txtTime, txtTimer, txtTime1, txtTimer1;
    private CardView cardView, cardView1;
    private Handler handler = new Handler();
    private MaterialButton btnStart1, btnStart;
    private ArrayList<DailyQuizData> dailyQuizData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz_home);

        setStatusBarGradiant(this);

        setToolBar("Daily Quizzes");
        initViews();
        intiControls();

    }

    private void intiControls() {

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyQuizHomeActivity.this,DailyQuizActivity.class));
            }
        });

        btnStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DailyQuizHomeActivity.this,DailyQuizActivity.class));
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        setDatas();
    }

    private void initViews() {

        cardView = (CardView) findViewById(R.id.cardView);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtTimer = (TextView) findViewById(R.id.txtTimer);

        cardView1 = (CardView) findViewById(R.id.cardView1);
        txtTime1 = (TextView) findViewById(R.id.txtTime1);
        txtTimer1 = (TextView) findViewById(R.id.txtTimer1);

        btnStart = (MaterialButton) findViewById(R.id.btnStart);
        btnStart1 = (MaterialButton) findViewById(R.id.btnStart1);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setDatas() {

        setTimeData1();
        setTimeData2();

        setHistory();

    }

    private void setHistory() {

       dailyQuizData = new Gson().fromJson(Session.getDailyQuizData(), new TypeToken<ArrayList<DailyQuizData>>(){}.getType());

        dailyQuizHomeAdapter = new DailyQuizHomeAdapter(this,dailyQuizData);
        recyclerView.setAdapter(dailyQuizHomeAdapter);

    }

    private void setTimeData1() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 18);

        final String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(calendar.getTime());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String nowDate = Utils.getTimeDifference(date);
                if (!TextUtils.isEmpty(nowDate)) {

                    txtTimer.setVisibility(View.VISIBLE);
                    btnStart.setVisibility(View.GONE);
                    handler.postDelayed(this, 1000);

                } else {

                    txtTimer.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                    cardView.setAlpha(1);

                    Animation anim = new AlphaAnimation(0.3f, 2.0f);
                    anim.setDuration(2500); //You can manage the blinking time with this parameter
                    anim.setStartOffset(20);
                    anim.setRepeatMode(Animation.REVERSE);
                    anim.setRepeatCount(Animation.INFINITE);
                    btnStart.startAnimation(anim);

                }

            }
        }, 1000);

    }

    private void setTimeData2() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 22);

        final String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(calendar.getTime());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String nowDate = Utils.getTimeDifference(date);
                if (!TextUtils.isEmpty(nowDate)) {

                    txtTimer1.setVisibility(View.VISIBLE);
                    btnStart1.setVisibility(View.GONE);

                    handler.postDelayed(this, 1000);

                } else {
                    txtTimer1.setVisibility(View.GONE);
                    btnStart1.setVisibility(View.VISIBLE);
                    cardView1.setAlpha(1);

                    Animation anim = new AlphaAnimation(0.3f, 2.0f);
                    anim.setDuration(2500); //You can manage the blinking time with this parameter
                    anim.setStartOffset(20);
                    anim.setRepeatMode(Animation.REVERSE);
                    anim.setRepeatCount(Animation.INFINITE);
                    btnStart1.startAnimation(anim);
                }

            }
        }, 1000);

    }


}
