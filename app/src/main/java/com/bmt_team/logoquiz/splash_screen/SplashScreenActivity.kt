package com.bmt_team.logoquiz.splash_screen

import android.content.Intent
import android.os.Handler
import com.example.logoquiz.R


import com.example.logoquiz.databinding.ActivitySplashScreenBinding
import com.bmt_team.logoquiz.base.BaseActivity
import com.bmt_team.logoquiz.base.initViewModel


class SplashScreenActivity : BaseActivity<SplashViewModel, ActivitySplashScreenBinding>() {
    private val TIME_SPLASH = 3000L
    override fun bindView() {
        Handler().postDelayed({
            startActivity(Intent(this, com.bmt_team.logoquiz.MainActivity::class.java)
                .apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK) })
        }, TIME_SPLASH)
    }

    override fun observeData() {
    }


    override fun layoutId(): Int = R.layout.activity_splash_screen
    override fun onCreateViewModel(): SplashViewModel = initViewModel()


}