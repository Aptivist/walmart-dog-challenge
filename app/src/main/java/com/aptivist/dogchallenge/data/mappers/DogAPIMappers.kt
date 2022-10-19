package com.aptivist.dogchallenge.data.mappers

import com.aptivist.dogchallenge.data.models.DogDTO
import com.aptivist.dogchallenge.domain.models.Dog

fun DogDTO.toDomainDog() : Dog {
    return Dog(this.message)
}