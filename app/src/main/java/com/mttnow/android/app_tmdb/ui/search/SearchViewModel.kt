package com.mttnow.android.app_tmdb.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBConnect
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import com.mttnow.android.app_tmdb.data.repository.SearchMoviePagingSource
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.modeldata.MovieResponse
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel ():ViewModel() {

//    lateinit var movieSearchNetworkDataSource: SearchMoviePagingSource

    private val compositeDisposable = CompositeDisposable()
//    private var searchNewMovie:String = ""


    private var page = Const.FIRST_PAGE

//    var moviePagedList = fetchSearchMovie(compositeDisposable, getMovie)


//    val  networkState = getSearchMovieNetworkState()

    fun zapros(getMovie:String) {

        val apiService : TMDBInterface = TMDBConnect.getClient()
        var moviePagedList = fetchSearchMovie(compositeDisposable, getMovie, apiService)
        val ddd = moviePagedList.value?.copy()

        Log.d("my" , "movieResp = ${moviePagedList.value?.page.toString()} ")
        Log.d("my" , "movieResp = $ddd ")
    }

//    fun chtototam(){
//        Log.d("my" , "movieResp = ${moviePagedList.value.toString()} ")
//        Log.d("my" , "movieResp = ${moviePagedList.value?.page.toString()} ")
//    }

    fun clear(){
        clearDisposible(compositeDisposable)
    }

//    fun listIsEmpty(): Boolean {
//        return moviePagedList.value?/*.isEmpty()*/ ?: true
//    }

    fun fetchSearchMovie (compositeDisposable: CompositeDisposable, movieSearch: String, apiService: TMDBInterface) : LiveData<MovieResponse> {

        var movieSearchNetworkDataSource = SearchMoviePagingSource(apiService,compositeDisposable)
        movieSearchNetworkDataSource.fetchMovieDetails(page= page, getMovie=movieSearch)
        Log.d("my" , "SearchViewModel $movieSearch");


        return movieSearchNetworkDataSource.downloadedMovieResponse
    }

//    private fun getSearchMovieNetworkState(): LiveData<NetworkState> {
//        return movieSearchNetworkDataSource.networkState
//    }

    private fun clearDisposible(compositeDisposable:CompositeDisposable){
        compositeDisposable.dispose()
        Log.d("my", "clear disposible")
    }


    override fun onCleared() {
        super.onCleared()
        clear()
        compositeDisposable.clear()
    }
}