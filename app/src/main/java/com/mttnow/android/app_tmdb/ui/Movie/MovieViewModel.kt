package com.mttnow.android.app_tmdb.ui.Movie

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.repository.MovieDataSourceFactoryPopular
import com.mttnow.android.app_tmdb.data.repository.MovieDataSourceFactoryTop
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel


class MovieViewModel() : BaseMovieViewModel() {

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: DataSource.Factory<Int, Movie>
    private var argQueryMoviePopular: Boolean = Const.GET_POPULAR_MOVIE

    fun getMovie(_i: Boolean) {
        argQueryMoviePopular = _i
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    private lateinit var networkState:LiveData<NetworkState>


    fun fetchLiveMoviePagedList(): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = if (argQueryMoviePopular) {
            MovieDataSourceFactoryPopular(apiService, compositeDisposable)
        } else {
            MovieDataSourceFactoryTop(apiService, compositeDisposable)
        }

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Const.POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList
    }
}