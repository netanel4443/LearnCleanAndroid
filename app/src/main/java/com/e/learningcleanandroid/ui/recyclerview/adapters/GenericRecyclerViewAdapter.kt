package com.e.androidcleanarchitecture.ui.recyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.e.androidcleanarchitecture.ui.recyclerview.viewholders.GenericViewHolder
import com.e.learningcleanandroid.ui.recyclerview.helpers.ItemClickViewHolderHelper
import com.e.learningcleanandroid.utils.arraylist.addFilteredItems

open class GenericRecyclerViewAdapter<T>:RecyclerView.Adapter<GenericViewHolder<T>>() {

    val items=ArrayList<T>()
    private var shouldLoadMoreItems=true

    protected var itemClick:ItemClickViewHolderHelper?=null

    fun addItems(items:ArrayList<T>){
        this.items.addFilteredItems(items)
        notifyDataSetChanged()
        shouldLoadMoreItems(false)
    }

    fun addItem(item:T){
        items.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(viewType,parent,false)
        val viewHolder = GenericViewHolder<T>(view as ViewBinding)
            viewHolder.setItemClickHelper(itemClick)
        return viewHolder

    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
      holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItemClickHelper(helper:ItemClickViewHolderHelper){
        itemClick=helper

    }

    fun isEmpty():Boolean= itemCount==0

    fun canLoadMoreItems():Boolean = shouldLoadMoreItems || isEmpty()

    fun shouldLoadMoreItems(load:Boolean){
        shouldLoadMoreItems=load
    }
}