package com.mttnow.android.app_tmdb.ui.MoviePopular

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBConnect
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.*
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel


class MoviePopularViewModel() : BaseMovieViewModel() {
    val apiService: TMDBInterface = TMDBConnect.getClient()

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactoryPopular


    fun getLiveMoviePagedList(): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactoryPopular(apiService, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Const.POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }


    fun getNetworkState(): LiveData<NetworkState> {
        return moviesDataSourceFactory.getNetworkStateLiveData()
//        return Transformations.switchMap<MovieDataSourcePopular, NetworkState>(
//            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSourcePopular::networkState
//        )

    }


    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }
}