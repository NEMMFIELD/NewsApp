package com.example.demoapplication.domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.demoapplication.data.model.ArticlesItem
import com.example.demoapplication.data.network.EverythingPagingSource
import com.example.demoapplication.domain.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EverythingInteractor @Inject constructor(
    private val repository: NewsRepository,
) {
    fun invoke(q: String): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EverythingPagingSource(repository, q) }
        ).flow
    }
}