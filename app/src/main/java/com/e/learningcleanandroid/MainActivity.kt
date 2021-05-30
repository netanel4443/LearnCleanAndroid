package com.e.learningcleanandroid

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.learningcleanandroid.api.data.DogPhotos
import com.e.learningcleanandroid.databinding.ActivityMainBinding
import com.e.learningcleanandroid.di.BaseApplication
import com.e.learningcleanandroid.di.graphs.MainActivityGraph
import com.e.learningcleanandroid.ui.BaseActivity
import com.e.learningcleanandroid.ui.recyclerview.adapters.DogPhotosRecyclerViewAdapter
import com.e.learningcleanandroid.ui.recyclerview.helpers.EndlessScrollListener
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.e.learningcleanandroid.viewmodels.MainActivityViewModel

class MainActivity : BaseActivity() {

    private val viewModel:MainActivityViewModel by lazy(this::getViewModel)
    lateinit var activityGraph:MainActivityGraph
    private lateinit var dogPhotosAdapter:DogPhotosRecyclerViewAdapter
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
         activityGraph=
            (application as BaseApplication).applicationGraph.activityGraph().create()
        activityGraph.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView(binding.recyclerview)
        initStateObservable()

        /* if no items, invoke. This line is added in order to prevent
         unnecessary calls due configuration changes*/
        if (dogPhotosAdapter.isEmpty()){
            viewModel.loadDogPhotos()
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        dogPhotosAdapter= DogPhotosRecyclerViewAdapter()
        recyclerView.adapter=dogPhotosAdapter

        val layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=layoutManager

        recyclerView.setHasFixedSize(true)

        val endlessScrollListener=EndlessScrollListener<DogPhotos>(recyclerView)
        recyclerView.addOnScrollListener(endlessScrollListener)
        endlessScrollListener.loadMore={
            printIfDebug("load more dog images")
            viewModel.loadMoreDogPhotos()
        }

    }


    private fun initStateObservable() {
        viewModel.viewStates.observe(this,  {
            val oldState=it.first
            val newState=it.second

            if (dogPhotosAdapter.canLoadMoreItems()){
                println(oldState.dogPhotos.size)
                println(newState.dogPhotos.size)
//                newState.dogPhotos.removeAll(oldState.dogPhotos)
                dogPhotosAdapter.addItems(newState.dogPhotos)
            }
        })
    }


}