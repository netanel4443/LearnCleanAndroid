package com.e.learningcleanandroid.ui.recyclerview.viewholders

import com.e.androidcleanarchitecture.ui.recyclerview.viewholders.GenericViewHolder
import com.e.learningcleanandroid.api.data.DogPhotos
import com.e.learningcleanandroid.databinding.DogPhotoCellDesignRecyclerviewBinding
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class DogPhotosViewHolder(binding: DogPhotoCellDesignRecyclerviewBinding):GenericViewHolder<DogPhotos>(binding) {

    private val dogImg=binding.dogImage
    private val dogInformation=binding.dogInformation

    override fun bind(item: DogPhotos, position: Int) {
        printIfDebug("item ${item.url}")
        //todo how to clear Callback reference to prevent leaks
        //todo fix bug when list is empty
        Picasso.get().load(item.url).into(dogImg,object:Callback{
            override fun onSuccess() {
               dogInformation.text=item.toString()
            }

            override fun onError(e: Exception?) {
                dogInformation.text="Couldn't load this image"
            }
        })


    }
}