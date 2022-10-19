package com.aptivist.dogchallenge.di

import com.aptivist.dogchallenge.data.repositories.DogRepositoryImpl
import com.aptivist.dogchallenge.domain.repositories.IDogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideDogRepository(implementation : DogRepositoryImpl) : IDogRepository

}