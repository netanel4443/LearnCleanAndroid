package com.e.learningcleanandroid.usecases

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.database.CachedDogPhotosRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ActivityScope
class MainActivityUseCases @Inject constructor(private val repo:CachedDogPhotosRepo) {

    fun getCachedDogPhotos(): Single<ArrayList<DogPhoto>> {
        return repo.getCachedDogPhotos()
    }

    fun cacheDogImages(dogPhotosArr:ArrayList<DogPhoto>):Completable {
        return repo.cacheDogPhotos(dogPhotosArr)
    }


}