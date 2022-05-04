package com.mttnow.android.app_tmdb.data

object Const {
    const val API_KEY = "a980b9c5403a48a0b70e5b9f0ba35a17"

    //    const val API_KEY_NEWS = "e7a4d3493ec84a1a9232789bf7a943cf"
    const val API_KEY_NEWS = "53bc8066d2c04b89b40ecaec64e20e77"
    const val URL_TMDB = "https://api.themoviedb.org/3/"
    const val URL_NEWS = "https://newsapi.org/v2/"


    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    private const val IMAGE_SIZE_DEFAULT_500 = "w500/"
    private const val IMAGE_SIZE_DEFAULT_342 = "w342/"
    private const val IMAGE_SIZE_DEFAULT_154 = "w154/"
    const val THE_MOVIES_DB_IMAGE_BASE_URL_WITH_SIZE500 = IMAGE_BASE_URL + IMAGE_SIZE_DEFAULT_500
    const val THE_MOVIES_DB_IMAGE_BASE_URL_WITH_SIZE342 = IMAGE_BASE_URL + IMAGE_SIZE_DEFAULT_154


    const val FIRST_PAGE = 1
    const val POST_PER_PAGE = 20


    const val SPAN_COUNT = 2


    const val MOVIE_VIEW_TYPE = 1
    const val NETWORK_VIEW_TYPE = 2

    const val AUTORIZ = "isLogin"

    const val LOGIN = "login"
    const val PASSWORD = "password"
}