package com.e.learningcleanandroid.di.modules

import com.e.learningcleanandroid.di.graphs.MainActivityGraph
import dagger.Module

@Module(subcomponents =
        [ MainActivityGraph::class
])
abstract class ApplicationGraphSubComponentsModule{}