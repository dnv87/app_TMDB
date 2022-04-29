package com.mttnow.android.app_tmdb.modeldata

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles") val newsList: List<Article>  = listOf(),
    @SerializedName("total_results") val total_results: Int
)