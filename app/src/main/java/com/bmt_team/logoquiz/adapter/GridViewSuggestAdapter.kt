package com.bmt_team.logoquiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bmt_team.logoquiz.model.SuggestModel
import com.example.logoquiz.R
import kotlinx.android.synthetic.main.item_button_question.view.*

class GridViewSuggestAdapter
    : ListAdapter<SuggestModel,GridViewSuggestAdapter.ViewHolderSuggest>(DiffCallBackSuggest()){
    var listener : OnClickItemSuggest?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSuggest {
        return ViewHolderSuggest(
            LayoutInflater.from(parent.context).inflate(R.layout.item_button_question, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderSuggest, position: Int) {
        holder.binDataSuggest(getItem(position),listener)
    }


    interface OnClickItemSuggest{
        fun onClickSuggest(position: Int, suggest: SuggestModel)
    }
    inner class ViewHolderSuggest(item : View) : RecyclerView.ViewHolder(item){
       fun binDataSuggest(suggest : SuggestModel, listener : OnClickItemSuggest?){
           itemView.run {
               setOnClickListener {
                   listener?.onClickSuggest(adapterPosition, suggest)
               }
               if (!suggest.isCheck){
                   btn_question.text = suggest.labelSuggest
               }else{
                   btn_question.text = " "
               }
           }
       }
    }

}
class DiffCallBackSuggest : DiffUtil.ItemCallback<SuggestModel>(){
    override fun areItemsTheSame(oldItem: SuggestModel, newItem: SuggestModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SuggestModel, newItem: SuggestModel): Boolean {
        return oldItem == newItem
    }

}