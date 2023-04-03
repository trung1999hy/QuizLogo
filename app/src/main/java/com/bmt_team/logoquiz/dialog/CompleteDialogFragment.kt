package com.bmt_team.logoquiz.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bmt_team.logoquiz.di.Constant
import com.example.logoquiz.R
import kotlinx.android.synthetic.main.fragment_complete_dialog.*
import kotlinx.android.synthetic.main.fragment_confirm_dialog.*

class CompleteDialogFragment : DialogFragment() {
    var listenerComplete : OnClickComplete?= null
    var id : Int?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt(Constant.COMPLETE)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        return inflater.inflate(R.layout.fragment_complete_dialog, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_complete.text = "Complete Level $id"
    }
    interface OnClickComplete{
        fun onClickComplete()
    }
    companion object{
        fun getInstanceComplete(id : Int) : CompleteDialogFragment{
            val complete = CompleteDialogFragment()
            val arg = Bundle()
            arg.putInt(Constant.COMPLETE, id)
            complete.arguments = arg
            return complete
        }
    }
}