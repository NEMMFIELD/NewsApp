package com.example.demoapplication.data.network

import com.example.demoapplication.data.model.News
import com.example.demoapplication.utils.Util
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getTopNews(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Response<News>

    @GET("v2/everything")
  suspend fun getEverything(
        @Query("q")q:String,
        @Query("page") page: Int,
        @Query("apiKey")apiKey: String
    ):Response<News>
}
