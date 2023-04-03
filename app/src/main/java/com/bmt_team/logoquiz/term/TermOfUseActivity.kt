package com.bmt_team.logoquiz.term

import android.view.WindowManager
import com.bmt_team.logoquiz.base.BaseActivity
import com.bmt_team.logoquiz.base.initViewModel
import com.bmt_team.logoquiz.privacy.PrivacyActivity
import com.example.logoquiz.R
import com.example.logoquiz.databinding.ActivityTermOfUseBinding
import kotlinx.android.synthetic.main.activity_term_of_use.*

class TermOfUseActivity : BaseActivity<TermOfUserViewModel, ActivityTermOfUseBinding>() {

    override fun onCreateViewModel(): TermOfUserViewModel = initViewModel()

    override fun layoutId(): Int = R.layout.activity_term_of_use

    override fun bindView() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        tv_term_of_use.text = viewModel.readText(PrivacyActivity.TERM_OF_USE, this)
        back_tem_of_user.setOnClickListener { onBackPressed() }
    }

    override fun observeData() {

    }
}