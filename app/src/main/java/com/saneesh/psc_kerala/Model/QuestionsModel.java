package com.saneesh.psc_kerala.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.http.Field;

/**
 * Created by saNeesH on 2018-06-02.
 */

public class QuestionsModel implements Serializable
{

    @SerializedName("question_no")
    String questionNo;

    String question;

    String option1;

    String option2;

    String option3;

    String option4;

    String answer;

    String status;

    @SerializedName("quiz_datas")
    ArrayList<QuestionsModel> dailyQuizDatas;

    @SerializedName("question_paper1")
    ArrayList<QuestionsModel> questionsModels1;

    @SerializedName("question_paper2")
    ArrayList<QuestionsModel> questionsModels2;

    @SerializedName("question_paper3")
    ArrayList<QuestionsModel> questionsModels3;

    @SerializedName("name")
    String qName;

    @SerializedName("date")
    String qDate;

    @SerializedName("number")
    String qNumber;

    @SerializedName("questions")
    ArrayList<QuestionsModel> questionsModels;

    @SerializedName("question_papers")
    ArrayList<QuestionsModel> namesArray;

    public ArrayList<QuestionsModel> getDailyQuizDatas() {
        return dailyQuizDatas;
    }

    public void setDailyQuizDatas(ArrayList<QuestionsModel> dailyQuizDatas) {
        this.dailyQuizDatas = dailyQuizDatas;
    }

    public ArrayList<QuestionsModel> getQuestionsModels1() {
        return questionsModels1;
    }

    public void setQuestionsModels1(ArrayList<QuestionsModel> questionsModels1) {
        this.questionsModels1 = questionsModels1;
    }

    public ArrayList<QuestionsModel> getQuestionsModels2() {
        return questionsModels2;
    }

    public void setQuestionsModels2(ArrayList<QuestionsModel> questionsModels2) {
        this.questionsModels2 = questionsModels2;
    }

    public ArrayList<QuestionsModel> getQuestionsModels3() {
        return questionsModels3;
    }

    public void setQuestionsModels3(ArrayList<QuestionsModel> questionsModels3) {
        this.questionsModels3 = questionsModels3;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getqDate() {
        return qDate;
    }

    public void setqDate(String qDate) {
        this.qDate = qDate;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }

    public String getqNumber() {
        return qNumber;
    }

    public void setqNumber(String qNumber) {
        this.qNumber = qNumber;
    }

    public ArrayList<QuestionsModel> getNamesArray() {
        return namesArray;
    }

    public void setNamesArray(ArrayList<QuestionsModel> namesArray) {
        this.namesArray = namesArray;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<QuestionsModel> getQuestionsModels() {
        return questionsModels;
    }

    public void setQuestionsModels(ArrayList<QuestionsModel> questionsModels) {
        this.questionsModels = questionsModels;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
