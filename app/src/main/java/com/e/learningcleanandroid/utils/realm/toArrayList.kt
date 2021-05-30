package com.e.learningcleanandroid.utils.realm

import io.realm.RealmResults

fun <T> RealmResults<T>.toArrayList():ArrayList<T>{
    val arrayList=ArrayList<T>()
    forEach {item->
        arrayList.add(item)
    }
    return arrayList
}