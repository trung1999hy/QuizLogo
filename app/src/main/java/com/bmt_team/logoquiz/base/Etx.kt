package com.bmt_team.logoquiz.base

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


@JvmName("initViewModel")
inline fun <reified T : ViewModel> AppCompatActivity.initViewModel() =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(this)).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.initViewModel() =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(this.requireContext()))
        .get(T::class.java)

fun AppCompatActivity.replaceFragment(
    frg: Fragment,
    @IdRes container: Int,
    isAddToBackStack: Boolean
) {
    val fragmentManager = this.supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(container, frg)
    if (isAddToBackStack) {
        transaction.addToBackStack("Toan PK")
    }
    transaction.commit()
}