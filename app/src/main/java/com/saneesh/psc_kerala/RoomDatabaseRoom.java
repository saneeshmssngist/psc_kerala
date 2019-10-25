package com.saneesh.psc_kerala;


import com.saneesh.psc_kerala.Interfaces.MyDao;
import com.saneesh.psc_kerala.Model.GeneralTable;
import com.saneesh.psc_kerala.Model.QuestionTable1;
import com.saneesh.psc_kerala.Model.QuizTable;
import com.saneesh.psc_kerala.Model.TopicTable;
import com.saneesh.psc_kerala.Model.TopicTableContent;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by saNeesH on 2018-07-25.
 */

@Database(entities = {GeneralTable.class, TopicTable.class, TopicTableContent.class, QuestionTable1.class, QuizTable.class}, version = 1, exportSchema = false)
public abstract class RoomDatabaseRoom extends RoomDatabase {

    public abstract MyDao myDao();


}
