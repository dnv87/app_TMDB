package com.mttnow.android.app_tmdb.ui.utils

import android.content.Context
import android.content.SharedPreferences
import com.mttnow.android.app_tmdb.MainActivity
import com.mttnow.android.app_tmdb.data.Const

object ModelPreferencesManager {

    lateinit var preferences: SharedPreferences

    private const val PREFERENCES_FILE_NAME = "PREFERENCES_FILE_NAME"

    fun with(application: MainActivity) {
        preferences = application.getSharedPreferences(
            PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
        )
    }

    fun SharedPrefPut(key: String, value: Pair<String, String>) {
        val edit = preferences.edit()
        edit.putString(key, value.toString())
        edit.putBoolean(Const.AUTORIZ, true)
        edit.apply()
    }

    fun SharedPrefGet(key: String): Pair<String, String> {
        val str = preferences.getString(key, "").toString()

        val pair = Pair(
            str.substringAfter("(")
                .substringBefore(",").trim(),
            str.substringAfter(",")
                .substringBefore(")").trim()
        )

        return pair
    }

    fun SharedPrefClean() {
        val edit = preferences.edit()
        edit.putString(Const.USER, "")
        edit.putBoolean(Const.AUTORIZ, false)
        edit.apply()
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(Const.AUTORIZ, false)
    }

}