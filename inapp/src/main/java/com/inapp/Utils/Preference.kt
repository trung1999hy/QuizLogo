package com.inapp.Utils

import android.content.Context
import android.content.SharedPreferences
import com.inapp.Utils.Constant.INT_ZERO
import com.inapp.Utils.Constant.KEY_FIRST_INSTALL
import com.inapp.Utils.Constant.KEY_TOTAL_COIN

class Preference private constructor() {

    private var sharedPreference: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun init(context: Context) {
       sharedPreference = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        editor = sharedPreference!!.edit()
    }

    fun setValueCoin(value: Int) {
        editor?.putInt(KEY_TOTAL_COIN, value)?.apply()
    }

    fun getValueCoin(): Int {
        return sharedPreference!!.getInt(KEY_TOTAL_COIN, INT_ZERO)
    }

    fun setFirstInstall(value: Boolean?) {
        editor?.putBoolean(KEY_FIRST_INSTALL, value!!)?.apply()
    }

    fun getFirstInstall(): Boolean? {
        return sharedPreference?.getBoolean(KEY_FIRST_INSTALL, false)
    }

    companion object {

        @Volatile
        private var instance: Preference? = null

        fun get(): Preference =
            instance ?: synchronized(this) {
                val newInstance = instance ?: Preference()
                    .also { instance = it }
                newInstance
            }

        val standard: Preference
            get() = get()
    }
}