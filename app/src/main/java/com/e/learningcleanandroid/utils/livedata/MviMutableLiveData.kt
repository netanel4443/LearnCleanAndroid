package com.e.androidcleanarchitecture.utils.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MviMutableLiveData<T>: MutableLiveData<Pair<T?,T>> {
    private var prevValue:T?=null


    constructor():super()

    constructor(initialVal:T):super(initialVal to initialVal){
        prevValue=initialVal
    }

    fun setMviValue(t:T){
        val pair=prevValue to t
        prevValue=t
        super.setValue(pair)
    }

    fun postMviValue(t:T){
        val pair=prevValue to t
        prevValue=t
        super.postValue(pair)
    }

    fun currState():T?{
        return value?.second
    }

}