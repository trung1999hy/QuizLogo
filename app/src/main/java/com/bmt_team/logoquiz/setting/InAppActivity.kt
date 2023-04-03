package com.bmt_team.logoquiz.setting

import com.bmt_team.logoquiz.base.BaseActivity
import com.bmt_team.logoquiz.base.initViewModel
import com.bmt_team.logoquiz.sharePreference.SharePreferences
import com.example.logoquiz.R
import com.example.logoquiz.databinding.ActivityInAppBinding
import kotlinx.android.synthetic.main.activity_in_app.*

class InAppActivity : BaseActivity<InAppViewModel, ActivityInAppBinding>() {
    private val pref = SharePreferences()
    override fun onCreateViewModel(): InAppViewModel = initViewModel()

    override fun layoutId(): Int = R.layout.activity_in_app

    override fun bindView() {
       ic_back_in_app.setOnClickListener { onBackPressed() }
    }

    override fun observeData() {
        viewModel.coin_update.value = pref.getInt("coin")
    }

}