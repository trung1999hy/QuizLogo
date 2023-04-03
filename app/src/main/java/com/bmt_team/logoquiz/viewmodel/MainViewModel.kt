package com.bmt_team.logoquiz.viewmodel


import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.bmt_team.logoquiz.base.BaseViewModel
import com.bmt_team.logoquiz.data.CreateReadJSON
import com.bmt_team.logoquiz.data.DataRepository
import com.bmt_team.logoquiz.model.Level
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class MainViewModel(private val repository: DataRepository) : BaseViewModel() {

    private var listQuiz = ArrayList<Level>()
    val listQuizLiveData = MutableLiveData<List<Level>>()
    fun getAllQuiz(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val obj = CreateReadJSON.getJSONDataFromAsset(context, "Level.json")?.let { JSONObject(it) }
            val gson = Gson()
            val jsonArr = obj?.getJSONArray("level")
            listQuiz = gson.fromJson(jsonArr.toString(), object : TypeToken<ArrayList<Level>>() {}.type)
            withContext(Dispatchers.Main) {
                listQuizLiveData.value = listQuiz
            }
        }
    }

}

