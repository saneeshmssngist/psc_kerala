package com.saneesh.psc_kerala.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saneesh.psc_kerala.Adapters.GeneralQuizAdapter;
import com.saneesh.psc_kerala.Adapters.GeneralQuizPagerDataAdapter;
import com.saneesh.psc_kerala.AppConstants;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.GeneralQuizInterface;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.GeneralModel;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class GeneralActivity extends BaseActivity implements GeneralQuizInterface {

    private RecyclerView recyclerViewDatas;
    private RecyclerView recyclerViewPager;

    private CoordinatorLayout coordinatorLayout;
    private LinearLayout frameLayouts;
    private BottomSheetBehavior bottomSheetBehavior;

    private ImageView imageViewLeft, imageViewRight;
    private TextView txtViewPageCount;
    private ImageView btnPopUpWindow, btnHideShowView1, btnHideShowView2;
    private LinearLayout layoutPopUp;

    private GeneralQuizPagerDataAdapter generalQuizPagerData;
    private GeneralQuizAdapter generalQuizAdapter;

    private Context context;
    private int tokenNo = 1;
    private String dataUrl = "", name = "";
    private int pageLimit = 0;
    private ArrayList<GeneralModel> generalQuestionDatasArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        context = this;

        dataUrl = getIntent().getStringExtra("dataUrl");
        name = getIntent().getStringExtra("name");

        setStatusBarGradiant(this);
        setToolBar(name);

        getViews();
        initControl();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Session.getSharedBoolean(AppConstants.ANSSHOW) == true) {
            btnHideShowView1.setVisibility(View.VISIBLE);
            btnHideShowView2.setVisibility(View.GONE);
        } else {
            btnHideShowView1.setVisibility(View.GONE);
            btnHideShowView2.setVisibility(View.VISIBLE);
        }

        setDatas();
    }

    public void getViews() {
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//        frameLayouts = findViewById(R.id.frameLayout);
//        bottomSheetBehavior = BottomSheetBehavior.from(frameLayouts);

        imageViewLeft = (ImageView) findViewById(R.id.imageViewLeft);
        imageViewRight = (ImageView) findViewById(R.id.imageViewRight);
        txtViewPageCount = (TextView) findViewById(R.id.txtViewPageCount);
        btnPopUpWindow = (ImageView) findViewById(R.id.btnPopUpWindow);
        btnHideShowView1 = (ImageView) findViewById(R.id.btnHideShowView1);
        btnHideShowView2 = (ImageView) findViewById(R.id.btnHideShowView2);
        layoutPopUp = (LinearLayout) findViewById(R.id.layoutPopUp);

        recyclerViewDatas = (RecyclerView) findViewById(R.id.recyclerViewDatas);
        recyclerViewDatas.setHasFixedSize(true);
        recyclerViewDatas.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewPager = (RecyclerView) findViewById(R.id.recyclerViewPager);
        recyclerViewPager.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

    }

    public void initControl() {

//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//
//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });


        imageViewLeft.setOnClickListener(onLeftArrowClicked);
        imageViewRight.setOnClickListener(onRightArrowClicked);
        btnPopUpWindow.setOnClickListener(onPopUpButtonTapped);

        btnHideShowView1.setOnClickListener(onBtnShowTapped);
        btnHideShowView2.setOnClickListener(onBtnHideTapped);
    }

    View.OnClickListener onLeftArrowClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (tokenNo == 1)
                return;

            tokenNo -= 1;
            generalQuizPagerData.reloadPager(tokenNo);
            recyclerViewPager.scrollToPosition(tokenNo - 1);
            setQuestionDatas();
        }
    };

    View.OnClickListener onRightArrowClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (tokenNo == pageLimit)
                return;

            tokenNo += 1;
            generalQuizPagerData.reloadPager(tokenNo);
            recyclerViewPager.scrollToPosition(tokenNo - 1);
            setQuestionDatas();

        }
    };

    View.OnClickListener onPopUpButtonTapped = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (layoutPopUp.getVisibility() == View.GONE)
                layoutPopUp.setVisibility(View.VISIBLE);
            else
                layoutPopUp.setVisibility(View.GONE);

        }
    };

    View.OnClickListener onBtnShowTapped = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Session.setSharedBoolean(AppConstants.ANSSHOW, false);
            btnHideShowView1.setVisibility(View.GONE);
            btnHideShowView2.setVisibility(View.VISIBLE);
            generalQuizAdapter.resetData();

        }
    };

    View.OnClickListener onBtnHideTapped = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Session.setSharedBoolean(AppConstants.ANSSHOW, true);
            btnHideShowView1.setVisibility(View.VISIBLE);
            btnHideShowView2.setVisibility(View.GONE);
            generalQuizAdapter.resetData();
        }
    };

    public void setDatas() {

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(GeneralActivity.this)) {
            startActivity(new Intent(GeneralActivity.this, NetworkStateActivity.class));

        } else {
            DataManager.getDatamanager().getGeneralData(dataUrl, new RetrofitCallBack<ArrayList<GeneralModel>>() {
                @Override
                public void Success(ArrayList<GeneralModel> generalDatasArray) {

                    generalQuestionDatasArray = generalDatasArray;
                    setQuestionDatas();
                    setPagerDatas();

                }

                @Override
                public void Failure(String error) {

                }
            });

        }

    }


    public void setQuestionDatas() {

        txtViewPageCount.setText(String.valueOf(tokenNo));

        ArrayList<GeneralModel> questionsModelsArray = new ArrayList<>();
        for (int i = ((tokenNo - 1) * 10); i < (tokenNo * 10) && i < generalQuestionDatasArray.size(); i++) {
            questionsModelsArray.add(generalQuestionDatasArray.get(i));
        }

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recyclerViewDatas.setLayoutAnimation(controller);

        generalQuizAdapter = new GeneralQuizAdapter(context, questionsModelsArray, tokenNo-1);
        recyclerViewDatas.setAdapter(generalQuizAdapter);

    }

    public void setPagerDatas() {

        pageLimit = generalQuestionDatasArray.size() / 10;

        if (!TextUtils.isEmpty(dataUrl)) {
            generalQuizPagerData = new GeneralQuizPagerDataAdapter(GeneralActivity.this, context, pageLimit);
            recyclerViewPager.setAdapter(generalQuizPagerData);
        }

    }

    @Override
    public void OnTokenButtonTapped(int position) {

        tokenNo = (position)+1;
        generalQuizPagerData.reloadPager(tokenNo);
        layoutPopUp.setVisibility(View.GONE);
        setQuestionDatas();

    }
}
