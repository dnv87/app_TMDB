package com.mttnow.android.app_tmdb.ui.Movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.MovieDataSource
import com.mttnow.android.app_tmdb.data.repository.MovieDataSourceFactory
import com.mttnow.android.app_tmdb.modeldata.Movie
import io.reactivex.disposables.CompositeDisposable

//следут наследоваться от абстрактной ViewModel  где CompositeDisposable() создаётся 1 раз
// и переопределёна ф-ция override fun onCleared()

class MovieViewModel(private val apiService : TMDBInterface, private val getMovie:Boolean = Const.GET_POPULAR_MOVIE): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    lateinit var moviePageList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    val  moviePagedList = fetchLiveMoviePagedList(compositeDisposable)

    val  networkState: LiveData<NetworkState> = getNetworkState()


    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    private fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable
    ) : LiveData<PagedList<Movie>> {

        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable,getMovie)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Const.POST_PER_PAGE)
            .build()

        moviePageList = LivePagedListBuilder(moviesDataSourceFactory, config).build()
        return moviePageList
    }

    @JvmName("getNetworkState1")
    private fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }

    override fun onCleared() {
        super.onCleared()
//        compositeDisposable.dispose()  //разобрать в чём разница!!!!
        compositeDisposable.clear()
    }

}