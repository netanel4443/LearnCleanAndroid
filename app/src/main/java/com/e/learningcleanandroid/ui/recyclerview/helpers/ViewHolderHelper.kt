package com.e.androidcleanarchitecture.ui.recyclerview.helpers

interface ViewHolderHelper<T> {

    fun bind(item:T,position:Int)
}