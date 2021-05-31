package com.e.learningcleanandroid.database

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.data.Breeds
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.database.configs.GeneralDogImagesConfig
import com.e.learningcleanandroid.database.data.cachedDogPhotos.CachedDogBreedsRealmObj
import com.e.learningcleanandroid.database.data.cachedDogPhotos.CachedDogPhotosRealmObj
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.e.learningcleanandroid.utils.realm.rxCompletableExecuteTransactionAsync
import com.e.learningcleanandroid.utils.realm.rxSingleExecuteTransactionAsync
import com.e.learningcleanandroid.utils.realm.toArrayList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import javax.inject.Inject

@ActivityScope
class CachedDogPhotosRepo @Inject constructor(private val config:GeneralDogImagesConfig) {
    val realm: Realm =  config.getRealmInstance()
    // if there is no cached data , return an empty array
  fun getCachedDogPhotos():Single<ArrayList<DogPhoto>>{
    return realm.rxSingleExecuteTransactionAsync{realm->
             val arrayList=ArrayList<DogPhoto>()
                printIfDebug("get cached dog images")
                val cachedArrayList= realm.where(CachedDogPhotosRealmObj::class.java)
                        .findAll()?.toArrayList()

                if (cachedArrayList==null) { return@rxSingleExecuteTransactionAsync arrayList }

                cachedArrayList.forEach { cachedDogObj->
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
                        breedsArray.add(breeds) // add only if we have breeds , otherwise keep empty
                    }
                    theDogPhoto.breeds= breedsArray
                    arrayList.add(theDogPhoto)

                }
           return@rxSingleExecuteTransactionAsync arrayList
           }
    }

   private fun deletePrevCachedDogImages():Completable {
        return realm.rxCompletableExecuteTransactionAsync {realm->
                printIfDebug("delete cached dog images")
                realm.where(CachedDogPhotosRealmObj::class.java)
                        .findAll()?.deleteAllFromRealm()
            }
   }


  fun cacheDogPhotos(dogPhotosArr:ArrayList<DogPhoto>):Completable{
    return deletePrevCachedDogImages()
            .concatWith(saveDogImages(dogPhotosArr))

  }

  private fun saveDogImages(dogPhotosArr:ArrayList<DogPhoto>):Completable{

     return realm.rxCompletableExecuteTransactionAsync {realm->
                  printIfDebug("save cached dog images")

                  dogPhotosArr.forEach {dogPhoto->
                      // probably, the object obtained from api must have an id
                      val cachedDogPhoto=realm.createObject(CachedDogPhotosRealmObj::class.java,dogPhoto.id!!)
                      //todo should we trust the api to have an id for any image ?

                      cachedDogPhoto.url=dogPhoto.url ?: "" //if no url , save an empty string

                      val breedsArr=dogPhoto.breeds

                      val breedsRealmObj= CachedDogBreedsRealmObj()

                      if (breedsArr !=null && breedsArr.isNotEmpty()){ //in case no breeds at all
                            val breeds=breedsArr.first()
                            breedsRealmObj.name=breeds.name ?: "unknown"
                            breedsRealmObj.origin=breeds.origin ?: "unknown"
                            breedsRealmObj.lifeSpan=breeds.lifeSpan ?: "unknown"
                            cachedDogPhoto.breeds.add(breedsRealmObj)// add only if we have breeds otherwise, keep an empty array
                      }
                      realm.insertOrUpdate(cachedDogPhoto)
                  }
              }
     }
}