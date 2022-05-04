package com.mttnow.android.app_tmdb.ui.Autorization


import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel


class AutorizViewModel() : BaseMovieViewModel() {
    private val trueLogPass = Pair("root", "admin")

    fun checkBtnAutoriz(login: String?, password: String?): Boolean {
        val result: Boolean
        if ((login == trueLogPass.first) && (password == trueLogPass.second)) {
            result = true
        } else {
            result = false
        }
        return result
    }

}