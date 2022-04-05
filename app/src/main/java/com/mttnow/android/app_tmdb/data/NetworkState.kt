package com.mttnow.android.app_tmdb.data.apiNetwork

class NetworkState(val status: Status, val msg: String) {

    companion object {

        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState
        val FIRSTLOADING:NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")

            LOADING = NetworkState(Status.RUNNING, "Running")

            ERROR = NetworkState(Status.FAILED, "Something went wrong")

            ENDOFLIST = NetworkState(Status.FAILED, "You have reached the end")

            FIRSTLOADING = NetworkState(Status.RUNNING, "Running first Page")
        }
    }
}

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED

}