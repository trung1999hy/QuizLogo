package com.bmt_team.logoquiz.detail_question

import androidx.lifecycle.MutableLiveData
import com.bmt_team.logoquiz.base.BaseViewModel
import com.bmt_team.logoquiz.data.DataRepository
import com.bmt_team.logoquiz.di.Common
import com.bmt_team.logoquiz.model.SuggestModel
import java.util.*
import kotlin.collections.ArrayList

class DetailQuestionViewModel(private val repository: DataRepository)  : BaseViewModel() {

    private var suggestSource = ArrayList<String>()

    var suggests = MutableLiveData<ArrayList<SuggestModel>>()

    private val suggest = ArrayList<SuggestModel>()

    fun randomList(answer : CharArray){
        val random = Random()
        suggestSource.clear()
        for(i in 0..2) suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.size)])
        for (i in answer) suggestSource.add(i.toString())
        suggestSource.shuffle()
        suggestSource.forEach {
            suggest.add(SuggestModel(it, false))
            suggests.value = suggest
        }

    }


}