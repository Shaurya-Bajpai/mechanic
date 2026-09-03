package com.example.mechanic.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mechanic.data.model.MechanicDetailsUiState
import com.example.mechanic.data.repository.MechanicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MechanicDetailsViewModel(
    private val repository: MechanicRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MechanicDetailsUiState())
    val uiState: StateFlow<MechanicDetailsUiState> = _uiState.asStateFlow()

    private var mechanicId: String = ""

    fun setMechanicId(id: String) {
        if (this.mechanicId != id) {
            this.mechanicId = id
            loadMechanic()
        }
    }

    private fun loadMechanic() {
        viewModelScope.launch {
            _uiState.value = MechanicDetailsUiState(isLoading = true)
            try {
                val mechanics = repository.getMechanics()
                val mechanic = mechanics.find { it.id == mechanicId }
                if (mechanic != null) {
                    _uiState.value = MechanicDetailsUiState(mechanic = mechanic)
                } else {
                    _uiState.value = MechanicDetailsUiState(errorMessage = "Mechanic not found")
                }
            } catch (e: Exception) {
                _uiState.value = MechanicDetailsUiState(errorMessage = e.message ?: "Unable to load mechanic")
            }
        }
    }
}