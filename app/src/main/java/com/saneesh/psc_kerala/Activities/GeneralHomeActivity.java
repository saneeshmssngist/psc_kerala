package com.saneesh.psc_kerala.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.GeneralModel;
import com.saneesh.psc_kerala.Model.GeneralTable;
import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Activities.HomeActivity.INSTANCE;
import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class GeneralHomeActivity extends AppCompatActivity implements View.OnClickListener {


    private CardView cardViewKerala, cardViewIndia, cardViewWorld, cardViewHistory, cardViewGeography,
            cardViewSports, cardViewMaths, cardViewMovies, cardViewScience, cardViewLiterature;
    private Toolbar toolBar;

    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    private SharedPreferences pref;
    private Context context;

    private AdView adMobView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_home);

        context = this;
        setStatusBarGradiant(this);

        getViews();
        setActionBar();
        initControl();

        setDatas();
        setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this, getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().addTestDevice("C0256988724EBA3D6A98B53747EE5900").build());
    }


    public void setDatas() {

        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("general_executed", false)) {

            return;

        } else {

            layoutProgress.setVisibility(View.VISIBLE);
            avilayoutProgress.show();

            DataManager.getDatamanager().getGeneralDatas(new RetrofitCallBack<ArrayList<GeneralModel>>() {
                @Override
                public void Success(ArrayList<GeneralModel> generalDatas) {

                    if (generalDatas.size() != 0) {
                        insertDataToDb(generalDatas);
                    } else {
                        Toast.makeText(context, "No datas there !!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void Failure(String error) {

                    avilayoutProgress.smoothToHide();
                    layoutProgress.setVisibility(View.GONE);
                    Toast.makeText(context, "Network connection needed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void insertDataToDb(final ArrayList<GeneralModel> generalDatas) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                for (int i = 0; i < generalDatas.size(); i++) {

                    GeneralTable generalTable = new GeneralTable();
                    generalTable.setId(Integer.parseInt(generalDatas.get(i).getId()));
                    generalTable.setQuestion(generalDatas.get(i).getQuestion());
                    generalTable.setAnswer(generalDatas.get(i).getAnswer());
                    generalTable.setStatus(generalDatas.get(i).getStatus());

                    INSTANCE.myDao().addGeneralDatas(generalTable);

                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                avilayoutProgress.smoothToHide();
                layoutProgress.setVisibility(View.GONE);

                SharedPreferences.Editor edt = pref.edit();
                edt.putBoolean("general_executed", true);
                edt.commit();
            }
        }.execute();

    }

    public void setActionBar() {

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Read to Learn");
    }

    public void getViews() {
        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

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

        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("general_executed", false)) {

            int id = v.getId();

            Intent intent = new Intent(this, GeneralActivity.class);

            switch (id) {
                case R.id.cardViewKerala:
                    intent.putExtra("status", "10");
                    break;
                case R.id.cardViewIndia:
                    intent.putExtra("status", "3");
                    break;
                case R.id.cardViewWorld:
                    intent.putExtra("status", "7");
                    break;
                case R.id.cardViewHistory:
                    intent.putExtra("status", "2");
                    break;
                case R.id.cardViewGeography:
                    intent.putExtra("status", "1");
                    break;
                case R.id.cardViewSports:
                    intent.putExtra("status", "6");
                    break;
                case R.id.cardViewMaths:
                    intent.putExtra("status", "5");
                    break;
                case R.id.cardViewMovies:
                    intent.putExtra("status", "4");
                    break;
                case R.id.cardViewScience:
                    intent.putExtra("status", "11");
                    break;
                case R.id.cardViewLiterature:
                    intent.putExtra("status", "9");
                    break;
            }

            startActivity(intent);
        }
    }

}
