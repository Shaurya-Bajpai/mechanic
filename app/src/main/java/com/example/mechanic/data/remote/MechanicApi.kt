package com.example.mechanic.data.remote

import com.example.mechanic.data.model.Mechanic
import retrofit2.http.GET

interface MechanicApi {

    @GET("mechanics")
    suspend fun getMechanics(): List<Mechanic>
}