package com.saneesh.psc_kerala.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrollHome implements Serializable
{

    String id;

    String url;

    String name;

    String date;

    @SerializedName("content_size")
    String contentSize;

    @SerializedName("first_url")
    String firstUrl;

    public String getContentSize() {
        return contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public void setFirstUrl(String firstUrl) {
        this.firstUrl = firstUrl;
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
}
