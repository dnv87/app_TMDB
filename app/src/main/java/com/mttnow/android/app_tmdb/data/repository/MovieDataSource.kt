package com.mttnow.android.app_tmdb.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.Const.FIRST_PAGE
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val apiService: TMDBInterface,
    private val compositeDisposable: CompositeDisposable,
) : PageKeyedDataSource<Int, Movie>() {

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    private var page = FIRST_PAGE
    private var getMovie = Const.GET_POPULAR_MOVIE
    fun queryMovie(_b: Boolean) {
        getMovie = _b
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        if (getMovie) {
            compositeDisposable.add(
                apiService.getPopularMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.movieList, null, page + 1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message!!)
                        }
                    )
            )
        } else {
            compositeDisposable.add(
                apiService.getTopMovie(page)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.movieList, null, page + 1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message!!)
                        }
                    )
            )
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
        if (getMovie) {
            compositeDisposable.add(
                apiService.getPopularMovie(params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            if (it.total_pages >= params.key) {
                                callback.onResult(it.movieList, params.key + 1)
                                networkState.postValue(NetworkState.LOADED)
                            } else {
                                networkState.postValue(NetworkState.ENDOFLIST)
                            }
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message!!)
                        }
                    )
            )
        } else {
            compositeDisposable.add(
                apiService.getTopMovie(params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            if (it.total_pages >= params.key) {
                                callback.onResult(it.movieList, params.key + 1)
                                networkState.postValue(NetworkState.LOADED)
                            } else {
                                networkState.postValue(NetworkState.ENDOFLIST)
                            }
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDataSource", it.message!!)
                        }
                    )
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }
}