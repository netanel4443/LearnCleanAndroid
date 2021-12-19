package com.e.learningcleanandroid.di.graphs

import com.e.androidcleanarchitecture.di.scopes.ApplicationScope
import com.e.learningcleanandroid.di.modules.ApplicationGraphSubComponentsModule
import com.e.learningcleanandroid.di.modules.ApplicationModule
import dagger.Component

@ApplicationScope
@Component(modules = [
    ApplicationGraphSubComponentsModule::class,
    ApplicationModule::class
])
interface ApplicationGraph {

    fun activityGraph(): MainActivityGraph.Factory

}

