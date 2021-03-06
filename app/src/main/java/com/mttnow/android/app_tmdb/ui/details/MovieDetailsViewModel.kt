package com.mttnow.android.app_tmdb.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.modeldata.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel (private val movieRepository : MovieDetailsRepository, private val movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    // ViewModel должен содержать ф-ции которые показывают что делается
  /*  fun loadData(){
        networkState.pos
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }*/

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}