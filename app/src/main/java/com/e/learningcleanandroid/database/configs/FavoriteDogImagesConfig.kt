package com.e.learningcleanandroid.database.configs

import com.e.androidcleanarchitecture.di.scopes.ApplicationScope
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

@ApplicationScope
class FavoriteDogImagesConfig @Inject constructor(){


    private lateinit var realmName:String
    private lateinit var config: RealmConfiguration
    private val instance: Realm by lazy(::_getRealmInstance)//todo is lazy init is the correct way?

    init {
        realmName = "FAVORITE_DOG_IMAGES"
        config = RealmConfiguration.Builder().name(realmName).build()
    }

    private fun _getRealmInstance(): Realm {
        return Realm.getInstance(config)
    }

    fun getRealmInstance(): Realm {
        return instance
    }
}