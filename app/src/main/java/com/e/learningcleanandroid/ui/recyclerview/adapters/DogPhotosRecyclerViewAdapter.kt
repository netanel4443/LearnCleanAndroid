package com.e.learningcleanandroid.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.e.androidcleanarchitecture.ui.recyclerview.adapters.GenericRecyclerViewAdapter
import com.e.androidcleanarchitecture.ui.recyclerview.viewholders.GenericViewHolder
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.databinding.DogPhotoCellDesignRecyclerviewBinding
import com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces.DogPhotosViewHolderHelperClicks
import com.e.learningcleanandroid.ui.recyclerview.viewholders.DogPhotosViewHolder

class DogPhotosRecyclerViewAdapter:GenericRecyclerViewAdapter<DogPhoto>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<DogPhoto> {
        val inflater=LayoutInflater.from(parent.context)
        val binding=DogPhotoCellDesignRecyclerviewBinding.inflate(inflater,parent,false)
        return DogPhotosViewHolder(binding,itemClick as DogPhotosViewHolderHelperClicks)
    }
}