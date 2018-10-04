package com.saneesh.psc_kerala.Interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.saneesh.psc_kerala.Model.GeneralModel;
import com.saneesh.psc_kerala.Model.GeneralTable;
import com.saneesh.psc_kerala.Model.QuestionTable1;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.Model.TopicTable;
import com.saneesh.psc_kerala.Model.TopicTableContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saNeesH on 2018-07-25.
 */

@Dao
public interface MyDao {


    @Insert
    void addQuizQuestions(QuizTable quizTable);

    @Query("SELECT * FROM `game_all` WHERE `flag` = :paperNameNo ORDER BY random() LIMIT 1")
    QuizTable getQuizQuestions(String paperNameNo);

    //........................................................................................

    @Query("SELECT * FROM `game_all` WHERE `flag` = :category ORDER BY random() LIMIT :range")
    List<QuizTable> getMockData(String category,String range);

    //...........................................................................................

    @Insert
    void addGeneralDatas(GeneralTable generalTable);

    @Query("SELECT * FROM `general_all` WHERE `status` = :status LIMIT :limit,10")
    List<GeneralTable> getGeneralDatas(String status,String limit);

    @Query("SELECT COUNT(`id`) FROM `general_all` WHERE `status` = :status")
    int getGeneralDatasCount(String status);

//................................................................................................

    @Insert
    void addTopicDatas(TopicTable topicTable);

    @Insert
    void addTopicDatasContents(TopicTableContent tableContent);

    @Query("SELECT * FROM `topic_master`")
    List<TopicTable> getTopicHomeDatas();

    @Query("SELECT `content` FROM `topic_datas` td WHERE `contentId` = :topicId")
    List<String> getTopicContents(String topicId);

//..................................................................................................

    @Insert
    void addQuestionPaper1(QuestionTable1 questionTable1);

    @Query("SELECT * FROM `question_table1` WHERE `flag`= :paperNameNo LIMIT :tokenNo,10 ")
    List<QuestionTable1> getQuestionPaper1(int tokenNo, String paperNameNo);


}
