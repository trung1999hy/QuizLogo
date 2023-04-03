package com.bmt_team.logoquiz.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.logoquiz.R
import kotlinx.android.synthetic.main.fragment_unlock_dialog.*

class UnLockDialogFragment : DialogFragment() {

    var listenerUnLock : OnClickUnlock?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        return inflater.inflate(R.layout.fragment_unlock_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_no_unlock.setOnClickListener {
            listenerUnLock?.onClickNoUnlock()
        }
        btn_yes_unlock.setOnClickListener {
            listenerUnLock?.onClickYesUnlock()
        }
    }

    interface OnClickUnlock{
        fun onClickNoUnlock()
        fun onClickYesUnlock()
    }
}