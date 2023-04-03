package com.bmt_team.logoquiz.setting

import androidx.lifecycle.MutableLiveData
import com.bmt_team.logoquiz.base.BaseViewModel


class InAppViewModel : BaseViewModel() {
    var coin_update = MutableLiveData<Int>()

}