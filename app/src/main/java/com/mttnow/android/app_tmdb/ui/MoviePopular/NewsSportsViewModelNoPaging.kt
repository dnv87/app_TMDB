package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.apiNetwork.NEWSInterface
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.data.apiNetwork.NewsConnect
import com.mttnow.android.app_tmdb.data.repository.NewsDataSourceSportsNoPaging
import com.mttnow.android.app_tmdb.modeldata.ArticleItem
import com.mttnow.android.app_tmdb.modeldata.ColorItem
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel
import com.mttnow.android.app_tmdb.ui.utils.ModelPreferencesManager
import com.mttnow.android.app_tmdb.ui.utils.ValidateUser
import io.reactivex.schedulers.Schedulers


class NewsSportsViewModelNoPaging() : BaseMovieViewModel() {

    private val apiService: NEWSInterface = NewsConnect.getClient()
    private val newsNetworkDataSource = NewsDataSourceSportsNoPaging(apiService)

    private var pageToLoad = Const.FIRST_PAGE
    private var pageSize = Const.POST_PER_PAGE

    private val _itemsNews = MutableLiveData<List<ArticleItem>>()
    val itemsNews: LiveData<List<ArticleItem>>
        get() = _itemsNews

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState



    @SuppressLint("CheckResult")
    fun checkValidation(): Boolean {
        val savesUsers = ModelPreferencesManager.SharedPrefGet(Const.USER)
        var result = false
        ValidateUser.validateUser(savesUsers)
        ValidateUser.isValidate.subscribe {
            result = it
        }
        return result
    }



    fun loadItems() {
        if (pageToLoad == Const.FIRST_PAGE) {
            _networkState.postValue(NetworkState.FIRSTLOADING)
        } else {
            _networkState.postValue(NetworkState.LOADING)
        }

        compositeDisposable.add(
            newsNetworkDataSource.loadNewsSports(pageToLoad, pageSize)
                .subscribeOn(Schedulers.io())
//                .delay(3, TimeUnit.SECONDS)
                .map {
                    val articleList = mutableListOf<ArticleItem>()
                    var color = ColorItem.RED
                    var parity = true

                    for (i in it.newsList.indices) {
                        if (i % 2 != 0) {
                            color = ColorItem.RED
                            parity = true
                        } else {
                            color = ColorItem.BLACK
                            parity = false
                        }
                        val item = ArticleItem(itemArticle = it.newsList[i], color, parity)
                        articleList.add(item)
                    }
                    articleList
                }
                .subscribe(
                    {
                        _itemsNews.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                        Log.d("my", "responce ${it.toString()}")
                        pageToLoad++
                    }, {
                        Log.e("MovieDetailsDataSource", it.message!!)
                        _networkState.postValue(NetworkState.ERROR)
                    }
                )
        )
    }
}
