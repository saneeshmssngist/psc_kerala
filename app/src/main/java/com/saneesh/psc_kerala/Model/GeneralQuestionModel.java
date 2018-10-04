package com.saneesh.psc_kerala.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by saNeesH on 2018-07-25.
 */

public class GeneralQuestionModel
{

    @SerializedName("q_number")
    String number;

    String question;

    String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
