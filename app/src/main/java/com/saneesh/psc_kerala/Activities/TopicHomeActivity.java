package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Adapters.TopicHomeAdapter;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.TopicModel;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

public class TopicHomeActivity extends BaseActivity {

    private RecyclerView recyclerHomeView;
    private SharedPreferences pref;

    private AdView adMobView;
    private ArrayList<TopicModel> contentsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_home);

        Base.setStatusBarGradiant(this);

        getViews();

        //  setUpAdmob();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setData();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void getViews() {

        setToolBar("Topic Learn");

        recyclerHomeView = findViewById(R.id.recyclerHomeView);
        recyclerHomeView.setHasFixedSize(true);
        recyclerHomeView.setLayoutManager(new GridLayoutManager(this, 3));

    }

    private void setData() {

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(TopicHomeActivity.this)) {
            startActivity(new Intent(TopicHomeActivity.this, NetworkStateActivity.class));

        } else {
            showProgress();
            DataManager.getDatamanager().getTopicHomeDatas(new RetrofitCallBack<TopicModel>() {
                @Override
                public void Success(TopicModel topicModel) {

                    hideProgress();
                    contentsArray = topicModel.getContentsArray();
                    setDatas();

                }

                @Override
                public void Failure(String error) {

                    hideProgress();
                    Toast.makeText(TopicHomeActivity.this, "Network connection needed !!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void setDatas() {

        TopicHomeAdapter topicHomeAdapter = new TopicHomeAdapter(this, contentsArray);
        recyclerHomeView.setAdapter(topicHomeAdapter);

    }
}
