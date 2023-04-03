package com.bmt_team.logoquiz.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.logoquiz.R
import kotlinx.android.synthetic.main.fragment_confirm_dialog.*

class ConfirmDialogFragment : DialogFragment() {
    var listenerConfirm : OnClickConfirm?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        return inflater.inflate(R.layout.fragment_confirm_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_no.setOnClickListener {
            listenerConfirm?.onClickNo()
        }
        btn_yes.setOnClickListener {
            listenerConfirm?.onClickYes()
        }
    }
    interface OnClickConfirm{
        fun onClickNo()
        fun onClickYes()
    }
}