package com.e.learningcleanandroid.api

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.data.DogPhoto
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import javax.inject.Inject
import kotlin.collections.ArrayList

@ActivityScope
class DogImagesApi @Inject constructor(
        private val dogPhotoApi: IDogApi) {

    fun getDogPhotosRequest(): Observable<Response<ArrayList<DogPhoto>>> {

        return dogPhotoApi.getPhotos()
    }
}