package com.e.learningcleanandroid.di.graphs

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.MainActivity
import com.e.learningcleanandroid.di.modules.ActivityViewModelModule
import com.e.learningcleanandroid.di.modules.ApplicationModule
import com.e.learningcleanandroid.ui.fragments.DogPhotosFragment
import com.e.learningcleanandroid.ui.fragments.FavoriteDogPhotosFragment
import com.e.learningcleanandroid.ui.fragments.ViewSwipeFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules =
     [ ActivityViewModelModule::class])
interface MainActivityGraph {

    @Subcomponent.Factory
    interface Factory{
        fun create():MainActivityGraph
    }

    fun inject(mainActivity: MainActivity)
//    fun inject(viewSwipeFragment: ViewSwipeFragment)
    fun inject(dogPhotosFragment: DogPhotosFragment)
    fun inject(favoriteDogPhotosFragment: FavoriteDogPhotosFragment)
}