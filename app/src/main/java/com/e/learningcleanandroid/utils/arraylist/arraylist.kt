package com.e.learningcleanandroid.utils.arraylist

fun <T> ArrayList<T>.addFilteredItems(items:ArrayList<T>){
    val tmpSet=toHashSet()
    items.forEach { item->
        if (!tmpSet.contains(item)){
            add(item)
        }
    }
}