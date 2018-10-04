package com.saneesh.psc_kerala.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by saNeesH on 2018-07-28.
 */

@Entity(tableName = "topic_datas")
public class TopicTableContent
{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    int contentId;

    String content;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }
}
