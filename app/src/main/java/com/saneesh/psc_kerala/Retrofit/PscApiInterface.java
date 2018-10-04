package com.saneesh.psc_kerala.Retrofit;

import com.saneesh.psc_kerala.Model.GeneralModel;
import com.saneesh.psc_kerala.Model.GeneralTable;
import com.saneesh.psc_kerala.Model.QuestionsModel;
import com.saneesh.psc_kerala.Model.ResponseResult;
import com.saneesh.psc_kerala.Model.TopicModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by saNeesH on 2018-04-17.
 */

public interface PscApiInterface {


    @POST("game_data.php")
    Call<ResponseResult<QuestionsModel>> getGameData();

    @POST("sample.php")
    Call<ResponseResult<GeneralModel>>  getGeneralData();

    @FormUrlEncoded()
    @POST("general_data_count.php")
    Call<ResponseResult> getGeneralDataCount(@Field("general_type") String generalType);

    @POST("question_papers.php")
    Call<ResponseResult<QuestionsModel>> getQuestionPapers();

    @POST("question_papers_count.php")
    Call<ResponseResult<QuestionsModel>> getQuestionPaperHomeData();

    @FormUrlEncoded()
    @POST("get_topic_content.php")
    Call<ResponseResult<TopicModel>> getTopicContentDatas(@Field("topic_id") String topicId);

    @POST("get_topic_names.php")
    Call<ResponseResult<TopicModel>> getTopicHomeDatas();

    @POST("get_share_link.php")
    Call<ResponseResult<String>> getShareLink();

    @FormUrlEncoded()
    @POST("login_user.php")
    Call<ResponseResult<String>> loginUser(@FieldMap HashMap<String, String> params);




}
