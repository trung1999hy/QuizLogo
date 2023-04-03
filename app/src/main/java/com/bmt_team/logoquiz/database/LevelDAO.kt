package com.bmt_team.logoquiz.database

import androidx.room.*
import com.bmt_team.logoquiz.model.Level

@Dao
interface LevelDAO : BaseDAO<Level>{
     @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertLevel(level: Level)

     @Query("SELECT * FROM Level")
     fun getAllLevel() : List<Level>

     @Query("UPDATE Level set numberQuestionDone =:count where level =:level")
     fun update(count : String, level : String)

//     @Query("update Level set numberQuestionDone =:done where level =:level and id =:id")
//     fun update(done : String, level : String, id : Int)
}