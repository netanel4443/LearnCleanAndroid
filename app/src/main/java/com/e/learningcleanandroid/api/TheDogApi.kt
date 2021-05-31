package com.e.learningcleanandroid.api

import com.e.learningcleanandroid.api.data.DogPhoto
import io.reactivex.rxjava3.core.Observable

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TheDogApi {

    @Headers("x-api-key: b4b78a43-25d4-42ea-aed8-af310548cfc6")
    @GET("v1/images/search?size=small")
    fun getPhotos(
            @Query("limit") limit:String="10"
    ): Observable<Response<ArrayList<DogPhoto>>>
}