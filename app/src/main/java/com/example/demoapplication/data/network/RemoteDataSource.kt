package com.example.demoapplication.data.network

import com.example.demoapplication.data.model.News
import com.example.demoapplication.utils.Util
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getTopHeadLines(page:Int): Response<News> {
        return newsApi.getTopNews("us", page, Util.API_KEY)
    }

    suspend fun getEverything(q:String,page:Int):Response<News> {
        return newsApi.getEverything(q,page,Util.API_KEY)
    }
}