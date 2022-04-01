package com.mttnow.android.app_tmdb.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState

class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val pbItemLoad = itemView.findViewById<View>(R.id.progress_bar_item)
    private val tvErrMessageItemV = itemView.findViewById<View>(R.id.error_msg_item)
    private val tvErrMessageItemTv = itemView.findViewById<TextView>(R.id.error_msg_item)

    fun bind(networkState: NetworkState?) {
        if (networkState != null && networkState == NetworkState.LOADING) {
            pbItemLoad.visibility = View.VISIBLE;
        } else {
            pbItemLoad.visibility = View.GONE;
        }

        if (networkState != null && networkState == NetworkState.ERROR) {
            tvErrMessageItemV.visibility = View.VISIBLE;
            tvErrMessageItemTv.text = networkState.msg;
        } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
            tvErrMessageItemV.visibility = View.VISIBLE;
            tvErrMessageItemTv.text = networkState.msg;
        } else {
            tvErrMessageItemV.visibility = View.GONE;
        }
    }
}