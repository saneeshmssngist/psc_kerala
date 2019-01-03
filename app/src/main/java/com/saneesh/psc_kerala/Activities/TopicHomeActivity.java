package com.saneesh.psc_kerala.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Adapters.TopicHomeAdapter;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.GeneralTable;
import com.saneesh.psc_kerala.Model.TopicModel;
import com.saneesh.psc_kerala.Model.TopicTable;
import com.saneesh.psc_kerala.Model.TopicTableContent;
import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class TopicHomeActivity extends AppCompatActivity {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;
    private RecyclerView recyclerHomeView;

    private Toolbar toolbar;
    private SharedPreferences pref;

    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_home);

        Base.setStatusBarGradiant(this);

        getViews();
        setData();

        setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this,getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void getViews() {

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Topic Learn");

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        recyclerHomeView = findViewById(R.id.recyclerHomeView);
        recyclerHomeView.setHasFixedSize(true);
        recyclerHomeView.setLayoutManager(new GridLayoutManager(this,3));

    }

    private void setData() {


        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("topic_executed", false)) {

            setDatas();

        } else {

            layoutProgress.setVisibility(View.VISIBLE);
            avilayoutProgress.show();

            DataManager.getDatamanager().getTopicHomeDatas(new RetrofitCallBack<TopicModel>()
            {
                @Override
                public void Success(TopicModel topicModel) {

                    setTopicDatas(topicModel);

                    //  Toast.makeText(SAmple.this,status,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void Failure(String error) {

                    Toast.makeText(TopicHomeActivity.this,"Network connection needed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void setTopicDatas(final TopicModel topicDatas) {


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                ArrayList<TopicModel> topicModelsNames = topicDatas.getTopicNames();

                for (int i = 0; i < topicModelsNames.size(); i++) {

                    TopicTable topicTable = new TopicTable();
                    topicTable.setId(Integer.parseInt(topicModelsNames.get(i).getId()));
                    topicTable.setTopicTitle(topicModelsNames.get(i).getTopicName());
                    topicTable.setUrl(topicModelsNames.get(i).getImageUrl());

                    HomeActivity.INSTANCE.myDao().addTopicDatas(topicTable);

                }

                ArrayList<TopicModel> topicModelsContents = topicDatas.getTopicContents();

                for (int i = 0; i < topicModelsContents.size(); i++) {

                    TopicTableContent tableContent = new TopicTableContent();
                    tableContent.setContentId(Integer.parseInt(topicModelsContents.get(i).getContentId()));
                    tableContent.setContent(topicModelsContents.get(i).getContents());

                    HomeActivity.INSTANCE.myDao().addTopicDatasContents(tableContent);

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                SharedPreferences.Editor edt = pref.edit();
                edt.putBoolean("topic_executed", true);
                edt.commit();

                avilayoutProgress.smoothToHide();
                layoutProgress.setVisibility(View.GONE);
                setDatas();

            }
        }.execute();

    }

    private void setDatas() {

        List<TopicTable> topicModelArray = HomeActivity.INSTANCE.myDao().getTopicHomeDatas();

        TopicHomeAdapter topicHomeAdapter = new TopicHomeAdapter(this,topicModelArray);
        recyclerHomeView.setAdapter(topicHomeAdapter);

    }
}
