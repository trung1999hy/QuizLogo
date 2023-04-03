package com.bmt_team.logoquiz.detail_question
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import com.bmt_team.logoquiz.adapter.GridViewAnswerAdapter
import com.bmt_team.logoquiz.adapter.GridViewSuggestAdapter
import com.bmt_team.logoquiz.base.App
import com.bmt_team.logoquiz.base.BaseActivity
import com.bmt_team.logoquiz.base.initViewModel
import com.bmt_team.logoquiz.database.AppDatabase
import com.bmt_team.logoquiz.di.Constant
import com.bmt_team.logoquiz.dialog.*
import com.bmt_team.logoquiz.inapp.PurchaseInAppActivity
import com.bmt_team.logoquiz.model.AnswerModel
import com.bmt_team.logoquiz.model.QuestionLevel
import com.bmt_team.logoquiz.model.SuggestModel
import com.bmt_team.logoquiz.sharePreference.SharePreferences
import com.bumptech.glide.Glide
import com.example.logoquiz.R
import com.example.logoquiz.databinding.ActivityDetailQuestionBinding
import kotlinx.android.synthetic.main.activity_detail_question.*
import kotlin.collections.ArrayList


class DetailQuestionActivity :
    BaseActivity<DetailQuestionViewModel, ActivityDetailQuestionBinding>(),
    GridViewSuggestAdapter.OnClickItemSuggest, SuccessDialogFragment.OnClickButton,
    ConfirmDialogFragment.OnClickConfirm, FailedDialogFragment.OnClickFailed,
    UnLockDialogFragment.OnClickUnlock, ReleaseDialogFragment.OnClickRelease {
    var questionLevel: QuestionLevel? = null
    var count = 0
    var coin = 0
    var result = ArrayList<String>()

    private val suggest = ArrayList<SuggestModel>()
    private var answerList = ArrayList<AnswerModel>()
    private val suggestAdapter = GridViewSuggestAdapter()
    private val answerAdapter = GridViewAnswerAdapter()

    private val resultList = ArrayList<String>()

    private val confirmDialog = ConfirmDialogFragment()

    private val failedDialog = FailedDialogFragment()

    private val unlockDialog = UnLockDialogFragment()

    lateinit var answer: CharArray

    private val pref = SharePreferences()

    private var listQuestion = ArrayList<QuestionLevel>()

    private var index: Int? = null

    private var listItemRelease = ArrayList<SuggestModel>()


    override fun layoutId(): Int = R.layout.activity_detail_question

    override fun bindView() {

        suggestAdapter.listener = this
        confirmDialog.listenerConfirm = this
        failedDialog.listenerAgain = this
        unlockDialog.listenerUnLock = this

        ic_back_home.setOnClickListener { onBackPressed() }
        plus_coin.setOnClickListener { startActivity(Intent(this, PurchaseInAppActivity::class.java)) }

        //unlock
        btn_unlock.setOnClickListener {
            unlockDialog.show(supportFragmentManager, "")
            unlockDialog.isCancelable = false
        }
        //suggest
        btn_suggest.setOnClickListener {
            confirmDialog.show(supportFragmentManager, "")
            confirmDialog.isCancelable = false
        }
        //delete all character
        btn_delete.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(Constant.QUESTION_LEVEL, listQuestion[index!!]) //cái này à
            bundle.putInt(Constant.POSITION, index!!)
            startActivity(Intent(this, DetailQuestionActivity::class.java).apply {
                putExtras(bundle)
                putExtra("listQuestion", listQuestion)

            })
            //clear list character old
            result.clear()
            val mp = MediaPlayer.create(this, R.raw.letter_removed)
            mp.start()
            mp.setOnCompletionListener { sound ->
                sound.release()
            }
            finish()
        }


    }

    override fun observeData() {
        questionLevel = intent?.extras?.getSerializable(Constant.QUESTION_LEVEL) as? QuestionLevel?
        index = intent?.extras?.getInt(Constant.POSITION)
        listQuestion = intent?.getSerializableExtra("listQuestion") as ArrayList<QuestionLevel>
        coin = App.getInstance().getPreference().getValueCoin()
        //get model questionLevel
        tv_coin.text = coin.toString()
        if (listQuestion[index!!].imageQuestion != null) {
            Glide.with(this)
                .load("file:///android_asset/image/${listQuestion[index!!!!].imageQuestion}")
                .into(
                    img_question_detail
                )
        }
        if (listQuestion[index!!].question != null) questionNumber.text =
            listQuestion[index!!].question.toString()
        answer = listQuestion[index!!].name.replace(" ", "")
            .substring(listQuestion[index!!].name.lastIndexOf("/") + 1).toCharArray()
        answer.forEach {
            resultList.add(it.toString())
        }
        //list character quiz
        viewModel.randomList(answer)
        viewModel.suggests.observe(this) { list ->
            suggest.addAll(list)
            listItemRelease.addAll(list)
            suggestAdapter.submitList(suggest)
            rcv_question.adapter = suggestAdapter
        }

        //list answer null
        answerAdapter.submitList(setUpNullList())
        rcv_answer.adapter = answerAdapter


    }


    private fun setUpNullList(): MutableList<AnswerModel> {
        for (i in answer.indices) {
            answerList.add(AnswerModel(" ", false))
        }
        return answerList
    }

    override fun onClickSuggest(position: Int, suggest: SuggestModel) {
        if (count < answer.size) {
            val mp = MediaPlayer.create(this, R.raw.click)
            mp.start()
            mp.setOnCompletionListener { sound ->
                sound.release()
            }
            suggestAdapter.currentList[position].apply {
                isCheck = true
                if (labelSuggest != "") result.add(labelSuggest)
                try {
                    if (labelSuggest != "") {
                        answerList[count] = AnswerModel(result[count], false)
                        answerAdapter.notifyItemChanged(count)
                        count++

                    }
                    labelSuggest = ""
                } catch (e: Exception) {
                    print(e.printStackTrace())
                }
            }
            suggestAdapter.notifyItemChanged(position)
            handleAnswer()
        }
    }

    private fun handleAnswer() {
        if (count == resultList.size) {
            if (answerList[count - 1].label != " ") {
                if (result == resultList) {
                    if (questionLevel!!.isDone) {
                        val releaseDialog =
                            ReleaseDialogFragment.newInstance(
                                questionLevel!!.name,
                                questionLevel!!.question
                            )
                        releaseDialog.listenerRelease = this
                        releaseDialog.show(supportFragmentManager, "")
                        releaseDialog.isCancelable = false
                        val mp = MediaPlayer.create(this, R.raw.right_answer)
                        mp.start()
                        mp.setOnCompletionListener { sound ->
                            sound.release()
                        }
                        listQuestion[index!!].isDone =
                            true
                        pref.save("id", listQuestion[index!!].question)
                        pref.save("check", listQuestion[index!!].isDone)
                        pref.save("uid", listQuestion[index!!].uid)
                        val intent = Intent()
                        intent.putExtra("id", listQuestion[index!!].question)
                        intent.putExtra("check", listQuestion[index!!].isDone)
                        intent.putExtra("uid", listQuestion[index!!].uid)
                        setResult(123, intent)
                    } else {
                        val successDialog =
                            SuccessDialogFragment.newInstance(
                                questionLevel!!.name,
                                questionLevel!!.question,
                                50
                            )
                        successDialog.listener = this
                        successDialog.show(supportFragmentManager, "")
                        successDialog.isCancelable = false
                        val mp = MediaPlayer.create(this, R.raw.right_answer)
                        mp.start()
                        mp.setOnCompletionListener { sound ->
                            sound.release()
                        }
                        listQuestion[index!!].isDone =
                            true
                        //plus 50 coin when user submit true
                        coin += 50
                        pref.setValueCoin(coin)
                        AppDatabase.getAppDatabaseInstance(applicationContext)
                            .questionLevelDAO()
                            .update(
                                listQuestion[index!!].isDone,
                                listQuestion[index!!].question,
                                listQuestion[index!!].uid
                            )
                        val intent = Intent()
                        intent.putExtra("id", listQuestion[index!!].question)
                        intent.putExtra("check", listQuestion[index!!].isDone)
                        intent.putExtra("uid", listQuestion[index!!].uid)
                        setResult(123, intent)
                    }

                } else {
                    failedDialog.show(supportFragmentManager, "")
                    failedDialog.isCancelable = false
                    val mp = MediaPlayer.create(this, R.raw.wrong_answer)
                    mp.start()
                    mp.setOnCompletionListener { sound ->
                        sound.release()
                    }
                }
            }
        }
    }

    //back home
    override fun onClickHome() {
        val intent = Intent()
        listQuestion[index!!].isDone =
            true
        pref.save("id", listQuestion[index!!].question)
        pref.save("check", listQuestion[index!!].isDone)
        pref.save("uid", listQuestion[index!!].uid)
        AppDatabase.getAppDatabaseInstance(applicationContext)
            .questionLevelDAO()
            .update(
                listQuestion[index!!].isDone,
                listQuestion[index!!].question,
                listQuestion[index!!].uid
            )
        finish()
    }

    //next question
    override fun onClickNext() {
        AppDatabase.getAppDatabaseInstance(applicationContext)
            .questionLevelDAO()
            .update(
                listQuestion[index!!].isDone,
                listQuestion[index!!].question,
                listQuestion[index!!].uid
            )
        val successDialog =
            SuccessDialogFragment.newInstance(questionLevel!!.name, questionLevel!!.question, 50)
        successDialog.listener = this
        val int = index!!?.plus(1)
        val bundle = Bundle()
        bundle.putSerializable(Constant.QUESTION_LEVEL, listQuestion[int!!]) //cái này à
        bundle.putInt(Constant.POSITION, int)
        startActivity(Intent(this, DetailQuestionActivity::class.java).apply {
            putExtras(bundle)
            putExtra("listQuestion", listQuestion)

        })

        finish()

    }

    //not confirm
    override fun onClickNo() {
        confirmDialog.dismiss()
    }

    //confirm suggest
    override fun onClickYes() {
        if (coin >= 30) {
            for ((index, value) in suggest.withIndex()) {
                if (answer.none { it.toString() == value.labelSuggest }) {
                    suggest[index] = SuggestModel(" ", true)
                    suggestAdapter.notifyItemChanged(index)
                }
            }
            confirmDialog.dismiss()
            val mp = MediaPlayer.create(this, R.raw.explosion_sound)
            mp.start()
            mp.setOnCompletionListener { sound ->
                sound.release()
            }
            //minus when user use suggest
            coin -= 30
            App.getInstance().getPreference().setValueCoin(coin)
            //get model questionLevel
            tv_coin.text = coin.toString()
        } else {
            Toast.makeText(
                this,
                "you don't have enough coins, please top up to use!",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    //try again
    override fun onClick() {
        val bundle = Bundle()
        bundle.putSerializable(Constant.QUESTION_LEVEL, listQuestion[index!!]) //cái này à
        bundle.putInt(Constant.POSITION, index!!)
        startActivity(Intent(this, DetailQuestionActivity::class.java).apply {
            putExtras(bundle)
            putExtra("listQuestion", listQuestion)

        })
        finish()
        result.clear()
        failedDialog.dismiss()
    }

    //no confirm unlock
    override fun onClickNoUnlock() {
        unlockDialog.dismiss()
    }

    //confirm unlock
    override fun onClickYesUnlock() {

        if (coin >= 50) {

            coin -= 50
            pref.save("coin", coin)
            answer.forEachIndexed { index, c ->
                answerList[index] = AnswerModel(c.toString(), false)
                answerAdapter.notifyItemChanged(index)
            }
            for ((index, value) in suggest.withIndex()) {
                if (answer.any { it.toString() == value.labelSuggest }) {
                    suggest[index] = SuggestModel(" ", true)
                    suggestAdapter.notifyItemChanged(index)
                }
            }
            unlockDialog.dismiss()
            val mp = MediaPlayer.create(this, R.raw.cartoon_success_fanfair)
            mp.start()
            mp.setOnCompletionListener { sound ->
                sound.release()
            }
            listQuestion[index!!].isDone = true
            val successDialogFragment =
                SuccessDialogFragment.newInstance(questionLevel!!.name, questionLevel!!.question, 0)
            successDialogFragment.listener = this
            successDialogFragment.show(supportFragmentManager, "")
            AppDatabase.getAppDatabaseInstance(applicationContext)
                .questionLevelDAO()
                .update(
                    listQuestion[index!!].isDone,
                    listQuestion[index!!].question,
                    listQuestion[index!!].uid
                )
            //get model questionLevel
            tv_coin.text = coin.toString()
        } else {
            Toast.makeText(
                this,
                "you don't have enough coins, please top up to use!",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    override fun onCreateViewModel(): DetailQuestionViewModel = initViewModel()
    override fun onClickRelease() {
        finish()
    }

    override fun onClickNextRelease() {
        val releaseDialog =
            ReleaseDialogFragment.newInstance(questionLevel!!.name, questionLevel!!.question)
        releaseDialog.listenerRelease = this
        val int = index!!.plus(1)
        val bundle = Bundle()
        bundle.putSerializable(Constant.QUESTION_LEVEL, listQuestion[int!!])
        bundle.putInt(Constant.POSITION, int)
        startActivity(Intent(this, DetailQuestionActivity::class.java).apply {
            putExtras(bundle)
            putExtra("listQuestion", listQuestion)
        })
        finish()
    }

    override fun onBackPressed() {
        AppDatabase.getAppDatabaseInstance(applicationContext)
            .questionLevelDAO()
            .update(
                listQuestion[index!!].isDone,
                listQuestion[index!!].question,
                listQuestion[index!!].uid
            )
        pref.save("id", listQuestion[index!!].question)
        pref.save("check", listQuestion[index!!].isDone)
        pref.save("uid", listQuestion[index!!].uid)
        finish()
    }
}