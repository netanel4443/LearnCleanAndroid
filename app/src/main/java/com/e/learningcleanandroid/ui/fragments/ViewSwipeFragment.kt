package com.e.learningcleanandroid.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.e.learningcleanandroid.R
import com.e.learningcleanandroid.databinding.FragmentViewSwipeBinding

class ViewSwipeFragment : Fragment() {

    private var _binding:FragmentViewSwipeBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewSwipeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val collectionAdapter = CollectionAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = collectionAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}

class CollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val dogPhotosFragment=DogPhotosFragment()
    private val favoriteDogPhotos=FavoriteDogPhotosFragment()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
      return when(position){
            1->  favoriteDogPhotos
            else-> dogPhotosFragment
        }
    }
}