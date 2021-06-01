package com.e.androidcleanarchitecture.ui.recyclerview.viewholders

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.e.androidcleanarchitecture.ui.recyclerview.helpers.ViewHolderHelper
import com.e.learningcleanandroid.ui.recyclerview.helpers.ItemClickViewHolderHelper

open class GenericViewHolder<T>(binding: ViewBinding):RecyclerView.ViewHolder(binding.root),ViewHolderHelper<T> {

    private var _clickHelper:ItemClickViewHolderHelper?=null
    protected val clickHelper get() = _clickHelper

    override fun bind(item: T) {}

    override fun setItemClickHelper(helper: ItemClickViewHolderHelper?) {
        _clickHelper=helper
    }


}