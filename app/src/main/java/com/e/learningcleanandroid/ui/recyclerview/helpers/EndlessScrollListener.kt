package com.e.learningcleanandroid.ui.recyclerview.helpers

import androidx.recyclerview.widget.RecyclerView
import com.e.androidcleanarchitecture.ui.recyclerview.adapters.GenericRecyclerViewAdapter
import com.e.androidcleanarchitecture.ui.recyclerview.viewholders.GenericViewHolder
import com.e.learningcleanandroid.utils.logs.printIfDebug
import kotlin.math.roundToInt

class EndlessScrollListener<T>(recyclerView: RecyclerView, percentage: Int) : RecyclerView.OnScrollListener() {
    private var isLoading=false
    private  var percentageToLoadMoreItems:Int = percentage
    private var currentItemCount=0
    var loadMore:(()-> Unit)? = null
    private var layoutManager:RecyclerView.LayoutManager?=null
    private var adapter:RecyclerView.Adapter<RecyclerView.ViewHolder>?=null

    init {
        layoutManager=recyclerView.layoutManager
        adapter=recyclerView.adapter
    }


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val itemCount=layoutManager!!.itemCount

        val offset = recyclerView.computeVerticalScrollOffset()
        val extent = recyclerView.computeVerticalScrollExtent()
        val range = recyclerView.computeVerticalScrollRange()

        val percentage = (100.0f * offset / (range - extent).toFloat()).toInt()

        if (percentage>=percentageToLoadMoreItems ) {

            if (currentItemCount!=itemCount){ //need to update it when items are added.
                currentItemCount=itemCount
                isLoading=false
            }

            if ( itemCount == currentItemCount && !isLoading ) {
                isLoading=true
                loadMore?.invoke()
            }

        }
    }
}