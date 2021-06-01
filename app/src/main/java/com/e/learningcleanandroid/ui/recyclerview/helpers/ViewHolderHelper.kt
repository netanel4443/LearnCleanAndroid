package com.e.androidcleanarchitecture.ui.recyclerview.helpers

import com.e.learningcleanandroid.ui.recyclerview.helpers.ItemClickViewHolderHelper

interface ViewHolderHelper<T> {

    fun bind(item:T)

    fun setItemClickHelper(helper:ItemClickViewHolderHelper?)
}