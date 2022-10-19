package com.aptivist.dogchallenge.data.dogapi

import com.aptivist.dogchallenge.data.models.DogDTO
import retrofit2.Response
import retrofit2.http.GET

interface DogAPI {

    @GET("image/random")
    suspend fun getRandomImage() : Response<DogDTO>

}