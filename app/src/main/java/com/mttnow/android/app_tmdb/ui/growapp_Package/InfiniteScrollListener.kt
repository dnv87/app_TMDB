package com.mttnow.android.app_tmdb.ui.growapp_Package

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mttnow.android.app_tmdb.ui.MoviePopular.NewsSportAdapter


typealias LoadMoreCallback = (offset: Int) -> Unit

class InfiniteScrollListener(
    private val recyclerView: RecyclerView,
    private val manager: LayoutManagerAdapter<*>,
    private val adapter: NewsSportAdapter,
    itemThreshold: Int = DEFAULT_ITEM_THRESHOLD,
    private val callback: LoadMoreCallback
) : RecyclerView.OnScrollListener() {

    companion object {
        const val DEFAULT_ITEM_THRESHOLD = 5
    }

    constructor(
        recyclerView: RecyclerView,
        adapter: NewsSportAdapter,
        itemThreshold: Int = DEFAULT_ITEM_THRESHOLD,
        callback: LoadMoreCallback
    ) : this(
        recyclerView,
        recyclerView.layoutManager.let {
            when (it) {
                is LinearLayoutManager -> LinearLayoutManagerAdapter(it)
                is StaggeredGridLayoutManager -> StaggeredGridLayoutManagerAdapter(it)
                is GridLayoutManager -> GridLayoutManagerAdapter(it)
                else -> throw IllegalArgumentException("Unknown type of RecyclerView.LayoutManager, pass LayoutManagerAdapter directly")
            }
        }, adapter, itemThreshold, callback
    )

    private val itemThreshold by lazy {
        itemThreshold * manager.thresholdMultiplier
    }

    private var previousTotalItemCount = 0
    var isLoading: Boolean = true
        private set
    var isDisabled: Boolean = false
        private set

    private val loadMoreItem = NewsSportAdapter.LoadeMoreItem()

    private val progressItemAddPosition: Int
        get() = adapter.itemCount
    private val progressItemRemovePosition: Int
        get() = adapter.listNews.indexOfLast { it === loadMoreItem }

    private var isAddProgressIndicatorQueued = false

    private val addProgressIndicatorTask = Runnable {
        adapter.addLoadingToList(loadMoreItem, progressItemAddPosition)
        previousTotalItemCount++
        isAddProgressIndicatorQueued = false
        Log.d("scrollListener", isAddProgressIndicatorQueued.toString())
    }

    fun showProgressIndicator() {
        isAddProgressIndicatorQueued = true
        recyclerView.post(addProgressIndicatorTask)
    }

    fun hideProgressIndicator() {
        Log.d("scrollListener", "hideProgressIndicator ${isAddProgressIndicatorQueued.toString()}")
        if (isAddProgressIndicatorQueued) {
            recyclerView.removeCallbacks(addProgressIndicatorTask)
        } else {
            removeProgressIndicatorIfExist()
        }
    }

    private fun removeProgressIndicatorIfExist() {
        progressItemRemovePosition.takeIf { it >= 0 }?.let {
            adapter.removeLoadingToList(it)
            Log.d("scrollListener", "removeLoading ${it.toString()}")
        }
    }

    fun resetState() {
        isLoading = false
        previousTotalItemCount = 0
    }

    fun enable() {
        isDisabled = false
    }

    fun disable() {
        isDisabled = true
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (isDisabled) {
            return
        }
        val totalItemCount = manager.itemCount
        val lastVisibleItem = manager.lastVisibleItemPosition

        if (isLoading && (totalItemCount - previousTotalItemCount > 2)) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        if (isLoading.not() && (lastVisibleItem + itemThreshold) >= totalItemCount) {
            isLoading = true
            callback(totalItemCount)
        }
    }
}