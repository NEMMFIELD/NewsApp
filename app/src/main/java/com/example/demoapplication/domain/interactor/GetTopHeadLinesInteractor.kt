package com.example.demoapplication.domain.interactor


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.demoapplication.data.model.ArticlesItem
import com.example.demoapplication.data.network.NewsPagingSource
import com.example.demoapplication.domain.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadLinesInteractor @Inject constructor(private val repository: NewsRepository) {
    fun invoke(): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(15,
                enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(repository) }
        ).flow
    }
}