package com.bmt_team.logoquiz.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bmt_team.logoquiz.model.Level
import com.bumptech.glide.Glide
import com.example.logoquiz.R
import com.example.logoquiz.databinding.ItemFlagBinding


class HomeAdapter : ListAdapter<Level, ViewHolderHome>(DiffCallBackHome()) {
    var listener : OnClickItem?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHome {
        val layout = LayoutInflater.from(parent.context)
        val binding : ItemFlagBinding = DataBindingUtil.inflate(layout, R.layout.item_flag, parent, false)
        return ViewHolderHome(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolderHome, position: Int) {
        holder.binData(getItem(position))
    }
    interface OnClickItem{
        fun onClickLevel(position: Int, level : Level)
    }
}
class ViewHolderHome(private val binding : ItemFlagBinding,
                     private val listener : HomeAdapter.OnClickItem?)
    : RecyclerView.ViewHolder(binding.root){
     fun binData(home : Level){
         itemView.run {
             binding.itemFlag = home
             binding.executePendingBindings()
             Glide.with(context).load(Uri.parse("file:///android_asset/home/${home.image}")).into(binding.imgHome)
             binding.processBar.progress = home.process.toInt()
             binding.root.setOnClickListener {
                 listener?.onClickLevel(adapterPosition, home)
             }
         }
     }
}
class DiffCallBackHome : DiffUtil.ItemCallback<Level>(){
    override fun areItemsTheSame(oldItem: Level, newItem: Level) : Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Level, newItem: Level): Boolean {
        return oldItem == newItem
    }

}