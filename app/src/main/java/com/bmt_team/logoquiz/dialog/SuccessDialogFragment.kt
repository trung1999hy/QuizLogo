package com.bmt_team.logoquiz.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.logoquiz.R
import kotlinx.android.synthetic.main.fragment_success_dialog.*


class SuccessDialogFragment : DialogFragment() {
    var nameImage : String?= null
    var listener : OnClickButton?= null
    var idQuestion : Int?= null
    var coin : Int?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nameImage = arguments?.getString("NAME")
        idQuestion = arguments?.getInt("ID_QUESTION")
        coin = arguments?.getInt("COIN")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        return inflater.inflate(R.layout.fragment_success_dialog, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_result.text = nameImage?.toUpperCase()
        btn_home.setOnClickListener {
            listener?.onClickHome()
        }
        btn_next.setOnClickListener {
            listener?.onClickNext()
        }
        if (idQuestion == 20){
            btn_next.visibility = View.GONE
        }
        tv_coin_success.text = coin.toString()
    }
    companion object{
        fun newInstance(name : String, id : Int, coin : Int): SuccessDialogFragment {
            val f = SuccessDialogFragment()
            val args = Bundle()
            args.putInt("COIN", coin)
            args.putString("NAME", name)
            args.putInt("ID_QUESTION", id)
            f.arguments = args
            return f
        }
    }
    interface OnClickButton{
        fun onClickHome()
        fun onClickNext()
    }
}