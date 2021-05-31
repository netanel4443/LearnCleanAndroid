package com.e.learningcleanandroid.viewmodels

import androidx.lifecycle.LiveData
import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.androidcleanarchitecture.utils.SingleLiveEvent
import com.e.androidcleanarchitecture.utils.livedata.MviMutableLiveData
import com.e.learningcleanandroid.api.MainActivityApi
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.usecases.FavoriteDogPhotosUseCases
import com.e.learningcleanandroid.usecases.MainActivityUseCases
import com.e.learningcleanandroid.utils.logs.printErrorIfDebug
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.e.learningcleanandroid.viewmodels.common.BaseViewModel
import com.e.learningcleanandroid.viewmodels.events.MainActivityEvents
import com.e.learningcleanandroid.viewmodels.states.MainActivityStates
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class MainActivityViewModel @Inject constructor(
    private val api:MainActivityApi,
    private val useCases: MainActivityUseCases,
    private val favoriteDogPhotosUseCases: FavoriteDogPhotosUseCases
) :BaseViewModel() {

    private val _viewState=MviMutableLiveData(MainActivityStates())
    val viewStates:LiveData<Pair<MainActivityStates,MainActivityStates>> get()= _viewState as LiveData<Pair<MainActivityStates, MainActivityStates>>

    private val _viewEvent=SingleLiveEvent<MainActivityEvents>()
    val viewEvent get()= _viewEvent

    private fun setMviValue(value:MainActivityStates){
        _viewState.setMviValue(value)
    }

    private fun postMviValue(value:MainActivityStates){
        _viewState.postMviValue(value)
    }

    fun loadDogPhotos(){
        useCases.getCachedDogPhotos()
            .doOnSuccess { cachedDogPhotos ->
                val copy=  _viewState.currState()?.copy(dogPhotos = cachedDogPhotos)
                postMviValue(copy!!)
                if (cachedDogPhotos.isEmpty()) {
                    printIfDebug("im empty")
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
                        val copy= _viewState.currState()?.copy()
                        copy?.dogPhotos?.addAll(body)
                        setMviValue(copy!!)
                        cacheDogPhotos(copy.dogPhotos)
                    }
                }
            },::printErrorIfDebug)
        )
    }


    private fun cacheDogPhotos(dogPhotos: ArrayList<DogPhoto>){
        useCases.cacheDogImages(dogPhotos)
//                .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({},{ printIfDebug(it.message)})
    }

    fun loadMoreDogPhotos(){ getDogPhotos() }

    fun saveFavoriteDogPhoto(dogPhoto: DogPhoto){
        favoriteDogPhotosUseCases.saveFavoritePhoto(dogPhoto)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({isSaved->
                val copy=_viewState.currState()?.copy()
                copy!!.favoriteDogPhotos.add(dogPhoto)
                setMviValue(copy)

                val text=   if(isSaved) { "Saved" }
                else{ "Already Saved" }

                _viewEvent.value=MainActivityEvents.ToastText(text)

            },::printErrorIfDebug)
    }

    fun getFavoriteDogPhotos(){
        favoriteDogPhotosUseCases.getCachedDogPhotos()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({favoriteDogPhotos->
                setMviValue(_viewState.currState()?.copy(favoriteDogPhotos =  favoriteDogPhotos)!!)
            },::printErrorIfDebug)
    }

    fun deleteFavoriteDogPhoto(photoId:String?,position:Int) {
        //todo delete nullable
        favoriteDogPhotosUseCases.deleteFavoriteDogPhoto(photoId!!)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val copy=_viewState.currState()?.copy()
                copy!!.favoriteDogPhotos.removeAt(position)
            },::printErrorIfDebug)
    }


}