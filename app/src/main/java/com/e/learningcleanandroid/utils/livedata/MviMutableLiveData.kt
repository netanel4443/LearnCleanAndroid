package com.e.androidcleanarchitecture.utils.livedata

import androidx.lifecycle.MutableLiveData
import com.e.learningcleanandroid.utils.livedata.MviPrevAndCurrentState

class MviMutableLiveData<T>(initialVal: T) : MutableLiveData<MviPrevAndCurrentState<T>>(MviPrevAndCurrentState(initialVal,initialVal)) {
    private var prevValue:T = initialVal


    fun setMviValue(t:T){
        val prevAndCurrentState=MviPrevAndCurrentState(prevValue,t)
        prevValue=t
        super.setValue(prevAndCurrentState)
    }

    fun postMviValue(t:T){
        val prevAndCurrentState=MviPrevAndCurrentState(prevValue,t)
        prevValue=t
        super.postValue(prevAndCurrentState)
    }

    fun currState():T{
        return value!!.currentState
    }

}