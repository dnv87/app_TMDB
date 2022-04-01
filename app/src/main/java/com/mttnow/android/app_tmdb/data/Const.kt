package com.mttnow.android.app_tmdb.data

object Const {
    const val API_KEY = "a980b9c5403a48a0b70e5b9f0ba35a17"
    const val URL_TMDB = "https://api.themoviedb.org/3/"

    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    private const val IMAGE_SIZE_DEFAULT_500 = "w500/"
    private const val IMAGE_SIZE_DEFAULT_342 = "w342/"
    const val THE_MOVIES_DB_IMAGE_BASE_URL_WITH_SIZE500 = IMAGE_BASE_URL + IMAGE_SIZE_DEFAULT_500
    const val THE_MOVIES_DB_IMAGE_BASE_URL_WITH_SIZE342 = IMAGE_BASE_URL + IMAGE_SIZE_DEFAULT_342

    const val FIRST_PAGE = 1
    const val POST_PER_PAGE = 20

    const val GET_TOP_MOVIE = false
    const val GET_POPULAR_MOVIE = true

    const val SPAN_COUNT = 2
}