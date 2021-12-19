package com.e.learningcleanandroid.di.modules

import com.e.androidcleanarchitecture.di.scopes.ApplicationScope
import com.e.learningcleanandroid.api.IDogApi
import com.e.learningcleanandroid.utils.logs.printIfDebug
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class ApplicationModule {

 @ApplicationScope
 @Provides
 fun provideRetrofit(): Retrofit {
     return Retrofit.Builder()
         .baseUrl("https://api.thedogapi.com/")
         .addConverterFactory(GsonConverterFactory.create())
         .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
         .build()
 }

 @ApplicationScope
 @Provides
 fun provideTheDogApi(retrofit: Retrofit): IDogApi {
     return retrofit.create(IDogApi::class.java)
 }


}