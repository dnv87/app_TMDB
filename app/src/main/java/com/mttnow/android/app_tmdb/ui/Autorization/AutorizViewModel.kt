package com.mttnow.android.app_tmdb.ui.Autorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mttnow.android.app_tmdb.data.Const
import com.mttnow.android.app_tmdb.ui.BaseMovieViewModel
import com.mttnow.android.app_tmdb.ui.utils.ModelPreferencesManager
import com.mttnow.android.app_tmdb.ui.utils.ValidateUser
import java.util.concurrent.TimeUnit


class AutorizViewModel() : BaseMovieViewModel() {


    private var _validate = MutableLiveData<Boolean>()
    val validate: LiveData<Boolean>
        get() = _validate

    var user = ModelPreferencesManager.SharedPrefGet(Const.USER)
    private var loadOnlyBtn = false

    fun updateUser(_user: Pair<String, String>) {
        user = _user

    }

    fun checkValidation() {
        compositeDisposable.add(
            ValidateUser.isValidate
                .delay(3, TimeUnit.SECONDS)
                .subscribe({
                    _validate.postValue(it)
                    if (it) {
                        ModelPreferencesManager.SharedPrefPut(Const.USER, user)
                    } else {
                        ModelPreferencesManager.SharedPrefClean()
                    }
                }, {
                    _validate.value = false
                    Log.d("my", "Throwable: ${it.message}")
                }
                )
        )
        ValidateUser.validateUser(user)
    }
}
