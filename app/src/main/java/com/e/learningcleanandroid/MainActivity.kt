package com.e.learningcleanandroid

import android.os.Bundle
import com.e.learningcleanandroid.databinding.ActivityMainBinding
import com.e.learningcleanandroid.di.graphs.MainActivityGraph
import com.e.learningcleanandroid.ui.BaseActivity
import com.e.learningcleanandroid.ui.fragments.ViewSwipeFragment
import com.e.learningcleanandroid.utils.fragment.addFragment
import com.e.learningcleanandroid.viewmodels.MainActivityViewModel

class MainActivity : BaseActivity() {

    private val viewModel:MainActivityViewModel by lazy(this::getViewModel)
    lateinit var activityGraph:MainActivityGraph
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
         activityGraph=
            (application as BaseApplication).applicationGraph.activityGraph().create()
        activityGraph.inject(this)

        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addFragment(ViewSwipeFragment(),binding.fragmentContainerView.id,"ViewSwipeFragment")

    }



}