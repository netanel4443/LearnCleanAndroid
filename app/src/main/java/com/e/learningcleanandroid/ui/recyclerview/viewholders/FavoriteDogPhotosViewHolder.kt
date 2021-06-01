package com.e.learningcleanandroid.ui.recyclerview.viewholders

import com.e.androidcleanarchitecture.ui.recyclerview.viewholders.GenericViewHolder
import com.e.learningcleanandroid.R
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.databinding.DogPhotoCellDesignRecyclerviewBinding
import com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces.FavoriteDogPhotosViewHolderClickHelper
import com.squareup.picasso.Picasso

class FavoriteDogPhotosViewHolder(private val binding: DogPhotoCellDesignRecyclerviewBinding,
                                  private val itemClickClickHelper:FavoriteDogPhotosViewHolderClickHelper ):GenericViewHolder<DogPhoto>(binding) {

    private val dogImg=binding.dogImage
    private val dogInformation=binding.dogInformation
    private val favoriteBtn=binding.favoriteBtn

    override fun bind(item: DogPhoto) {

        Picasso.get().load(item.url).into(dogImg)
        favoriteBtn.setBackgroundResource(R.drawable.favorite_filled_24)
        dogInformation.text=item.toString()

        //todo should it be at another place?
        favoriteBtn.setOnClickListener {
            itemClickClickHelper.onFavoriteIconCLick(item.id!!,adapterPosition)
        }

    }
}