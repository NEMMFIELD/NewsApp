package com.example.demoapplication.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.demoapplication.R
import com.example.demoapplication.data.model.ArticlesItem
import com.example.demoapplication.data.model.News
import com.example.demoapplication.data.network.NewsApi
import com.example.demoapplication.databinding.ItemViewBinding

class NewsAdapter(private var onClickListener: OnClickListener?) :
    PagingDataAdapter<ArticlesItem, NewsAdapter.ViewHolder>(NewsDiffUtil()) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemViewBinding.bind(view)
        fun bind(item: ArticlesItem?) {
            with(binding) {
                imageUrl.load(item?.urlToImage)
                textTitle.text = item?.title
                textUrl.text = item?.date?.substring(0, 10)
                imageUrl.setOnClickListener {
                    if (onClickListener != null) {
                        onClickListener!!.onClick(bindingAdapterPosition, item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnClickListener(onClickListener: OnClickListener?) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: ArticlesItem?)
    }

    companion object {
        class NewsDiffUtil : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}