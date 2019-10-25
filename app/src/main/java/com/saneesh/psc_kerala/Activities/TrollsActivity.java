package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.saneesh.psc_kerala.Adapters.TrollsAdapter;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;

import java.util.ArrayList;

public class TrollsActivity extends BaseActivity {


    private int position = 0;
    private ArrayList<String> arrayListDatas;
    private CardStackView cardStack;
    private TrollsAdapter trollsAdapter;
    private String trollUrl = "";
    private CircleView circleViewRefresh, circleViewClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trolls);

        trollUrl = getIntent().getStringExtra("trollUrl");
        Base.setStatusBarGradiant(this);

        initViews();
        setUpAdmob();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setDatas();
    }

    private void initViews() {

        setToolBar(getIntent().getStringExtra("name"));

        cardStack = (CardStackView) findViewById(R.id.cardStack);
        circleViewRefresh = (CircleView) findViewById(R.id.circleViewRefresh);
        circleViewClose = (CircleView) findViewById(R.id.circleViewClose);

        circleViewRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trollsAdapter = new TrollsAdapter(TrollsActivity.this, R.layout.list_item_trolls, arrayListDatas);
                cardStack.setAdapter(trollsAdapter);
            }
        });

        circleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

    }

    private void setDatas() {

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(TrollsActivity.this)) {
            startActivity(new Intent(TrollsActivity.this, NetworkStateActivity.class));

        } else {

            showProgress();
            DataManager.getDatamanager().getTrollsDatas(trollUrl, new RetrofitCallBack<ArrayList<String>>() {
                @Override
                public void Success(ArrayList<String> arrayList) {

                    hideProgress();
                    arrayListDatas = arrayList;
                    trollsAdapter = new TrollsAdapter(TrollsActivity.this, R.layout.list_item_trolls, arrayListDatas);
                    cardStack.setAdapter(trollsAdapter);
                }

                @Override
                public void Failure(String error) {
                    hideProgress();
                }
            });

        }

        cardStack.setCardStackEventListener(new CardStackView.CardStackEventListener() {
            @Override
            public void onBeginSwipe() {

            }

            @Override
            public void onEndSwipe(Direction direction) {

                if (cardStack.getTopIndex() == arrayListDatas.size() - 1)
                    finish();

            }

            @Override
            public void onSwiping(float oldX, float oldY, float newX, float newY) {

            }

            @Override
            public void onDiscarded(int index, Direction direction) {

            }

            @Override
            public void onTapUp(int index) {

            }
        });

    }


}
