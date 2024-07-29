package com.life.plus.tv.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.life.plus.tv.databinding.ItemShowBinding
import com.life.plus.tv.domain.model.ShowInfo


class ShowAdapter() : ListAdapter<ShowInfo, ShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(
        private val binding: ItemShowBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: ShowInfo, position: Int) {
            binding.apply {

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        parent.context
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position), position)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShowInfo>() {
            override fun areItemsTheSame(oldItem: ShowInfo, newItem: ShowInfo): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: ShowInfo, newItem: ShowInfo): Boolean = oldItem.id == newItem.id
        }
    }
}