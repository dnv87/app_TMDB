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

    private fun saveUserDataInSP() {
        TODO("следует добавить данные об авторизации в SharedPreferences")
    }

    private fun removeUserDataInSP() {
        TODO("следует удалить данные об авторизации из SharedPreferences")
    }

    fun isAutorize():Boolean {


        TODO("проверяем есть ли в SharedPreferences состояние вход")
    }

    fun getLogPassSP():Pair<String,String>{
        TODO("достаём созранённые данные об авторизации из SharedPreferences")
    }



}