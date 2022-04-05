package com.mttnow.android.app_tmdb.ui.Movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.repository.*
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel


class MovieViewModel() : BaseMovieViewModel() {

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSource: Pair<DataSource.Factory<Int, Movie>, LiveData<NetworkState>>

    //получаем аргумент с выбором запроса
    private var argQueryMoviePopular: Boolean = Const.GET_POPULAR_MOVIE
    fun getMovie(_i: Boolean) {
        argQueryMoviePopular = _i
    }


    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }


    fun getLiveMoviePagedList(): LiveData<PagedList<Movie>> {
        moviesDataSource = choisQery(argQueryMoviePopular)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Const.POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSource.first, config).build()
        return moviePagedList
    }


    fun getNetworkState(): LiveData<NetworkState> {
        moviesDataSource = choisQery(argQueryMoviePopular)
        return moviesDataSource.second
    }


    private fun choisQery(QueryMovie: Boolean): Pair<DataSource.Factory<Int, Movie>, LiveData<NetworkState>> {
        if (QueryMovie) {
            val moviesDataSourceFactory =
                MovieDataSourceFactoryPopular(apiService, compositeDisposable)

            val networkStateDataSource =
                Transformations.switchMap<MovieDataSourcePopular, NetworkState>(
                    moviesDataSourceFactory.moviesLiveDataSource,
                    MovieDataSourcePopular::networkState
                )
            return Pair(moviesDataSourceFactory, networkStateDataSource)

        } else {
            val moviesDataSourceFactory =
                MovieDataSourceFactoryTop(apiService, compositeDisposable)

            val networkStateDataSource =
                Transformations.switchMap<MovieDataSourceTop, NetworkState>(
                    moviesDataSourceFactory.moviesLiveDataSource, MovieDataSourceTop::networkState
                )
            return Pair(moviesDataSourceFactory, networkStateDataSource)
        }
    }
}