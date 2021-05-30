package com.e.learningcleanandroid.database.data.cachedDogPhotos

import io.realm.RealmList
import io.realm.RealmObject

open class CachedDogPhotosRealmObj:RealmObject() {

    var id:String=""
    var url:String=""
    var breeds:RealmList<CachedDogBreedsRealmObj> = RealmList()

}