package com.saneesh.psc_kerala.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-07-07.
 */

public class TopicModel implements Serializable {


    @SerializedName("id")
    String id;

    @SerializedName("topic_name")
    String topicName;

    @SerializedName("image_url")
    String imageUrl;

    @SerializedName("contents")
    ArrayList<String> content;

    @SerializedName("topic_content")
    ArrayList<TopicModel> contentsArray;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public ArrayList<TopicModel> getContentsArray() {
        return contentsArray;
    }

    public void setContentsArray(ArrayList<TopicModel> contentsArray) {
        this.contentsArray = contentsArray;
    }
}
