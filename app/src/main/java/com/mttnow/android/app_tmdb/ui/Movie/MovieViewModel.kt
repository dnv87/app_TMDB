package com.mttnow.android.app_tmdb.ui.Movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.ui.MovieViewModelAll
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val movieRepository : MoviePagedListRepository): MovieViewModelAll() {

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val  networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


}