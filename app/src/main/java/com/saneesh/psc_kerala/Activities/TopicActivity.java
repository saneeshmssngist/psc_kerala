package com.saneesh.psc_kerala.Activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Adapters.TopicAdapter;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.TopicModel;
import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends BaseActivity {

    private RecyclerView recyclerViewTopic;
    private ImageView imageView;
    private TextView txtTitle;

    private AdView adMobView;
    private TopicModel topicData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Base.setStatusBarGradiant(this);

        topicData = (TopicModel) getIntent().getSerializableExtra("contents_data");

        getViews();
        setData();

     //   setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this,getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

    }

    private void getViews()
    {

       setToolBar("Topic Learn");

        txtTitle = findViewById(R.id.txtTitle);
        imageView = findViewById(R.id.imageView);

//        imgTopicImage = findViewById(R.id.imgTopicImage);
        recyclerViewTopic = findViewById(R.id.recyclerViewTopic);
        recyclerViewTopic.setHasFixedSize(true);
        recyclerViewTopic.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setData()
    {

        txtTitle.setText(topicData.getTopicName());

        Glide
                .with(this)
                .load(topicData.getImageUrl())
                .into(imageView);

        TopicAdapter topicAdapter = new TopicAdapter(this,topicData.getContent());
        recyclerViewTopic.setAdapter(topicAdapter);
    }

}
