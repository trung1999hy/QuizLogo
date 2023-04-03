package com.inapp.inapp

import android.app.Application
import com.inapp.Utils.Preference

class MainApp : Application() {
    companion object {
        var instance: MainApp? = null
        var preference: Preference? = null
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        preference = Preference.standard
        preference?.getFirstInstall()?.let {
            preference?.setFirstInstall(true)
            preference?.setValueCoin(100)
        }

    }

    fun getPreference(): Preference? {
        return preference
    }

}