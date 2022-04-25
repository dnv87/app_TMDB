package com.mttnow.android.app_tmdb.modeldata

import android.icu.text.CaseMap
import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?
)
