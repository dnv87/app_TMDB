package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.repository.MovieDataSourcePopularNoPaging
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.modeldata.MovieResponse
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel
import io.reactivex.schedulers.Schedulers


class MoviePopularViewModelNoPaging() : BaseMovieViewModel() {

    private val movieNetworkDataSource = MovieDataSourcePopularNoPaging(apiService)

    private var pageToLoad = Const.FIRST_PAGE

    private val _itemsMovie = MutableLiveData<List<Movie>>()
    val itemsMovie: LiveData<List<Movie>>
        get() = _itemsMovie



    private fun loadItems(pageToLoad: Int) {
        compositeDisposable.add(
            movieNetworkDataSource.loadMoviePopular(pageToLoad)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _itemsMovie.postValue(it.movieList)
                    },{
                        Log.e("MovieDetailsDataSource", it.message!!)
                    }
                )
        )
    }
}
