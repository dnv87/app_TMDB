package com.mttnow.android.app_tmdb.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class MovieViewModelAll: ViewModel() {
    val compositeDisposable = CompositeDisposable()




    override fun onCleared() {
        super.onCleared()
//        compositeDisposable.dispose()
        compositeDisposable.clear()
    }
}