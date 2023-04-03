package com.bmt_team.logoquiz.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bmt_team.logoquiz.data.DataRepository
import com.bmt_team.logoquiz.database.AppDatabase
import com.bmt_team.logoquiz.detail_question.DetailQuestionViewModel
import com.bmt_team.logoquiz.list_question.ListQuestionViewModel
import com.bmt_team.logoquiz.privacy.PrivacyViewModel
import com.bmt_team.logoquiz.setting.InAppViewModel
import com.bmt_team.logoquiz.setting.SettingViewModel
import com.bmt_team.logoquiz.splash_screen.SplashViewModel
import com.bmt_team.logoquiz.term.TermOfUserViewModel
import com.bmt_team.logoquiz.viewmodel.MainViewModel


class ViewModelFactory(
    context: Context
) : ViewModelProvider.Factory {
    private val repository = DataRepository(AppDatabase.getAppDatabaseInstance(context).levelDAO(), AppDatabase.getAppDatabaseInstance(context).questionLevelDAO())
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(SplashViewModel::class.java) -> {
                    SplashViewModel()
                }
                isAssignableFrom(MainViewModel::class.java) -> {
                    MainViewModel(repository)
                }

                isAssignableFrom(ListQuestionViewModel::class.java) -> {
                    ListQuestionViewModel(repository)
                }

                isAssignableFrom(DetailQuestionViewModel::class.java) -> {
                    DetailQuestionViewModel(repository)
                }
                isAssignableFrom(SettingViewModel::class.java) -> {
                    SettingViewModel()
                }
                isAssignableFrom(PrivacyViewModel::class.java) -> {
                    PrivacyViewModel()
                }
                isAssignableFrom(TermOfUserViewModel::class.java) -> {
                    TermOfUserViewModel()
                }
                isAssignableFrom(InAppViewModel::class.java) -> {
                    InAppViewModel()
                }

                else -> throw IllegalStateException("unknown view model: $modelClass")
            }
        } as T


    companion object {
        fun getInstance(activity: Context): ViewModelFactory = ViewModelFactory(activity)
    }
}

