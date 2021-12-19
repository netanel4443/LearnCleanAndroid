package com.e.learningcleanandroid.utils.realm

import com.e.learningcleanandroid.utils.logs.printErrorIfDebug
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


fun <T> Realm.rxObservableExecuteTransactionAsync(block:(Realm)->T):Observable<T>{
   return Observable.create {emitter->
        try {
            executeTransactionAsync {realm->
                emitter.onNext( block(realm) )
            }
        }catch (e:Exception){
            printErrorIfDebug(e)
            emitter.onError(e)
        }
        finally {
            emitter.onComplete()
        }
    }

}

fun <T> Realm.rxSingleExecuteTransactionAsync(block:(Realm)->T): Single<T> {
    return Single.create {emitter->
        try {
            executeTransactionAsync {realm->
                emitter.onSuccess( block(realm) )
            }
        }catch (e:Exception){
            printErrorIfDebug(e)
            emitter.onError(e)
        }

    }
}

fun  Realm.rxCompletableExecuteTransactionAsync(block:(Realm)->Unit):Completable{
    return Completable.create {emitter->
        try {
            executeTransactionAsync {realm-> block(realm) }
        }catch (e:Exception){
            printErrorIfDebug(e)
            emitter.onError(e)
        }
        finally {
            emitter.onComplete()
        }
    }
}

fun <T:RealmObject,V> Realm.rxGetData(clazz:Class<T>,block: (ArrayList<T>) -> ArrayList<V>):Single<ArrayList<V>>{
      return Single.fromCallable {
            val arrayList=   where(clazz).findAll()?.toArrayList()

            if (arrayList==null) { return@fromCallable ArrayList() }

             block(arrayList)

      }
}


