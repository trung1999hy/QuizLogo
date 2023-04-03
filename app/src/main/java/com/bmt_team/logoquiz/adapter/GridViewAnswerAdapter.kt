package com.bmt_team.logoquiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bmt_team.logoquiz.model.AnswerModel
import com.example.logoquiz.R
import kotlinx.android.synthetic.main.item_button_answer.view.*

class GridViewAnswerAdapter : ListAdapter<AnswerModel,GridViewAnswerAdapter.ViewHolderAnswer>(DiffCallBackAnswer()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAnswer {
        return ViewHolderAnswer(
            LayoutInflater.from(parent.context).inflate(R.layout.item_button_answer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderAnswer, position: Int) {
        holder.binDataAnswer(getItem(position))
    }
    inner class ViewHolderAnswer(item : View) : RecyclerView.ViewHolder(item){

        fun binDataAnswer(answer : AnswerModel){
            itemView.run {
                if (answer.isCheck){
                    btn_answer.text = " "
                }else{
                    btn_answer.text = answer.label
                }
            }
        }
    }


}
class DiffCallBackAnswer : DiffUtil.ItemCallback<AnswerModel>(){
    override fun areItemsTheSame(oldItem: AnswerModel, newItem: AnswerModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AnswerModel, newItem: AnswerModel): Boolean {
        return oldItem == newItem
    }

}