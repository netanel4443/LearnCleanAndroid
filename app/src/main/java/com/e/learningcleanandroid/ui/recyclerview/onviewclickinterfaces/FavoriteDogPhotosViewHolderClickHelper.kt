package com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces

import com.e.learningcleanandroid.ui.recyclerview.helpers.ItemClickViewHolderHelper

interface FavoriteDogPhotosViewHolderClickHelper:ItemClickViewHolderHelper {

    fun onFavoriteIconCLick(photoId:String,position:Int)
}