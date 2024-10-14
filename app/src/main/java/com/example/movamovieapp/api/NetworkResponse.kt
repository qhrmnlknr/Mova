package com.example.movamovieapp.api

sealed class NetworkResponse<T:Any> {

    class Success<T:Any>(val data: T) : NetworkResponse<T>()

    class Error<T:Any>(val message: String) : NetworkResponse<T>()

    class Loading<Nothing : Any> : NetworkResponse<Nothing>()


}