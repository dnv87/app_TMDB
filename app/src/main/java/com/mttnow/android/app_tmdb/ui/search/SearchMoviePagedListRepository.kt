package com.mttnow.android.app_tmdb.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.SearchMovieDataSourceFactory
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class SearchMoviePagedListRepository(private val apiService: TMDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: SearchMovieDataSourceFactory


    fun fetchLiveMoviePagedList(
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
        return moviePagedList
    }

//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<SearchMovieDataSource, NetworkState>(
//            moviesDataSourceFactory.moviesLiveDataSource?, SearchMovieDataSource::networkState
//        )
//    }

//    fun invalidDataSource() {
//        moviesDataSourceFactory.searchMovieDataSource?.invalidate()
//    }
}