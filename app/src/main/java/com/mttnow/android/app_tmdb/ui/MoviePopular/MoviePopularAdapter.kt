package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.modeldata.Article
import com.mttnow.android.app_tmdb.modeldata.Movie
import com.mttnow.android.app_tmdb.ui.adapter.MovieItemViewHolder


class MoviePopularAdapter(private val onMovieClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listMovie = mutableListOf<Any>()

    fun addLoadingToList() {
        val item = LoadItem()
        listMovie.add(item)
        notifyItemInserted(listMovie.lastIndex)
    }

    fun deleteLoadingToList() {
        val item = listMovie.find { it is LoadItem }
        if (item != null){
            val positionRemove = listMovie.indexOf(item)
            listMovie.remove(item)
            notifyItemRemoved(positionRemove)
        }
    }

    fun addNewListToList(list: List<Any>) {
        listMovie.addAll(list)
        notifyItemRangeChanged(0, listMovie.size)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        //определяем загружаемый layout
        return when (viewType) {
            Const.MOVIE_VIEW_TYPE -> {
                view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
                val holder = MovieItemViewHolder(view, onMovieClick)
                holder.setOnClick() //инициализируем действие на элементе при создании холдера
                holder
            }
            Const.NETWORK_VIEW_TYPE -> {
                view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
                NetworkStateItemViewHolder(view)
            }
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Const.MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(listMovie[position] as Article)
        } else {
            (holder as NetworkStateItemViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listMovie[position]) {
            is Movie -> {
                Const.MOVIE_VIEW_TYPE
            }
            is LoadItem -> {
                Const.NETWORK_VIEW_TYPE
            }
            else -> {
                throw RuntimeException("Unknown Item: ${listMovie[position]}")
            }
        }
    }

    class LoadItem {

    }
}