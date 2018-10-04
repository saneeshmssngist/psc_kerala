package com.saneesh.psc_kerala.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.saneesh.psc_kerala.Interfaces.GeneralQuizInterface;
import com.saneesh.psc_kerala.Model.GeneralTable;
import com.saneesh.psc_kerala.R;
import com.saneesh.psc_kerala.Session;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class GeneralActivity extends AppCompatActivity implements GeneralQuizInterface {

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

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

    private Toolbar toolBar;

    private Context context;
    private int tokenNo = 0;
    private String status = "";
    private int pageLimit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        context = this;

        status = getIntent().getStringExtra("status");

        setStatusBarGradiant(this);
        setActionBar();
        getViews();
        initControl();

        if (Session.getSharedBoolean(AppConstants.ANSSHOW) == true) {
            btnHideShowView1.setVisibility(View.VISIBLE);
            btnHideShowView2.setVisibility(View.GONE);
        } else {
            btnHideShowView1.setVisibility(View.GONE);
            btnHideShowView2.setVisibility(View.VISIBLE);
        }

        setPagerDatas();
        setDatas();

    }

    public void setActionBar() {

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        String title = "Read to Learn";
        switch (status)
        {
            case "1" :title = getResources().getString(R.string.geography);
                break;
            case "2" :title = getResources().getString(R.string.history);
                break;
            case "3" :title = getResources().getString(R.string.india);
                break;
            case "4" :title = getResources().getString(R.string.movies);
                break;
            case "5" :title = getResources().getString(R.string.maths);
                break;
            case "6" :title = getResources().getString(R.string.sports);
                break;
            case "7" :title = getResources().getString(R.string.world);
                break;
            case "9" :title = getResources().getString(R.string.literature);
                break;
            case "10" :title = getResources().getString(R.string.kerala);
                break;
            case "11" :title = getResources().getString(R.string.science);
                break;
        }

        getSupportActionBar().setTitle(title);
    }

    public void getViews() {
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//        frameLayouts = findViewById(R.id.frameLayout);
//        bottomSheetBehavior = BottomSheetBehavior.from(frameLayouts);

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

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

            if (tokenNo == 0)
                return;

            tokenNo -= 1;
            generalQuizPagerData.reloadPager(tokenNo+1);
            recyclerViewPager.scrollToPosition(tokenNo - 1);
            setDatas();
        }
    };

    View.OnClickListener onRightArrowClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (tokenNo == pageLimit)
                return;

            tokenNo += 1;
            generalQuizPagerData.reloadPager(tokenNo+1);
            recyclerViewPager.scrollToPosition(tokenNo - 1);
            setDatas();

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

        txtViewPageCount.setText(String.valueOf(tokenNo+1));

        List<GeneralTable> generalDatas = HomeActivity.INSTANCE.myDao().getGeneralDatas(status,String.valueOf(tokenNo*10));

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recyclerViewDatas.setLayoutAnimation(controller);

        generalQuizAdapter = new GeneralQuizAdapter(context, generalDatas, tokenNo);
        recyclerViewDatas.setAdapter(generalQuizAdapter);

    }

    public void setPagerDatas() {

        pageLimit = (int) Integer.valueOf(HomeActivity.INSTANCE.myDao().getGeneralDatasCount(status))/10;

        if (!TextUtils.isEmpty(status)) {
            generalQuizPagerData = new GeneralQuizPagerDataAdapter(GeneralActivity.this, context, pageLimit);
            recyclerViewPager.setAdapter(generalQuizPagerData);
        }

    }

    @Override
    public void OnTokenButtonTapped(int position) {

        tokenNo = (position);
        generalQuizPagerData.reloadPager(tokenNo+1);
        layoutPopUp.setVisibility(View.GONE);
        setDatas();

    }
}
