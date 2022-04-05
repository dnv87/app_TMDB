package com.mttnow.android.app_tmdb.modeldata

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("results") val movieList: List<Movie> = listOf(),
    @SerializedName("total_pages") val total_pages: Int = 0,
    @SerializedName("total_results") val total_results: Int
)