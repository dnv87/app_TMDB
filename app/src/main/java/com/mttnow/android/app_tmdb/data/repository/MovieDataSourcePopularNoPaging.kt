package com.mttnow.android.app_tmdb.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.modeldata.MovieDetails
import com.mttnow.android.app_tmdb.modeldata.MovieResponse
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSourcePopularNoPaging(
    private val apiService: TMDBInterface,
) {
    fun loadMoviePopular(page: Int): Single<MovieResponse> {
         return apiService.getPopularMovie(page)
    }
}