package com.mttnow.android.app_tmdb.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.modeldata.Movie

class MovieItemViewHolder(view: View, val onMovieCkick: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {

    //Всегда findViewById вызывать взаранеедля ViewHolder
    private val tvMovieTitle = itemView.findViewById<TextView>(R.id.cv_movie_title)
    private val tvMovieRelease = itemView.findViewById<TextView>(R.id.cv_movie_release_date)
    private val ivMoviePoster = itemView.findViewById<ImageView>(R.id.cv_iv_movie_poster)


    private var movieId: Int = 0

    fun bind(movie: Movie?) {

        tvMovieTitle.text = movie?.title
        tvMovieRelease.text = movie?.releaseDate

        if (movie?.poster_path != null) {
            val moviePosterURL = Const.THE_MOVIES_DB_IMAGE_BASE_URL_WITH_SIZE342 + movie.poster_path
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(ivMoviePoster)
        }else ivMoviePoster.setImageResource(R.drawable.poster_placeholder)

        movieId = movie!!.id
    }


    //инициализирован в onCreateViewHolder
    fun setOnClick() {
        itemView.setOnClickListener {
            onMovieCkick.invoke(movieId)
        }
    }

}