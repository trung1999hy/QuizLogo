package com.bmt_team.logoquiz.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.logoquiz.R
import kotlinx.android.synthetic.main.fragment_continue_answer_dialog.*


class ContinueAnswerDialogFragment : DialogFragment() {
    var listenerContinue : OnClickContinueAnswer?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        return inflater.inflate(R.layout.fragment_continue_answer_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_yes_continue.setOnClickListener {
            listenerContinue?.onClickYesContinue()
        }
        btn_no_continue.setOnClickListener {
            listenerContinue?.onClickNoContinue()
        }
    }

    interface OnClickContinueAnswer{
        fun onClickYesContinue()
        fun onClickNoContinue()
    }


}