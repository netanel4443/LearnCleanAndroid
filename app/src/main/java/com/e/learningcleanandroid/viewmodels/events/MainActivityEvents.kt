package com.e.learningcleanandroid.viewmodels.events

sealed class MainActivityEvents{
    data class ToastText(val text:String) :MainActivityEvents()

}