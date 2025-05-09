package com.example.musicapplicationse114.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class ApiRetrofit() {
    private val BASE_URL = "https://api.example.com/"

    val api : Api by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}