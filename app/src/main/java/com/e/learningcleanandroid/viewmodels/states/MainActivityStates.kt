package com.e.learningcleanandroid.viewmodels.states

import com.e.learningcleanandroid.api.data.DogPhoto

data class MainActivityStates(
        var dogPhotos: ArrayList<DogPhoto> = ArrayList(),
        var favoriteDogPhotos:ArrayList<DogPhoto> = ArrayList()
)
