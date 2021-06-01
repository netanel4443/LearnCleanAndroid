package com.e.learningcleanandroid.utils.livedata

class MviPrevAndNow< T>(private val initialValue:T) {
    var prevValue:T
    var currentValue:T


    init {
        currentValue=initialValue
        prevValue=initialValue
    }




}