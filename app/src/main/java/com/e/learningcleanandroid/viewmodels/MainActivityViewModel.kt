package com.e.learningcleanandroid.viewmodels

import androidx.lifecycle.LiveData
import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.androidcleanarchitecture.utils.livedata.MviMutableLiveData
import com.e.learningcleanandroid.api.MainActivityApi
import com.e.learningcleanandroid.api.data.DogPhotos
import com.e.learningcleanandroid.usecases.MainActivityUseCases
import com.e.learningcleanandroid.utils.logs.printErrorIfDebug
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.e.learningcleanandroid.viewmodels.common.BaseViewModel
import com.e.learningcleanandroid.viewmodels.states.MainActivityStates
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class MainActivityViewModel @Inject constructor(
        private val api:MainActivityApi,
        private val useCases: MainActivityUseCases
) :BaseViewModel() {

    private val _viewStates=MviMutableLiveData(MainActivityStates())
    val viewStates:LiveData<Pair<MainActivityStates,MainActivityStates>> get()= _viewStates as LiveData<Pair<MainActivityStates, MainActivityStates>>

    private fun setMviValue(value:MainActivityStates){
        _viewStates.setMviValue(value)
    }

    fun loadDogPhotos(){
        useCases.getCachedDogPhotos()
           .doOnSuccess { cachedDogPhotos ->

                 if (cachedDogPhotos.isEmpty()) {
                     println("im empty")
                        getDogPhotos()
                 }
           }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},::printIfDebug)
    }

    private fun getDogPhotos(){
        compositeDisposable.add(api.getDogPhotosRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response->
                    if (response.isSuccessful ){
                        //todo check if body can be null
                        response.body()?.let {body->
                            val copy= _viewStates.currState()?.copy()
                            copy?.dogPhotos?.addAll(body)
                            setMviValue(copy!!)
                            cacheDogPhotos(copy.dogPhotos)
                        }
                    }
                },::printErrorIfDebug)
        )
    }


    private fun cacheDogPhotos(dogPhotos: ArrayList<DogPhotos>){
        useCases.cacheDogImages(dogPhotos)
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},{ printIfDebug(it.message)})
    }

    fun loadMoreDogPhotos(){ getDogPhotos() }


}