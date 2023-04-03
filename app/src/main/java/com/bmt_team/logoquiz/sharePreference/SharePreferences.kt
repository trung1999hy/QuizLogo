package com.bmt_team.logoquiz.sharePreference

import android.content.Context
import android.content.SharedPreferences
import com.bmt_team.logoquiz.base.App
import com.bmt_team.logoquiz.di.Constant.INT_ZERO
import com.bmt_team.logoquiz.di.Constant.KEY_FIRST_INSTALL
import com.bmt_team.logoquiz.di.Constant.KEY_PREMIUM
import com.bmt_team.logoquiz.di.Constant.KEY_TOTAL_COIN

class SharePreferences : PreferencesInterface {
    private val mPrefs : SharedPreferences = App.getInstance().getSharedPreferences("PREF", Context.MODE_PRIVATE)
    companion object {
        private const val DEFAULT_VALUE_LONG: Long = 0
        private const val DEFAULT_VALUE_INTEGER = 0
        private const val DEFAULT_VALUE_FLOAT = 0
    }

    override fun save(key: String?, value: Boolean) {
        mPrefs.edit().putBoolean(key, value).apply()
    }

    override fun save(key: String?, value: String?) {
        mPrefs.edit().putString(key, value).apply()
    }

    override fun save(key: String?, value: Float) {
        mPrefs.edit().putFloat(key, value).apply()
    }

    override fun save(key: String?, value: Int) {
        mPrefs.edit().putInt(key, value).apply()
    }

    override fun save(key: String?, value: Long) {
        mPrefs.edit().putLong(key, value).apply()
    }

    override fun getBoolean(key: String?): Boolean {
        return mPrefs.getBoolean(key, false)
    }

    override fun getString(key: String?): String? {
        return mPrefs.getString(key, null)
    }

    override fun getLong(key: String?): Long {
        return mPrefs.getLong(key, DEFAULT_VALUE_LONG)
    }

    override fun getInt(key: String?): Int {
        return mPrefs.getInt(key, DEFAULT_VALUE_INTEGER)
    }

    override fun getFloat(key: String?): Float {
        return mPrefs.getFloat(key, DEFAULT_VALUE_FLOAT.toFloat())
    }

    override fun remove(key: String?) {
        mPrefs.edit().remove(key).apply()
    }

    fun setValueCoin(value: Int) {
        mPrefs.edit().putInt(KEY_TOTAL_COIN, value)?.apply()
    }

    fun getValueCoin(): Int {
        return mPrefs!!.getInt(KEY_TOTAL_COIN, INT_ZERO)
    }

    fun setFirstInstall(value: Boolean?) {
        mPrefs.edit().putBoolean(KEY_FIRST_INSTALL, value!!)?.apply()
    }

    fun getFirstInstall(): Boolean? {
        return mPrefs.getBoolean(KEY_FIRST_INSTALL, false)
    }


    fun setPremium(value: Int) {
        mPrefs.edit().putInt(KEY_PREMIUM, value).apply()
    }

    fun getPremium(): Int {
        return mPrefs.getInt(KEY_PREMIUM, 0)
    }
}

interface PreferencesInterface {
    fun save(key: String?, value: Boolean)
    fun save(key: String?, value: String?)
    fun save(key: String?, value: Float)
    fun save(key: String?, value: Int)
    fun save(key: String?, value: Long)
    fun getBoolean(key: String?): Boolean
    fun getString(key: String?): String?
    fun getLong(key: String?): Long
    fun getInt(key: String?): Int
    fun getFloat(key: String?): Float
    fun remove(key: String?)
}