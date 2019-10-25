package com.saneesh.psc_kerala.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

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
