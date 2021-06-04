package com.e.learningcleanandroid.utils.livedata

data class MviPrevAndCurrentState<T>(
        val previousState:T,
        val currentState:T
)
