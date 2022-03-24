package com.mttnow.android.app_tmdb.data.apiNetwork

import com.mttnow.android.app_tmdb.modeldata.MovieDetails
import com.mttnow.android.app_tmdb.modeldata.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBInterface {
    @GET("movie/popular")
    fun getPopularMovie(
        @Query("page") page: Int,
        @Query("language") lang: String = "ru"
    ): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getTopMovie(
        @Query("page") page: Int,
        @Query("language") lang: String = "ru"
    ): Single<MovieResponse>

    @GET("search/movie")
    fun getSearchMovie(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("language") lang: String = "ru"
    ): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getDetailsMovie(
        @Path("movie_id") id: Int,
        @Query("language") lang: String = "ru"
    ): Single<MovieDetails>
}