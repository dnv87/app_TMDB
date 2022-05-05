package com.mttnow.android.app_tmdb.ui.utils


import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

object ValidateUser {
    val isValidate: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)

    fun validateUser(user: Pair<String?, String?>?) {
        val validUser = Pair("root", "admin")

        if (user == validUser) {
            isValidate.onNext(true)
            isValidate.onError(Exception("ошибка вышла однако"))
        } else {
            isValidate.onNext(false)
        }
    }

}