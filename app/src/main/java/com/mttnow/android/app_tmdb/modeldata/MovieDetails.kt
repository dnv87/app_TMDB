package com.mttnow.android.app_tmdb.modeldata

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("budget") val budget: Int = 0,
    @SerializedName("id") val id: Int,
    @SerializedName("overview") val overview: String = "",
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("revenue") val revenue: Long,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("status") val status: String = "",
    @SerializedName("tagline") val tagline: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("vote_average") val rating: Double
)