package com.mttnow.android.app_tmdb.ui.MoviePopular

import androidx.recyclerview.widget.DiffUtil
import com.mttnow.android.app_tmdb.modeldata.Movie

class MovieListDiffCallback (
    private val oldList: List<Movie>,
    private val newList: List<Movie>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}