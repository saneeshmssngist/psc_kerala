package com.saneesh.psc_kerala.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.saneesh.psc_kerala.Adapters.QuestionHomeAdapter;
import com.saneesh.psc_kerala.DataManager;
import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.QuestionTable1;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import static com.saneesh.psc_kerala.Base.setStatusBarGradiant;

public class QuestionPaperHomeActivity extends AppCompatActivity implements QuestionHomeAdapter.QuestionsInterface{


    private LinearLayout layoutProgress;
    private AVLoadingIndicatorView avilayoutProgress;

    QuestionHomeAdapter questionHomeAdapter;
    RecyclerView recyclerView;
    private Toolbar toolBar;

    private SharedPreferences pref;
    private AdView adMobView;
    private InterstitialAd interstitialAd;

    private ArrayList<QuestionsModel> questionsModelArray;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper_home);

        context = this;
        setStatusBarGradiant(this);
        getViews();
        setActionBar();
        initControl();

        setUpAdmob();
    }

    private void setUpAdmob() {

        //admob sync..
        MobileAds.initialize(this,getResources().getString(R.string.APPID));

        adMobView = (AdView) findViewById(R.id.adMobView);
        adMobView.loadAd(new AdRequest.Builder().build());

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.INTERTITIAID));
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }

    public void setActionBar() {

        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Question Papers");

    }

    private void getViews() {

        layoutProgress = findViewById(R.id.layoutProgress);
        avilayoutProgress = findViewById(R.id.avilayoutProgress);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void initControl() {

        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("questions_executed", false)) {

            setDatas();

        } else {

            setDatas();

            layoutProgress.setVisibility(View.VISIBLE);
            avilayoutProgress.show();

            DataManager.getDatamanager().getQuestionPaper(new RetrofitCallBack<QuestionsModel>() {
                @Override
                public void Success(final QuestionsModel questionsModel) {

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {


                            ArrayList<QuestionsModel> questionsModels1 = questionsModel.getQuestionsModels1();

                            for (int i = 0; i < questionsModels1.size(); i++) {
                                QuestionTable1 model = new QuestionTable1();
                                model.setQuestion(questionsModels1.get(i).getQuestion());
                                model.setOption1(questionsModels1.get(i).getOption1());
                                model.setOption2(questionsModels1.get(i).getOption2());
                                model.setOption3(questionsModels1.get(i).getOption3());
                                model.setOption4(questionsModels1.get(i).getOption4());
                                model.setAnswer(questionsModels1.get(i).getAnswer());
                                model.setFlag("1");

                                HomeActivity.INSTANCE.myDao().addQuestionPaper1(model);
                            }

                            ArrayList<QuestionsModel> questionsModels2 = questionsModel.getQuestionsModels2();

                            for (int i = 0; i < questionsModels2.size(); i++) {
                                QuestionTable1 model = new QuestionTable1();
                                model.setQuestion(questionsModels2.get(i).getQuestion());
                                model.setOption1(questionsModels2.get(i).getOption1());
                                model.setOption2(questionsModels2.get(i).getOption2());
                                model.setOption3(questionsModels2.get(i).getOption3());
                                model.setOption4(questionsModels2.get(i).getOption4());
                                model.setAnswer(questionsModels2.get(i).getAnswer());
                                model.setFlag("2");

                                HomeActivity.INSTANCE.myDao().addQuestionPaper1(model);
                            }

                            ArrayList<QuestionsModel> questionsModels3 = questionsModel.getQuestionsModels3();

                            for (int i = 0; i < questionsModels3.size(); i++) {
                                QuestionTable1 model = new QuestionTable1();
                                model.setQuestion(questionsModels3.get(i).getQuestion());
                                model.setOption1(questionsModels3.get(i).getOption1());
                                model.setOption2(questionsModels3.get(i).getOption2());
                                model.setOption3(questionsModels3.get(i).getOption3());
                                model.setOption4(questionsModels3.get(i).getOption4());
                                model.setAnswer(questionsModels3.get(i).getAnswer());
                                model.setFlag("3");

                                HomeActivity.INSTANCE.myDao().addQuestionPaper1(model);
                            }

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {

                            avilayoutProgress.smoothToHide();
                            layoutProgress.setVisibility(View.GONE);

                            SharedPreferences.Editor edt = pref.edit();
                            edt.putBoolean("questions_executed", true);
                            edt.commit();

                        }
                    }.execute();

                }

                @Override
                public void Failure(String error) {

                    Toast.makeText(QuestionPaperHomeActivity.this,"Network connection needed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void setDatas() {

        questionsModelArray = new ArrayList<>();

        QuestionsModel questionsModel1 = new QuestionsModel();
        questionsModel1.setqName("എൽ ഡി ക്ലാർക്ക് ");
        questionsModel1.setqDate("10-02-18");
        questionsModel1.setqNumber("100");
        questionsModel1.setStatus("1");

        QuestionsModel questionsModel2 = new QuestionsModel();
        questionsModel2.setqName("വനിതാ പോലീസ് കോൺസ്റ്റബിൾ ");
        questionsModel2.setqDate("10-04-18");
        questionsModel2.setqNumber("100");
        questionsModel2.setStatus("1");

        QuestionsModel questionsModel3 = new QuestionsModel();
        questionsModel3.setqName(" വനിതാ പോലീസ് കോൺസ്റ്റബിൾ കെ എ പി -1");
        questionsModel3.setqDate("");
        questionsModel3.setqNumber("100");
        questionsModel3.setStatus("1");

        QuestionsModel questionsModel4 = new QuestionsModel();
        questionsModel4.setqName("എൽ ഡി ക്ലാർക്ക്");
        questionsModel4.setqDate("");
        questionsModel4.setqNumber("100");
        questionsModel4.setStatus("0");

        QuestionsModel questionsModel5 = new QuestionsModel();
        questionsModel5.setqName("എൽ ഡി ക്ലാർക്ക്");
        questionsModel5.setqDate("");
        questionsModel5.setqNumber("100");
        questionsModel5.setStatus("0");

        questionsModelArray.add(questionsModel1);
        questionsModelArray.add(questionsModel2);
        questionsModelArray.add(questionsModel3);
        questionsModelArray.add(questionsModel4);
        questionsModelArray.add(questionsModel5);

        questionHomeAdapter = new QuestionHomeAdapter(this,context, questionsModelArray);
        recyclerView.setAdapter(questionHomeAdapter);

    }

    @Override
    public void questtionTapped(final int adapterPosition)
    {

        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if (pref.getBoolean("questions_executed", false)) {

            final QuestionsModel questionsModel = questionsModelArray.get(adapterPosition);

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();

                    Intent intent = new Intent(QuestionPaperHomeActivity.this, QuestionPaperActivity.class);
                    intent.putExtra("paperNameNo", String.valueOf(adapterPosition + 1));
                    intent.putExtra("paperName", questionsModel.getqName());
                    startActivity(intent);

                    interstitialAd.loadAd(new AdRequest.Builder().build());

                }
            });

            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {

                Intent intent = new Intent(QuestionPaperHomeActivity.this, QuestionPaperActivity.class);
                intent.putExtra("paperNameNo", String.valueOf(adapterPosition + 1));
                intent.putExtra("paperName", questionsModel.getqName());
                startActivity(intent);
            }
        }

    }
}


