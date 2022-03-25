package com.mttnow.android.app_tmdb.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.mttnow.android.app_tmdb.modeldata.Movie

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}