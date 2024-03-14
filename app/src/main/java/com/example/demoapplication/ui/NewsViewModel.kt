package com.example.demoapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.demoapplication.data.model.ArticlesItem
import com.example.demoapplication.domain.interactor.EverythingInteractor
import com.example.demoapplication.domain.interactor.GetTopHeadLinesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val topRatedInteractor: GetTopHeadLinesInteractor,
    private val everythingInteractor: EverythingInteractor
) : ViewModel() {
    var poisk:String = ""
    var newsList:Flow<PagingData<ArticlesItem>>? = null
    init {
        loadTopNews()
    }

    private fun loadTopNews():Flow<PagingData<ArticlesItem>> {
        newsList = topRatedInteractor.invoke().cachedIn(viewModelScope)
        return newsList as Flow<PagingData<ArticlesItem>>
    }
    fun load(q: String): Flow<PagingData<ArticlesItem>> {
        newsList = everythingInteractor.invoke(q).cachedIn(viewModelScope)
        return newsList as Flow<PagingData<ArticlesItem>>
    }
}