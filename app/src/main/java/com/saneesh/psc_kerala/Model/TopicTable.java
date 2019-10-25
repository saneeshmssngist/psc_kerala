package com.saneesh.psc_kerala.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

/**
 * Created by saNeesH on 2018-07-26.
 */

@Entity(tableName = "topic_master")
public class TopicTable
{

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    String topicTitle;

    String Url;

    public TopicTable() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
