package com.saneesh.psc_kerala.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saneesh.psc_kerala.Adapters.TrollHomeAdapter;
import com.saneesh.psc_kerala.Base;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.TrollHome;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

public class TrollsHomeActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private TrollHomeAdapter trollHomeAdapter;
    private ArrayList<TrollHome> trollHomesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trolls_home);

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

        setToolBar("PSC Trolls");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trollHomeAdapter = new TrollHomeAdapter(this, new ArrayList<TrollHome>(), new TrollHomeAdapter.TrollHomeListener() {
            @Override
            public void onItemTapped(int position) {

                startActivity(new Intent(TrollsHomeActivity.this, TrollsActivity.class)
                        .putExtra("trollUrl", trollHomesArray.get(position).getUrl())
                        .putExtra("name", trollHomesArray.get(position).getName())
                );
            }
        });
        recyclerView.setAdapter(trollHomeAdapter);

    }

    private void setDatas() {

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(TrollsHomeActivity.this)) {
            startActivity(new Intent(TrollsHomeActivity.this, NetworkStateActivity.class));

        } else {
            showProgress();
            DataManager.getDatamanager().getPscTrollHomeDatas(new RetrofitCallBack<ArrayList<TrollHome>>() {
                @Override
                public void Success(ArrayList<TrollHome> trollHomes) {

                    hideProgress();
                    trollHomesArray = trollHomes;
                    trollHomeAdapter.update(trollHomesArray);

                }

                @Override
                public void Failure(String error) {

                    hideProgress();
                }
            });
        }

    }


}
