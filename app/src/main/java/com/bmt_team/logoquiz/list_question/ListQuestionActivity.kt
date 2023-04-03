package com.bmt_team.logoquiz.list_question

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import com.bmt_team.logoquiz.adapter.ListQuestionAdapter
import com.bmt_team.logoquiz.base.BaseActivity
import com.bmt_team.logoquiz.base.initViewModel
import com.bmt_team.logoquiz.database.AppDatabase
import com.bmt_team.logoquiz.detail_question.DetailQuestionActivity
import com.bmt_team.logoquiz.di.Constant
import com.bmt_team.logoquiz.dialog.CompleteDialogFragment
import com.bmt_team.logoquiz.dialog.ContinueAnswerDialogFragment
import com.bmt_team.logoquiz.model.Level
import com.bmt_team.logoquiz.model.QuestionLevel
import com.bmt_team.logoquiz.sharePreference.SharePreferences
import com.example.logoquiz.R
import com.example.logoquiz.databinding.ActivityListQuestionBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_question.*

class ListQuestionActivity : BaseActivity<ListQuestionViewModel, ActivityListQuestionBinding>(),
    ListQuestionAdapter.OnCLickItem, ContinueAnswerDialogFragment.OnClickContinueAnswer{
    private var level: Level? = null
    private val mListQuestionAdapter = ListQuestionAdapter()
    private var count = 0

    private var index = 0
    private var listQuestionLevel = ArrayList<QuestionLevel>()

    private val continueDialog = ContinueAnswerDialogFragment()

    private val pref = SharePreferences()
    override fun layoutId(): Int = R.layout.activity_list_question

    override fun bindView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ic_back.setOnClickListener { onBackPressed() }
        mListQuestionAdapter.listener = this
        continueDialog.listenerContinue = this

    }

    override fun observeData() {
        val valueObs = intent.getStringExtra(Constant.DETAIL_LEVEL)
        level = intent.extras?.getSerializable(Constant.LEVEL) as? Level?
        if (level?.level != null) {
            title_number_level.text = level?.level.toString()
        }
        val arrListQuestion = Gson().fromJson(valueObs, Level::class.java)

       AppDatabase.getAppDatabaseInstance(applicationContext).questionLevelDAO().onInsertOrUpdate(arrListQuestion.listQuestionLevel)
        listQuestionLevel =
            AppDatabase.getAppDatabaseInstance(applicationContext).questionLevelDAO()
                .getAllQuestionLevel(level!!.level) as ArrayList<QuestionLevel>
        mListQuestionAdapter.submitList(
           listQuestionLevel
        )
        rcv_list_question.adapter = mListQuestionAdapter
        for (i in listQuestionLevel.indices){
            if (listQuestionLevel[i].isDone){
                if(count < 20){
                    count++
                }
            }
        }
       // AppDatabase.getAppDatabaseInstance(applicationContext).levelDAO().update(co)
        pref.save("id_level", level!!.level)
        pref.save("count", count)
        val intent = Intent()
        val sendData = Bundle()
        sendData.putString(Constant.ID_LEVEL, level!!.level)
        sendData.putInt(Constant.QUESTION_DONE_NUMBERS, count)
        sendData.putInt(Constant.ID, level!!.id)
        intent.putExtras(sendData)
        setResult(RESULT_OK, intent)
        //check index
        if (listQuestionLevel.all { it.isDone }){
            val completeDialog = CompleteDialogFragment.getInstanceComplete(level!!.id)
            completeDialog.show(supportFragmentManager, "")
        }


    }

    override fun onClick(questionLevel: QuestionLevel, position : Int) {
        index = position
        val mp = MediaPlayer.create(this, R.raw.btn_click_sound)
        mp.start()
        mp.setOnCompletionListener { sound ->
            sound.release()
        }
        if (questionLevel.isDone){
            continueDialog.show(supportFragmentManager, "")
            continueDialog.isCancelable = false
        }else{
                    val bundle = Bundle()
        bundle.putSerializable(Constant.QUESTION_LEVEL, questionLevel)
        bundle.putInt(Constant.POSITION, position)
        startActivityForResult(Intent(this, DetailQuestionActivity::class.java).apply {
            putExtras(bundle)
            putExtra("listQuestion", listQuestionLevel)
        }, Constant.QUESTION_REQUEST)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.QUESTION_REQUEST && resultCode == 123) {
            val receiverId = data?.extras?.getInt("id")
            val receiverCheckDone = data?.extras?.getBoolean("check")
            val receiverLevelDetail = data?.extras?.getInt("uid")
            for (i in listQuestionLevel.indices) {
                if (listQuestionLevel[i].question == receiverId && listQuestionLevel[i].uid == receiverLevelDetail) {
                    listQuestionLevel[i].isDone = receiverCheckDone!!
                    mListQuestionAdapter.notifyItemChanged(i)
                    AppDatabase.getAppDatabaseInstance(applicationContext).questionLevelDAO()
                        .update(receiverCheckDone, receiverId, receiverLevelDetail)
                }

            }

            count = 0
            for (i in listQuestionLevel.indices) {
                if (listQuestionLevel[i].isDone) {
                    if (count < 20) {
                        count++
                    }
                }
            }
        }
    }


    override fun onCreateViewModel(): ListQuestionViewModel = initViewModel()
    override fun onBackPressed() {
        val intent = Intent()
        val sendData = Bundle()
        sendData.putString(Constant.ID_LEVEL, level!!.level)
        sendData.putInt(Constant.QUESTION_DONE_NUMBERS, count)
        sendData.putInt(Constant.ID, level!!.id)
        intent.putExtras(sendData)
        setResult(RESULT_OK, intent)
        finish()
    }
    override fun onResume() {
        super.onResume()
        listQuestionLevel =
            AppDatabase.getAppDatabaseInstance(applicationContext).questionLevelDAO()
                .getAllQuestionLevel(level!!.level) as ArrayList<QuestionLevel>
        mListQuestionAdapter.submitList(
            listQuestionLevel
        )
        mListQuestionAdapter.notifyDataSetChanged()
        count = 0
        for (i in listQuestionLevel.indices) {
            if (listQuestionLevel[i].isDone) {
                if (count < 20) {
                    count++
                }
            }
        }
        //check index
        if (listQuestionLevel.all { it.isDone }){
            val completeDialog = CompleteDialogFragment.getInstanceComplete(level!!.id)
            completeDialog.show(supportFragmentManager, "")
        }
    }

    override fun onClickYesContinue() {
        val bundle = Bundle()
        bundle.putSerializable(Constant.QUESTION_LEVEL, listQuestionLevel[index])
        bundle.putInt(Constant.POSITION, index)
        startActivityForResult(Intent(this, DetailQuestionActivity::class.java).apply {
            putExtras(bundle)
            putExtra("listQuestion", listQuestionLevel)
        }, Constant.QUESTION_REQUEST)
        continueDialog.dismiss()
    }

    override fun onClickNoContinue() {
        continueDialog.dismiss()
    }

}


