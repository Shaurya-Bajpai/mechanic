package com.example.mechanic.data.model

data class MechanicDetailsUiState(
    val isLoading: Boolean = false,
    val mechanic: Mechanic? = null,
    val errorMessage: String? = null
)