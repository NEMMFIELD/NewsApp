package com.example.demoapplication.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.demoapplication.data.model.ArticlesItem
import com.example.demoapplication.domain.NewsRepository
import retrofit2.HttpException
import java.lang.Exception

class NewsPagingSource(private val repository: NewsRepository) : PagingSource<Int, ArticlesItem>() {
    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getTopHeadlines(currentPage)
            val data = response?.articles ?: emptyList()
            val fixedNews:MutableList<ArticlesItem> = data.filter { it.urlToImage != null }.toMutableList() //Список без пустых картинок
            val responseData = mutableListOf<ArticlesItem>()
            if (data.isNotEmpty()) {
                responseData.addAll(fixedNews)
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = currentPage.plus(1)
                )
            } else {
                LoadResult.Page(
                    data = responseData,
                    prevKey = if (currentPage == 1) null else -1,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (httpE: HttpException) {
            LoadResult.Error(httpE)
        }
    }
}