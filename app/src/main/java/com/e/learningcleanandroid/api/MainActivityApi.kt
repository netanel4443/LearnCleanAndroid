package com.e.learningcleanandroid.api

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.data.DogPhotos
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import javax.inject.Inject
import kotlin.collections.ArrayList

@ActivityScope
class MainActivityApi @Inject constructor(
        private val dogPhotoApi: TheDogApi) {

    fun getDogPhotosRequest(): Observable<Response<ArrayList<DogPhotos>>> {

        return dogPhotoApi.getPhotos()
    }
}