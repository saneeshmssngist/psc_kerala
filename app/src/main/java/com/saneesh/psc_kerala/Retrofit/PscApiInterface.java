package com.saneesh.psc_kerala.Retrofit;

import com.saneesh.psc_kerala.Model.BaseUrl;
import com.saneesh.psc_kerala.Model.DailyQuiz;
import com.saneesh.psc_kerala.Model.GeneralHome;
import com.saneesh.psc_kerala.Model.GeneralModel;
import com.saneesh.psc_kerala.Model.QuestionPaperHome;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.Model.ResponseResult;
import com.saneesh.psc_kerala.Model.TopicModel;
import com.saneesh.psc_kerala.Model.TrollHome;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by saNeesH on 2018-04-17.
 */

public interface PscApiInterface {


    //@POST("game_data.txt?alt=media&token=6934f80b-83da-438f-9a22-f356087f1a30")

    @POST("get_urls.php")
    Call<ResponseResult<ArrayList<BaseUrl>>> getBaseUrls();

    @GET
    Call<ResponseResult<QuestionsModel>> getGameData(@Url String s);

    @GET
    Call<ResponseResult<ArrayList<GeneralHome>>> getGeneralHomeDatas(@Url String s);

    @GET
    Call<ResponseResult<GeneralModel>> getGeneralData(@Url String s);

    @GET
    Call<ResponseResult<TopicModel>> getTopicHomeDatas(@Url String s);

    @GET
    Call<ResponseResult<QuestionPaperHome>> getQuestionPapers(@Url String s);

    @POST("get_share_link.php")
    Call<ResponseResult<String>> getShareLink();

    @FormUrlEncoded()
    @POST("login_user.php")
    Call<ResponseResult<String>> loginUser(@FieldMap HashMap<String, String> params);


    // .......................................................

    @FormUrlEncoded()
    @POST("general_data_count.php")
    Call<ResponseResult> getGeneralDataCount(@Field("general_type") String generalType);

    @GET
    Call<ResponseResult<ArrayList<QuestionPaperHome>>> getQuestionPaperHomeData(@Url String s);

    @FormUrlEncoded()
    @POST("get_topic_content.php")
    Call<ResponseResult<TopicModel>> getTopicContentDatas(@Field("topic_id") String topicId);

    @GET
    Call<ResponseResult<ArrayList<String>>> getTrollsDatas(@Url String trollsHome);

    @GET
    Call<ResponseResult<ArrayList<TrollHome>>> getPscTrollHomeDatas(@Url String masterUrl);

    @GET
    Call<ResponseResult<ArrayList<DailyQuiz>>> getDailyQuizHomeDatas(@Url String url);

    @GET
    Call<ResponseResult<QuestionsModel>> fetchDailyQuizDatas(@Url String quizUrl);


}
