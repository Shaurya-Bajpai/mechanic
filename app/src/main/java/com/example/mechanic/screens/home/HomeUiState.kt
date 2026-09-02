package com.example.mechanic.screens.home

import com.example.mechanic.data.model.Mechanic

data class HomeUiState(
    val isLoading: Boolean = false,
    val mechanics: List<Mechanic> = emptyList(),
    val errorMessage: String? = null
)