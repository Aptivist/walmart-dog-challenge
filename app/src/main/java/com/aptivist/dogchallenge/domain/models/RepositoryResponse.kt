package com.aptivist.dogchallenge.domain.models

sealed class RepositoryResponse<T> {
    data class Success<T>(val data : T) : RepositoryResponse<T>()
    data class Failed<T>(val errorMessage : String) : RepositoryResponse<T>()
}
