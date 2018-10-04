package com.saneesh.psc_kerala.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-07-25.
 */

@Entity(tableName = "general_all")
public class GeneralTable
{

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo( name = "id")
    int id;

    String question;

    String answer;

    String status;

    public GeneralTable() {
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getStatus() {
        return status;
    }
}
