package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.modeldata.ArticleItem
import com.mttnow.android.app_tmdb.modeldata.ColorItem


class NewsSportAdapter(private val onMovieClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val PAYLOAD_ITEM = "GREEN"
        const val MAX_POOL_SIZE = 30
    }

    val listNews = mutableListOf<Any>()


    fun addLoadingToList(item: LoadeMoreItem, index: Int) {
        listNews.add(item)
        notifyItemInserted(index)
    }

    fun removeLoadingToList(positionRemove: Int) {
        listNews.remove(listNews[positionRemove])
        notifyItemRemoved(positionRemove)

    }

    fun addNewListToList(list: List<Any>) {
        listNews.addAll(list)
        notifyItemRangeChanged(0, listNews.size, PAYLOAD_ITEM)
    }

    fun updateListTimer() {
        for (i in listNews.indices) {
            val item = listNews[i]
            if (item is ArticleItem) {
                when (item.color) {
                    ColorItem.BLUE -> {
                        item.setItemColor(ColorItem.ORANGE)
                    }
                    ColorItem.ORANGE -> {
                        item.setItemColor(ColorItem.BLUE)
                    }
                    ColorItem.GREEN -> {}
                    else -> {
                        item.setItemColor(ColorItem.ORANGE)
                    }
                }
                notifyItemChanged(i, PAYLOAD_ITEM)
            }
        }
    }

    fun updateColorTitle(position: Int) {
        if (position < listNews.size) {
            val item = listNews[position]
            if (item is ArticleItem) {
                if (item.color == ColorItem.GREEN) {
                    defaultColorTitle(item)
                } else {
                    item.setItemColor(ColorItem.GREEN)
                }
            }
            notifyItemChanged(position, PAYLOAD_ITEM)
        }
    }

    fun defaultListColorTitle() {
        for (i in listNews.indices) {
            val item = listNews[i]
            if (item is ArticleItem) {
                if (item.color != ColorItem.GREEN) {
                    defaultColorTitle(item)
                }

            }
        }
    }

    private fun defaultColorTitle(item: ArticleItem) {
        if (item.parity) {
            item.setItemColor(ColorItem.RED)
        } else {
            item.setItemColor(ColorItem.BLACK)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        //определяем загружаемый layout
        return when (viewType) {
            Const.MOVIE_VIEW_TYPE -> {
                view = layoutInflater.inflate(R.layout.news_list_item, parent, false)
                val holder = NewsItemViewHolder(view, onMovieClick)
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


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        @NonNull payloads: MutableList<Any>
    ) {
        Log.d("RW", "payloads")
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            for (payload in payloads) {
                if (payload == PAYLOAD_ITEM && getItemViewType(position) == Const.MOVIE_VIEW_TYPE) {
                    (holder as NewsItemViewHolder).bind(listNews[position] as ArticleItem, position)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Const.MOVIE_VIEW_TYPE) {
            (holder as NewsItemViewHolder).bind(listNews[position] as ArticleItem, position)
        } else {
            (holder as NetworkStateItemViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (listNews[position]) {
            is ArticleItem -> {
                Const.MOVIE_VIEW_TYPE
            }
            is LoadeMoreItem -> {
                Const.NETWORK_VIEW_TYPE
            }
            else -> {
                throw RuntimeException("Unknown Item: ${listNews[position]}")
            }
        }
    }

    class LoadeMoreItem
}