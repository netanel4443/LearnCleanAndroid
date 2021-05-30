package com.e.learningcleanandroid.di

import android.app.Application
import com.e.learningcleanandroid.di.graphs.DaggerApplicationGraph
import io.realm.Realm

class BaseApplication:Application() {

    val applicationGraph= DaggerApplicationGraph.create()


    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}