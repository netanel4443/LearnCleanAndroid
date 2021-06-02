package com.e.learningcleanandroid.utils

fun <T> HashSet<T>.toArrayList():ArrayList<T>{
    val tmpArrayList=ArrayList<T>()
    forEach {
        tmpArrayList.add(it)
    }
    return tmpArrayList
}