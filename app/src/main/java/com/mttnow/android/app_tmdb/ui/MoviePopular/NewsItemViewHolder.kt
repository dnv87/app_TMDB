package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.modeldata.Article
import com.mttnow.android.app_tmdb.modeldata.ArticleItem
import com.mttnow.android.app_tmdb.modeldata.ColorItem


class NewsItemViewHolder(view: View, val onMovieCkick: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {

    //Всегда findViewById вызывать взаранеедля ViewHolder
    private val tvMovieTitle = itemView.findViewById<TextView>(R.id.cv_news_title)
    private val tvMovieRelease = itemView.findViewById<TextView>(R.id.cv_news_description)
    private val ivMoviePoster = itemView.findViewById<ImageView>(R.id.cv_iv_news_poster)

    private val colorRed = android.R.color.holo_red_light
    private val colorBlack = android.R.color.black
    private val colorGreen = android.R.color.holo_green_dark
    private val colorBlue = android.R.color.holo_blue_bright
    private val colorOrange = android.R.color.holo_orange_dark

    private var movieId: Int = 0


    fun bind(news: ArticleItem?, position: Int) {

        tvMovieTitle.text = news?.itemArticle?.title ?: "No Title"
        tvMovieRelease.text = news?.itemArticle?.description ?: "No description"

        if (news?.parity == true) {
            downloadPoster(news = news.itemArticle)
            ivMoviePoster.visibility = View.VISIBLE
        } else {
            ivMoviePoster.visibility = View.GONE
        }

        when (news!!.color) {
            ColorItem.RED -> {setColorTitle(colorRed)}
            ColorItem.BLACK -> {setColorTitle(colorBlack)}
            ColorItem.GREEN -> {setColorTitle(colorGreen)}
            ColorItem.BLUE -> {setColorTitle(colorBlue)}
            ColorItem.ORANGE -> {setColorTitle(colorOrange)}
        }
        movieId = position
    }

    private fun setColorTitle(color: Int) {
        tvMovieTitle.setTextColor(ContextCompat.getColor(itemView.context, color))
    }

    private fun downloadPoster(news: Article?) {
        if (news?.urlToImage != null) {
            val newsPosterURL = news.urlToImage
            Glide.with(itemView.context)
                .load(newsPosterURL)
                .into(ivMoviePoster)
        } else ivMoviePoster.setImageResource(R.drawable.ic_baseline_local_movies_24)
    }


    //инициализирован в onCreateViewHolder
    fun setOnClick() {
        itemView.setOnClickListener {
            Log.d("my", "position = ${movieId.toString()}")

            onMovieCkick.invoke(movieId)
        }
    }


}