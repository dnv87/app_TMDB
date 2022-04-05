package com.mttnow.android.app_tmdb.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactoryPopular(
    private val apiService: TMDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSourcePopular>()
    private var movieDataSource: MovieDataSourcePopular? = null


    override fun create(): DataSource<Int, Movie> {
        movieDataSource = MovieDataSourcePopular(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource!!)
        return movieDataSource!!
    }

}