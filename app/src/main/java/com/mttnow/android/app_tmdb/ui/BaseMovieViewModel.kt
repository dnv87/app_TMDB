package com.mttnow.android.app_tmdb.ui

import androidx.lifecycle.ViewModel
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBConnect
import com.mttnow.android.app_tmdb.data.apiNetwork.TMDBInterface
import io.reactivex.disposables.CompositeDisposable

abstract class BaseMovieViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}