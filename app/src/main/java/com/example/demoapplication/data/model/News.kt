package com.example.demoapplication.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class News(@Json(name = "articles") val articles: @RawValue List<ArticlesItem> = listOf()) : Parcelable

@Parcelize
data class ArticlesItem(
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "urlToImage")
    val urlToImage: String? = null,
    @Json(name = "publishedAt")
    val date: String? = null,
    @Json(name = "url")
    val url:String? = null
):Parcelable
