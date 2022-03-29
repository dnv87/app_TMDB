package com.mttnow.android.app_tmdb.ui.search

import androidx.lifecycle.LiveData
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.SearchMoviePagingSource

import com.mttnow.android.app_tmdb.modeldata.MovieResponse
import io.reactivex.disposables.CompositeDisposable

class SearchMovieRepository (private val apiService : TMDBInterface) {

    lateinit var movieSearchNetworkDataSource: SearchMoviePagingSource

    lateinit var moviePagedList: LiveData<MovieResponse>
    private var page = Const.FIRST_PAGE


    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieSearch: String) : LiveData<MovieResponse> {

        movieSearchNetworkDataSource = SearchMoviePagingSource(apiService,compositeDisposable)
        movieSearchNetworkDataSource.fetchMovieDetails(page= page, getMovie=movieSearch)

        return movieSearchNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieSearchNetworkDataSource.networkState
    }
}