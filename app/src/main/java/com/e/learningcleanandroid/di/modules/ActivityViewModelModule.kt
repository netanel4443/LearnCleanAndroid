package com.e.learningcleanandroid.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.androidcleanarchitecture.di.scopes.ViewModelKey
import com.e.learningcleanandroid.viewmodels.MainActivityViewModel
import com.softwaredevelopment.di.viewmodelfactory.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @ActivityScope
    abstract fun viewModelProviderFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

}