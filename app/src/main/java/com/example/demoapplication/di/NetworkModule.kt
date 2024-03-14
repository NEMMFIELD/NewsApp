package com.example.demoapplication.di

import com.example.demoapplication.data.network.NetworkService
import com.example.demoapplication.data.network.NewsApi
import com.example.demoapplication.utils.Util
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideNetworkService() = NetworkService()

    @Provides
    @Singleton
    fun provideMoshi(networkService: NetworkService): Moshi = networkService.provideMoshi()

    @Singleton
    @Provides
    fun provideOkHttpClient(networkService: NetworkService): OkHttpClient {
        return networkService.provideOkHttpClient()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): NewsApi = Retrofit.Builder()
        .baseUrl(Util.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .client(client)
        .build()
        .create(NewsApi::class.java)
}