package com.bmt_team.logoquiz.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.logoquiz.R
import kotlinx.android.synthetic.main.fragment_release_dialog.*

class ReleaseDialogFragment : DialogFragment() {
    var listenerRelease : OnClickRelease?= null
    var nameRelease : String?=null
    var idQuestion : Int?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nameRelease = arguments?.getString("NAME")
        idQuestion = arguments?.getInt("ID_QUESTION")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        return inflater.inflate(R.layout.fragment_release_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_home_release.setOnClickListener {
            listenerRelease?.onClickRelease()
        }
        btn_next_release.setOnClickListener {
            listenerRelease?.onClickNextRelease()
        }
        tv_result.text = nameRelease?.toUpperCase()
        if (idQuestion == 20) btn_next_release.visibility = View.GONE

    }
    interface OnClickRelease{
        fun onClickRelease()
        fun onClickNextRelease()
    }
    companion object{
        fun newInstance(name : String, id : Int): ReleaseDialogFragment {
            val r = ReleaseDialogFragment()
            val args = Bundle()
            args.putString("NAME", name)
            args.putInt("ID_QUESTION",id)
            r.arguments = args
            return r
        }
    }
}