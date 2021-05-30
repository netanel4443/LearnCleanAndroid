package com.e.learningcleanandroid.database

import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.learningcleanandroid.api.data.Breeds
import com.e.learningcleanandroid.api.data.DogPhotos
import com.e.learningcleanandroid.database.configs.GeneralDogImagesConfig
import com.e.learningcleanandroid.database.data.cachedDogPhotos.CachedDogBreedsRealmObj
import com.e.learningcleanandroid.database.data.cachedDogPhotos.CachedDogPhotosRealmObj
import com.e.learningcleanandroid.utils.logs.printErrorIfDebug
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.e.learningcleanandroid.utils.realm.rxObservableExecuteTransactionAsync
import com.e.learningcleanandroid.utils.realm.toArrayList
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import java.lang.Exception
import javax.inject.Inject

@ActivityScope
class CachedDogPhotosRepo @Inject constructor(private val config:GeneralDogImagesConfig) {
    val realm: Realm =  config.getRealmInstance()
    // if there is no cached data , return an empty array
  fun getCachedDogPhotos():Single<ArrayList<DogPhotos>>{
    return  Single.fromCallable {

        val arrayList=ArrayList<DogPhotos>()
       try {
          realm.rxObservableExecuteTransactionAsync {realm->

                printIfDebug("get cached dog images")
                val cachedArrayList= realm.where(CachedDogPhotosRealmObj::class.java)
                        .findAll()?.toArrayList()

                if (cachedArrayList==null) {return@rxObservableExecuteTransactionAsync }

                cachedArrayList.forEach { cachedDogObj->
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
                        breedsArray.add(breeds) // add only if we have breeds , otherwise keep empty
                    }
                    theDogPhoto.breeds= breedsArray
                    arrayList.add(theDogPhoto)
                    println("array size ${arrayList.size}")

                }
           }
        }
        catch (e:Exception){ printErrorIfDebug(e)}
        finally {

        }
        println("array size ${arrayList.size}")
        return@fromCallable arrayList

    }
  }
   private fun deletePrevCachedDogImages():Completable {
        return Completable.fromAction {

            try {
                realm.executeTransactionAsync {realm->
                    printIfDebug("delete cached dog images")
                    realm.where(CachedDogPhotosRealmObj::class.java)
                            .findAll()?.deleteAllFromRealm()
                }
            } catch (e: Exception) {
                printErrorIfDebug(e)
            }
        }
    }

  fun cacheDogPhotos(dogPhotosArr:ArrayList<DogPhotos>):Completable{
    return deletePrevCachedDogImages()
            .concatWith(saveDogImages(dogPhotosArr))

  }

  private fun saveDogImages(dogPhotosArr:ArrayList<DogPhotos>):Completable{
        return Completable.fromAction(){
            try {
                realm.executeTransactionAsync {realm->
                    printIfDebug("save cached dog images")

                    dogPhotosArr.forEach {dogPhoto->
                        val cachedDogPhoto=realm.createObject(CachedDogPhotosRealmObj::class.java)
                        //todo should we trust the api to have an id for any image ?
                        cachedDogPhoto.id=dogPhoto.id!! // probably, the object obtained from api must have an id
                        cachedDogPhoto.url=dogPhoto.url ?: "" //if no url , save an empty string

                        val breedsArr=dogPhoto.breeds

                        val breedsRealmObj= CachedDogBreedsRealmObj()

                        if (breedsArr !=null && breedsArr.isNotEmpty()){ //in case no breeds at all
                            val breeds=breedsArr.first()
                            breedsRealmObj.name=breeds.name ?: "unknown"
                            breedsRealmObj.origin=breeds.origin ?: "unknown"
                            breedsRealmObj.lifeSpan=breeds.lifeSpan ?: "unknown"
                            cachedDogPhoto.breeds.add(breedsRealmObj)// add only if we have breeds otherwise, keep an empty array
                        printIfDebug(breeds)
                        }


                        realm.insertOrUpdate(cachedDogPhoto)
                    }
                }
            }
            catch (e:Exception){ printErrorIfDebug(e) }
        }
    }
}