package com.mttnow.android.app_tmdb.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class SearchMovieDataSourceFactory (private val apiService : TMDBInterface,
                                    private val compositeDisposable: CompositeDisposable,
                                    private val getMovie:String)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<SearchMovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = SearchMovieDataSource(apiService,compositeDisposable,getMovie)
        Log.d("my", "SMDSF ${getMovie}")

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}