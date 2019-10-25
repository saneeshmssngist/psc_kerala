package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdView;
import com.saneesh.psc_kerala.Adapters.MockReviewAdapter;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class MockReviewActivity extends BaseActivity {

    private ArrayList<QuizTable> questionDatasArray;
    private ArrayList<String> answerArray;

    private RecyclerView recyclerViewReview;
    private Button btnNextLevel;
    private AdView adMobView;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_review);

        questionDatasArray = new ArrayList<>();
        questionDatasArray = (ArrayList<QuizTable>) getIntent().getSerializableExtra("mockDatas");
        answerArray = (ArrayList<String>) getIntent().getSerializableExtra("answerDatas");

        setStatusBarGradiant(this);

        getViews();
        setToolBar("Feed back");
        setDatas();

        setUpAdmob();
    }

    private void getViews() {

        btnNextLevel = findViewById(R.id.btnNextLevel);
        recyclerViewReview = findViewById(R.id.recyclerViewReview);
        recyclerViewReview.setHasFixedSize(true);
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setDatas() {

        MockReviewAdapter mockReviewAdapter = new MockReviewAdapter(this,questionDatasArray,answerArray);
        recyclerViewReview.setAdapter(mockReviewAdapter);

        btnNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MockReviewActivity.this, MockHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(MockReviewActivity.this, MockHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
