package com.example.mechanic.data.model

data class RequestServiceUiState(
    val customerName: String = "",
    val phoneNumber: String = "",
    val vehicleNumber: String = "",
    val selectedService: String = "",
    val problemDescription: String = "",
    val errorMessage: String? = null,
    val isSubmitted: Boolean = false
)