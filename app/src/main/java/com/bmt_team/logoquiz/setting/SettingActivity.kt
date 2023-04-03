package com.bmt_team.logoquiz.setting

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.bmt_team.logoquiz.base.BaseActivity
import com.bmt_team.logoquiz.base.initViewModel
import com.bmt_team.logoquiz.privacy.PrivacyActivity
import com.bmt_team.logoquiz.term.TermOfUseActivity
import com.example.logoquiz.R
import com.example.logoquiz.databinding.ActivitySettingBinding
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity :
    BaseActivity<SettingViewModel, ActivitySettingBinding>() {
    private val publishName: String = "Fitnees Group"
    private val email: String = "mobile.hoangtien.ios@gmail.com"
    private val subject: String = "Logo Quiz"

    override fun layoutId(): Int = R.layout.activity_setting
    override fun bindView() {
       privacy_setting.setOnClickListener {
           startActivity(Intent(this, PrivacyActivity::class.java))
       }
        term_of_use.setOnClickListener {
            startActivity(Intent(this, TermOfUseActivity::class.java))
        }
        ic_back_setting.setOnClickListener { onBackPressed() }
        rate_app.setOnClickListener { rateApp() }
        support.setOnClickListener { sendEmail() }
        share_app.setOnClickListener { shareApp() }
    }

    override fun observeData() {

    }

    override fun onCreateViewModel(): SettingViewModel = initViewModel()

    private fun rateApp(){
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }
    private fun sendEmail() {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.feed_back))
        emailIntent.putExtra(Intent.EXTRA_TEXT, this.getString(R.string.what_you_need_help))
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
        }
    }
    private fun shareApp() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody =
            "https://play.google.com/store/apps/details?id=" + packageName.trim { it <= ' ' }
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share to"))
    }
}