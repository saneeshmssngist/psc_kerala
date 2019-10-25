package com.saneesh.psc_kerala.Activities;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.saneesh.psc_kerala.Adapters.TopicAdapter;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.Model.TopicModel;
import com.saneesh.psc_kerala.R;

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

        setUpAdmob();

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
