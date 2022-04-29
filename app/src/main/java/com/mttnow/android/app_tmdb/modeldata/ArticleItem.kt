package com.mttnow.android.app_tmdb.modeldata

import android.graphics.Color

data class ArticleItem(
    val itemArticle: Article,
    var color: ColorItem,
    var parity:Boolean
){
    fun setItemColor(_color: ColorItem){
        this.color = _color
    }
}



enum class ColorItem {
    RED,
    BLACK,
    GREEN,
    BLUE,
    ORANGE
}
