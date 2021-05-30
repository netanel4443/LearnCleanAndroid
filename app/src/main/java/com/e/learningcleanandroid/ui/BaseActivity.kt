package com.e.learningcleanandroid.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity:AppCompatActivity() {

    protected val compositeDisposable=CompositeDisposable()
    @Inject lateinit var factory: ViewModelProvider.Factory

    protected inline fun <reified T:ViewModel> getViewModel():T{
        return ViewModelProvider(this,factory)[T::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}