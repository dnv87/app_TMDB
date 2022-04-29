package com.mttnow.android.app_tmdb.ui.MoviePopular

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mttnow.android.app_tmdb.R

class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val pbItemLoad = itemView.findViewById<View>(R.id.progress_bar_item)
    private val tvErrMessageItemTv = itemView.findViewById<TextView>(R.id.error_msg_item)


    fun bind() {
        pbItemLoad.visibility = View.VISIBLE
        tvErrMessageItemTv.text = "Loading"
    }
}