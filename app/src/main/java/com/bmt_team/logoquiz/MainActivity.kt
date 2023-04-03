package com.bmt_team.logoquiz


import android.content.Intent
import android.os.Bundle
import com.bmt_team.logoquiz.adapter.HomeAdapter
import com.bmt_team.logoquiz.base.BaseActivity
import com.bmt_team.logoquiz.base.initViewModel
import com.bmt_team.logoquiz.data.CreateReadJSON
import com.bmt_team.logoquiz.database.AppDatabase
import com.bmt_team.logoquiz.di.Constant
import com.bmt_team.logoquiz.inapp.PurchaseInAppActivity
import com.bmt_team.logoquiz.list_question.ListQuestionActivity
import com.bmt_team.logoquiz.model.Level
import com.bmt_team.logoquiz.model.QuestionLevel
import com.bmt_team.logoquiz.sharePreference.SharePreferences
import com.bmt_team.logoquiz.viewmodel.MainViewModel
import com.example.logoquiz.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), HomeAdapter.OnClickItem {
    private var mListLevel = ArrayList<Level>()
    private val mHomeAdapter = HomeAdapter()

    private val prefs = SharePreferences()

    var level :Level?= null

    override fun bindView() {
        img_menu.setOnClickListener { startActivity(Intent(this, PurchaseInAppActivity::class.java)) }

    }

    override fun observeData() {
        val receiverLevel = prefs.getString("id_level")
        val receiverNumberQuestionDone = prefs.getInt("count")
        for (i in mListLevel.indices) {
            if (mListLevel[i].level == receiverLevel) {
                mListLevel[i].numberQuestionDone = receiverNumberQuestionDone.toString()
                if (receiverNumberQuestionDone != null) {
                    mListLevel[i].process =
                        (((receiverNumberQuestionDone.toFloat() / 20) * 100 * 100).roundToInt() / 100).toString()
                }
                mHomeAdapter.notifyItemChanged(i)
                AppDatabase.getAppDatabaseInstance(applicationContext).levelDAO()
                    .update(mListLevel[i])

            }
        }
        val obj = JSONObject(CreateReadJSON.getJSONDataFromAsset(this, "Level.json"))
        val jsonArr = obj.getJSONArray("level")
        for (i in 0 until jsonArr.length()) {
            val jsonObs: JSONObject? = jsonArr.getJSONObject(i)
            val id: Int = jsonObs!!.getInt("id")
            val image: String = jsonObs.getString("image")
            val level1: String = jsonObs.getString("level")
            val numberQuestion: String = jsonObs.getString("numberQuestion")
            val numberQuestionDone: String = jsonObs.getString("numberQuestionDone")
            val process: String = jsonObs.getString("process")
            val listQuestionLevel = jsonObs.getJSONArray("listQuestionLevel")
            val list: ArrayList<QuestionLevel> =
                Gson().fromJson(
                    listQuestionLevel.toString(),
                    object : TypeToken<ArrayList<QuestionLevel?>?>() {}.type
                )
            val level = Level(id, image, level1, numberQuestion, numberQuestionDone, process, list)
            AppDatabase.getAppDatabaseInstance(applicationContext).levelDAO().insertLevel(level)
            mListLevel = (AppDatabase.getAppDatabaseInstance(applicationContext).levelDAO()
                .getAllLevel()) as ArrayList<Level>
            mHomeAdapter.submitList(mListLevel)
            rcv_home.adapter = mHomeAdapter
            mHomeAdapter.listener = this
        }
    }


    override fun layoutId(): Int = com.example.logoquiz.R.layout.activity_main
    override fun onClickLevel(position: Int, level: Level) {
        val item = Gson().toJson(mListLevel[position])
        val bundle = Bundle()
        bundle.putSerializable(
            Constant.LEVEL,
            level
        )
        startActivityForResult(Intent(this, ListQuestionActivity::class.java).apply {
            putExtra(Constant.DETAIL_LEVEL, item)
            putExtras(bundle)
        }, Constant.QUESTION_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.QUESTION_REQUEST && resultCode == RESULT_OK){
            val receiverLevel = data?.extras?.getString(Constant.ID_LEVEL)
            val receiverNumberQuestionDone = data?.extras?.getInt(Constant.QUESTION_DONE_NUMBERS)
            for (i in mListLevel.indices){
               if (mListLevel[i].level == receiverLevel){
                   mListLevel[i].numberQuestionDone = receiverNumberQuestionDone.toString()
                   if (receiverNumberQuestionDone != null) {
                      mListLevel[i].process = (((receiverNumberQuestionDone.toFloat() / 20) * 100 *100).roundToInt() /100).toString()
                   }
                   mHomeAdapter.notifyItemChanged(i)
                   AppDatabase.getAppDatabaseInstance(applicationContext).levelDAO().update(mListLevel[i])

               }
            }
        }
    }
    override fun onCreateViewModel(): MainViewModel = initViewModel()

}