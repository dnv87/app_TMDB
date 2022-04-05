package com.mttnow.android.app_tmdb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactoryTop(
    private val apiService: TMDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSourceTop>()
    private var movieDataSource: MovieDataSourceTop? = null


    override fun create(): DataSource<Int, Movie> {
        movieDataSource = MovieDataSourceTop(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource!!)
        return movieDataSource!!
    }
}
