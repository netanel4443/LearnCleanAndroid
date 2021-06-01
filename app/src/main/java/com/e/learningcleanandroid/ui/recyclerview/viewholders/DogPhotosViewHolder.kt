package com.e.learningcleanandroid.ui.recyclerview.viewholders

import com.e.androidcleanarchitecture.ui.recyclerview.viewholders.GenericViewHolder
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.databinding.DogPhotoCellDesignRecyclerviewBinding
import com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces.DogPhotosViewHolderHelperClicks
import com.squareup.picasso.Picasso

class DogPhotosViewHolder(private val binding: DogPhotoCellDesignRecyclerviewBinding,
                          private val itemClickHelper:DogPhotosViewHolderHelperClicks):GenericViewHolder<DogPhoto>(binding) {

    private val dogImg=binding.dogImage
    private val dogInformation=binding.dogInformation
    protected val favoriteBtn=binding.favoriteBtn


    override fun bind(item: DogPhoto) {

        Picasso.get().load(item.url).into(dogImg)
        dogInformation.text=item.toString()

        favoriteBtn.setOnClickListener {
            itemClickHelper.onFavoriteIconCLick(item)
        }
    }
}