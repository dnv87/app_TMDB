package com.mttnow.android.app_tmdb.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.modeldata.Article

class MovieItemViewHolder(view: View, val onMovieCkick: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {

    //Всегда findViewById вызывать взаранеедля ViewHolder
    private val tvMovieTitle = itemView.findViewById<TextView>(R.id.cv_movie_title)
    private val tvMovieRelease = itemView.findViewById<TextView>(R.id.cv_movie_release_date)
    private val ivMoviePoster = itemView.findViewById<ImageView>(R.id.cv_iv_movie_poster)

    private var movieId: Int = 0


    fun bind(news: Article?) {

        tvMovieTitle.text = news?.title
        tvMovieRelease.text = news?.description

        if (news?.urlToImage!= null) {
            val newsPosterURL =  news.urlToImage
            Glide.with(itemView.context)
                .load(newsPosterURL)
                .into(ivMoviePoster)
        } else ivMoviePoster.setImageResource(R.drawable.ic_baseline_local_movies_24)

//        movieId = news!!
    }


    //инициализирован в onCreateViewHolder
    fun setOnClick() {
        itemView.setOnClickListener {
            onMovieCkick.invoke(movieId)
        }
    }

}