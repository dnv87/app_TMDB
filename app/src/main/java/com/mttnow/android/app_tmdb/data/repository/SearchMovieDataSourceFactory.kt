package com.mttnow.android.app_tmdb.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class SearchMovieDataSourceFactory(
    private val apiService: TMDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSourceSearch>()
    var movieDataSourceSearch: MovieDataSourceSearch? = null

    private var searchMovieText = ""
    fun searchMovieText(_str: String) {
        searchMovieText = _str
    }

    override fun create(): DataSource<Int, Movie> {
        movieDataSourceSearch = MovieDataSourceSearch(apiService, compositeDisposable)
        movieDataSourceSearch?.textQueryMovie(searchMovieText)

        moviesLiveDataSource.postValue(movieDataSourceSearch)
        return movieDataSourceSearch!!
    }
}