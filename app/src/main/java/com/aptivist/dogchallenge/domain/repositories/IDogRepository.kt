package com.aptivist.dogchallenge.domain.repositories

import com.aptivist.dogchallenge.domain.models.Dog
import com.aptivist.dogchallenge.domain.models.RepositoryResponse

interface IDogRepository {

    suspend fun getRandomDog() : RepositoryResponse<Dog>

}