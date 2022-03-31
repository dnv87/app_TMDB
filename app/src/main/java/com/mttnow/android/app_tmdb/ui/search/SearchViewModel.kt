package com.mttnow.android.app_tmdb.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBConnect
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel ():ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val apiService : TMDBInterface = TMDBConnect.getClient()
    private val movieRepository:SearchMoviePagedListRepository = SearchMoviePagedListRepository(apiService)


//    val  networkState : LiveData<NetworkState> by lazy {
//        movieRepository.getNetworkState()
//    }

    fun listIsEmpty(): Boolean {
        return /*moviePagedList.value?.isEmpty() ?:*/ true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


    fun Search (searchMovietext:String):LiveData<PagedList<Movie>>{
          return movieRepository.fetchLiveMoviePagedList(compositeDisposable, searchMovietext)
    }

}