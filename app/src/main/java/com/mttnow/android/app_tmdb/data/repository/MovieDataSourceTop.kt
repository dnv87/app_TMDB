package com.mttnow.android.app_tmdb.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.Const.FIRST_PAGE
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MovieDataSourceTop(
    private val apiService: TMDBInterface,
    private val compositeDisposable: CompositeDisposable,
) : PageKeyedDataSource<Int, Movie>() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState //with this get, no need to implement get function to get networkSate

//    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    private var page = FIRST_PAGE

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        _networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getTopMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page + 1)
                        _networkState.postValue(NetworkState.FIRSTLOADING)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        _networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getTopMovie(params.key)
                .subscribeOn(Schedulers.io())
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribe(
                    {
                        if (it.total_pages >= params.key) {
                            callback.onResult(it.movieList, params.key + 1)
                            _networkState.postValue(NetworkState.LOADED)
                        } else {
                            _networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message!!)
                    }
                )
        )
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }
}