package com.saneesh.psc_kerala;

import android.content.Context;

import com.saneesh.psc_kerala.Interfaces.RetrofitCallBack;
import com.saneesh.psc_kerala.Model.BaseUrl;
import com.saneesh.psc_kerala.Model.GeneralModel;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.Model.ResponseResult;
import com.saneesh.psc_kerala.Model.TopicModel;
import com.saneesh.psc_kerala.Retrofit.PscApiClient;
import com.saneesh.psc_kerala.Retrofit.PscApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saNeesH on 2018-05-31.
 */

public class DataManager {


    private static DataManager dataManger = null;
    private PscApiInterface cabApiInterface;
    private Context mContext;


    public static DataManager getDatamanager() {
        if (dataManger == null)
            dataManger = new DataManager();

        return dataManger;
    }

    public void init(Context applicationContext) {
        cabApiInterface = PscApiClient.getAPiClient("").create(PscApiInterface.class);
        mContext = applicationContext;

    }

    public void getBaseUrls(final RetrofitCallBack<ArrayList<BaseUrl>> retrofitCallBack) {

        Call<ResponseResult<ArrayList<BaseUrl>>> resultCall = cabApiInterface.getBaseUrls();

        resultCall.enqueue(new Callback<ResponseResult<ArrayList<BaseUrl>>>() {
            @Override
            public void onResponse(Call<ResponseResult<ArrayList<BaseUrl>>> call, Response<ResponseResult<ArrayList<BaseUrl>>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Error");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<ArrayList<BaseUrl>>> call, Throwable t) {
                retrofitCallBack.Failure("Error");
            }
        });

    }

    public void getDatas(final RetrofitCallBack<ArrayList<QuestionsModel>> retrofitCallBack) {

        Call<ResponseResult<QuestionsModel>> resultCall = cabApiInterface.getGameData("https://raw.githubusercontent.com/saneeshmssngist/psc_datas/master/game_data.txt");

        resultCall.enqueue(new Callback<ResponseResult<QuestionsModel>>() {
            @Override
            public void onResponse(Call<ResponseResult<QuestionsModel>> call, Response<ResponseResult<QuestionsModel>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData().getQuestionsModels());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<QuestionsModel>> call, Throwable t) {
                retrofitCallBack.Failure("Error");
            }
        });

    }

    public void getGeneralDatas(final RetrofitCallBack<ArrayList<GeneralModel>> retrofitCallBack) {

        Call<ResponseResult<GeneralModel>> resultCall = cabApiInterface.getGeneralData("https://raw.githubusercontent.com/saneeshmssngist/psc_datas/master/general_data.txt");

        resultCall.enqueue(new Callback<ResponseResult<GeneralModel>>() {
            @Override
            public void onResponse(Call<ResponseResult<GeneralModel>> call, Response<ResponseResult<GeneralModel>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData().getGeneralModels());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<GeneralModel>> call, Throwable t) {

                retrofitCallBack.Failure("Some error happened !!");
            }
        });
    }

    public void getGeneralDatasCount(String generalType, final RetrofitCallBack<String> retrofitCallBack) {

        Call<ResponseResult> resultCall = cabApiInterface.getGeneralDataCount(generalType);

        resultCall.enqueue(new Callback<ResponseResult>() {
            @Override
            public void onResponse(Call<ResponseResult> call, Response<ResponseResult> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData().toString());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult> call, Throwable t) {

                retrofitCallBack.Failure("Some error happened !!");
            }
        });
    }

    public void getQuestionPaper(final RetrofitCallBack<QuestionsModel> retrofitCallBack) {

        Call<ResponseResult<QuestionsModel>> resultCall = cabApiInterface.getQuestionPapers("https://raw.githubusercontent.com/saneeshmssngist/psc_datas/master/question_papers.txt");

        resultCall.enqueue(new Callback<ResponseResult<QuestionsModel>>() {
            @Override
            public void onResponse(Call<ResponseResult<QuestionsModel>> call, Response<ResponseResult<QuestionsModel>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<QuestionsModel>> call, Throwable t) {

                retrofitCallBack.Failure("Some error happened !!");
            }
        });
    }

    public void getQuestionPaperHomeDatas(final RetrofitCallBack<ArrayList<QuestionsModel>> retrofitCallBack) {

        Call<ResponseResult<QuestionsModel>> resultCall = cabApiInterface.getQuestionPaperHomeData();

        resultCall.enqueue(new Callback<ResponseResult<QuestionsModel>>() {
            @Override
            public void onResponse(Call<ResponseResult<QuestionsModel>> call, Response<ResponseResult<QuestionsModel>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData().getNamesArray());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<QuestionsModel>> call, Throwable t) {

                retrofitCallBack.Failure("Some error happened !!");
            }
        });
    }

    public void getTopicDatas(String topicName, final RetrofitCallBack<TopicModel> retrofitCallBack) {

        Call<ResponseResult<TopicModel>> resultCall = cabApiInterface.getTopicContentDatas(topicName);

        resultCall.enqueue(new Callback<ResponseResult<TopicModel>>() {
            @Override
            public void onResponse(Call<ResponseResult<TopicModel>> call, Response<ResponseResult<TopicModel>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<TopicModel>> call, Throwable t) {

                retrofitCallBack.Failure("Some error happened !!");
            }
        });
    }

    public void getTopicHomeDatas(final RetrofitCallBack<TopicModel> retrofitCallBack) {

        Call<ResponseResult<TopicModel>> resultCall = cabApiInterface.getTopicHomeDatas("https://raw.githubusercontent.com/saneeshmssngist/psc_datas/master/get_topic_names.txt");

        resultCall.enqueue(new Callback<ResponseResult<TopicModel>>() {
            @Override
            public void onResponse(Call<ResponseResult<TopicModel>> call, Response<ResponseResult<TopicModel>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<TopicModel>> call, Throwable t) {

                retrofitCallBack.Failure("Some error happened !!");
            }
        });
    }

    public void getShareLink(final RetrofitCallBack<String> retrofitCallBack) {

        Call<ResponseResult<String>> resultCall = PscApiClient.getAPiClient("server").create(PscApiInterface.class).getShareLink();

        resultCall.enqueue(new Callback<ResponseResult<String>>() {
            @Override
            public void onResponse(Call<ResponseResult<String>> call, Response<ResponseResult<String>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData().toString());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<String>> call, Throwable t) {

                retrofitCallBack.Failure("Some error happened !!");
            }
        });
    }

    public void loginUser(HashMap<String, String> params, final RetrofitCallBack<String> retrofitCallBack) {

        Call<ResponseResult<String>> resultCall = PscApiClient.getAPiClient("server").create(PscApiInterface.class).loginUser(params);

        resultCall.enqueue(new Callback<ResponseResult<String>>() {
            @Override
            public void onResponse(Call<ResponseResult<String>> call, Response<ResponseResult<String>> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("100")) {
                        retrofitCallBack.Success(response.body().getData().toString());
                    } else {
                        retrofitCallBack.Failure(response.body().getStatus());
                    }
                } else {
                    retrofitCallBack.Failure("Some error happened !!");
                }
            }

            @Override
            public void onFailure(Call<ResponseResult<String>> call, Throwable t) {

                retrofitCallBack.Failure("Some error happened !!");
            }
        });
    }

}
