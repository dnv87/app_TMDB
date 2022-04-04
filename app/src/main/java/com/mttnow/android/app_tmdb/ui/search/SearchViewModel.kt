package com.mttnow.android.app_tmdb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.repository.MovieDataSourceSearch
import com.mttnow.android.app_tmdb.data.repository.SearchMovieDataSourceFactory
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel() : BaseMovieViewModel() {

    var moviePagedList: LiveData<PagedList<Movie>>? = null
    private lateinit var moviesDataSourceFactory: SearchMovieDataSourceFactory

    fun listIsEmpty(): Boolean {
        return moviePagedList?.value?.isEmpty() ?: true
    }

    fun Search(searchMovietext: String): LiveData<PagedList<Movie>> {
        return fetchLiveMoviePagedList(
            compositeDisposable,
            searchMovietext
        )
    }

    private fun fetchLiveMoviePagedList(
        compositeDisposable: CompositeDisposable,
        searchQueryMovie: String
    ): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory =
            SearchMovieDataSourceFactory(apiService, compositeDisposable)

        //изменяем переменную searchMovieText в moviesDataSourceFactory,
        // т.к. SearchMovieDataSource инициализируется там.
        moviesDataSourceFactory.searchMovieText(searchQueryMovie)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Const.POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePagedList!!
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSourceSearch, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSourceSearch::networkState
        )
    }

//    fun invalidDataSource() {
//        moviesDataSourceFactory.searchMovieDataSource?.invalidate()
//    }

}