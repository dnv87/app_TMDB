package com.mttnow.android.app_tmdb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.SearchMovieDataSource
import com.mttnow.android.app_tmdb.data.repository.SearchMovieDataSourceFactory
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class SearchMoviePagedListRepository (private val apiService : TMDBInterface,private val getMovie:String = "") {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: SearchMovieDataSourceFactory
    private var searchNewMovie:String = ""

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {

        moviesDataSourceFactory = SearchMovieDataSourceFactory(apiService, compositeDisposable,getMovie)


        if (getMovie == searchNewMovie) {
            val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Const.POST_PER_PAGE)
                .build()
        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        } else {
            searchNewMovie = getMovie
            val config = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Const.POST_PER_PAGE)
                .build()
            moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        }

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<SearchMovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, SearchMovieDataSource::networkState)
    }
}