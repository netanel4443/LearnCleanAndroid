package com.e.learningcleanandroid.usecases

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.DogImagesApi
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.database.CachedDogPhotosRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import javax.inject.Inject

@ActivityScope
class DogImagesUseCases @Inject constructor(
        private val repo:CachedDogPhotosRepo,
        private val api :DogImagesApi
) {

    fun getDogPhotosFromApi(): Observable<Response<ArrayList<DogPhoto>>> {
      return  api.getDogPhotosRequest()
    }

    fun getCachedDogPhotos(): Single<ArrayList<DogPhoto>> {
        return repo.getCachedDogPhotos()
    }

    fun cacheDogImages(dogPhotosArr:ArrayList<DogPhoto>):Completable {
        return repo.cacheDogPhotos(dogPhotosArr)
    }



}