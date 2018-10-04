package com.saneesh.psc_kerala;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.saneesh.psc_kerala.Interfaces.MyDao;
import com.saneesh.psc_kerala.Model.GeneralTable;
import com.saneesh.psc_kerala.Model.QuestionTable1;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.Model.TopicTable;
import com.saneesh.psc_kerala.Model.TopicTableContent;

/**
 * Created by saNeesH on 2018-07-25.
 */

@Database(entities = {GeneralTable.class, TopicTable.class, TopicTableContent.class, QuestionTable1.class, QuizTable.class}, version = 3)
public abstract class RoomDatabaseRoom extends RoomDatabase {

    public abstract MyDao myDao();


}
