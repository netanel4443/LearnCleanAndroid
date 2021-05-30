package com.e.learningcleanandroid.utils.logs

import android.util.Log

private const val isDebug=true

fun printIfDebug(text:Any?){
    if (isDebug){
        println(text)
    }
}
fun printErrorIfDebug(text:String){
    if (isDebug){
        Log.e("",text)
    }
}
fun printErrorIfDebug(throwable:Throwable){
    if (isDebug){
        Log.e("error", throwable.stackTraceToString())
        }
    }
