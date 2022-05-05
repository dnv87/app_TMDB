package com.mttnow.android.app_tmdb.ui.Autorization

import android.annotation.SuppressLint
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel
import com.mttnow.android.app_tmdb.ui.utils.ValidateUser
import com.mttnow.android.app_tmdb.ui.utils.ValidateUser.isValidate


class AutorizViewModel() : BaseMovieViewModel() {


    @SuppressLint("CheckResult")
    fun checkValidation(user: Pair<String?, String?>?): Boolean {
        var result = false
        ValidateUser.validateUser(user)
        isValidate.subscribe {
            result = it
        }
        return result
    }
}
