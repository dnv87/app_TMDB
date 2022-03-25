package com.mttnow.android.app_tmdb.ui.Movie

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.data.MAIN
import com.mttnow.android.app_tmdb.modeldata.Movie

class MovieItemViewHolder (view: View, val onMovieCkick: (Int)-> Unit) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie?) {

        itemView.findViewById<TextView>(R.id.cv_movie_title).text = movie?.title
        itemView.findViewById<TextView>(R.id.cv_movie_release_date).text =  movie?.releaseDate

        val moviePosterURL = Const.THE_MOVIES_DB_IMAGE_BASE_URL_WITH_SIZE342 + movie?.poster_path
        val into = Glide.with(itemView.context)
            .load(moviePosterURL)
            .into(itemView.findViewById<ImageView>(R.id.cv_iv_movie_poster))

        itemView.setOnClickListener{

            Log.d("my", "setOnClickListener ${movie!!.id}")

/*            // бывает глючит
            val action = MovieFragmentDirections.actionNavigationMovieToNavigationMovieDetail(movie!!.id)
            MAIN.navControl.navigate(action)*/

            onMovieCkick.invoke(movie!!.id)

        }
    }


}