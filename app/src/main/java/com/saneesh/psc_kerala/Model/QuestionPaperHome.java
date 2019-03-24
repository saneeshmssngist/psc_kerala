package com.saneesh.psc_kerala.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionPaperHome implements Serializable
{

    String id;

    String url;

    String name;

    String date;

    @SerializedName("question_no")
    String questionNo;

    String status;

    @SerializedName("question_paper")
    ArrayList<QuestionsModel> questionsModelsArry;

    public ArrayList<QuestionsModel> getQuestionsModelsArry() {
        return questionsModelsArry;
    }

    public void setQuestionsModelsArry(ArrayList<QuestionsModel> questionsModelsArry) {
        this.questionsModelsArry = questionsModelsArry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
