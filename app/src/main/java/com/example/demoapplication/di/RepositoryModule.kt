package com.example.demoapplication.di

import com.example.demoapplication.data.network.RemoteDataSource
import com.example.demoapplication.data.repository.NewsRepositoryImpl
import com.example.demoapplication.domain.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource):NewsRepository = NewsRepositoryImpl(remoteDataSource)
}