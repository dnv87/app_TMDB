package com.mttnow.android.app_tmdb.data.apiNetwork

import com.mttnow.android.app_tmdb.modeldata.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NEWSInterface {

    @GET("everything")
    fun getSearchNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("q") q: String = "sports",
    ): Single<NewsResponse>

}