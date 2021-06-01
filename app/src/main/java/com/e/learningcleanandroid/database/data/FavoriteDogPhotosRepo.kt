package com.e.learningcleanandroid.database.data

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.data.Breeds
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.database.configs.FavoriteDogImagesConfig
import com.e.learningcleanandroid.database.data.cachedDogPhotos.CachedDogBreedsRealmObj
import com.e.learningcleanandroid.database.data.cachedDogPhotos.CachedDogPhotosRealmObj
import com.e.learningcleanandroid.utils.logs.printErrorIfDebug
import com.e.learningcleanandroid.utils.realm.rxCompletableExecuteTransactionAsync
import com.e.learningcleanandroid.utils.realm.rxSingleExecuteTransactionAsync
import com.e.learningcleanandroid.utils.realm.toArrayList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.lang.Exception
import javax.inject.Inject

@ActivityScope
class FavoriteDogPhotosRepo @Inject constructor(private val config:FavoriteDogImagesConfig) {
   private val realm=config.getRealmInstance()

    /** @return  true if saved or false if already exists.
     * If  [Breeds] is empty , it'll save an empty array of [CachedDogBreedsRealmObj] because
     * the default value of [CachedDogBreedsRealmObj] array in [CachedDogPhotosRealmObj] is an empty array.

     **/
     fun saveFavoritePhoto(favoriteDogPhoto:DogPhoto): Single<Boolean> {

       return realm.rxSingleExecuteTransactionAsync {realm->
                    val result= realm.where(CachedDogPhotosRealmObj::class.java)
                            .equalTo("id",favoriteDogPhoto.id)
                            .findFirst()

                    if (result!= null) {// if the photo is already saved, don't save it again
                        return@rxSingleExecuteTransactionAsync false // isSaved = false
                    }

                    val dogPhotoRealmObj=realm.createObject(CachedDogPhotosRealmObj::class.java,favoriteDogPhoto.id)
                    dogPhotoRealmObj.url=favoriteDogPhoto.url ?: "" //if no url , save an empty string

                    val breedsArray=favoriteDogPhoto.breeds
                    if (breedsArray!=null && breedsArray.isNotEmpty()){
                        val breed=breedsArray.first()
                        val breedsRealmObj=CachedDogBreedsRealmObj()
                        breedsRealmObj.name=breed.name ?: "unknown"
                        breedsRealmObj.origin=breed.origin ?: "unknown"
                        breedsRealmObj.lifeSpan=breed.lifeSpan ?: "unknown"
                        dogPhotoRealmObj.breeds.add(breedsRealmObj)
                    }
                    realm.insertOrUpdate(dogPhotoRealmObj)

                 return@rxSingleExecuteTransactionAsync true
                }
    }


    // if there is no saved data (favorite photos) , return an empty array
    fun getFavoriteDogPhotos(): Single<ArrayList<DogPhoto>> {

       return realm.rxSingleExecuteTransactionAsync {realm->
           val arrayList=ArrayList<DogPhoto>()

           val favoriteDogsArray=realm.where(CachedDogPhotosRealmObj::class.java)
                            .findAll()?.toArrayList()

                    if (favoriteDogsArray==null) return@rxSingleExecuteTransactionAsync arrayList

                    favoriteDogsArray.forEach { cachedDogObj->
                        val theDogPhoto= DogPhoto(
                                id=cachedDogObj.id,
                                url=cachedDogObj.url,
                        )

                        val cachedBreedArray=cachedDogObj.breeds
                        val breedsArray=ArrayList<Breeds>()

                        if (cachedBreedArray.isNotEmpty()) {
                            val cachedBreeds=cachedBreedArray.first()
                            val breeds = Breeds(
                                    origin = cachedBreeds!!.origin,
                                    name = cachedBreeds.name,
                                    lifeSpan = cachedBreeds.lifeSpan
                            )
                            breedsArray.add(breeds)
                        }

                        theDogPhoto.breeds= breedsArray
                        arrayList.add(theDogPhoto)
                    }
           return@rxSingleExecuteTransactionAsync arrayList
       }
    }

    fun deleteFavoritePhoto(photoId:String):Completable{
          return  realm.rxCompletableExecuteTransactionAsync {realm->
                    realm.where(CachedDogPhotosRealmObj::class.java)
                        .equalTo("id",photoId)
                        .findFirst()?.deleteFromRealm()
                  }
    }
}