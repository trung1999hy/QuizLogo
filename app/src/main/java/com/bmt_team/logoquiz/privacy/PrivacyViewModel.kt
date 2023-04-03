package com.bmt_team.logoquiz.privacy

import android.content.Context
import com.bmt_team.logoquiz.base.BaseViewModel


class PrivacyViewModel  : BaseViewModel() {

    fun readText(fileName : String, context: Context) : String{
       return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}