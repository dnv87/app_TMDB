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

    val moviesLiveDataSource = MutableLiveData<SearchMovieDataSource>()
    var searchMovieDataSource: SearchMovieDataSource? = null

    private var searchMovieText = ""
    fun searchMovieText(_str: String) {
        searchMovieText = _str
    }

    override fun create(): DataSource<Int, Movie> {
        searchMovieDataSource = SearchMovieDataSource(apiService, compositeDisposable)
        searchMovieDataSource?.textQueryMovie(searchMovieText)

        moviesLiveDataSource.postValue(searchMovieDataSource)
        return searchMovieDataSource!!
    }
}