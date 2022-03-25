package com.mttnow.android.app_tmdb.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mttnow.android.app_tmdb.R
import com.mttnow.android.app_tmdb.data.apiNetwork.NetworkState
import com.mttnow.android.app_tmdb.modeldata.Movie

class MoviePagedListAdapter (val onMovieCkick: (Int)-> Unit)
    : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieItemViewHolder(view,onMovieCkick)

        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position))
            Log.d("my", "position = ${position.toString()}")
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.findViewById<View>(R.id.progress_bar_item).visibility = View.VISIBLE;
            }
            else  {
                itemView.findViewById<View>(R.id.progress_bar_item).visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.findViewById<View>(R.id.error_msg_item).visibility = View.VISIBLE;
                itemView.findViewById<TextView>(R.id.error_msg_item).text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.findViewById<View>(R.id.error_msg_item).visibility = View.VISIBLE;
                itemView.findViewById<TextView>(R.id.error_msg_item).text = networkState.msg;
            }
            else {
                itemView.findViewById<View>(R.id.error_msg_item).visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }

}