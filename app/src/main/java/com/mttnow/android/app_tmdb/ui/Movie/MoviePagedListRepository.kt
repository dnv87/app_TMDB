package com.mttnow.android.app_tmdb.ui.Movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.MovieDataSource
import com.mttnow.android.app_tmdb.data.repository.MovieDataSourceFactory
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService : TMDBInterface,private val getMovie:Boolean = Const.GET_POPULAR_MOVIE) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable,getMovie)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Const.POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }
}