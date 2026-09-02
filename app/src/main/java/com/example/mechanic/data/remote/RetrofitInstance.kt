package com.example.mechanic.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://6a97eb4c7160beda22927f9b.mockapi.io/"

    val api: MechanicApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MechanicApi::class.java)
    }
}