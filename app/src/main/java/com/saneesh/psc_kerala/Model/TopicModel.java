package com.saneesh.psc_kerala.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-07-07.
 */

public class TopicModel
{
    @SerializedName("id")
    String id;

    @SerializedName("topic_name")
    String topicName;

    @SerializedName("image_url")
    String imageUrl;

    @SerializedName("topic_names")
    ArrayList<TopicModel> topicNames;


    @SerializedName("content_id")
    String contentId;

    @SerializedName("topic_content")
    String contents;

    @SerializedName("topic_contents")
    ArrayList<TopicModel> topicContents;


    public ArrayList<TopicModel> getTopicContents() {
        return topicContents;
    }

    public void setTopicContents(ArrayList<TopicModel> topicContents) {
        this.topicContents = topicContents;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<TopicModel> getTopicNames() {
        return topicNames;
    }

    public void setTopicNames(ArrayList<TopicModel> topicNames) {
        this.topicNames = topicNames;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
