package com.mttnow.android.app_tmdb.ui.Movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

//следут наследоваться от абстрактной ViewModel  где CompositeDisposable() создаётся 1 раз
// и переопределёна ф-ция override fun onCleared()

class MovieViewModel(private val movieRepository : MoviePagedListRepository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val  moviePagedList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    //следует объявлять тут. убрать из movieRepository LiveData
    val  networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
//        compositeDisposable.dispose()  //разобрать в чём разница!!!!
        compositeDisposable.clear()
    }
}