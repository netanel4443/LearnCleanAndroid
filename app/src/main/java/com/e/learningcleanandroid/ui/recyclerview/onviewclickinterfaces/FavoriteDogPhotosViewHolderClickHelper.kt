package com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces

import com.e.learningcleanandroid.ui.recyclerview.helpers.ItemClickViewHolderHelper

interface FavoriteDogPhotosViewHolderClickHelper:ItemClickViewHolderHelper {
    //todo check if we can remove nullable type
    fun onFavoriteIconCLick(photoId:String?,position:Int)
}