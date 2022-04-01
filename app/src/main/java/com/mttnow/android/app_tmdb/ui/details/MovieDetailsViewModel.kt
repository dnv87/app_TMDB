package com.mttnow.android.app_tmdb.ui.details

import androidx.lifecycle.LiveData
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.modeldata.MovieDetails
import com.mttnow.android.app_tmdb.ui.MovieViewModelAll
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel (private val movieRepository : MovieDetailsRepository)  : MovieViewModelAll() {

//    private val compositeDisposable = CompositeDisposable()


    private var movieId: Int = 0
    fun getMovieId(_i: Int){
        movieId = _i
    }


    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

//    override fun onCleared() {
//        super.onCleared()
//        compositeDisposable.dispose()
//    }
}