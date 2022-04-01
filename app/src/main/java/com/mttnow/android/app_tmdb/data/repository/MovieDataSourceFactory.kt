package com.mttnow.android.app_tmdb.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: TMDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    private var getQueryMovie: Boolean = Const.GET_POPULAR_MOVIE
    fun queryMovie(_b: Boolean) {
        getQueryMovie = _b
    }

    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        movieDataSource.queryMovie(getQueryMovie)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}