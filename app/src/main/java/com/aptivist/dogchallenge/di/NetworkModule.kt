package com.aptivist.dogchallenge.di

import com.aptivist.dogchallenge.data.dogapi.DogAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun retrofitProvider() : Retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/breeds/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun dogAPIProvider(retrofit: Retrofit) : DogAPI = retrofit.create(DogAPI::class.java)

}