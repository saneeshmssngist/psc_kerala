package com.saneesh.psc_kerala.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-06-04.
 */

public class GeneralModel
{
    String id;

    String question;

    String answer;

    String status;

    @SerializedName("general_datas")
    ArrayList<GeneralModel> generalModels;

    public ArrayList<GeneralModel> getGeneralModels() {
        return generalModels;
    }

    public void setGeneralModels(ArrayList<GeneralModel> generalModels) {
        this.generalModels = generalModels;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
