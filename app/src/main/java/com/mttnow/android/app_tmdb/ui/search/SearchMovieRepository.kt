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
import com.mttnow.android.app_tmdb.data.repository.SearchMoviePagingSource
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.modeldata.MovieResponse
import io.reactivex.disposables.CompositeDisposable

class SearchMovieRepository (private val apiService : TMDBInterface) {

    lateinit var movieSearchNetworkDataSource: SearchMoviePagingSource

    lateinit var moviePagedList: LiveData<MovieResponse>
    private var page = Const.FIRST_PAGE


    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieSearch: String) : LiveData<MovieResponse> {

        movieSearchNetworkDataSource = SearchMoviePagingSource(apiService,compositeDisposable)
        movieSearchNetworkDataSource.fetchMovieDetails(page= page, getMovie=movieSearch)

//        val moviePaged = movieSearchNetworkDataSource.downloadedMovieResponse.movieList
        return movieSearchNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieSearchNetworkDataSource.networkState
    }






    /*fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {

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
*/


}