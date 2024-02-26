package com.example.demoapplication.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.LoadStates
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapplication.databinding.LoadMoreBinding

class LoadMoreAdapter(private val retry: () -> Unit): LoadStateAdapter<LoadMoreAdapter.ViewHolder>() {
    private lateinit var binding:LoadMoreBinding
    inner class ViewHolder(retry: () -> Unit): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnRetry.setOnClickListener {
                retry()
            }
        }
        fun setData(states: LoadState) {
            binding.apply {
                prgBarLoadMore.isVisible = states is LoadState.Loading
                textError.isVisible = states is LoadState.Error
                btnRetry.isVisible = states is LoadState.Error
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.setData(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        binding = LoadMoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(retry)
    }

}