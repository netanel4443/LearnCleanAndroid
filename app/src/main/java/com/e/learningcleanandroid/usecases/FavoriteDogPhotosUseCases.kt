package com.e.learningcleanandroid.usecases

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.database.data.FavoriteDogPhotosRepo
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ActivityScope
class FavoriteDogPhotosUseCases @Inject constructor(private val repo:FavoriteDogPhotosRepo) {

    fun getCachedDogPhotos(): Single<ArrayList<DogPhoto>> {
        return repo.getFavoriteDogPhotos()
    }

    fun saveFavoritePhoto(dogPhoto:DogPhoto):Single<Boolean> {
        return repo.saveFavoritePhoto(dogPhoto)
    }

    fun deleteFavoriteDogPhoto(favoriteDogPhotoId:String):Completable{
        return repo.deleteFavoritePhoto(favoriteDogPhotoId)
    }


}