package com.life.plus.tv.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.life.plus.tv.databinding.ItemShowBinding
import com.life.plus.tv.domain.model.ShowInfo
import com.life.plus.tv.utils.gone
import com.life.plus.tv.utils.loadDrawable


class ShowAdapter(
    private val onItemClick: (ShowInfo) -> Unit
) : ListAdapter<ShowInfo, ShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(
        private val binding: ItemShowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindView(item: ShowInfo) {
            binding.apply {
                image.loadDrawable(item.mediumImage)
                name.text = item.name
                time.text = item.premiered.split("-").first()
                genre.text = item.genres.firstOrNull() ?: run { genreCard.gone() ; "" }
                rate.text = item.rating.toString()
                language.text = "Language: ${item.language}"
                score.text = "TvMaze Score: ${"%.2f".format(item.score)}"

                btnDetails.setOnClickListener {
                    onItemClick(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShowInfo>() {
            override fun areItemsTheSame(oldItem: ShowInfo, newItem: ShowInfo): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: ShowInfo, newItem: ShowInfo): Boolean = oldItem.id == newItem.id
        }
    }
}