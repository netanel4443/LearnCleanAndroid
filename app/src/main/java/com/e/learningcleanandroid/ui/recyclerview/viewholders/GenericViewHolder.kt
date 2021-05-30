package com.e.androidcleanarchitecture.ui.recyclerview.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.e.androidcleanarchitecture.ui.recyclerview.helpers.ViewHolderHelper

open class GenericViewHolder<T>(binding: ViewBinding):RecyclerView.ViewHolder(binding.root),ViewHolderHelper<T> {

    override fun bind(item: T,position:Int) {}


}