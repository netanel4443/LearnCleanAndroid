package com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces

import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.ui.recyclerview.helpers.ItemClickViewHolderHelper

interface DogPhotosViewHolderHelperClicks:ItemClickViewHolderHelper {
    fun onFavoriteIconCLick(dogPhoto:DogPhoto)
}