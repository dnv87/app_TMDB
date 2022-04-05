package com.mttnow.android.app_tmdb.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mttnow.android.app_tmdb.data.Const.FIRST_PAGE
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MovieDataSourceSearch(
    private val apiService: TMDBInterface,
    private val compositeDisposable: CompositeDisposable,
) : PageKeyedDataSource<Int, Movie>() {

    private var page = FIRST_PAGE

//--------------------------------------------------------------
// пробрасываем SearchQueryMovie из SearchMovieDataFactory
    private var SearchQueryMovie = ""
    fun textQueryMovie(_str: String) {
        SearchQueryMovie = _str
    }
//--------------------------------------------------------------

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getSearchMovie(page = page, query = SearchQueryMovie)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                        Log.d("my", "SearchMovieDataSource ${it.toString()}")
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("SearchMovieDataSource", it.message!!)
                    }
                )
        )
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)
         compositeDisposable.add(
            apiService.getSearchMovie(page = params.key, query = SearchQueryMovie)
                .subscribeOn(Schedulers.io())
                //.delaySubscription(5000, TimeUnit.MILLISECONDS)
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


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //not used
    }
}