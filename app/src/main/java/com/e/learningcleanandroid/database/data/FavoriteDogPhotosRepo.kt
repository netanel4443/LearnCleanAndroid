package com.e.learningcleanandroid.database.data

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.data.Breeds
import com.e.learningcleanandroid.api.data.DogPhotos
import com.e.learningcleanandroid.database.configs.FavoriteDogImagesConfig
import com.e.learningcleanandroid.database.data.cachedDogPhotos.CachedDogBreedsRealmObj
import com.e.learningcleanandroid.database.data.cachedDogPhotos.CachedDogPhotosRealmObj
import com.e.learningcleanandroid.utils.logs.printErrorIfDebug
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
     fun saveFavoritePhoto(favoriteDogPhoto:DogPhotos): Single<Boolean> {

       return Single.fromCallable {
           var isSaved=false
            try{
                realm.executeTransactionAsync {realm->
                    val result= realm.where(CachedDogPhotosRealmObj::class.java)
                            .equalTo("id",favoriteDogPhoto.id)
                            .findFirst()

                    if (result!= null) {// if the photo is already saved, don't save it again
                        return@executeTransactionAsync // isSaved = false
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
                    //todo
                    // if we arrived here, will the code return true because the object
                    // has been saved, or we can arrive here and the code returns true even if an error was thrown ?
                    isSaved=true
                }

            }catch (e:Exception){ printErrorIfDebug(e)}
          return@fromCallable isSaved
        }
    }


    // if there is no saved data (favorite photos) , return an empty array
    fun getFavoriteDogPhotos(): Single<ArrayList<DogPhotos>> {

       return Single.fromCallable{
           val arrayList=ArrayList<DogPhotos>()
           try {
                 realm.executeTransactionAsync {realm->
                    val favoriteDogsArray=realm.where(CachedDogPhotosRealmObj::class.java)
                            .findAll()?.toArrayList()

                    if (favoriteDogsArray==null) return@executeTransactionAsync

                    favoriteDogsArray.forEach { cachedDogObj->
                        val theDogPhoto= DogPhotos(
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
                }
           }catch (e:Exception){ printErrorIfDebug(e)}
         return@fromCallable  arrayList
       }
    }

    fun deleteFavoritePhoto(photoId:String):Completable{

        return Completable.fromAction{
            try {
                realm.executeTransactionAsync {realm->
                    realm.where(CachedDogPhotosRealmObj::class.java)
                            .equalTo("id",photoId)
                            .findFirst()?.deleteFromRealm()
                }
            }catch (e:Exception){ printErrorIfDebug(e)}
        }

    }
}