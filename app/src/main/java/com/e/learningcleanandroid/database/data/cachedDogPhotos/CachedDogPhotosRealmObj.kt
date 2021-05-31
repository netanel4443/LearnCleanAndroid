package com.e.learningcleanandroid.database.data.cachedDogPhotos

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class CachedDogPhotosRealmObj:RealmObject() {
    @PrimaryKey
    @Required
    var id:String=""
    var url:String=""
    var breeds:RealmList<CachedDogBreedsRealmObj> = RealmList()

}