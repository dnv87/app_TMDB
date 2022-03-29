package com.mttnow.android.app_tmdb.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.MovieResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SearchMoviePagingSource(private val apiService : TMDBInterface,
                              private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
    get() = _networkState //with this get, no need to implement get function to get networkSate

    private val _downloadedMovieDetailsResponse =  MutableLiveData<MovieResponse>()
    val downloadedMovieResponse: LiveData<MovieResponse>
    get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(page: Int, getMovie: String) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getSearchMovie(page = page, query=getMovie)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                            Log.d("my", it.movieList.toString())
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("my", it.message!!)
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("MovieDetailsDataSource",e.message!!)
        }


    }
}

