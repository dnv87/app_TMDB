package com.mttnow.android.app_tmdb.data.repository

import com.mttnow.android.app_tmdb.data.apiNetwork.NEWSInterface
import com.mttnow.android.app_tmdb.modeldata.NewsResponse
import io.reactivex.Single

class NewsDataSourceSportsNoPaging(
    private val apiService: NEWSInterface,
) {
    fun loadNewsSports(page: Int, pageSize:Int): Single<NewsResponse> {
         return apiService.getSearchNews(page, pageSize)
    }
}