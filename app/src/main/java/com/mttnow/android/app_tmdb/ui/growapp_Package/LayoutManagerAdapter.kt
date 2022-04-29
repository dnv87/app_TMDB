package com.mttnow.android.app_tmdb.ui.growapp_Package

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class LayoutManagerAdapter<out T : RecyclerView.LayoutManager>(val manager: T) {
    abstract val firstVisibleItemPosition: Int
    abstract val lastVisibleItemPosition: Int
    abstract val thresholdMultiplier: Int
    val itemCount get() = manager.itemCount
}

class LinearLayoutManagerAdapter(manager: LinearLayoutManager
) : LayoutManagerAdapter<LinearLayoutManager>(manager) {
    override val firstVisibleItemPosition get() = manager.findFirstVisibleItemPosition()
    override val lastVisibleItemPosition get() = manager.findLastVisibleItemPosition()
    override val thresholdMultiplier = 1
}

class StaggeredGridLayoutManagerAdapter(manager: StaggeredGridLayoutManager
) : LayoutManagerAdapter<StaggeredGridLayoutManager>(manager) {
    override val firstVisibleItemPosition get() = manager.findFirstVisibleItemPositions(null).minOrNull() ?: 0
    override val lastVisibleItemPosition get() = manager.findLastVisibleItemPositions(null).maxOrNull() ?: 0
    override val thresholdMultiplier get() = manager.spanCount
}

class GridLayoutManagerAdapter(manager: GridLayoutManager
) : LayoutManagerAdapter<GridLayoutManager>(manager) {
    override val firstVisibleItemPosition get() = manager.findFirstVisibleItemPosition()
    override val lastVisibleItemPosition get() = manager.findLastVisibleItemPosition()
    override val thresholdMultiplier = manager.spanCount
}