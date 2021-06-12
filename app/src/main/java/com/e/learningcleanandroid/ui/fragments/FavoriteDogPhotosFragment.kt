package com.e.learningcleanandroid.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.learningcleanandroid.MainActivity
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.databinding.FragmentDogPhotosBinding
import com.e.learningcleanandroid.ui.recyclerview.adapters.FavoriteDogPhotosRecyclerViewAdapter
import com.e.learningcleanandroid.ui.recyclerview.onviewclickinterfaces.FavoriteDogPhotosViewHolderClickHelper
import com.e.learningcleanandroid.utils.arraylist.newItemsSubList
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.e.learningcleanandroid.viewmodels.MainActivityViewModel
import com.e.learningcleanandroid.viewmodels.events.MainActivityEvents

class FavoriteDogPhotosFragment : BaseFragment() {

    private val viewModel:MainActivityViewModel by lazy(this::getViewModel)
    private  var _binding:FragmentDogPhotosBinding?=null
    private val binding get() = _binding!!
    private lateinit var favoriteDogPhotosAdapter:FavoriteDogPhotosRecyclerViewAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).activityGraph.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentDogPhotosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initRecyclerView(binding.recyclerView,view.context)
        initStateObservable()
        initEventsObservable()


        viewModel.getFavoriteDogPhotos()


    }

    private fun initEventsObservable() {
        viewModel.viewEvent.observe(viewLifecycleOwner,{event->
            when(event) {
                is MainActivityEvents.ToastText->toast(event.text)
            }
        })
    }

    private fun toast(text:String){
        Toast.makeText(requireActivity(),text,Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView(recyclerView: RecyclerView, context: Context) {
        favoriteDogPhotosAdapter= FavoriteDogPhotosRecyclerViewAdapter()
        recyclerView.adapter=favoriteDogPhotosAdapter
        favoriteDogPhotosAdapter.setItemClickHelper(object :FavoriteDogPhotosViewHolderClickHelper{
            override fun onFavoriteIconCLick(photoId: String,position:Int) {
                favoriteDogPhotosAdapter.removeItem(position)
                viewModel.deleteFavoriteDogPhoto(photoId,position)
            }
        })

        val layoutManager= LinearLayoutManager( context, LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=layoutManager

        recyclerView.setHasFixedSize(true)
    }

    private fun initStateObservable() {
        viewModel.viewStates.observe(viewLifecycleOwner,  {
            val oldState=it.previousState
            val newState=it.currentState
                printIfDebug("${oldState.favoriteDogPhotos.size}=${newState.favoriteDogPhotos.size}")
            if (oldState.favoriteDogPhotos!=newState.favoriteDogPhotos || favoriteDogPhotosAdapter.isEmpty()){
                addNewNotDuplicatedItemsToRecyclerViewAdapter(newState.favoriteDogPhotos)
            }
        })
    }

    private fun addNewNotDuplicatedItemsToRecyclerViewAdapter(newItems:ArrayList<DogPhoto>){
        val newNotDuplicatedItems=favoriteDogPhotosAdapter.items.newItemsSubList(newItems)
        favoriteDogPhotosAdapter.addItems(newNotDuplicatedItems)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}