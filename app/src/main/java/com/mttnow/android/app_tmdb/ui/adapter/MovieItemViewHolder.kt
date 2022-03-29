package com.mttnow.android.app_tmdb.ui.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.modeldata.Movie

class MovieItemViewHolder (view: View, private val onMovieClick: (Int)-> Unit) : RecyclerView.ViewHolder(view) {

    //!!!!!!!! взаранее нужно инициализировать findViewById<>
    private val tvMovieTitle: TextView = view.findViewById<TextView>(R.id.cv_movie_title)
    private val ivMoviePoster: ImageView = view.findViewById<ImageView>(R.id.cv_iv_movie_poster)


    // следует избавиться от setOnClickListener здесь
    fun bind(movie: Movie?) {

        tvMovieTitle.text = movie?.title
        tvMovieTitle.text =  movie?.releaseDate

        val moviePosterURL = Const.THE_MOVIES_DB_IMAGE_BASE_URL_WITH_SIZE342 + movie?.poster_path
        val into = Glide.with(itemView.context)
            .load(moviePosterURL)
            .into(ivMoviePoster)

        itemView.setOnClickListener{
            Log.d("my", "setOnClickListener ${movie!!.id}")
            onMovieClick.invoke(movie!!.id)
        }
    }


}