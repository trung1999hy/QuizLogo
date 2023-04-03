package com.bmt_team.logoquiz.term

import android.content.Context
import com.bmt_team.logoquiz.base.BaseViewModel


class TermOfUserViewModel : BaseViewModel() {
    fun readText(fileName : String, context: Context) : String{
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}