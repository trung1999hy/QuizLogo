package com.bmt_team.logoquiz.base

import android.app.Application
import com.bmt_team.logoquiz.database.AppDatabase
import com.bmt_team.logoquiz.sharePreference.SharePreferences


class App : Application(){
    private lateinit var appDatabase: AppDatabase
    companion object {
        @JvmStatic
        private lateinit var instance: App

        @JvmStatic
        fun getInstance(): App = instance

        private lateinit var mPrefs: SharePreferences
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appDatabase = AppDatabase.getAppDatabaseInstance(applicationContext)
        mPrefs = SharePreferences()
    }

    fun getPreference(): SharePreferences {
        return mPrefs
    }


}