package com.e.learningcleanandroid.viewmodels

import androidx.lifecycle.LiveData
import com.e.androidcleanarchitecture.di.scopes.ActivityScope
import com.e.androidcleanarchitecture.utils.SingleLiveEvent
import com.e.androidcleanarchitecture.utils.livedata.MviMutableLiveData
import com.e.learningcleanandroid.api.DogImagesApi
import com.e.learningcleanandroid.api.data.DogPhoto
import com.e.learningcleanandroid.usecases.FavoriteDogPhotosUseCases
import com.e.learningcleanandroid.usecases.DogImagesUseCases
import com.e.learningcleanandroid.utils.arraylist.addFilteredItems
import com.e.learningcleanandroid.utils.livedata.MviPrevAndCurrentState
import com.e.learningcleanandroid.utils.logs.printErrorIfDebug
import com.e.learningcleanandroid.utils.logs.printIfDebug
import com.e.learningcleanandroid.viewmodels.common.BaseViewModel
import com.e.learningcleanandroid.viewmodels.events.MainActivityEvents
import com.e.learningcleanandroid.viewmodels.states.MainActivityStates
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

@ActivityScope
class MainActivityViewModel @Inject constructor(
        private val api:DogImagesApi,
        private val dogImagesUseCases: DogImagesUseCases,
        private val favoriteDogPhotosUseCases: FavoriteDogPhotosUseCases
) :BaseViewModel() {

    private val _viewState=MviMutableLiveData(MainActivityStates())
    val viewStates:LiveData<MviPrevAndCurrentState<MainActivityStates>> get()= _viewState

    private val _viewEvent=SingleLiveEvent<MainActivityEvents>()
    val viewEvent get()= _viewEvent

    private fun setMviValue(value:MainActivityStates){
        _viewState.setMviValue(value)
    }

    private fun postMviValue(value:MainActivityStates){
        _viewState.postMviValue(value)
    }

    fun getCachedDogPhotos(){
        if (_viewState.value!!.currentState.dogPhotos.isEmpty()){
        dogImagesUseCases.getCachedDogPhotos()
            .doOnSuccess { cachedDogPhotos ->
                val copy=  _viewState.currState().copy(dogPhotos = cachedDogPhotos)
                postMviValue(copy)
            }
            .filter{cachedPhotos-> cachedPhotos.isEmpty()}
            .flatMapObservable{ dogImagesUseCases.getDogPhotosFromApi()
                                 .subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::dogPhotosApiConsumer,::printErrorIfDebug)
        }
    }


    private fun getDogPhotos(){
        compositeDisposable.add(dogImagesUseCases.getDogPhotosFromApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::dogPhotosApiConsumer,::printErrorIfDebug)
        )
    }

    private fun dogPhotosApiConsumer(response:Response<ArrayList<DogPhoto>>){
        if (response.isSuccessful ){
            //todo check if body can be null
            response.body()?.let {body->
                val copy= _viewState.currState().copy()
                val tmpArrayList=ArrayList<DogPhoto>()
                tmpArrayList.addAll(copy.dogPhotos)
                tmpArrayList.addFilteredItems(body)
                copy.dogPhotos=tmpArrayList
                setMviValue(copy)
                cacheDogPhotos(copy.dogPhotos)
            }
        }
    }


    private fun cacheDogPhotos(dogPhotos: ArrayList<DogPhoto>){
        dogImagesUseCases.cacheDogImages(dogPhotos)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({},{ printIfDebug(it.message)})
    }

    fun loadMoreDogPhotos(){ getDogPhotos() }

    fun saveFavoriteDogPhoto(dogPhoto: DogPhoto){
        favoriteDogPhotosUseCases.saveFavoritePhoto(dogPhoto)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({isSaved->
                val copy=_viewState.currState().copy().favoriteDogPhotos
                val tmpArray=ArrayList<DogPhoto>()
                tmpArray.addAll(copy)
                tmpArray.add(dogPhoto)

                setMviValue(_viewState.currState().copy(favoriteDogPhotos = tmpArray))

                val text=   if(isSaved) { "Saved" }
                else{ "Already Saved" }

                _viewEvent.value=MainActivityEvents.ToastText(text)

            },::printErrorIfDebug)
    }

    fun getFavoriteDogPhotos(){
        //get favorite photos from db only if we haven't received it yet
        if (_viewState.currState().favoriteDogPhotos.isEmpty()){
        favoriteDogPhotosUseCases.getCachedDogPhotos()
            .filter{cachedPhotos->cachedPhotos.isNotEmpty()}
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({favoriteDogPhotos->
                println(favoriteDogPhotos.size)
                setMviValue(_viewState.currState().copy(favoriteDogPhotos =  favoriteDogPhotos))
            },::printErrorIfDebug)
        }
    }

    fun deleteFavoriteDogPhoto(photoId:String,position:Int) {
        favoriteDogPhotosUseCases.deleteFavoriteDogPhoto(photoId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val tmpArrayList=ArrayList<DogPhoto>()
                val copy=_viewState.currState().copy().favoriteDogPhotos
                tmpArrayList.addAll(copy)
                tmpArrayList.removeAt(position)
                setMviValue(_viewState.currState().copy(favoriteDogPhotos = tmpArrayList))
            },::printErrorIfDebug)
    }


}