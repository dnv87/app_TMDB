package com.mttnow.android.app_tmdb.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.MovieDataSourceDetails
import com.mttnow.android.app_tmdb.modeldata.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel (private val apiService : TMDBInterface,
                             private val movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    lateinit var movieDetailsNetworkDataSource: MovieDataSourceDetails

    val movieDetails: LiveData<MovieDetails> = fetchSingleMovieDetails(compositeDisposable,movieId)

    val networkState : LiveData<NetworkState> = getMovieDetailsNetworkState()

    private fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable,
                                         movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDataSourceDetails(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    private fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}