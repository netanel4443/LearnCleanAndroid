package com.e.learningcleanandroid.utils.arraylist

fun <T> ArrayList<T>.addFilteredItems(items:ArrayList<T>){
    val tmpSet=toHashSet()
    items.forEach { item->
        if (!tmpSet.contains(item)){
            add(item)
        }
    }
}

fun <T> ArrayList<T>.newItemsSubList(items:ArrayList<T>): ArrayList<T> {

        return if (items.size > size ) {
            val tmpList=ArrayList<T>()
            for (i in (lastIndex+1)..items.lastIndex){
                tmpList.add(items[i])
            }
            tmpList
        }
        else{
            ArrayList()
        }
}