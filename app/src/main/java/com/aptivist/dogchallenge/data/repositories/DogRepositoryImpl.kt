package com.aptivist.dogchallenge.data.repositories

import com.aptivist.dogchallenge.data.dogapi.DogAPI
import com.aptivist.dogchallenge.data.mappers.toDomainDog
import com.aptivist.dogchallenge.domain.models.Dog
import com.aptivist.dogchallenge.domain.models.RepositoryResponse
import com.aptivist.dogchallenge.domain.repositories.IDogRepository
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(private val dogAPI: DogAPI) : IDogRepository {

    override suspend fun getRandomDog(): RepositoryResponse<Dog> {
        try {
            val response = dogAPI.getRandomImage()
            if(response.isSuccessful) {
                val dog = response.body()
                dog?.let { return RepositoryResponse.Success(it.toDomainDog()) }
                return RepositoryResponse.Failed("Response was empty")
            } else {
                return RepositoryResponse.Failed(response.errorBody()?.string() ?: "Network error")
            }
        } catch (e: Exception){
            return RepositoryResponse.Failed(e.localizedMessage ?: "Unknown error")
        }

    }

}