package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saneesh.psc_kerala.Adapters.DailyQuizAttentedAdapter;
import com.saneesh.psc_kerala.Adapters.DailyQuizHomeAdapter;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.DailyQuiz;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class DailyQuizHomeActivity extends BaseActivity {


    private RecyclerView recyclerView, recyclerViewOld;
    private DailyQuizHomeAdapter dailyQuizHomeAdapter;
    private DailyQuizAttentedAdapter dailyQuizAttentedAdapter;
    private ArrayList<DailyQuiz> dailyQuizUpArray;
    private ArrayList<DailyQuiz> dailyQuizOldArray;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz_home);

        setStatusBarGradiant(this);

        setToolBar("Daily Quizzes");
        initViews();
    }


    @Override
    protected void onStart() {
        super.onStart();
        setDatas();
    }

    private void initViews() {

        txtView = (TextView) findViewById(R.id.txtView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewOld = (RecyclerView) findViewById(R.id.recyclerViewOld);
        recyclerViewOld.setHasFixedSize(true);
        recyclerViewOld.setLayoutManager(new GridLayoutManager(this,2));

        dailyQuizHomeAdapter = new DailyQuizHomeAdapter(this, new ArrayList<DailyQuiz>(), new DailyQuizHomeAdapter.DailyQuizListener() {
            @Override
            public void datilyQuizTapped(int position) {

                startActivity(new Intent(DailyQuizHomeActivity.this, DailyQuizActivity.class)
                        .putExtra("quizUrl", dailyQuizUpArray.get(position).getUrl()));
            }
        });
        recyclerView.setAdapter(dailyQuizHomeAdapter);

        dailyQuizAttentedAdapter = new DailyQuizAttentedAdapter(this, new ArrayList<DailyQuiz>(), new DailyQuizAttentedAdapter.DailyQuizListener() {
            @Override
            public void datilyQuizTapped(int position) {

                startActivity(new Intent(DailyQuizHomeActivity.this, DailyQuizActivity.class)
                        .putExtra("quizUrl", dailyQuizOldArray.get(position).getUrl()));
            }
        });
        recyclerViewOld.setAdapter(dailyQuizAttentedAdapter);

    }

    private void setDatas()
    {

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(DailyQuizHomeActivity.this)) {
            startActivity(new Intent(DailyQuizHomeActivity.this, NetworkStateActivity.class));

        } else {

            showProgress();
            DataManager.getDatamanager().getDailyQuizHomeDatas(new RetrofitCallBack<ArrayList<DailyQuiz>>() {
                @Override
                public void Success(ArrayList<DailyQuiz> dailyQuizsArray) {

                    hideProgress();

                    dailyQuizUpArray = new ArrayList<>();
                    dailyQuizOldArray = new ArrayList<>();

                    for (int i = 0; i < dailyQuizsArray.size(); i++) {

                        if (dailyQuizsArray.get(i).getStatus().contentEquals("0"))
                            dailyQuizUpArray.add(dailyQuizsArray.get(i));
                        else
                            dailyQuizOldArray.add(dailyQuizsArray.get(i));

                    }
                    dailyQuizHomeAdapter.update(dailyQuizUpArray);
                    dailyQuizAttentedAdapter.update(dailyQuizOldArray);
                    txtView.setVisibility(View.VISIBLE);

                }

                @Override
                public void Failure(String error) {
                    hideProgress();

                }
            });
        }

    }
}
