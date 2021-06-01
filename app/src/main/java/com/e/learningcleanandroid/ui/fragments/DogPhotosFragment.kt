package com.e.learningcleanandroid.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.learningcleanandroid.MainActivity
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.databinding.FragmentDogPhotosBinding
import com.e.learningcleanandroid.ui.recyclerview.adapters.DogPhotosRecyclerViewAdapter
import com.e.learningcleanandroid.ui.recyclerview.helpers.EndlessScrollListener
import com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces.DogPhotosViewHolderHelperClicks
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.e.learningcleanandroid.viewmodels.MainActivityViewModel


class DogPhotosFragment : BaseFragment() {

    private val viewModel:MainActivityViewModel by lazy(this::getViewModel)
    private  var _binding:FragmentDogPhotosBinding?=null
    private val binding get() = _binding!!
    private lateinit var dogPhotosAdapter:DogPhotosRecyclerViewAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).activityGraph.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         _binding=FragmentDogPhotosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initRecyclerView(binding.recyclerView,view.context)
        initStateObservable()
        /* if no items, invoke. This line is added in order to prevent
                unnecessary calls due configuration changes*/

            viewModel.getCachedDogPhotos()

    }

    private fun initRecyclerView(recyclerView:RecyclerView,context: Context) {
            dogPhotosAdapter= DogPhotosRecyclerViewAdapter()
            recyclerView.adapter=dogPhotosAdapter

            dogPhotosAdapter.setItemClickHelper(object : DogPhotosViewHolderHelperClicks {
                override fun onFavoriteIconCLick(dogPhoto: DogPhoto) {
                    viewModel.saveFavoriteDogPhoto(dogPhoto)
                }
            })

            val layoutManager= LinearLayoutManager( context,LinearLayoutManager.VERTICAL,false)
            recyclerView.layoutManager=layoutManager

            recyclerView.setHasFixedSize(true)


            val endlessScrollListener= EndlessScrollListener<DogPhoto>(recyclerView)
            recyclerView.addOnScrollListener(endlessScrollListener)
            endlessScrollListener.loadMore={
                printIfDebug("load more dog images")
                viewModel.loadMoreDogPhotos()
            }

        }

    private fun initStateObservable() {
        viewModel.viewStates.observe(viewLifecycleOwner,  {
            val oldState=it.first
            val newState=it.second

            if (dogPhotosAdapter.canLoadMoreItems()){
                dogPhotosAdapter.addItems(newState.dogPhotos)
            }
        })
    }
    }




