package com.mttnow.android.app_tmdb.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : TMDBInterface,
                              private val compositeDisposable: CompositeDisposable,
                              private val getMovie:Boolean)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable,getMovie)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}