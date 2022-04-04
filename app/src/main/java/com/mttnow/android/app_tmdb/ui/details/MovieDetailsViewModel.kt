package com.mttnow.android.app_tmdb.ui.details

import androidx.lifecycle.LiveData
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.repository.MovieDataSourceDetails
import com.mttnow.android.app_tmdb.modeldata.MovieDetails
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel

class MovieDetailsViewModel() : BaseMovieViewModel() {

    lateinit var movieDetailsNetworkDataSource: MovieDataSourceDetails

    private var movieId: Int = 0
    fun getMovieId(_i: Int) {
        movieId = _i
    }

    fun fetchSingleMovieDetails(): LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDataSourceDetails(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}