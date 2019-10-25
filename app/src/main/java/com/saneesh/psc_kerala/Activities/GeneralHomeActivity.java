package com.saneesh.psc_kerala.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.GeneralHome;
import com.saneesh.psc_kerala.NetworkConnection;
import com.saneesh.psc_kerala.R;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class GeneralHomeActivity extends BaseActivity implements View.OnClickListener {


    private CardView cardViewKerala, cardViewIndia, cardViewWorld, cardViewHistory, cardViewGeography,
            cardViewSports, cardViewMaths, cardViewMovies, cardViewScience, cardViewLiterature;

    private SharedPreferences pref;
    private Context context;

    private AdView adMobView;
    private ArrayList<GeneralHome> generalDatasHome = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_home);

        context = this;
        setStatusBarGradiant(this);

        getViews();
        setActionBar();
        initControl();
        setUpAdmob();

    }

    @Override
    protected void onStart() {
        super.onStart();
        setDatas();
    }

    public void setDatas() {

//        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
//        if (pref.getBoolean("general_executed", false)) {
//
//            return;
//
//        } else {

        NetworkConnection networkConnection = new NetworkConnection();
        if (!networkConnection.isConnected(GeneralHomeActivity.this)) {
            startActivity(new Intent(GeneralHomeActivity.this, NetworkStateActivity.class));

        } else {

            showProgress();
            DataManager.getDatamanager().getGeneralHomeDatas(new RetrofitCallBack<ArrayList<GeneralHome>>() {
                @Override
                public void Success(ArrayList<GeneralHome> generalDatas) {

                    hideProgress();
                    generalDatasHome = generalDatas;

                }

                @Override
                public void Failure(String error) {

                    hideProgress();
                    Toast.makeText(context, "Network connection needed !!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void setActionBar() {

        setToolBar("Read to Learn");
    }

    public void getViews() {

        cardViewKerala = (CardView) findViewById(R.id.cardViewKerala);
        cardViewIndia = (CardView) findViewById(R.id.cardViewIndia);
        cardViewWorld = (CardView) findViewById(R.id.cardViewWorld);
        cardViewHistory = (CardView) findViewById(R.id.cardViewHistory);
        cardViewGeography = (CardView) findViewById(R.id.cardViewGeography);

        cardViewSports = (CardView) findViewById(R.id.cardViewSports);
        cardViewMaths = (CardView) findViewById(R.id.cardViewMaths);
        cardViewMovies = (CardView) findViewById(R.id.cardViewMovies);
        cardViewScience = (CardView) findViewById(R.id.cardViewScience);
        cardViewLiterature = (CardView) findViewById(R.id.cardViewLiterature);

    }

    public void initControl() {
        cardViewKerala.setOnClickListener(this);
        cardViewIndia.setOnClickListener(this);
        cardViewWorld.setOnClickListener(this);
        cardViewHistory.setOnClickListener(this);
        cardViewGeography.setOnClickListener(this);

        cardViewSports.setOnClickListener(this);
        cardViewMaths.setOnClickListener(this);
        cardViewMovies.setOnClickListener(this);
        cardViewScience.setOnClickListener(this);
        cardViewLiterature.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (generalDatasHome.size() != 0) {
            int id = v.getId();


            Intent intent = new Intent(this, GeneralActivity.class);

            switch (id) {
                case R.id.cardViewKerala:
                    intent.putExtra("dataUrl", generalDatasHome.get(0).getUrl())
                            .putExtra("name", getResources().getString(R.string.kerala));
                    break;
                case R.id.cardViewIndia:
                    intent.putExtra("dataUrl", generalDatasHome.get(1).getUrl())
                            .putExtra("name", getResources().getString(R.string.india));
                    break;
                case R.id.cardViewWorld:
                    intent.putExtra("dataUrl", generalDatasHome.get(2).getUrl())
                            .putExtra("name", getResources().getString(R.string.world));
                    break;
                case R.id.cardViewHistory:
                    intent.putExtra("dataUrl", generalDatasHome.get(3).getUrl())
                            .putExtra("name", getResources().getString(R.string.history));
                    break;
                case R.id.cardViewGeography:
                    intent.putExtra("dataUrl", generalDatasHome.get(4).getUrl())
                            .putExtra("name", getResources().getString(R.string.geography));
                    break;
                case R.id.cardViewSports:
                    intent.putExtra("dataUrl", generalDatasHome.get(5).getUrl())
                            .putExtra("name", getResources().getString(R.string.sports));
                    break;
                case R.id.cardViewMaths:
                    intent.putExtra("dataUrl", generalDatasHome.get(6).getUrl())
                            .putExtra("name", getResources().getString(R.string.maths));
                    break;
                case R.id.cardViewMovies:
                    intent.putExtra("dataUrl", generalDatasHome.get(7).getUrl())
                            .putExtra("name", getResources().getString(R.string.movies));
                    break;
                case R.id.cardViewScience:
                    intent.putExtra("dataUrl", generalDatasHome.get(8).getUrl())
                            .putExtra("name", getResources().getString(R.string.science));
                    break;
                case R.id.cardViewLiterature:
                    intent.putExtra("dataUrl", generalDatasHome.get(9).getUrl())
                            .putExtra("name", getResources().getString(R.string.literature));
                    break;
            }

            startActivity(intent);

        }
    }

}
