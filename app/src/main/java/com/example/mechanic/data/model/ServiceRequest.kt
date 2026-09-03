package com.example.mechanic.data.model

data class ServiceRequest(
    val customerName: String,
    val phoneNumber: String,
    val vehicleNumber: String,
    val service: String,
    val problemDescription: String
)