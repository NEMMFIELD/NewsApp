package com.example.demoapplication.di

import com.example.demoapplication.data.model.News

import com.example.demoapplication.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
     suspend fun bindDataSource(remoteDataSource: RemoteDataSource,page:Int):Response<News>  = remoteDataSource.getTopHeadLines(page)
}