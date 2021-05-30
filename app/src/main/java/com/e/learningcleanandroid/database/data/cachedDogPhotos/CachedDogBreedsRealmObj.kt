package com.e.learningcleanandroid.database.data.cachedDogPhotos

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CachedDogBreedsRealmObj:RealmObject() {

    var name:String=""
    var origin:String=""
    var lifeSpan:String=""


}