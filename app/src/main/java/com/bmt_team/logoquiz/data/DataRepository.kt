package com.bmt_team.logoquiz.data

import com.bmt_team.logoquiz.database.LevelDAO
import com.bmt_team.logoquiz.database.QuestionLevelDAO


class DataRepository(
    private val levelDAO: LevelDAO,
    private val questionLevelDAO: QuestionLevelDAO
    ) {


//    suspend fun insertLevel(level: Level){
//        levelDAO.insertLevel(level)
//    }

//    fun getAllLevel() : LiveData<List<Level>>{
//        return levelDAO.getAllLevel()
//    }

//    fun getListQuestionByLevel(level : String) : LiveData<List<QuestionLevel>>{
//        return questionLevelDAO.getAllQuestionLevel(level)
//    }

//    fun getAllListQuestion() : LiveData<List<QuestionLevel>>{
//        return questionLevelDAO.getAllQuestionLevel()
//    }
//
//    suspend fun insertQuestionLevel(questionLevel: List<QuestionLevel>){
//        questionLevelDAO.insertOrUpdate(questionLevel)
//    }

}