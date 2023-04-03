package com.bmt_team.logoquiz.database
import android.content.Context
import androidx.room.*
import com.bmt_team.logoquiz.model.Level
import com.bmt_team.logoquiz.model.QuestionLevel


@Database(entities = [Level::class, QuestionLevel::class], version = 1, exportSchema = false)
@TypeConverters(QuestionLevelConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun levelDAO() : LevelDAO
    abstract fun questionLevelDAO() : QuestionLevelDAO
    companion object{
        private var db_instance : AppDatabase?= null

        fun getAppDatabaseInstance(context: Context) : AppDatabase{
            if (db_instance == null){
                db_instance = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                "level")
                    .allowMainThreadQueries()
                    .build()
            }
            return db_instance!!
        }
    }
}