package com.example.mechanic.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MechanicDetailsViewModelFactory(private val mechanicId: String) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MechanicDetailsViewModel::class.java)) {
            return MechanicDetailsViewModel(mechanicId) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}