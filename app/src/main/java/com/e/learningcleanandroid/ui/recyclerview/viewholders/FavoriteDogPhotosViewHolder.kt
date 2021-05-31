package com.e.learningcleanandroid.ui.recyclerview.viewholders

import com.e.androidcleanarchitecture.ui.recyclerview.viewholders.GenericViewHolder
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.databinding.DogPhotoCellDesignRecyclerviewBinding
import com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces.FavoriteDogPhotosViewHolderClickHelper
import com.squareup.picasso.Picasso

class FavoriteDogPhotosViewHolder(private val binding: DogPhotoCellDesignRecyclerviewBinding,
                                  private val itemClickClickHelper:FavoriteDogPhotosViewHolderClickHelper ):GenericViewHolder<DogPhoto>(binding) {

    private val dogImg=binding.dogImage
    private val dogInformation=binding.dogInformation
    private val favoriteBtn=binding.dogImage

    override fun bind(item: DogPhoto, position: Int) {

        Picasso.get().load(item.url).into(dogImg)

        dogInformation.text=item.toString()

        //todo should it be on another place?
        favoriteBtn.setOnClickListener {
            itemClickClickHelper.onFavoriteIconCLick(item.id,position)
        }

    }
}