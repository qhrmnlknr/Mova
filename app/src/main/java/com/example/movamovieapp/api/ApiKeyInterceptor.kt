package com.example.movamovieapp.api

import com.example.movamovieapp.utils.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        return chain.proceed(newRequest)
    }
}