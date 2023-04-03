package com.bmt_team.logoquiz.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bmt_team.logoquiz.model.QuestionLevel
import com.bumptech.glide.Glide
import com.example.logoquiz.R
import com.example.logoquiz.databinding.ItemQuestionBinding
import kotlinx.android.synthetic.main.item_question.view.*

class ListQuestionAdapter
    : ListAdapter<QuestionLevel, ListQuestionAdapter.ViewHolderQuestion>(DiffCallBackQuestion()) {
    var listener : OnCLickItem?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderQuestion {
        val layout = LayoutInflater.from(parent.context)
        val binding : ItemQuestionBinding = DataBindingUtil.inflate(layout, R.layout.item_question, parent, false)
        return ViewHolderQuestion(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolderQuestion, position: Int) {
        holder.binListQuestion(getItem(position))
    }
    interface OnCLickItem{
        fun onClick(questionLevel: QuestionLevel, position : Int)
    }
    inner class ViewHolderQuestion(private val binding : ItemQuestionBinding, private val onClickItem : OnCLickItem?)
        : RecyclerView.ViewHolder(binding.root){
        fun binListQuestion(list : QuestionLevel){
            itemView.run {
                binding.apply {
                    itemQuestion = list
                    executePendingBindings()
                    root.setOnClickListener {
                       onClickItem?.onClick(list, adapterPosition)
                    }
                    Glide.with(context).load(Uri.parse("file:///android_asset/image/${list.imageQuestion}")).into(binding.imgQuestion)
                    if(list.isDone){
                        checkDone.visibility = View.VISIBLE
                    }else{
                        checkDone.visibility = View.GONE
                    }
                }

            }
        }
    }
}
class DiffCallBackQuestion : DiffUtil.ItemCallback<QuestionLevel>(){
    override fun areItemsTheSame(oldItem: QuestionLevel, newItem: QuestionLevel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: QuestionLevel, newItem: QuestionLevel): Boolean {
        return oldItem == newItem
    }

}