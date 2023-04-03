package com.bmt_team.logoquiz.database

import androidx.room.*
import com.bmt_team.logoquiz.model.QuestionLevel

@Dao
interface QuestionLevelDAO  : BaseDAO<QuestionLevel>{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun onInsertOrUpdate(question: List<QuestionLevel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateQuestion(question: QuestionLevel)

    @Query("update QuestionLevel set isDone = :isDone where question = :id and uid =:uid")
    fun update(isDone: Boolean, id: Int, uid : Int)

    @Query("SELECT * FROM QuestionLevel WHERE levelDetail = :levelDetail")
    fun getAllQuestionLevel(levelDetail : String): List<QuestionLevel>

    @Query("SELECT * FROM QuestionLevel")
    fun getAllQuestionLevel() : List<QuestionLevel>


}