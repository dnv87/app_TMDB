package com.mttnow.android.app_tmdb.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.SearchMovieDataSource
import com.mttnow.android.app_tmdb.data.repository.SearchMovieDataSourceFactory
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel (private val apiService : TMDBInterface, private val getMovie:String):ViewModel() {

    lateinit var moviesDataSourceFactory: SearchMovieDataSourceFactory

    private val compositeDisposable = CompositeDisposable()
    private var searchNewMovie:String = ""


    var moviePagedList = fetchLiveMoviePagedList(compositeDisposable)

    val  networkState = getNetworkState()


    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    private fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {

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

    @JvmName("getNetworkState1")
    private fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<SearchMovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, SearchMovieDataSource::networkState)
    }


    override fun onCleared() {
        super.onCleared()
//        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}