package com.example.demoapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.example.demoapplication.R
import com.example.demoapplication.background.NewsWorkerRepository
import com.example.demoapplication.data.LoadMoreAdapter
import com.example.demoapplication.data.NewsAdapter
import com.example.demoapplication.data.model.ArticlesItem
import com.example.demoapplication.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsAdapter.OnClickListener {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter
    private val viewModel: NewsViewModel by viewModels()
    private val newsWorkerRepository = NewsWorkerRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        val editText = activity?.findViewById<EditText>(R.id.searching)
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.poisk = s.toString()
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.load(viewModel.poisk)
                        viewModel.newsList?.collect {
                            adapter.submitData(it)
                        }
                    }
                }
            }
        })
        lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                val state = it.refresh
                binding.prgBarNews.isVisible = state is LoadState.Loading
            }
        }
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "NewsWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            newsWorkerRepository.periodicWork
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        WorkManager.getInstance(requireContext()).cancelAllWork()
    }

    private fun initRecycler() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(onClickListener = this@NewsFragment)
        recyclerView.adapter = adapter.withLoadStateFooter(LoadMoreAdapter { adapter.retry() })
        val divider = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.line_divider)
            ?.let { divider.setDrawable(it) }
        recyclerView.addItemDecoration(divider)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsList?.collect {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onClick(position: Int, model: ArticlesItem?) {
        println("I am here $position")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(model?.url))
        requireContext().startActivity(intent)
    }
}