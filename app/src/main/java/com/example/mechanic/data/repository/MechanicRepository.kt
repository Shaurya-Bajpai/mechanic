package com.example.mechanic.data.repository

import com.example.mechanic.data.model.Mechanic
import com.example.mechanic.data.remote.MechanicApi

class MechanicRepository(
    private val api: MechanicApi
) {
    suspend fun getMechanics(): List<Mechanic> {
        return api.getMechanics()
    }
}