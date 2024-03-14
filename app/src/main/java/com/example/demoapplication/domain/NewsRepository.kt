package com.example.demoapplication.domain

import com.example.demoapplication.data.model.News

interface NewsRepository {
    suspend fun getTopHeadlines(page: Int): News?

    suspend fun getEverything(q: String, page: Int): News?
}