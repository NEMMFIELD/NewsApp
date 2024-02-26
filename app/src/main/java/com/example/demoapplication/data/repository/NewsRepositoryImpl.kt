package com.example.demoapplication.data.repository

import com.example.demoapplication.data.model.News
import com.example.demoapplication.data.network.RemoteDataSource
import com.example.demoapplication.domain.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    NewsRepository {
    override suspend fun getTopHeadlines(page: Int): News? {
        return remoteDataSource.getTopHeadLines(page).body()
    }

    override suspend fun getEverything(q:String,page: Int): News? {
        return remoteDataSource.getEverything(q,page).body()
    }
}