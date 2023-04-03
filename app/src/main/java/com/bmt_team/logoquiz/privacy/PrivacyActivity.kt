package com.bmt_team.logoquiz.privacy

import android.view.WindowManager
import com.bmt_team.logoquiz.base.BaseActivity
import com.bmt_team.logoquiz.base.initViewModel
import com.example.logoquiz.R
import com.example.logoquiz.databinding.ActivityPrivacyBinding
import kotlinx.android.synthetic.main.activity_privacy.*

class PrivacyActivity : BaseActivity<PrivacyViewModel, ActivityPrivacyBinding>() {


    override fun layoutId(): Int = R.layout.activity_privacy

    override fun bindView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        tv_privacy.text = viewModel.readText(PRIVACY, applicationContext)
        privacy_back.setOnClickListener { onBackPressed() }
    }

    override fun observeData() {

    }
    companion object{
        const val PRIVACY = "privacy_policy.txt"
        const val TERM_OF_USE = "term_of_use.txt"
    }

    override fun onCreateViewModel(): PrivacyViewModel = initViewModel()

}